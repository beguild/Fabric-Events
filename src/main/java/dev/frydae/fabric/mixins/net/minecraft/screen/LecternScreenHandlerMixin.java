package dev.frydae.fabric.mixins.net.minecraft.screen;

import dev.frydae.fabric.events.container.PlayerTakeLecternEvent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.LecternScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LecternScreenHandler.class)
public class LecternScreenHandlerMixin {
    @Shadow @Final private Inventory inventory;

    @Inject(
            method = "onButtonClick",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/inventory/Inventory;removeStack(I)Lnet/minecraft/item/ItemStack;"),
            cancellable = true
    )
    public void onButtonClick(PlayerEntity player, int id, CallbackInfoReturnable<Boolean> cir) {
        if (player instanceof ServerPlayerEntity serverPlayer) {
            ItemStack stack = this.inventory.getStack(0);

            PlayerTakeLecternEvent event = new PlayerTakeLecternEvent(serverPlayer, stack);

            event.callEvent();

            if (event.isCancelled()) {
                cir.setReturnValue(false);
                event.getPlayer().closeHandledScreen();
            }
        }
    }
}
