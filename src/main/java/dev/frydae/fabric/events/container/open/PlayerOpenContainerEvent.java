package dev.frydae.fabric.events.container.open;

import dev.frydae.fabric.events.Cancellable;
import dev.frydae.fabric.events.player.PlayerEvent;
import lombok.Getter;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.server.network.ServerPlayerEntity;

@Getter
public class PlayerOpenContainerEvent<T extends BlockEntity> extends PlayerEvent implements Cancellable {
    private final T blockEntity;
    private boolean cancelled = false;

    public PlayerOpenContainerEvent(ServerPlayerEntity player, T blockEntity) {
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
