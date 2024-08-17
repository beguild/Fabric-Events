package dev.frydae.fabric.events.generation;

import net.minecraft.world.gen.structure.StructureType;

import java.util.Arrays;

public enum StructureStartType {
    JIGSAW(StructureType.JIGSAW),
    RUINED_PORTAL(StructureType.RUINED_PORTAL),
    END_CITY(StructureType.END_CITY),
    STRONGHOLD(StructureType.STRONGHOLD),
    MINESHAFT(StructureType.MINESHAFT),
    BURIED_TREASURE(StructureType.BURIED_TREASURE),
    OCEAN_RUIN(StructureType.OCEAN_RUIN),
    SHIPWRECK(StructureType.SHIPWRECK),
    OCEAN_MONUMENT(StructureType.OCEAN_MONUMENT),
    IGLOO(StructureType.IGLOO),
    SWAMP_HUT(StructureType.SWAMP_HUT),
    DESERT_PYRAMID(StructureType.DESERT_PYRAMID),
    JUNGLE_TEMPLE(StructureType.JUNGLE_TEMPLE),
    WOODLAND_MANSION(StructureType.WOODLAND_MANSION),
    FORTRESS(StructureType.FORTRESS),
    NETHER_FOSSIL(StructureType.NETHER_FOSSIL);

    private final StructureType<?> type;

    StructureStartType(StructureType<?> type) {
        this.type = type;
    }

    public static StructureStartType getByType(StructureType<?> type) {
        return Arrays.stream(values()).filter(value -> value.type.equals(type)).findFirst().orElse(null);

    }
}
