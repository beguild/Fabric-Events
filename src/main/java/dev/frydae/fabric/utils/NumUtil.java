package dev.frydae.fabric.utils;

public class NumUtil {
    public static int assertBounds(int value, int min, int max) {
        return Math.min(Math.max(value, min), max);
    }

    public static double assertBounds(double value, double min, double max) {
        return Math.min(Math.max(value, min), max);
    }

    public static float assertBounds(float value, float min, float max) {
        return Math.min(Math.max(value, min), max);
    }

    public static double assertHorizontalCoordinate(double d) {
        return assertBounds(d, -3.0E7, 3.0E7);
    }

    public static double assertVerticalCoordinate(double d) {
        return assertBounds(d, -64, 320);
    }

    /**
     * Fixes negative coordinates due to -0 not being a thing in Minecraft.
     *
     * If the coordinate is negative, it rounds down to the next lower integer.
     *
     * @param d the coordinate to be fixed
     * @return the corrected coordinate
     */
    public static double correctCoordinate(double d) {
        if (d < 0.0) {
            return Math.floor(d);
        }

        return d;
    }
}
