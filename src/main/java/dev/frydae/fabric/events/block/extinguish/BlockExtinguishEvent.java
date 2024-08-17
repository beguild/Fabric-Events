package dev.frydae.fabric.events.block.extinguish;

import dev.frydae.fabric.utils.Location;
import dev.frydae.fabric.events.Cancellable;
import dev.frydae.fabric.events.player.PlayerEvent;
import lombok.Getter;
import net.minecraft.server.network.ServerPlayerEntity;

@Getter
public class BlockExtinguishEvent extends PlayerEvent implements Cancellable {
    private final Location location;

    private boolean cancelled = false;

    public BlockExtinguishEvent(ServerPlayerEntity player, Location location) {
        super(player);

        this.location = location;
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
