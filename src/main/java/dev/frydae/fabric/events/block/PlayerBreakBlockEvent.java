package dev.frydae.fabric.events.block;

import dev.frydae.fabric.utils.Location;
import net.minecraft.block.BlockState;
import net.minecraft.server.network.ServerPlayerEntity;

public class PlayerBreakBlockEvent extends PlayerBlockEvent {
    public PlayerBreakBlockEvent(ServerPlayerEntity player, Location location, BlockState block) {
        super(player, location, block);
    }
}
