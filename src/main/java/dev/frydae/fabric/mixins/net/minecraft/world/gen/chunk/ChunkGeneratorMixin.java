package dev.frydae.fabric.mixins.net.minecraft.world.gen.chunk;

import com.llamalad7.mixinextras.sugar.Local;
import dev.frydae.fabric.mixins.net.minecraft.world.gen.structure.JigsawStructureAccessor;
import dev.frydae.fabric.events.generation.StructureStartType;
import dev.frydae.fabric.events.generation.VillageType;
import dev.frydae.fabric.events.generation.ancientcity.PostGenerateAncientCityEvent;
import dev.frydae.fabric.events.generation.structure.PostGenerateStructureEvent;
import dev.frydae.fabric.events.generation.village.PostGenerateVillageEvent;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.AncientCityGenerator;
import net.minecraft.structure.BastionRemnantGenerator;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChunkGenerator.class)
public class ChunkGeneratorMixin {
    @Inject(
            method = "generateFeatures",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/StructureWorldAccess;setCurrentlyGeneratingStructureName(Ljava/util/function/Supplier;)V", ordinal = 2)
    )
    public void generateFeatures(StructureWorldAccess world, Chunk chunk, StructureAccessor structureAccessor, CallbackInfo ci, @Local(ordinal = 0) ChunkPos chunkPos2) {
        if (world.isClient()) {
            return;
        }

        ServerWorld serverWorld = world.toServerWorld();

        chunk.getStructureStarts().forEach((structure, structureStart) -> {
            StructureStartType byType = StructureStartType.getByType(structureStart.getStructure().getType());

            if (byType != null) {
                if (byType == StructureStartType.JIGSAW) {
                    RegistryEntry<StructurePool> startJigsawName = ((JigsawStructureAccessor) structure).getStartPool();

                    if (startJigsawName != null) {
                        startJigsawName.getKey().ifPresent(key -> {
                            if (VillageType.keySet().contains(key)) {
                                VillageType type = VillageType.getByKey(key);

                                new PostGenerateVillageEvent(type, serverWorld, chunkPos2, structure).callEvent();
                            } else if (key == BastionRemnantGenerator.STRUCTURE_POOLS) {
                                new PostGenerateAncientCityEvent(serverWorld, chunkPos2, structure).callEvent();
                            } else if (key == AncientCityGenerator.CITY_CENTER) {
                                new PostGenerateAncientCityEvent(serverWorld, chunkPos2, structure).callEvent();
                            }
                        });
                    }
                } else {
                    new PostGenerateStructureEvent(byType, serverWorld, chunkPos2, structure).callEvent();
                }
            }
        });
    }
}
