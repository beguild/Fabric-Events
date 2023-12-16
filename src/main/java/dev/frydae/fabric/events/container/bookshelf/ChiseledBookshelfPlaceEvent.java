package dev.frydae.fabric.events.container.bookshelf;

import net.minecraft.block.entity.ChiseledBookshelfBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;

public class ChiseledBookshelfPlaceEvent extends ChiseledBookshelfEvent {
    public ChiseledBookshelfPlaceEvent(ServerPlayerEntity player, ChiseledBookshelfBlockEntity chiseledBookshelfBlockEntity, int slot, ItemStack book) {
        super(player, chiseledBookshelfBlockEntity, slot, book);
    }
}
