package dev.frydae.fabric.events.container;

import net.minecraft.block.entity.LecternBlockEntity;
import net.minecraft.server.network.ServerPlayerEntity;

public class PlayerOpenLecternEvent extends PlayerOpenContainerEvent<LecternBlockEntity> {
    public PlayerOpenLecternEvent(ServerPlayerEntity player, LecternBlockEntity blockEntity) {
        super(player, blockEntity);
    }
}
