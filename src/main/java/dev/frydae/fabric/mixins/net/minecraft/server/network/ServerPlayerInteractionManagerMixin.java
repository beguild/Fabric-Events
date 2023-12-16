package dev.frydae.fabric.mixins.net.minecraft.server.network;

import com.llamalad7.mixinextras.sugar.Local;
import dev.frydae.fabric.events.block.PlayerBreakBlockEvent;
import dev.frydae.fabric.events.block.PlayerPlaceBlockEvent;
import dev.frydae.fabric.events.player.PlayerUseItemEvent;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerInteractionManager.class)
public class ServerPlayerInteractionManagerMixin {
    @Shadow protected ServerWorld world;
    @Shadow @Final protected ServerPlayerEntity player;

    @Inject(
        method = "tryBreakBlock",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;onBreak(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;Lnet/minecraft/entity/player/PlayerEntity;)V"),
        cancellable = true
    )
    public void onBlockBreak(BlockPos pos, CallbackInfoReturnable<Boolean> cir, @Local(ordinal = 0) BlockState blockState) {
        PlayerBreakBlockEvent event = new PlayerBreakBlockEvent(this.player, this.world, pos, blockState);

        event.callEvent();

        if (event.isCancelled()) {
            cir.setReturnValue(false);
        }
    }

    @Inject(
            method = "interactBlock",
            at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/item/ItemStack;copy()Lnet/minecraft/item/ItemStack;"),
            cancellable = true
    )
    public void onBlockPlace(ServerPlayerEntity player, World world, ItemStack stack, Hand hand, BlockHitResult hitResult, CallbackInfoReturnable<ActionResult> cir) {
        PlayerPlaceBlockEvent event = new PlayerPlaceBlockEvent(player, world, hitResult.getBlockPos(), world.getBlockState(hitResult.getBlockPos()), stack);

        event.callEvent();

        if (event.isCancelled()) {
            cir.setReturnValue(ActionResult.FAIL);
        }
    }

    @Inject(
            method = "interactItem",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;use(Lnet/minecraft/world/World;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/TypedActionResult;"),
            cancellable = true
    )
    public void onUseItem(ServerPlayerEntity player, World world, ItemStack stack, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        PlayerUseItemEvent event = new PlayerUseItemEvent(player, world, stack, hand);

        event.callEvent();

        if (event.isCancelled()) {
            cir.setReturnValue(ActionResult.FAIL);
        }
    }
}
