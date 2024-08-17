package dev.frydae.fabric.events.player;

import dev.frydae.fabric.events.Cancellable;
import dev.frydae.fabric.utils.Location;
import lombok.Getter;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.NotNull;

@Getter
public class PlayerMoveEvent extends PlayerEvent implements Cancellable {
    private final Location from;
    private final Location to;
    private boolean cancelled = false;

    public PlayerMoveEvent(ServerPlayerEntity player, @NotNull Location from, @NotNull Location to) {
        super(player);

        this.from = from;
        this.to = to;
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
