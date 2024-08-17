package dev.frydae.fabric.mixins.net.minecraft.server.network;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import dev.frydae.fabric.events.player.PlayerSpawnPacketFilterEvent;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.server.network.EntityTrackerEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.Consumer;

@Mixin(EntityTrackerEntry.class)
public class EntityTrackerEntryMixin {
    @WrapWithCondition(
            method = "sendPackets",
            at = @At(value = "INVOKE", target = "Ljava/util/function/Consumer;accept(Ljava/lang/Object;)V", ordinal = 0))
    public boolean shouldSendSpawnPacket(Consumer<Packet<ClientPlayPacketListener>> instance, Object o, ServerPlayerEntity player, Consumer<Packet<ClientPlayPacketListener>> sender) {
        if (o instanceof EntitySpawnS2CPacket packet) {
            PlayerSpawnPacketFilterEvent event = new PlayerSpawnPacketFilterEvent(player, packet);

            event.callEvent();

            return !event.isCancelled();
        }

        return true;
    }
}
