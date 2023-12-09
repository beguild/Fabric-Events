package dev.frydae.fabric.events.player;

import dev.frydae.fabric.events.Cancellable;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PlayerPlaceBlockEvent extends PlayerEvent implements Cancellable {
    private final World world;
    private final BlockPos blockPos;
    private final BlockState blockState;
    private final ItemStack stack;

    private boolean cancelled = false;

    public PlayerPlaceBlockEvent(ServerPlayerEntity player, World world, BlockPos blockPos, BlockState blockState, ItemStack stack) {
        super(player);

        this.world = world;
        this.blockPos = blockPos;
        this.blockState = blockState;
        this.stack = stack;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled() {
        this.cancelled = true;
    }

    public World getWorld() {
        return world;
    }

    public BlockPos getBlockPos() {
        return blockPos;
    }

    public BlockState getBlockState() {
        return blockState;
    }

    public ItemStack getStack() {
        return stack;
    }
}
