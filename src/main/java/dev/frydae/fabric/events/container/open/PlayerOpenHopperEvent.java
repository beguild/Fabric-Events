package dev.frydae.fabric.events.container.open;

import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.server.network.ServerPlayerEntity;

public class PlayerOpenHopperEvent extends PlayerOpenContainerEvent<HopperBlockEntity> {
    public PlayerOpenHopperEvent(ServerPlayerEntity player, HopperBlockEntity blockEntity) {
        super(player, blockEntity);
    }
}
