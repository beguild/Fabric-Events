package dev.frydae.fabric.events.container.open;

import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.server.network.ServerPlayerEntity;

public class PlayerOpenShulkerBoxEvent extends PlayerOpenContainerEvent<ShulkerBoxBlockEntity> {
    public PlayerOpenShulkerBoxEvent(ServerPlayerEntity player, ShulkerBoxBlockEntity blockEntity) {
        super(player, blockEntity);
    }
}
