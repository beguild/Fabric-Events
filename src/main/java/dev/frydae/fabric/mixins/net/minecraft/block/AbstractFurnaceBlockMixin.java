package dev.frydae.fabric.mixins.net.minecraft.block;

import dev.frydae.fabric.events.container.open.PlayerOpenBlastFurnaceEvent;
import dev.frydae.fabric.events.container.open.PlayerOpenContainerEvent;
import dev.frydae.fabric.events.container.open.PlayerOpenFurnaceEvent;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlastFurnaceBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.FurnaceBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
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

@Mixin(AbstractFurnaceBlock.class)
public class AbstractFurnaceBlockMixin {
    @Inject(
            method = "onUse",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/block/AbstractFurnaceBlock;openScreen(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/entity/player/PlayerEntity;)V"),
            cancellable = true
    )
    public void onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir) {
        BlockEntity blockEntity = world.getBlockEntity(pos);

        if (player instanceof ServerPlayerEntity serverPlayer) {
            PlayerOpenContainerEvent event = null;

            if (blockEntity instanceof FurnaceBlockEntity furnaceBlockEntity) {
                event = new PlayerOpenFurnaceEvent(serverPlayer, furnaceBlockEntity);
            } else if (blockEntity instanceof BlastFurnaceBlockEntity blastFurnaceBlockEntity) {
                event = new PlayerOpenBlastFurnaceEvent(serverPlayer, blastFurnaceBlockEntity);
            }

            if (event != null) {
                event.callEvent();

                if (event.isCancelled()) {
                    cir.setReturnValue(ActionResult.CONSUME);
                }
            }
        }
    }
}
