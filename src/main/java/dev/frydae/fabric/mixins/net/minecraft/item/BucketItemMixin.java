package dev.frydae.fabric.mixins.net.minecraft.item;

import dev.frydae.fabric.utils.Location;
import dev.frydae.fabric.events.block.BlockEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BucketItem.class)
public class BucketItemMixin {
    @Inject(
            method = "placeFluid",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z"),
            cancellable = true)
    public void onPlaceFluid(PlayerEntity player, World world, BlockPos pos, BlockHitResult hitResult, CallbackInfoReturnable<Boolean> cir) {
        ItemStack bucket;

        if (player.getMainHandStack().isOf(Items.WATER_BUCKET) || player.getMainHandStack().isOf(Items.LAVA_BUCKET)) {
            bucket = player.getStackInHand(Hand.MAIN_HAND);
        } else if (player.getOffHandStack().isOf(Items.WATER_BUCKET) || player.getOffHandStack().isOf(Items.LAVA_BUCKET)) {
            bucket = player.getStackInHand(Hand.OFF_HAND);
        } else {
            bucket = null;
        }

        Block block = world.getBlockState(pos).getBlock();

        boolean failure = !BlockEvents.callBucketEmptyEvent((ServerPlayerEntity) player, block, bucket, player.getActiveHand());

        failure |= !BlockEvents.callPlaceBlockEvent((ServerPlayerEntity) player, world, pos, world.getBlockState(pos), bucket);

        if (failure) {
            cir.setReturnValue(false);
        }
    }

    @Inject(
            method = "placeFluid",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/block/FluidFillable;tryFillWithFluid(Lnet/minecraft/world/WorldAccess;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;Lnet/minecraft/fluid/FluidState;)Z"),
            cancellable = true
    )
    public void onWaterlog(PlayerEntity player, World world, BlockPos pos, BlockHitResult hitResult, CallbackInfoReturnable<Boolean> cir) {
        BlockState blockState = world.getBlockState(pos);
        Block block = blockState.getBlock();
        Location location = new Location(world, pos);

        // Check if you're allowed to empty a bucket
        boolean failure = !BlockEvents.callBucketEmptyEvent((ServerPlayerEntity) player, block, player.getStackInHand(player.getActiveHand()), player.getActiveHand());

        // Check if you're allowed to waterlog a block
        failure |= !BlockEvents.callWaterlogEvent((ServerPlayerEntity) player, location);

        // Check if you're allowed to place a block
        failure |= !BlockEvents.callPlaceBlockEvent((ServerPlayerEntity) player, world, pos, blockState, player.getStackInHand(player.getActiveHand()));

        // Check if you're allowed to extinguish a campfire
        if (blockState.isOf(Blocks.CAMPFIRE) || blockState.isOf(Blocks.SOUL_CAMPFIRE)) {
            if (blockState.get(CampfireBlock.LIT)) {
                failure |= !BlockEvents.callCampfireExtinguishEvent((ServerPlayerEntity) player, location);
            }
        }

        if (failure) {
            cir.setReturnValue(false);
        }
    }
}
