package dev.frydae.fabric.events.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import org.spongepowered.asm.mixin.Unique;

import java.util.stream.Stream;

public final class BlockUtils {
    @Unique
    public static boolean isCandle(final BlockState blockState) {
        return Stream.of(
                Blocks.WHITE_CANDLE, Blocks.ORANGE_CANDLE, Blocks.MAGENTA_CANDLE, Blocks.LIGHT_BLUE_CANDLE,
                Blocks.YELLOW_CANDLE, Blocks.LIME_CANDLE, Blocks.PINK_CANDLE, Blocks.GRAY_CANDLE,
                Blocks.LIGHT_GRAY_CANDLE, Blocks.CYAN_CANDLE, Blocks.PURPLE_CANDLE, Blocks.BLUE_CANDLE,
                Blocks.BROWN_CANDLE, Blocks.GREEN_CANDLE, Blocks.RED_CANDLE, Blocks.BLACK_CANDLE
        ).anyMatch(blockState::isOf);
    }

    @Unique
    public static  boolean isCandleCake(final BlockState blockState) {
        return Stream.of(
                Blocks.WHITE_CANDLE_CAKE, Blocks.ORANGE_CANDLE_CAKE, Blocks.MAGENTA_CANDLE_CAKE, Blocks.LIGHT_BLUE_CANDLE_CAKE,
                Blocks.YELLOW_CANDLE_CAKE, Blocks.LIME_CANDLE_CAKE, Blocks.PINK_CANDLE_CAKE, Blocks.GRAY_CANDLE_CAKE,
                Blocks.LIGHT_GRAY_CANDLE_CAKE, Blocks.CYAN_CANDLE_CAKE, Blocks.PURPLE_CANDLE_CAKE, Blocks.BLUE_CANDLE_CAKE,
                Blocks.BROWN_CANDLE_CAKE, Blocks.GREEN_CANDLE_CAKE, Blocks.RED_CANDLE_CAKE, Blocks.BLACK_CANDLE_CAKE
        ).anyMatch(blockState::isOf);
    }
}
