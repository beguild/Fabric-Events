package dev.frydae.fabric.mixins.net.minecraft.block;

import dev.frydae.fabric.events.container.open.PlayerOpenShulkerBoxEvent;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
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

@Mixin(ShulkerBoxBlock.class)
public class ShulkerBoxMixin {
    @Inject(
            method = "onUse",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;openHandledScreen(Lnet/minecraft/screen/NamedScreenHandlerFactory;)Ljava/util/OptionalInt;"),
            cancellable = true
    )
    public void onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir) {
        BlockEntity blockEntity = world.getBlockEntity(pos);

        if (player instanceof ServerPlayerEntity serverPlayer && blockEntity instanceof ShulkerBoxBlockEntity shulkerBoxBlockEntity) {
            PlayerOpenShulkerBoxEvent event = new PlayerOpenShulkerBoxEvent(serverPlayer, shulkerBoxBlockEntity);

            event.callEvent();

            if (event.isCancelled()) {
                cir.setReturnValue(ActionResult.CONSUME);
            }
        }
    }
}
