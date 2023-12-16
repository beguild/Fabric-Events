package dev.frydae.fabric.events.container.bookshelf;

import dev.frydae.fabric.events.Cancellable;
import dev.frydae.fabric.events.player.PlayerEvent;
import lombok.Getter;
import net.minecraft.block.entity.ChiseledBookshelfBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;

@Getter
public class ChiseledBookshelfEvent extends PlayerEvent implements Cancellable {
    private final ChiseledBookshelfBlockEntity chiseledBookshelfBlockEntity;
    private final int slot;
    private final ItemStack book;

    private boolean cancelled = false;

    public ChiseledBookshelfEvent(ServerPlayerEntity player, ChiseledBookshelfBlockEntity chiseledBookshelfBlockEntity, int slot, ItemStack book) {
        super(player);

        this.chiseledBookshelfBlockEntity = chiseledBookshelfBlockEntity;
        this.slot = slot;
        this.book = book;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled() {
        this.cancelled = true;
    }
}
