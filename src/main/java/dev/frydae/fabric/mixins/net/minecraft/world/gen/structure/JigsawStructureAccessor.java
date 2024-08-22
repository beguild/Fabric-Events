package dev.frydae.fabric.mixins.net.minecraft.world.gen.structure;

import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.structure.JigsawStructure;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Optional;

@Mixin(JigsawStructure.class)
public interface JigsawStructureAccessor {
    @Accessor
    Optional<Identifier> getStartJigsawName();

    @Accessor
    RegistryEntry<StructurePool> getStartPool();
}
