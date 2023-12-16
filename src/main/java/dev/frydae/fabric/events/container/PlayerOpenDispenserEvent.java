package dev.frydae.fabric.events.container;

import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.server.network.ServerPlayerEntity;

public class PlayerOpenDispenserEvent extends PlayerOpenContainerEvent {
    public PlayerOpenDispenserEvent(ServerPlayerEntity player, DispenserBlockEntity blockEntity) {
        super(player, blockEntity);
    }

    @Override
    public DispenserBlockEntity getBlockEntity() {
        return (DispenserBlockEntity) super.getBlockEntity();
    }
}
