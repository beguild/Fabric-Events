package dev.frydae.fabric.events.container.hopper;

import dev.frydae.beguild.utils.Location;
import net.minecraft.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

/**
 * Called when a hopper gets filled with items from another inventory.
 */
public class HopperFillEvent extends HopperEvent {
    public HopperFillEvent(@NotNull Inventory hopper, @NotNull Location hopperLoc, @NotNull Inventory source, @NotNull Location sourceLoc) {
        super(hopper, hopperLoc, source, sourceLoc);
    }
}
