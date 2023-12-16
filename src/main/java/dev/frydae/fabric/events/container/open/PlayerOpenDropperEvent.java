package dev.frydae.fabric.events.container.open;

import net.minecraft.block.entity.DropperBlockEntity;
import net.minecraft.server.network.ServerPlayerEntity;

public class PlayerOpenDropperEvent extends PlayerOpenContainerEvent<DropperBlockEntity> {
    public PlayerOpenDropperEvent(ServerPlayerEntity player, DropperBlockEntity blockEntity) {
        super(player, blockEntity);
    }
}
