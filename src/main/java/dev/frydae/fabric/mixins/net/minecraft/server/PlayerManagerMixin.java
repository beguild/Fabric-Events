package dev.frydae.fabric.mixins.net.minecraft.server;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import dev.frydae.fabric.events.FabricEvents;
import dev.frydae.fabric.events.player.PlayerJoinEvent;
import dev.frydae.fabric.events.player.PlayerJoinMessageEvent;
import dev.frydae.fabric.events.player.PlayerLeaveEvent;
import dev.frydae.fabric.events.player.PlayerListFilterEvent;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.PlayerListS2CPacket;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ConnectedClientData;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.UUID;

@Mixin(PlayerManager.class)
public class PlayerManagerMixin {
    @Shadow @Final private List<ServerPlayerEntity> players;

    @Redirect(
            method = "onPlayerConnect",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/server/PlayerManager;broadcast(Lnet/minecraft/text/Text;Z)V"))
    public void broadcast(PlayerManager instance, Text message, boolean overlay, ClientConnection connection, ServerPlayerEntity player) {
        PlayerJoinMessageEvent messageEvent = new PlayerJoinMessageEvent(player, message);

        messageEvent.callEvent();

        if (!messageEvent.isCancelled()) {
            instance.broadcast(messageEvent.getMessage(), overlay);
        }
    }

    @Inject(method = "onPlayerConnect", at = @At(value = "TAIL"))
    public void handlePlayerJoin(ClientConnection connection, ServerPlayerEntity player, ConnectedClientData clientData, CallbackInfo ci) {
        PlayerJoinEvent event = new PlayerJoinEvent(player);

        event.callEvent();
    }

    @Inject(method = "remove", at = @At(value = "TAIL"))
    public void handlePlayerLeave(ServerPlayerEntity player, CallbackInfo ci) {
        PlayerLeaveEvent event = new PlayerLeaveEvent(player);

        event.callEvent();
    }

    @WrapWithCondition(
            method = "onPlayerConnect",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/server/PlayerManager;sendToAll(Lnet/minecraft/network/packet/Packet;)V"))
    public boolean sendToAllRedirect(PlayerManager instance, Packet<?> packet) {
        // This method replaces the one that sends player list entries to everyone

        if (packet instanceof PlayerListS2CPacket newPacket) {
            UUID uuid = newPacket.getEntries().get(0).profileId();
            ServerPlayerEntity playerToSend = FabricEvents.getServer().getPlayerManager().getPlayer(uuid);

            PlayerListFilterEvent event = new PlayerListFilterEvent(newPacket, playerToSend, players);

            event.callEvent();

            event.getPlayersToSendTo().forEach(p -> p.networkHandler.sendPacket(newPacket));

            return false;
        }

        return true;
    }
}
