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
}
