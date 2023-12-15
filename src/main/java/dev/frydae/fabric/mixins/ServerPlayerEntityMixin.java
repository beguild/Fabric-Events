package dev.frydae.fabric.mixins;

import dev.frydae.fabric.events.player.PlayerDropItemEvent;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin {
    @Inject(method = "dropSelectedItem", at = @At("HEAD"), cancellable = true)
    public void onDropItem(boolean entireStack, CallbackInfoReturnable<Boolean> cir) {
        ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;
        ItemStack stack = player.getMainHandStack();

        if (!stack.isEmpty()) {
            PlayerDropItemEvent event = new PlayerDropItemEvent(player, stack);

            event.callEvent();

            if (event.isCancelled()) {
                cir.setReturnValue(false);
            }
        }
    }
}
