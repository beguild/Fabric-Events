package dev.frydae.fabric.events.generation;

import dev.frydae.fabric.events.Cancellable;
import dev.frydae.fabric.events.Event;
import lombok.Getter;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.gen.structure.Structure;

@Getter
public abstract class PreGenerateEvent extends Event implements Cancellable {
    private final ServerWorld world;
    private final ChunkPos pos;
    private final Structure structure;
    private boolean cancelled = false;
    
    public PreGenerateEvent(ServerWorld world, ChunkPos pos, Structure structure) {
        super();
        
        this.world = world;
        this.pos = pos;
        this.structure = structure;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled() {
        this.cancelled = true;
    }
}
