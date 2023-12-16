package dev.frydae.fabric.events.container;

import net.minecraft.block.entity.BeaconBlockEntity;
import net.minecraft.server.network.ServerPlayerEntity;

public class PlayerOpenBeaconEvent extends PlayerOpenContainerEvent<BeaconBlockEntity> {
    public PlayerOpenBeaconEvent(ServerPlayerEntity player, BeaconBlockEntity blockEntity) {
        super(player, blockEntity);
    }
}
