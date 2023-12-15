package dev.frydae.fabric.events.player;

import dev.frydae.fabric.events.Cancellable;
import lombok.Getter;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;

@Getter
public class PlayerItemEvent extends PlayerEvent implements Cancellable {
    private final ItemStack stack;

    private boolean cancelled = false;

    public PlayerItemEvent(ServerPlayerEntity player, ItemStack stack) {
        super(player);

        this.stack = stack;
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
