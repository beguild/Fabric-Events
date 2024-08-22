package dev.frydae.fabric.mixins.net.minecraft.block;

import dev.frydae.fabric.events.container.jukebox.JukeboxPlaceEvent;
import dev.frydae.fabric.events.container.jukebox.JukeboxTakeEvent;
import net.minecraft.block.BlockState;
import net.minecraft.block.JukeboxBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.JukeboxBlockEntity;
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

@Mixin(JukeboxBlock.class)
public class JukeboxBlockMixin {
    @Inject(
            method = "onUse",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/block/entity/JukeboxBlockEntity;dropRecord()V"),
            cancellable = true
    )
    public void onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir) {
        BlockEntity blockEntity = world.getBlockEntity(pos);

        if (player instanceof ServerPlayerEntity serverPlayer && blockEntity instanceof JukeboxBlockEntity jukeboxBlockEntity) {
            ItemStack discStack = jukeboxBlockEntity.getStack(0);

            JukeboxTakeEvent event = new JukeboxTakeEvent(serverPlayer, jukeboxBlockEntity, discStack);

            event.callEvent();

            if (event.isCancelled()) {
                cir.setReturnValue(ActionResult.CONSUME);
            }
        }
    }

    @Inject(
            method = "onUseWithItem",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/component/type/JukeboxPlayableComponent;tryPlayStack(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/player/PlayerEntity;)Lnet/minecraft/util/ItemActionResult;"),
            cancellable = true
    )
    public void onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ItemActionResult> cir) {
        BlockEntity blockEntity = world.getBlockEntity(pos);

        if (player instanceof ServerPlayerEntity serverPlayer && blockEntity instanceof JukeboxBlockEntity jukeboxBlockEntity) {
            ItemStack discStack = player.getStackInHand(hand);

            JukeboxPlaceEvent event = new JukeboxPlaceEvent(serverPlayer, jukeboxBlockEntity, discStack);

            event.callEvent();

            if (event.isCancelled()) {
                cir.setReturnValue(ItemActionResult.CONSUME);
            }
        }
    }
}
