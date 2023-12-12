package dev.frydae.fabric.mixins;

import dev.frydae.fabric.events.player.PlayerBucketFillEvent;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FluidBlock.class)
public class FluidBlockMixin {
    @Inject(
            method = "tryDrainFluid",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/WorldAccess;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z"),
            cancellable = true
    )
    public void onTryDrainFluid(PlayerEntity player, WorldAccess world, BlockPos pos, BlockState state, CallbackInfoReturnable<ItemStack> cir) {
        ItemStack bucket = player.getMainHandStack().isOf(Items.BUCKET) ? player.getMainHandStack() : (player.getOffHandStack().isOf(Items.BUCKET) ? player.getOffHandStack() : null);

        if (bucket != null) {
            PlayerBucketFillEvent event = new PlayerBucketFillEvent((ServerPlayerEntity) player, state.getBlock(), bucket.getItem(), bucket, player.getActiveHand());

            event.callEvent();

            if (event.isCancelled()) {
                cir.setReturnValue(ItemStack.EMPTY);
            }
        }
    }
}
