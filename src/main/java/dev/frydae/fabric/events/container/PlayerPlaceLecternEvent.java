package dev.frydae.fabric.events.container;

import lombok.Getter;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;

@Getter
public class PlayerPlaceLecternEvent extends PlayerLecternEvent {
    private final ItemStack stack;

    public PlayerPlaceLecternEvent(ServerPlayerEntity player, ItemStack stack) {
        super(player);

        this.stack = stack;
    }
}
