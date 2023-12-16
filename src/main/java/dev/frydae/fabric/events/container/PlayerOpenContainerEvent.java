package dev.frydae.fabric.events.container;

import dev.frydae.fabric.events.Cancellable;
import dev.frydae.fabric.events.player.PlayerEvent;
import lombok.Getter;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.server.network.ServerPlayerEntity;

@Getter
public class PlayerOpenContainerEvent extends PlayerEvent implements Cancellable {
    private final BlockEntity blockEntity;
    private boolean cancelled = false;

    public PlayerOpenContainerEvent(ServerPlayerEntity player, BlockEntity blockEntity) {
        super(player);

        this.blockEntity = blockEntity;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled() {
        this.cancelled = true;
    }
}
