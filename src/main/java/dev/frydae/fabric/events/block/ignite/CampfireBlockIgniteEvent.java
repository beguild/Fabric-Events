package dev.frydae.fabric.events.block.ignite;

import dev.frydae.fabric.utils.Location;
import net.minecraft.server.network.ServerPlayerEntity;

public class CampfireBlockIgniteEvent extends BlockIgniteEvent {
    public CampfireBlockIgniteEvent(ServerPlayerEntity player, Location world) {
        super(player, world);
    }
}
