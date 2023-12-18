package dev.frydae.fabric.events.container.hopper;

import dev.frydae.beguild.utils.Location;
import net.minecraft.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public class HopperFillEvent extends HopperEvent {
    public HopperFillEvent(@NotNull Inventory hopper, @NotNull Location hopperLoc, @NotNull Inventory target, @NotNull Location targetLoc) {
        super(hopper, hopperLoc, target, targetLoc);
    }
}
