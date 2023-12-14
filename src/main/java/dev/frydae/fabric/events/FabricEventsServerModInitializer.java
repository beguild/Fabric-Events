package dev.frydae.fabric.events;

import dev.frydae.fabric.events.server.ServerStartedEvent;
import dev.frydae.fabric.events.server.ServerStartingEvent;
import dev.frydae.fabric.events.server.ServerStoppedEvent;
import dev.frydae.fabric.events.server.ServerStoppingEvent;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

public class FabricEventsServerModInitializer implements DedicatedServerModInitializer {
    @Override
    public void onInitializeServer() {
        ServerLifecycleEvents.SERVER_STARTING.register(server -> new ServerStartingEvent(server).callEvent());
        ServerLifecycleEvents.SERVER_STARTED.register(server -> new ServerStartedEvent(server).callEvent());
        ServerLifecycleEvents.SERVER_STOPPING.register(server -> new ServerStoppingEvent(server).callEvent());
        ServerLifecycleEvents.SERVER_STOPPED.register(server -> new ServerStoppedEvent(server).callEvent());
    }
}