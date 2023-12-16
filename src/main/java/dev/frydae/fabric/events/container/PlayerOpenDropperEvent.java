package dev.frydae.fabric.events.container;

import net.minecraft.block.entity.DropperBlockEntity;
import net.minecraft.server.network.ServerPlayerEntity;

public class PlayerOpenDropperEvent extends PlayerOpenContainerEvent {
    public PlayerOpenDropperEvent(ServerPlayerEntity player, DropperBlockEntity blockEntity) {
        super(player, blockEntity);
    }

    @Override
    public DropperBlockEntity getBlockEntity() {
        return (DropperBlockEntity) super.getBlockEntity();
    }
}
