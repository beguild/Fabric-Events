package dev.frydae.fabric.events.player;

import dev.frydae.fabric.events.Cancellable;
import net.minecraft.server.network.ServerPlayerEntity;

public class PlayerFoodLevelChangeEvent extends PlayerEvent implements Cancellable {
    private final int oldFood;
    private final int newFood;

    private boolean shouldReset = false;
    private boolean cancelled = false;

    public PlayerFoodLevelChangeEvent(ServerPlayerEntity player, int oldFood, int newFood) {
        super(player);

        this.oldFood = oldFood;
        this.newFood = newFood;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled() {
        this.cancelled = true;
    }

    public void resetFoodLevel() {
        this.shouldReset = true;
    }

    public boolean shouldResetFoodLevel() {
        return shouldReset;
    }
}
