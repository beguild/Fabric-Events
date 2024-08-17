package dev.frydae.fabric.events.block.ignite;

import dev.frydae.fabric.utils.Location;
import net.minecraft.server.network.ServerPlayerEntity;

public class CandleCakeBlockIgniteEvent extends BlockIgniteEvent {
    public CandleCakeBlockIgniteEvent(ServerPlayerEntity player, Location world) {
        super(player, world);
    }
}
