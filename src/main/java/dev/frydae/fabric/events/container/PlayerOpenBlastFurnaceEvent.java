package dev.frydae.fabric.events.container;

import net.minecraft.block.entity.BlastFurnaceBlockEntity;
import net.minecraft.server.network.ServerPlayerEntity;

public class PlayerOpenBlastFurnaceEvent extends PlayerOpenContainerEvent<BlastFurnaceBlockEntity> {
    public PlayerOpenBlastFurnaceEvent(ServerPlayerEntity player, BlastFurnaceBlockEntity blockEntity) {
        super(player, blockEntity);
    }
}
