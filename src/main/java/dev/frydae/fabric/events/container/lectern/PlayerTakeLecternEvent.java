package dev.frydae.fabric.events.container.lectern;

import lombok.Getter;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;

@Getter
public class PlayerTakeLecternEvent extends PlayerLecternEvent {
    private final ItemStack stack;

    public PlayerTakeLecternEvent(ServerPlayerEntity player, ItemStack stack) {
        super(player);

        this.stack = stack;
    }
}
