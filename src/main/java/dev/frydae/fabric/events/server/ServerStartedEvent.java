package dev.frydae.fabric.events.server;

import net.minecraft.server.MinecraftServer;

public final class ServerStartedEvent extends ServerLifecycleEvent {
    public ServerStartedEvent(MinecraftServer server) {
        super(server);
    }
}
