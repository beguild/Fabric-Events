package dev.frydae.fabric.mixins.net.minecraft.block;

import dev.frydae.fabric.events.container.open.PlayerOpenContainerEvent;
import dev.frydae.fabric.events.container.open.PlayerOpenDispenserEvent;
import dev.frydae.fabric.events.container.open.PlayerOpenDropperEvent;
import net.minecraft.block.BlockState;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.block.entity.DropperBlockEntity;
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

@Mixin(DispenserBlock.class)
public class DispenserBlockMixin {
    @Inject(
            method = "onUse",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;openHandledScreen(Lnet/minecraft/screen/NamedScreenHandlerFactory;)Ljava/util/OptionalInt;"),
            cancellable = true
    )
    public void onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir) {
        BlockEntity blockEntity = world.getBlockEntity(pos);

        // A dispenser can be both a dropper and a dispenser, so we need to check which one it is.
        if (player instanceof ServerPlayerEntity serverPlayer) {
            if (blockEntity instanceof DispenserBlockEntity dispenserBlockEntity) {
                PlayerOpenContainerEvent event;

                if (blockEntity instanceof DropperBlockEntity dropperBlockEntity) {
                    event = new PlayerOpenDropperEvent(serverPlayer, dropperBlockEntity);
                } else {
                    event = new PlayerOpenDispenserEvent(serverPlayer, dispenserBlockEntity);
                }

                event.callEvent();

                if (event.isCancelled()) {
                    cir.setReturnValue(ActionResult.CONSUME);
                }
            }
        }
    }
}
