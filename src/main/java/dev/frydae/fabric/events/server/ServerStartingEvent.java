package dev.frydae.fabric.events.server;

import net.minecraft.server.MinecraftServer;

public final class ServerStartingEvent extends ServerLifecycleEvent {
    public ServerStartingEvent(MinecraftServer server) {
        super(server);
    }
}
