package dev.frydae.fabric.events.block.extinguish;

import dev.frydae.beguild.utils.Location;
import net.minecraft.server.network.ServerPlayerEntity;

public class CandleBlockExtinguishEvent extends BlockExtinguishEvent {
    public CandleBlockExtinguishEvent(ServerPlayerEntity player, Location location) {
        super(player, location);
    }
}
