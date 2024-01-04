package dev.frydae.fabric.events.container.hopper;

import dev.frydae.beguild.utils.Location;
import dev.frydae.fabric.events.Cancellable;
import dev.frydae.fabric.events.Event;
import lombok.Getter;
import net.minecraft.block.entity.BarrelBlockEntity;
import net.minecraft.block.entity.BlastFurnaceBlockEntity;
import net.minecraft.block.entity.BrewingStandBlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.block.entity.DropperBlockEntity;
import net.minecraft.block.entity.FurnaceBlockEntity;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.block.entity.SmokerBlockEntity;
import net.minecraft.block.entity.TrappedChestBlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

@Getter
public abstract class HopperEvent extends Event implements Cancellable {
    private final Inventory hopper;
    private final Location hopperLoc;
    private final Inventory other;
    private final Location otherLoc;

    private boolean cancelled = false;

    public HopperEvent(@NotNull Inventory hopper, @NotNull Location hopperLoc, @NotNull Inventory other, @NotNull Location otherLoc) {
        this.hopper = hopper;
        this.hopperLoc = hopperLoc;
        this.other = other;
        this.otherLoc = otherLoc;
    }

    public World getWorld() {
        return hopperLoc.getWorld();
    }

    public boolean isBarrel() {
        return this.other instanceof BarrelBlockEntity;
    }
    public BarrelBlockEntity getBarrel() {
        if (!isBarrel()) {
            throw new UnsupportedOperationException("Other inventory is not a barrel");
        }

        return (BarrelBlockEntity) this.other;
    }

    public boolean isBlastFurnace() {
        return this.other instanceof BlastFurnaceBlockEntity;
    }

    public BlastFurnaceBlockEntity getBlastFurnace() {
        if (!isBlastFurnace()) {
            throw new UnsupportedOperationException("Other inventory is not a blast furnace");
        }

        return (BlastFurnaceBlockEntity) this.other;
    }

    public boolean isBrewingStand() {
        return this.other instanceof BrewingStandBlockEntity;
    }

    public BrewingStandBlockEntity getBrewingStand() {
        if (!isBrewingStand()) {
            throw new UnsupportedOperationException("Other inventory is not a brewing stand");
        }

        return (BrewingStandBlockEntity) this.other;
    }

    /**
     * This one is different from the others because {@link TrappedChestBlockEntity} extends {@link ChestBlockEntity}
     */
    public boolean isChest() {
        return this.other instanceof ChestBlockEntity && !(this.other instanceof TrappedChestBlockEntity);
    }

    public ChestBlockEntity getChest() {
        if (!isChest()) {
            throw new UnsupportedOperationException("Other inventory is not a chest");
        }

        return (ChestBlockEntity) this.other;
    }

    /**
     * This one is different from the others because {@link DropperBlockEntity} extends {@link DispenserBlockEntity}
     */
    public boolean isDispenser() {
        return this.other instanceof DispenserBlockEntity && !(this.other instanceof DropperBlockEntity);
    }

    public DispenserBlockEntity getDispenser() {
        if (!isDispenser()) {
            throw new UnsupportedOperationException("Other inventory is not a dispenser");
        }

        return (DispenserBlockEntity) this.other;
    }

    public boolean isDropper() {
        return this.other instanceof DropperBlockEntity;
    }

    public DropperBlockEntity getDropper() {
        if (!isDropper()) {
            throw new UnsupportedOperationException("Other inventory is not a dropper");
        }

        return (DropperBlockEntity) this.other;
    }

    public boolean isFurnace() {
        return this.other instanceof FurnaceBlockEntity;
    }

    public FurnaceBlockEntity getFurnace() {
        if (!isFurnace()) {
            throw new UnsupportedOperationException("Other inventory is not a furnace");
        }

        return (FurnaceBlockEntity) this.other;
    }

    public boolean isHopper() {
        return this.other instanceof HopperBlockEntity;
    }

    public HopperBlockEntity getHopper() {
        if (!isHopper()) {
            throw new UnsupportedOperationException("Other inventory is not a hopper");
        }

        return (HopperBlockEntity) this.other;
    }

    public boolean isShulkerBox() {
        return this.other instanceof ShulkerBoxBlockEntity;
    }

    public ShulkerBoxBlockEntity getShulkerBox() {
        if (!isShulkerBox()) {
            throw new UnsupportedOperationException("Other inventory is not a shulker box");
        }

        return (ShulkerBoxBlockEntity) this.other;
    }

    public boolean isSmoker() {
        return this.other instanceof SmokerBlockEntity;
    }

    public SmokerBlockEntity getSmoker() {
        if (!isSmoker()) {
            throw new UnsupportedOperationException("Other inventory is not a smoker");
        }

        return (SmokerBlockEntity) this.other;
    }

    public boolean isTrappedChest() {
        return this.other instanceof TrappedChestBlockEntity;
    }

    public TrappedChestBlockEntity getTrappedChest() {
        if (!isTrappedChest()) {
            throw new UnsupportedOperationException("Other inventory is not a trapped chest");
        }

        return (TrappedChestBlockEntity) this.other;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled() {
        this.cancelled = true;
    }
}
