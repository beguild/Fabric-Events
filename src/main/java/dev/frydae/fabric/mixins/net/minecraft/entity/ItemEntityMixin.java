package dev.frydae.fabric.mixins.net.minecraft.entity;

import dev.frydae.fabric.events.player.PlayerPickupItemEvent;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Debug(export = true)
@Mixin(ItemEntity.class)
public class ItemEntityMixin {
    @Inject(
            method = "onPlayerCollision",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerInventory;insertStack(Lnet/minecraft/item/ItemStack;)Z"),
            cancellable = true
    )
    public void onPlayerCollision(PlayerEntity player, CallbackInfo ci) {
        if (player instanceof ServerPlayerEntity serverPlayer) {
            ItemEntity itemEntity = (ItemEntity) (Object) this;

            PlayerPickupItemEvent event = new PlayerPickupItemEvent(serverPlayer, itemEntity.getStack(), itemEntity.getOwner());

            event.callEvent();

            if (event.isCancelled()) {
                ci.cancel();
            }
        }

    }
}
