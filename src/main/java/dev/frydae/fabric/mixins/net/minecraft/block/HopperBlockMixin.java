package dev.frydae.fabric.mixins.net.minecraft.block;

import dev.frydae.fabric.events.container.open.PlayerOpenHopperEvent;
import net.minecraft.block.BlockState;
import net.minecraft.block.HopperBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.HopperBlockEntity;
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

@Mixin(HopperBlock.class)
public class HopperBlockMixin {
    @Inject(
            method = "onUse",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;openHandledScreen(Lnet/minecraft/screen/NamedScreenHandlerFactory;)Ljava/util/OptionalInt;"),
            cancellable = true
    )
    public void onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir) {
        BlockEntity blockEntity = world.getBlockEntity(pos);

        if (player instanceof ServerPlayerEntity serverPlayer && blockEntity instanceof HopperBlockEntity hopperBlockEntity) {
            PlayerOpenHopperEvent event = new PlayerOpenHopperEvent(serverPlayer, hopperBlockEntity);

            event.callEvent();

            if (event.isCancelled()) {
                cir.setReturnValue(ActionResult.CONSUME);
            }
        }
    }
}
