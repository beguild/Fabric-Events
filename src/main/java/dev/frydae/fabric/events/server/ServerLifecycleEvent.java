package dev.frydae.fabric.events.server;

import dev.frydae.fabric.events.Cancellable;
import dev.frydae.fabric.events.Event;
import net.minecraft.server.MinecraftServer;

public abstract class ServerLifecycleEvent extends Event implements Cancellable {
    private boolean cancelled = false;

    private final MinecraftServer server;

    public ServerLifecycleEvent(MinecraftServer server) {
        super();

        this.server = server;
    }

    public MinecraftServer getServer() {
        return server;
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
