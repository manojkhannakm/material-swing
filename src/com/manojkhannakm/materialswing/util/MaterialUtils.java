package com.manojkhannakm.materialswing.util;

import com.manojkhannakm.materialswing.swing.Material;

import java.awt.*;

/**
 * @author Manoj Khanna
 */

public class MaterialUtils {

    private MaterialUtils() {
    }

    public static Point getSwingPoint(Material material, Point materialPoint) {  //TODO: Improve later
        return null;
    }

    public static Point getMaterialPoint(Material material, Point swingPoint) {   //TODO: Improve later
        float scaledWidth = material.getMaterialScaledWidth(), scaledHeight = material.getMaterialScaledHeight(),
                rotatedWidth = material.getEffectRotatedWidth(), rotatedHeight = material.getEffectRotatedHeight();

        float x = swingPoint.x, y = swingPoint.y;

        x -= (rotatedWidth - scaledWidth) / 2.0f;
        y -= (rotatedHeight - scaledHeight) / 2.0f;

        x /= material.getMaterialScaleX();
        y /= material.getMaterialScaleY();

        float pivotX = material.getMaterialWidth() / 2.0f, pivotY = material.getMaterialHeight() / 2.0f,
                rotation = material.getMaterialRotation();

        return new Point(Math.round(MathUtils.getRotatedX(x, y, pivotX, pivotY, -rotation)),
                Math.round(MathUtils.getRotatedY(x, y, pivotX, pivotY, -rotation)));
    }

}
