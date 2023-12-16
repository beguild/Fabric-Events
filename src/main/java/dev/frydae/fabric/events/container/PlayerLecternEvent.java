package dev.frydae.fabric.events.container;

import dev.frydae.fabric.events.Cancellable;
import dev.frydae.fabric.events.player.PlayerEvent;
import lombok.Getter;
import net.minecraft.server.network.ServerPlayerEntity;

@Getter
public abstract class PlayerLecternEvent extends PlayerEvent implements Cancellable {
    private boolean cancelled = false;

    public PlayerLecternEvent(ServerPlayerEntity player) {
        super(player);
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled() {
        this.cancelled = true;
    }
}
