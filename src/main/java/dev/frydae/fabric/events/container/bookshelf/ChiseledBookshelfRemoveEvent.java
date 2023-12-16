package dev.frydae.fabric.events.container.bookshelf;

import net.minecraft.block.entity.ChiseledBookshelfBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;

public class ChiseledBookshelfRemoveEvent extends ChiseledBookshelfEvent {
    public ChiseledBookshelfRemoveEvent(ServerPlayerEntity serverPlayer, ChiseledBookshelfBlockEntity chiseledBookshelfBlockEntity, int slot, ItemStack book) {
        super(serverPlayer, chiseledBookshelfBlockEntity, slot, book);
    }
}
