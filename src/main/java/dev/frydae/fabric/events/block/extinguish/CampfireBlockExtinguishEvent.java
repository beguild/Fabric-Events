package dev.frydae.fabric.events.block.extinguish;

import dev.frydae.beguild.utils.Location;
import net.minecraft.server.network.ServerPlayerEntity;

public class CampfireBlockExtinguishEvent extends BlockExtinguishEvent {
    public CampfireBlockExtinguishEvent(ServerPlayerEntity player, Location location) {
        super(player, location);
    }
}
