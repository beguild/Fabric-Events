package dev.frydae.fabric.events.container.hopper;

import dev.frydae.beguild.utils.Location;
import net.minecraft.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public class HopperDrainEvent extends HopperEvent {
    public HopperDrainEvent(@NotNull Inventory hopper, @NotNull Location hopperLoc, @NotNull Inventory source, @NotNull Location sourceLoc) {
        super(hopper, hopperLoc, source, sourceLoc);
    }
}
