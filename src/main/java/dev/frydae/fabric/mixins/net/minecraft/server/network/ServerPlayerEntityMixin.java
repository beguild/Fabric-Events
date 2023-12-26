package dev.frydae.fabric.mixins.net.minecraft.server.network;

import dev.frydae.fabric.events.player.PlayerDropItemEvent;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.ScreenHandlerSlotUpdateS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin {
    @Inject(method = "dropSelectedItem", at = @At("HEAD"), cancellable = true)
    public void onDropSelectedItem(boolean entireStack, CallbackInfoReturnable<Boolean> cir) {
        ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;
        ItemStack stack = player.getMainHandStack();

        if (!stack.isEmpty()) {
            PlayerDropItemEvent event = new PlayerDropItemEvent(player, stack);

            event.callEvent();

            if (event.isCancelled()) {
                cir.setReturnValue(false);
                player.networkHandler.sendPacket(new ScreenHandlerSlotUpdateS2CPacket(-2, 0, player.getInventory().getSlotWithStack(stack), stack));
            }
        }
    }

    @Inject(
            method = "dropItem",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    public void onDropItem(ItemStack stack, boolean throwRandomly, boolean retainOwnership, CallbackInfoReturnable<ItemEntity> cir) {
        ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;

        if (!stack.isEmpty()) {
            PlayerDropItemEvent event = new PlayerDropItemEvent(player, stack);

            event.callEvent();

            if (event.isCancelled()) {
                cir.setReturnValue(null);

                int slotWithStack = player.getInventory().getSlotWithStack(stack);

                if (slotWithStack >= 0) {
                    player.networkHandler.sendPacket(new ScreenHandlerSlotUpdateS2CPacket(-2, 0, slotWithStack, stack));
                } else {
                    player.getInventory().insertStack(stack);
                    player.currentScreenHandler.updateToClient();
                }
            }
        }
    }
}
