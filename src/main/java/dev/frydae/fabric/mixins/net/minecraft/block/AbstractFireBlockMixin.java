package dev.frydae.fabric.mixins.net.minecraft.block;

import dev.frydae.fabric.utils.Location;
import dev.frydae.fabric.events.block.BlockEvents;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractFireBlock.class)
public class AbstractFireBlockMixin {
    @Inject(
            method = "onBreak",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;onBreak(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;Lnet/minecraft/entity/player/PlayerEntity;)Lnet/minecraft/block/BlockState;"),
            cancellable = true
    )
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player, CallbackInfoReturnable<BlockState> cir) {
        boolean failure = !BlockEvents.callBreakBlockEvent((ServerPlayerEntity) player, (ServerWorld) world, pos, state);

        failure |= !BlockEvents.callExtinguishEvent((ServerPlayerEntity) player, new Location(world, pos));

        if (failure) {
            cir.setReturnValue(null);
        }
    }
}
