package dev.frydae.fabric.events.player;

import dev.frydae.fabric.events.Cancellable;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;

public class PlayerSpawnPacketFilterEvent extends PlayerEvent implements Cancellable {
    private final EntitySpawnS2CPacket packet;
    private boolean cancelled = false;

    public PlayerSpawnPacketFilterEvent(ServerPlayerEntity player, EntitySpawnS2CPacket packet) {
        super(player);

        this.packet = packet;
    }

    public EntitySpawnS2CPacket getPacket() {
        return this.packet;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled() {
        this.cancelled = true;
    }
}
