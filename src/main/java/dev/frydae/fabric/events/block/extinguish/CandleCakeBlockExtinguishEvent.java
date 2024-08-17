package dev.frydae.fabric.events.block.extinguish;

import dev.frydae.fabric.utils.Location;
import net.minecraft.server.network.ServerPlayerEntity;

public class CandleCakeBlockExtinguishEvent extends BlockExtinguishEvent {
    public CandleCakeBlockExtinguishEvent(ServerPlayerEntity player, Location location) {
        super(player, location);
    }
}
