package dev.frydae.fabric.events.generation.structure;

import dev.frydae.fabric.events.generation.PreGenerateEvent;
import dev.frydae.fabric.events.generation.StructureStartType;
import lombok.Getter;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.gen.structure.Structure;

@Getter
public class PreGenerateStructureEvent extends PreGenerateEvent {
    private final StructureStartType type;

    public PreGenerateStructureEvent(StructureStartType type, ServerWorld world, ChunkPos pos, Structure structure) {
        super(world, pos, structure);

        this.type = type;
    }
}
