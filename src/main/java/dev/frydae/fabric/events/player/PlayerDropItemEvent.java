package dev.frydae.fabric.events.player;

import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;

public class PlayerDropItemEvent extends PlayerItemEvent {
    public PlayerDropItemEvent(ServerPlayerEntity player, ItemStack stack) {
        super(player, stack);
    }
}
