package dev.frydae.fabric.events.player;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;

public class PlayerBucketFillEvent extends PlayerBucketEvent {
    public PlayerBucketFillEvent(ServerPlayerEntity player, Block block, Item bucket, ItemStack itemInHand, Hand hand) {
        super(player, block, bucket, itemInHand, hand);
    }
}
