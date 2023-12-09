package dev.frydae.fabric.events.player;

import dev.frydae.fabric.events.Event;
import net.minecraft.server.network.ServerPlayerEntity;

public class PlayerEvent extends Event {
    private final ServerPlayerEntity player;

    public PlayerEvent(ServerPlayerEntity player) {
        this.player = player;
    }

    public ServerPlayerEntity getPlayer() {
        return player;
    }
}
