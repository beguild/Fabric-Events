package dev.frydae.fabric.events.player;

import dev.frydae.fabric.events.Cancellable;
import lombok.Getter;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;

@Getter
public class PlayerBucketEvent extends PlayerEvent implements Cancellable {
    private final Block block;
    private final Item bucket;
    private final ItemStack itemInHand;
    private final Hand hand;
    private boolean cancelled = false;

    public PlayerBucketEvent(ServerPlayerEntity player, Block block, Item bucket, ItemStack itemInHand, Hand hand) {
        super(player);

        this.block = block;
        this.bucket = bucket;
        this.itemInHand = itemInHand;
        this.hand = hand;
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
