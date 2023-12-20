package dev.frydae.fabric.events.block;

import dev.frydae.beguild.utils.Location;
import dev.frydae.fabric.events.Cancellable;
import dev.frydae.fabric.events.player.PlayerEvent;
import lombok.Getter;
import net.minecraft.block.BlockState;
import net.minecraft.server.network.ServerPlayerEntity;

@Getter
public abstract class PlayerBlockEvent extends PlayerEvent implements Cancellable {
    private final Location location;
    private final BlockState blockState;
    private boolean cancelled = false;

    public PlayerBlockEvent(ServerPlayerEntity player, Location location, BlockState blockState) {
        super(player);

        this.location = location;
        this.blockState = blockState;
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
