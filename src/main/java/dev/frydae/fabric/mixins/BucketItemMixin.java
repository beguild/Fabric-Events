package dev.frydae.fabric.mixins;

import dev.frydae.fabric.events.player.PlayerBucketEmptyEvent;
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

        PlayerBucketEmptyEvent event = new PlayerBucketEmptyEvent((ServerPlayerEntity) player, world.getBlockState(pos).getBlock(), bucket.getItem(), bucket, player.getActiveHand());

        event.callEvent();

        if (event.isCancelled()) {
            cir.setReturnValue(false);
        }
    }
}
