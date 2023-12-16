package dev.frydae.fabric.events.container;

import net.minecraft.block.entity.JukeboxBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;

public class JukeboxPlaceEvent extends JukeboxEvent {
    public JukeboxPlaceEvent(ServerPlayerEntity player, JukeboxBlockEntity jukeboxBlockEntity, ItemStack discStack) {
        super(player, jukeboxBlockEntity, discStack);
    }
}
