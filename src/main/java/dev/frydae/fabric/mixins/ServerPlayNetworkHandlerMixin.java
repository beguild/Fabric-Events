package dev.frydae.fabric.mixins;

import com.llamalad7.mixinextras.sugar.Local;
import dev.frydae.fabric.events.player.PlayerDisconnectMessageEvent;
import dev.frydae.fabric.events.player.PlayerMoveEvent;
import dev.frydae.fabric.util.Location;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(ServerPlayNetworkHandler.class)
public class ServerPlayNetworkHandlerMixin {
    @Shadow public ServerPlayerEntity player;

    @Inject(
            method = "onPlayerMove",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayerEntity;move(Lnet/minecraft/entity/MovementType;Lnet/minecraft/util/math/Vec3d;)V"),
            cancellable = true
    )
    public void move(PlayerMoveC2SPacket packet, CallbackInfo ci,
                     @Local(ordinal = 0) double x, @Local(ordinal = 1) double y, @Local(ordinal = 2) double z) {
        int oldX = (int) player.getX();
        int oldY = (int) player.getY();
        int oldZ = (int) player.getZ();

        int newX = (int) x; // field d in mixin'd method
        int newY = (int) y; // field e in mixin'd method
        int newZ = (int) z; // field f in mixin'd method

        if (!Objects.equals(oldX, newX) || !Objects.equals(oldY, newY) || !Objects.equals(oldZ, newZ)) {
            PlayerMoveEvent event = new PlayerMoveEvent(player, new Location(player.getServerWorld(), oldX, oldY, oldZ), new Location(player.getServerWorld(), newX, newY, newZ));

            event.callEvent();

            if (event.isCancelled()) {
                ci.cancel();

                player.teleport(player.getX(), player.getY(), player.getZ());
            }
        }
    }

    @Redirect(
            method = "cleanUp",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/server/PlayerManager;broadcast(Lnet/minecraft/text/Text;Z)V"))
    public void broadcast(PlayerManager instance, Text message, boolean overlay) {
        PlayerDisconnectMessageEvent messageEvent = new PlayerDisconnectMessageEvent(player, message);

        messageEvent.callEvent();

        if (!messageEvent.isCancelled()) {
            instance.broadcast(messageEvent.getMessage(), overlay);
        }
    }
}
