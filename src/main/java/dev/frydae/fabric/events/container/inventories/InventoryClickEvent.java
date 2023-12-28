package dev.frydae.fabric.events.container.inventories;

import dev.frydae.fabric.events.Cancellable;
import dev.frydae.fabric.events.Event;
import lombok.Getter;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.server.network.ServerPlayerEntity;

public class InventoryClickEvent extends Event implements Cancellable {
    @Getter private final ServerPlayerEntity player;
    @Getter private final ScreenHandler currentScreenHandler;
    @Getter private final int slot;
    @Getter private final SlotActionType actionType;
    @Getter private final int button;
    @Getter private final InventoryClickType clickType;
    private boolean cancelled = false;

    public InventoryClickEvent(ServerPlayerEntity player, ScreenHandler currentScreenHandler, int slot, SlotActionType actionType, int button) {
        this.player = player;
        this.currentScreenHandler = currentScreenHandler;
        this.slot = slot;
        this.actionType = actionType;
        this.button = button;
        this.clickType = InventoryClickType.getClickType(actionType, button);
    }

    public boolean isTopInventory() {
        return slot >= 0 && slot < currentScreenHandler.slots.size();
    }

    public boolean isBottomInventory() {
        return slot >= currentScreenHandler.slots.size();
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled() {
        this.cancelled = true;
    }

    public enum InventorySide {
        TOP, BOTTOM
    }

    @Getter
    public enum InventoryClickType {
        LEFT_CLICK(SlotActionType.PICKUP, 0),
        RIGHT_CLICK(SlotActionType.PICKUP, 1),
        SHIFT_LEFT_CLICK(SlotActionType.QUICK_MOVE, 0),
        DROP(SlotActionType.THROW, 0),
        DROP_ALL(SlotActionType.THROW, 1);

        private final SlotActionType actionType;
        private final int button;

        InventoryClickType(SlotActionType actionType, int button) {
            this.actionType = actionType;
            this.button = button;
        }

        public static InventoryClickType getClickType(SlotActionType actionType, int button) {
            for (InventoryClickType clickType : values()) {
                if (clickType.getActionType() == actionType && clickType.getButton() == button) {
                    return clickType;
                }
            }

            return null;
        }
    }
}

