package dev.frydae.fabric.events.player;

import dev.frydae.fabric.events.Cancellable;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;

@Getter @Setter
public class PlayerDisconnectMessageEvent extends PlayerEvent implements Cancellable {
    private Text message;
    private boolean cancelled = false;

    public PlayerDisconnectMessageEvent(ServerPlayerEntity player, @NotNull Text message) {
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
