package dev.frydae.fabric.events.container;

import net.minecraft.block.entity.BrewingStandBlockEntity;
import net.minecraft.server.network.ServerPlayerEntity;

public class PlayerOpenBrewingStandEvent extends PlayerOpenContainerEvent {
    public PlayerOpenBrewingStandEvent(ServerPlayerEntity player, BrewingStandBlockEntity blockEntity) {
        super(player, blockEntity);
    }

    @Override
    public BrewingStandBlockEntity getBlockEntity() {
        return (BrewingStandBlockEntity) super.getBlockEntity();
    }
}
