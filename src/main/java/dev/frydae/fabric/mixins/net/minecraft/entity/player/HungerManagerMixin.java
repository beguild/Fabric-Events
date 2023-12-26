package dev.frydae.fabric.mixins.net.minecraft.entity.player;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.frydae.fabric.events.player.PlayerFoodLevelChangeEvent;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(HungerManager.class)
public class HungerManagerMixin {
    @Shadow private int foodLevel;

    @WrapOperation(
            method = "update",
            at = @At(value = "INVOKE", target = "Ljava/lang/Math;max(II)I")
    )
    public int update(int check, int zero, Operation<Integer> original, PlayerEntity player) {
        int newFood = original.call(check, zero);

        PlayerFoodLevelChangeEvent event = new PlayerFoodLevelChangeEvent((ServerPlayerEntity) player, foodLevel,  newFood);

        event.callEvent();

        if (event.isCancelled()) {
            if (event.shouldResetFoodLevel()) {
                return 20;
            }

            return foodLevel;
        } else {
            return newFood;
        }
    }
}
