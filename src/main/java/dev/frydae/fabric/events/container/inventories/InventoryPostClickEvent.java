package dev.frydae.fabric.events.container.inventories;

import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.server.network.ServerPlayerEntity;

public class InventoryPostClickEvent extends InventoryClickEvent {
    public InventoryPostClickEvent(ServerPlayerEntity player, ScreenHandler currentScreenHandler, int slot, SlotActionType actionType, int button) {
        super(player, currentScreenHandler, slot, actionType, button);
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public void setCancelled() {
        // do nothing
    }
}
