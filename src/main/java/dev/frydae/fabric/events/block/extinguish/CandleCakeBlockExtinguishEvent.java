package dev.frydae.fabric.events.block.extinguish;

import dev.frydae.beguild.utils.Location;
import net.minecraft.server.network.ServerPlayerEntity;

public class CandleCakeBlockExtinguishEvent extends BlockExtinguishEvent {
    public CandleCakeBlockExtinguishEvent(ServerPlayerEntity player, Location location) {
        super(player, location);
    }
}
