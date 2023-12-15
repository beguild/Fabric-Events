package dev.frydae.fabric.events.player;

import lombok.Getter;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;

@Getter
public class PlayerPickupItemEvent extends PlayerItemEvent {
    private final Entity owner;

    public PlayerPickupItemEvent(ServerPlayerEntity player, ItemStack stack, Entity owner) {
        super(player, stack);

        this.owner = owner;
    }
}
