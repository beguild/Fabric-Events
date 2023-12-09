package dev.frydae.fabric.events.player;

import dev.frydae.fabric.events.Cancellable;
import net.minecraft.block.BlockState;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public class PlayerBreakBlockEvent extends PlayerEvent implements Cancellable {
    private final ServerWorld world;
    private final BlockPos pos;
    private final BlockState block;

    private boolean cancelled = false;

    public PlayerBreakBlockEvent(ServerPlayerEntity player, ServerWorld world, BlockPos pos, BlockState block) {
        super(player);

        this.world = world;
        this.pos = pos;
        this.block = block;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled() {
        this.cancelled = true;
    }

    public ServerWorld getWorld() {
        return world;
    }

    public BlockPos getPos() {
        return pos;
    }

    public BlockState getBlock() {
        return block;
    }
}
