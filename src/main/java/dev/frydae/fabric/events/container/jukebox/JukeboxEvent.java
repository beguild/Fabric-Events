package dev.frydae.fabric.events.container.jukebox;

import dev.frydae.fabric.events.Cancellable;
import dev.frydae.fabric.events.player.PlayerEvent;
import lombok.Getter;
import net.minecraft.block.entity.JukeboxBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;

@Getter
public class JukeboxEvent extends PlayerEvent implements Cancellable {
    private final JukeboxBlockEntity jukeboxBlockEntity;
    private final ItemStack discStack;

    private boolean cancelled = false;

    public JukeboxEvent(ServerPlayerEntity player, JukeboxBlockEntity jukeboxBlockEntity, ItemStack discStack) {
        super(player);

        this.jukeboxBlockEntity = jukeboxBlockEntity;
        this.discStack = discStack;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled() {
        this.cancelled = true;
    }
}
