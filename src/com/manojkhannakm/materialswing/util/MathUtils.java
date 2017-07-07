package com.manojkhannakm.materialswing.util;

/**
 * @author Manoj Khanna
 */

public class MathUtils {

    private MathUtils() {
    }

    public static float getRotatedX(float x, float y, float pivotX, float pivotY, float rotation) {
        double angle = 2.0 * Math.PI * rotation;

        return (float) ((x - pivotX) * Math.cos(angle) - (y - pivotY) * Math.sin(angle) + pivotX);
    }

    public static float getRotatedY(float x, float y, float pivotX, float pivotY, float rotation) {
        double angle = 2.0 * Math.PI * rotation;

        return (float) ((x - pivotX) * Math.sin(angle) + (y - pivotY) * Math.cos(angle) + pivotY);
    }

    public static float getRotatedWidth(float width, float height, float rotation) {
        double angle = 2.0 * Math.PI * rotation;

        return (float) (Math.abs(width * Math.cos(angle)) + Math.abs(height * Math.sin(angle)));
    }

    public static float getRotatedHeight(float width, float height, float rotation) {
        double angle = 2.0 * Math.PI * rotation;

        return (float) (Math.abs(width * Math.sin(angle)) + Math.abs(height * Math.cos(angle)));
    }

}
