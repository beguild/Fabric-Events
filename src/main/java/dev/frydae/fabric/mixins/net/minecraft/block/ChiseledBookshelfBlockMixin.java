package dev.frydae.fabric.mixins.net.minecraft.block;

import com.llamalad7.mixinextras.sugar.Local;
import dev.frydae.fabric.events.container.bookshelf.ChiseledBookshelfPlaceEvent;
import dev.frydae.fabric.events.container.bookshelf.ChiseledBookshelfRemoveEvent;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChiseledBookshelfBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChiseledBookshelfBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChiseledBookshelfBlock.class)
public class ChiseledBookshelfBlockMixin {
    @Inject(
            method = "onUse",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/block/ChiseledBookshelfBlock;tryRemoveBook(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/block/entity/ChiseledBookshelfBlockEntity;I)V"),
            cancellable = true
    )
    public void onRemoveBook(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir, @Local int slot) {
        BlockEntity blockEntity = world.getBlockEntity(pos);

        if (player instanceof ServerPlayerEntity serverPlayer && blockEntity instanceof ChiseledBookshelfBlockEntity chiseledBookshelfBlockEntity) {
            ItemStack book = chiseledBookshelfBlockEntity.getStack(slot);

            ChiseledBookshelfRemoveEvent event = new ChiseledBookshelfRemoveEvent(serverPlayer, chiseledBookshelfBlockEntity, slot, book);

            event.callEvent();

            if (event.isCancelled()) {
                cir.setReturnValue(ActionResult.CONSUME);
            }
        }
    }

    @Inject(
            method = "onUse",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/block/ChiseledBookshelfBlock;tryAddBook(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/block/entity/ChiseledBookshelfBlockEntity;Lnet/minecraft/item/ItemStack;I)V"),
            cancellable = true
    )
    public void onPlaceBook(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir, @Local int slot) {
        BlockEntity blockEntity = world.getBlockEntity(pos);

        if (player instanceof ServerPlayerEntity serverPlayer && blockEntity instanceof ChiseledBookshelfBlockEntity chiseledBookshelfBlockEntity) {
            ItemStack book = player.getStackInHand(hand);

            ChiseledBookshelfPlaceEvent event = new ChiseledBookshelfPlaceEvent(serverPlayer, chiseledBookshelfBlockEntity, slot, book);

            event.callEvent();

            if (event.isCancelled()) {
                cir.setReturnValue(ActionResult.CONSUME);
            }
        }
    }
}
