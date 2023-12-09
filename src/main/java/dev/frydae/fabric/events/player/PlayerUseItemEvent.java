package dev.frydae.fabric.events.player;

import dev.frydae.fabric.events.Cancellable;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class PlayerUseItemEvent extends PlayerEvent implements Cancellable {
    private final World world;
    private final ItemStack stack;
    private final Hand hand;

    private boolean cancelled = false;

    public PlayerUseItemEvent(ServerPlayerEntity player, World world, ItemStack stack, Hand hand) {
        super(player);

        this.world = world;
        this.stack = stack;
        this.hand = hand;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled() {
        this.cancelled = true;
    }

    public World getWorld() {
        return world;
    }

    public ItemStack getStack() {
        return stack;
    }

    public Hand getHand() {
        return hand;
    }
}
