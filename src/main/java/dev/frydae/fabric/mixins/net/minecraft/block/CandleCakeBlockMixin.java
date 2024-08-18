package dev.frydae.fabric.mixins.net.minecraft.block;

import dev.frydae.fabric.utils.Location;
import dev.frydae.fabric.events.block.BlockEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.CandleCakeBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CandleCakeBlock.class)
public class CandleCakeBlockMixin {
    @Inject(
            method = "onUseWithItem",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/block/CandleCakeBlock;extinguish(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/block/BlockState;Lnet/minecraft/world/WorldAccess;Lnet/minecraft/util/math/BlockPos;)V"),
            cancellable = true
    )
    public void onUse(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ItemActionResult> cir) {
        boolean failure = !BlockEvents.callCandleCakeBlockExtinguishEvent((ServerPlayerEntity) player, new Location(world, pos));

        if (failure) {
            cir.setReturnValue(ItemActionResult.FAIL);
        }
    }
}
