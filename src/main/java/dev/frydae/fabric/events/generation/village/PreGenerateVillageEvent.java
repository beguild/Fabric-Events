package dev.frydae.fabric.events.generation.village;

import dev.frydae.fabric.events.generation.PreGenerateEvent;
import dev.frydae.fabric.events.generation.VillageType;
import lombok.Getter;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.gen.structure.Structure;

@Getter
public class PreGenerateVillageEvent extends PreGenerateEvent {
    private final VillageType type;

    public PreGenerateVillageEvent(VillageType type, ServerWorld world, ChunkPos pos, Structure structure) {
        super(world, pos, structure);

        this.type = type;
    }
}
