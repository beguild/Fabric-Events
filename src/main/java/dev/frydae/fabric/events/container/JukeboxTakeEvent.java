package dev.frydae.fabric.events.container;

import lombok.Getter;
import net.minecraft.block.entity.JukeboxBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;

public class JukeboxTakeEvent extends JukeboxEvent {
    public JukeboxTakeEvent(ServerPlayerEntity player, JukeboxBlockEntity jukeboxBlockEntity, ItemStack discStack) {
        super(player, jukeboxBlockEntity, discStack);
    }
}
