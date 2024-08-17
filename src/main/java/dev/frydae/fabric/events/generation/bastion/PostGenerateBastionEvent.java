package dev.frydae.fabric.events.generation.bastion;

import dev.frydae.fabric.events.generation.PostGenerateEvent;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.gen.structure.Structure;

public class PostGenerateBastionEvent extends PostGenerateEvent {
    public PostGenerateBastionEvent(ServerWorld world, ChunkPos pos, Structure structure) {
        super(world, pos, structure);
    }
}
