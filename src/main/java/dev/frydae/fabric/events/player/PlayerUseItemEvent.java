package dev.frydae.fabric.events.player;

import lombok.Getter;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

@Getter
public class PlayerUseItemEvent extends PlayerItemEvent {
    private final World world;
    private final Hand hand;

    public PlayerUseItemEvent(ServerPlayerEntity player, World world, ItemStack stack, Hand hand) {
        super(player, stack);

        this.world = world;
        this.hand = hand;
    }
}
