package dev.frydae.fabric.events;

import net.fabricmc.api.DedicatedServerModInitializer;

public class FabricEventsServerModInitializer implements DedicatedServerModInitializer {
    @Override
    public void onInitializeServer() {
        System.out.println("Hello Fabric world!");
    }
}