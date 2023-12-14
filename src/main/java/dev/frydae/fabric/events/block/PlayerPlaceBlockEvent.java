package dev.frydae.fabric.events.block;

import lombok.Getter;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Getter
public class PlayerPlaceBlockEvent extends PlayerBlockEvent {
    private final ItemStack stack;

    public PlayerPlaceBlockEvent(ServerPlayerEntity player, World world, BlockPos blockPos, BlockState blockState, ItemStack stack) {
        super(player, world, blockPos, blockState);

        this.stack = stack;
    }
}
