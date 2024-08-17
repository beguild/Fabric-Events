package dev.frydae.fabric.events.generation.ancientcity;

import dev.frydae.fabric.events.generation.PreGenerateEvent;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.gen.structure.Structure;

public class PreGenerateAncientCityEvent extends PreGenerateEvent {
    public PreGenerateAncientCityEvent(ServerWorld world, ChunkPos pos, Structure structure) {
        super(world, pos, structure);
    }
}
