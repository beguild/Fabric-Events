package dev.frydae.fabric.events.container;

import net.minecraft.block.entity.FurnaceBlockEntity;
import net.minecraft.server.network.ServerPlayerEntity;

public class PlayerOpenFurnaceEvent extends PlayerOpenContainerEvent<FurnaceBlockEntity> {
    public PlayerOpenFurnaceEvent(ServerPlayerEntity player, FurnaceBlockEntity blockEntity) {
        super(player, blockEntity);
    }
}
