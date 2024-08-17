package dev.frydae.fabric.events.generation;

import com.google.common.collect.Sets;
import net.minecraft.registry.RegistryKey;
import net.minecraft.structure.DesertVillageData;
import net.minecraft.structure.PlainsVillageData;
import net.minecraft.structure.SavannaVillageData;
import net.minecraft.structure.SnowyVillageData;
import net.minecraft.structure.TaigaVillageData;
import net.minecraft.structure.pool.StructurePool;

import java.util.Arrays;
import java.util.HashSet;

public enum VillageType {
    DESERT(DesertVillageData.TOWN_CENTERS_KEY),
    PLAINS(PlainsVillageData.TOWN_CENTERS_KEY),
    SAVANNA(SavannaVillageData.TOWN_CENTERS_KEY),
    SNOWY(SnowyVillageData.TOWN_CENTERS_KEY),
    TAIGA(TaigaVillageData.TOWN_CENTERS_KEY);

    private final RegistryKey<StructurePool> townCentersKey;

    VillageType(RegistryKey<StructurePool> townCentersKey) {
        this.townCentersKey = townCentersKey;
    }

    public static HashSet<Object> keySet() {
        return Sets.newHashSet(Arrays.stream(values()).map(value -> value.townCentersKey).toArray());
    }

    public static VillageType getByKey(RegistryKey<StructurePool> key) {
        for (VillageType type : values()) {
            if (type.townCentersKey.equals(key)) {
                return type;
            }
        }

        return null;
    }
}
