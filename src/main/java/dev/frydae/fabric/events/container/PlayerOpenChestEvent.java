package dev.frydae.fabric.events.container;

import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.TrappedChestBlockEntity;
import net.minecraft.server.network.ServerPlayerEntity;

public class PlayerOpenChestEvent extends PlayerOpenContainerEvent {
    private final boolean trapped;

    public PlayerOpenChestEvent(ServerPlayerEntity player, ChestBlockEntity blockEntity, boolean trapped) {
        super(player, blockEntity);

        this.trapped = trapped;
    }

    @Override
    public ChestBlockEntity getBlockEntity() {
        return (ChestBlockEntity) super.getBlockEntity();
    }

    public TrappedChestBlockEntity asTrappedChestEntity() {
        if (!isTrapped()) {
            throw new UnsupportedOperationException("Cannot cast a non-trapped chest to a trapped chest.");
        }

        return (TrappedChestBlockEntity) super.getBlockEntity();
    }

    public boolean isTrapped() {
        return trapped;
    }
}
