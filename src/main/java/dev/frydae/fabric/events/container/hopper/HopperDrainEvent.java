package dev.frydae.fabric.events.container.hopper;

import dev.frydae.fabric.utils.Location;
import net.minecraft.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

/**
 * Called when a hopper drains an item to another inventory.
 */
public class HopperDrainEvent extends HopperEvent {
    public HopperDrainEvent(@NotNull Inventory hopper, @NotNull Location hopperLoc, @NotNull Inventory target, @NotNull Location targetLoc) {
        super(hopper, hopperLoc, target, targetLoc);
    }
}
