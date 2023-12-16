package dev.frydae.fabric.events.container;

import lombok.Getter;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.TrappedChestBlockEntity;
import net.minecraft.server.network.ServerPlayerEntity;

@Getter
public class PlayerOpenChestEvent extends PlayerOpenContainerEvent<ChestBlockEntity> {
    private final boolean trapped;

    public PlayerOpenChestEvent(ServerPlayerEntity player, ChestBlockEntity blockEntity, boolean trapped) {
        super(player, blockEntity);

        this.trapped = trapped;
    }

    public TrappedChestBlockEntity asTrappedChestEntity() {
        if (!isTrapped()) {
            throw new UnsupportedOperationException("Cannot cast a non-trapped chest to a trapped chest.");
        }

        return (TrappedChestBlockEntity) super.getBlockEntity();
    }

}
