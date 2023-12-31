package dev.frydae.fabric.events;

import dev.frydae.beguild.loader.BeGuildMod;

public final class FabricEvents extends BeGuildMod {
    @Override
    public void onInitialize() {
        EventManager.getInstance();
    }
}
