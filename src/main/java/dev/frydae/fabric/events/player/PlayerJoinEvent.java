package dev.frydae.fabric.events.player;

import net.minecraft.server.network.ServerPlayerEntity;

public class PlayerJoinEvent extends PlayerEvent {
    public PlayerJoinEvent(ServerPlayerEntity player) {
        super(player);
    }
}
