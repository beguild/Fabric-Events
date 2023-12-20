package dev.frydae.fabric.events.block.ignite;

import dev.frydae.beguild.utils.Location;
import net.minecraft.server.network.ServerPlayerEntity;

public class CandleBlockIgniteEvent extends BlockIgniteEvent {
    public CandleBlockIgniteEvent(ServerPlayerEntity player, Location location) {
        super(player, location);
    }
}
