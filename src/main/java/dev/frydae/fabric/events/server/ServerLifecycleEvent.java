package dev.frydae.fabric.events.server;

import dev.frydae.fabric.events.Event;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.minecraft.server.MinecraftServer;

@Data
@EqualsAndHashCode(callSuper = true)
public class ServerLifecycleEvent extends Event {
    private final MinecraftServer server;
}
