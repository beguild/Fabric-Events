package dev.frydae.fabric.events.player;

import net.minecraft.server.network.ServerPlayerEntity;

public class PlayerLeaveEvent extends PlayerEvent {
    public PlayerLeaveEvent(ServerPlayerEntity player) {
        super(player);
    }
}
