package dev.frydae.fabric.mixins.net.minecraft.entity.projectile.thrown;

import com.llamalad7.mixinextras.sugar.Local;
import dev.frydae.beguild.utils.Location;
import dev.frydae.fabric.events.block.BlockEvents;
import dev.frydae.fabric.events.block.BlockUtils;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.thrown.PotionEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Debug(export = true)
@Mixin(PotionEntity.class)
public class PotionEntityMixin {
    @Inject(
            method = "extinguishFire",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;breakBlock(Lnet/minecraft/util/math/BlockPos;ZLnet/minecraft/entity/Entity;)Z"),
            cancellable = true
    )
    public void extinguishFire(BlockPos pos, CallbackInfo ci, @Local(ordinal = 0) BlockState state) {
        PotionEntity instance = (PotionEntity) (Object) this;

        Entity owner = instance.getOwner();

        if (owner instanceof ServerPlayerEntity player) {
            boolean failure = !BlockEvents.callExtinguishEvent(player, new Location(instance.getWorld(), pos));

            failure |= !BlockEvents.callBreakBlockEvent(player, (ServerWorld) instance.getWorld(), pos, state);

            if (failure) {
                ci.cancel();
            }
        }
    }

    @Inject(
            method = "extinguishFire",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/block/AbstractCandleBlock;extinguish(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/block/BlockState;Lnet/minecraft/world/WorldAccess;Lnet/minecraft/util/math/BlockPos;)V"),
            cancellable = true
    )
    public void extinguishCandle(BlockPos pos, CallbackInfo ci, @Local(ordinal = 0) BlockState state) {
        PotionEntity instance = (PotionEntity) (Object) this;

        Entity owner = instance.getOwner();

        if (owner instanceof ServerPlayerEntity player) {
            boolean failure = false;

            if (BlockUtils.isCandleCake(state)) {
                failure |= !BlockEvents.callCandleCakeBlockExtinguishEvent(player, new Location(instance.getWorld(), pos));
            } else if (BlockUtils.isCandle(state)) {
                failure |= !BlockEvents.callCandleBlockExtinguishEvent(player, new Location(instance.getWorld(), pos));
            }

            failure |= !BlockEvents.callBreakBlockEvent(player, (ServerWorld) instance.getWorld(), pos, state);

            if (failure) {
                ci.cancel();
            }
        }
    }

    @Inject(
            method = "extinguishFire",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/block/CampfireBlock;extinguish(Lnet/minecraft/entity/Entity;Lnet/minecraft/world/WorldAccess;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)V"),
            cancellable = true
    )
    public void extinguishCampfire(BlockPos pos, CallbackInfo ci, @Local(ordinal = 0) BlockState state) {
        PotionEntity instance = (PotionEntity) (Object) this;

        Entity owner = instance.getOwner();

        if (owner instanceof ServerPlayerEntity player) {
            boolean failure = !BlockEvents.callCampfireExtinguishEvent(player, new Location(instance.getWorld(), pos));

            failure |= !BlockEvents.callBreakBlockEvent(player, (ServerWorld) instance.getWorld(), pos, state);

            if (failure) {
                ci.cancel();
            }
        }
    }
}
