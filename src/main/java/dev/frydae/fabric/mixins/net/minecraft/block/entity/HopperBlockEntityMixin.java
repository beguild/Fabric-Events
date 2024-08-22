package dev.frydae.fabric.mixins.net.minecraft.block.entity;

import com.llamalad7.mixinextras.sugar.Local;
import dev.frydae.fabric.utils.Location;
import dev.frydae.fabric.events.container.hopper.HopperDrainEvent;
import dev.frydae.fabric.events.container.hopper.HopperFillEvent;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.Hopper;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Debug(export = true)
@Mixin(HopperBlockEntity.class)
public class HopperBlockEntityMixin {
    @Inject(
            method = "insert",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/block/entity/HopperBlockEntity;removeStack(II)Lnet/minecraft/item/ItemStack;"),
            cancellable = true
    )
    private static void drain(World world, BlockPos pos, HopperBlockEntity blockEntity, CallbackInfoReturnable<Boolean> cir, @Local(ordinal = 0) Inventory target, @Local(ordinal = 0) Direction direction) {
        BlockPos targetPos = pos.offset(direction);

        boolean failure = !callDrainEvent(blockEntity, new Location(world, pos), target, new Location(world, targetPos));

        if (target instanceof HopperBlockEntity) {
            boolean fillStatus = !callFillEvent((Hopper) target, new Location(world, targetPos), blockEntity, new Location(world, pos));

            failure |= fillStatus;
        }

        if (failure) {
            cir.setReturnValue(false);
        }
    }

    @Inject(
            method = "extract(Lnet/minecraft/world/World;Lnet/minecraft/block/entity/Hopper;)Z",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/block/entity/HopperBlockEntity;getAvailableSlots(Lnet/minecraft/inventory/Inventory;Lnet/minecraft/util/math/Direction;)[I"),
            cancellable = true
    )
    private static void fill(World world, Hopper hopper, CallbackInfoReturnable<Boolean> cir, @Local(ordinal = 0) Inventory source) {
        BlockPos sourcePos = null;
        if (source instanceof LockableContainerBlockEntity lockableContainerBlockEntity) {
            sourcePos = lockableContainerBlockEntity.getPos();
        }

        if (sourcePos != null && world != null && !source.isEmpty()) {
            if (!callFillEvent(hopper, new Location(world, hopper.getHopperX(), hopper.getHopperY(), hopper.getHopperZ()), source, new Location(world, sourcePos))) {
                cir.setReturnValue(false);
            }
        }
    }

    @Unique
    private static boolean callFillEvent(Hopper hopper, Location hopperLoc, Inventory source, Location sourceLoc) {
        HopperFillEvent event = new HopperFillEvent(hopper, hopperLoc, source, sourceLoc);

        event.callEvent();

        return !event.isCancelled();
    }

    @Unique
    private static boolean callDrainEvent(Hopper hopper, Location hopperLoc, Inventory target, Location targetLoc) {
        HopperDrainEvent event = new HopperDrainEvent(hopper, hopperLoc, target, targetLoc);

        event.callEvent();

        return !event.isCancelled();
    }
}
