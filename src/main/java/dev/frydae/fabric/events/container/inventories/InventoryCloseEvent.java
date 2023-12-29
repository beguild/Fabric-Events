package dev.frydae.fabric.events.container.inventories;

import dev.frydae.fabric.events.Event;
import lombok.Getter;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;

@Getter
public class InventoryCloseEvent extends Event {
    private final ServerPlayerEntity player;
    private final ScreenHandler previousScreenHandler;

    public InventoryCloseEvent(ServerPlayerEntity player, ScreenHandler previousScreenHandler) {
        this.player = player;
        this.previousScreenHandler = previousScreenHandler;
    }
}
