package dev.frydae.fabric.events.player;

import dev.frydae.fabric.events.Event;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.minecraft.server.network.ServerPlayerEntity;

@Data
@EqualsAndHashCode(callSuper = true)
public class PlayerEvent extends Event {
    private final ServerPlayerEntity player;
}
