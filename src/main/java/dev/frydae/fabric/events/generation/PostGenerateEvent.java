package dev.frydae.fabric.events.generation;

import dev.frydae.fabric.events.Event;
import lombok.Getter;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.gen.structure.Structure;

@Getter
public abstract class PostGenerateEvent extends Event {
    private final ServerWorld world;
    private final ChunkPos pos;
    private final Structure structure;

    public PostGenerateEvent(ServerWorld world, ChunkPos pos, Structure structure) {
        super();

        this.world = world;
        this.pos = pos;
        this.structure = structure;
    }
}
