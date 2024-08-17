package dev.frydae.fabric.events.server;

import net.minecraft.server.MinecraftServer;

public final class ServerStoppingEvent extends ServerLifecycleEvent {
    public ServerStoppingEvent(MinecraftServer server) {
        super(server);
    }
}
