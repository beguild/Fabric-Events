package dev.frydae.fabric.mixins.block;

import dev.frydae.fabric.events.container.PlayerOpenLecternEvent;
import dev.frydae.fabric.events.container.PlayerPlaceLecternEvent;
import net.minecraft.block.BlockState;
import net.minecraft.block.LecternBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.LecternBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LecternBlock.class)
public class LecternBlockMixin {
    @Inject(
            method = "openScreen",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;openHandledScreen(Lnet/minecraft/screen/NamedScreenHandlerFactory;)Ljava/util/OptionalInt;"),
            cancellable = true
    )
    public void onUse(World world, BlockPos pos, PlayerEntity player, CallbackInfo ci) {
        BlockEntity blockEntity = world.getBlockEntity(pos);

        if (player instanceof ServerPlayerEntity serverPlayer && blockEntity instanceof LecternBlockEntity lecternBlockEntity) {
            PlayerOpenLecternEvent event = new PlayerOpenLecternEvent(serverPlayer, lecternBlockEntity);

            event.callEvent();

            if (event.isCancelled()) {
                ci.cancel();
            }
        }
    }

    @Inject(
            method = "putBook",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/block/entity/LecternBlockEntity;setBook(Lnet/minecraft/item/ItemStack;)V"),
            cancellable = true
    )
    private static void putBook(Entity user, World world, BlockPos pos, BlockState state, ItemStack stack, CallbackInfo ci) {
        BlockEntity blockEntity = world.getBlockEntity(pos);

        if (user instanceof ServerPlayerEntity serverPlayer && blockEntity instanceof LecternBlockEntity) {
            PlayerPlaceLecternEvent event = new PlayerPlaceLecternEvent(serverPlayer, stack);

            event.callEvent();

            if (event.isCancelled()) {
                ci.cancel();
            }
        }
    }
}
