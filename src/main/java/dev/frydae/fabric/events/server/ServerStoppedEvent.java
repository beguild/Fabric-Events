package dev.frydae.fabric.events.server;

import net.minecraft.server.MinecraftServer;

public class ServerStoppedEvent extends ServerLifecycleEvent {
    public ServerStoppedEvent(MinecraftServer server) {
        super(server);
    }
}
