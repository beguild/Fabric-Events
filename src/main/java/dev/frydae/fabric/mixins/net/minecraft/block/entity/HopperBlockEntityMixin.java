package dev.frydae.fabric.mixins.net.minecraft.block.entity;

import com.llamalad7.mixinextras.sugar.Local;
import dev.frydae.beguild.utils.Location;
import dev.frydae.fabric.events.container.hopper.HopperDrainEvent;
import dev.frydae.fabric.events.container.hopper.HopperFillEvent;
import net.minecraft.block.BlockState;
import net.minecraft.block.HopperBlock;
import net.minecraft.block.entity.Hopper;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Debug(export = true)
@Mixin(HopperBlockEntity.class)
public class HopperBlockEntityMixin {
    @Inject(
            method = "insert",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/inventory/Inventory;removeStack(II)Lnet/minecraft/item/ItemStack;"),
            cancellable = true
    )
    private static void fill(World world, BlockPos pos, BlockState state, Inventory hopper, CallbackInfoReturnable<Boolean> cir, @Local(ordinal = 0) Inventory target) {
        Direction direction = state.get(HopperBlock.FACING);
        BlockPos targetPos = pos.offset(direction);

        HopperFillEvent event = new HopperFillEvent(hopper, new Location(world, pos), target, new Location(world, targetPos));

        event.callEvent();

        if (event.isCancelled()) {
            cir.setReturnValue(false);
        }
    }

    @Inject(
            method = "extract(Lnet/minecraft/world/World;Lnet/minecraft/block/entity/Hopper;)Z",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/block/entity/HopperBlockEntity;isInventoryEmpty(Lnet/minecraft/inventory/Inventory;Lnet/minecraft/util/math/Direction;)Z"),
            cancellable = true
    )
    private static void drain(World world, Hopper hopper, CallbackInfoReturnable<Boolean> cir, @Local(ordinal = 0) Inventory source) {
        BlockPos sourcePos = null;
        if (source instanceof LockableContainerBlockEntity lockableContainerBlockEntity) {
            sourcePos = lockableContainerBlockEntity.getPos();
        }

        if (sourcePos != null && !source.isEmpty()) {
            HopperDrainEvent event = new HopperDrainEvent(hopper, new Location(world, hopper.getHopperX(), hopper.getHopperY(), hopper.getHopperZ()), source, new Location(world, sourcePos));

            event.callEvent();

            if (event.isCancelled()) {
                cir.setReturnValue(false);
            }
        }
    }
}
