package dev.frydae.fabric.events.player;

import dev.frydae.fabric.events.Cancellable;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

@Getter @Setter
public class PlayerJoinMessageEvent extends PlayerEvent implements Cancellable {
    private Text message;

    private boolean cancelled = false;

    public PlayerJoinMessageEvent(ServerPlayerEntity player, Text message) {
        super(player);

        this.message = message;
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
