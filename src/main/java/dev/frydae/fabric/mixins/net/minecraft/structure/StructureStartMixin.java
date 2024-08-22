package dev.frydae.fabric.mixins.net.minecraft.structure;

import dev.frydae.fabric.events.generation.PreGenerateEvent;
import dev.frydae.fabric.events.generation.StructureStartType;
import dev.frydae.fabric.events.generation.VillageType;
import dev.frydae.fabric.events.generation.ancientcity.PreGenerateAncientCityEvent;
import dev.frydae.fabric.events.generation.bastion.PreGenerateBastionEvent;
import dev.frydae.fabric.events.generation.structure.PreGenerateStructureEvent;
import dev.frydae.fabric.events.generation.village.PreGenerateVillageEvent;
import dev.frydae.fabric.mixins.net.minecraft.world.gen.structure.JigsawStructureAccessor;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.AncientCityGenerator;
import net.minecraft.structure.BastionRemnantGenerator;
import net.minecraft.structure.StructurePiecesList;
import net.minecraft.structure.StructureStart;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.structure.Structure;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Debug(export = true)
@Mixin(StructureStart.class)
public class StructureStartMixin {
    @Shadow @Final private Structure structure;
    @Shadow @Final private StructurePiecesList children;
    @Shadow @Final private ChunkPos pos;

    @Inject(method = "place", at = @At(value = "HEAD"), cancellable = true)
    public void beguild$place(StructureWorldAccess world, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox chunkBox, ChunkPos chunkPos, CallbackInfo ci) {
        ServerWorld serverWorld = world.toServerWorld();

        StructureStartType byType = StructureStartType.getByType(structure.getType());

        PreGenerateEvent event = null;

        if (byType != null) {
            if (byType == StructureStartType.JIGSAW) {
                RegistryEntry<StructurePool> startJigsawName = ((JigsawStructureAccessor) structure).getStartPool();

                if (startJigsawName != null && startJigsawName.getKey().isPresent()) {
                    RegistryKey<StructurePool> key = startJigsawName.getKey().get();

                    if (VillageType.keySet().contains(key)) {
                        VillageType type = VillageType.getByKey(key);

                        event = new PreGenerateVillageEvent(type, serverWorld, chunkPos, structure);
                    } else if (key.equals(BastionRemnantGenerator.STRUCTURE_POOLS)) {
                        event = new PreGenerateBastionEvent(serverWorld, chunkPos, structure);
                    } else if (key.equals(AncientCityGenerator.CITY_CENTER)) {
                        event = new PreGenerateAncientCityEvent(serverWorld, chunkPos, structure);
                    }
                }
            } else {
                event = new PreGenerateStructureEvent(byType, serverWorld, chunkPos, structure);
            }
        }

        if (event != null) {
            event.callEvent();

            if (event.isCancelled()) {
                ci.cancel();
            }
        }
    }
}
