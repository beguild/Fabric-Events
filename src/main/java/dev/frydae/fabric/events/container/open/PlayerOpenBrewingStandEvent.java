package dev.frydae.fabric.events.container.open;

import net.minecraft.block.entity.BrewingStandBlockEntity;
import net.minecraft.server.network.ServerPlayerEntity;

public class PlayerOpenBrewingStandEvent extends PlayerOpenContainerEvent<BrewingStandBlockEntity> {
    public PlayerOpenBrewingStandEvent(ServerPlayerEntity player, BrewingStandBlockEntity blockEntity) {
        super(player, blockEntity);
    }
}
