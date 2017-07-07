package com.manojkhannakm.materialswing.animator.interpolator;

import org.jdesktop.core.animation.timing.interpolators.SplineInterpolator;

/**
 * @author Manoj Khanna
 */

public class SharpInterpolator {

    private static SplineInterpolator interpolator;

    private SharpInterpolator() {
    }

    public static SplineInterpolator getInstance() {
        if (interpolator == null) {
            interpolator = new SplineInterpolator(0.4, 0.0, 0.6, 1.0);
        }

        return interpolator;
    }

}
