package dev.frydae.fabric.events.block;

import net.minecraft.block.BlockState;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public class PlayerBreakBlockEvent extends PlayerBlockEvent {
    public PlayerBreakBlockEvent(ServerPlayerEntity player, ServerWorld world, BlockPos pos, BlockState block) {
        super(player, world, pos, block);
    }
}
