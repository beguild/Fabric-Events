package dev.frydae.fabric.events.block;

import dev.frydae.fabric.events.Cancellable;
import dev.frydae.fabric.events.player.PlayerEvent;
import lombok.Getter;
import net.minecraft.block.BlockState;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Getter
public abstract class PlayerBlockEvent extends PlayerEvent implements Cancellable {
    private final World world;
    private final BlockPos blockPos;
    private final BlockState blockState;
    private boolean cancelled = false;

    public PlayerBlockEvent(ServerPlayerEntity player, World world, BlockPos blockPos, BlockState blockState) {
        super(player);

        this.world = world;
        this.blockPos = blockPos;
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
