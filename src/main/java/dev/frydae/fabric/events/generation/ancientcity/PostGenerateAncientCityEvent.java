package dev.frydae.fabric.events.generation.ancientcity;

import dev.frydae.fabric.events.generation.PostGenerateEvent;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.gen.structure.Structure;

public class PostGenerateAncientCityEvent extends PostGenerateEvent {
    public PostGenerateAncientCityEvent(ServerWorld world, ChunkPos pos, Structure structure) {
        super(world, pos, structure);
    }
}
