package dev.frydae.fabric.events.block.ignite;

import dev.frydae.fabric.utils.Location;
import dev.frydae.fabric.events.Cancellable;
import dev.frydae.fabric.events.player.PlayerEvent;
import lombok.Getter;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.NotNull;

@Getter
public class BlockIgniteEvent extends PlayerEvent implements Cancellable {
    private final Location location;

    private boolean cancelled = false;

    public BlockIgniteEvent(@NotNull ServerPlayerEntity player, @NotNull Location location) {
        super(player);

        this.location = location;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled() {
        this.cancelled = true;
    }
}
