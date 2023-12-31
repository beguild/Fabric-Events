package dev.frydae.fabric.mixins.net.minecraft.server;

import dev.frydae.fabric.events.player.PlayerJoinEvent;
import dev.frydae.fabric.events.player.PlayerJoinMessageEvent;
import dev.frydae.fabric.events.player.PlayerLeaveEvent;
import net.minecraft.network.ClientConnection;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ConnectedClientData;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerManager.class)
public class PlayerManagerMixin {
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
}
