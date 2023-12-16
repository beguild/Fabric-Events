package dev.frydae.fabric.mixins.net.minecraft.server;

import dev.frydae.fabric.events.player.PlayerJoinMessageEvent;
import net.minecraft.network.ClientConnection;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

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
}
