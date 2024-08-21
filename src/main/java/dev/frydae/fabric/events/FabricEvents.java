package dev.frydae.fabric.events;

import com.mojang.logging.LogUtils;
import lombok.Getter;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.minecraft.server.MinecraftServer;
import org.slf4j.Logger;

public final class FabricEvents implements DedicatedServerModInitializer {
    public static MinecraftServer server;
    @Getter private static final Logger logger = LogUtils.getLogger();

    @Override
    public void onInitializeServer() {
        EventManager.getInstance();
    }

    public static MinecraftServer getServer() {
        return server;
    }
}
