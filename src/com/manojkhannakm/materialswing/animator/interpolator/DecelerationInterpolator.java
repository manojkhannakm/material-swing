package com.manojkhannakm.materialswing.animator.interpolator;

import org.jdesktop.core.animation.timing.interpolators.SplineInterpolator;

/**
 * @author Manoj Khanna
 */

public class DecelerationInterpolator {

    private static SplineInterpolator interpolator;

    private DecelerationInterpolator() {
    }

    public static SplineInterpolator getInstance() {
        if (interpolator == null) {
            interpolator = new SplineInterpolator(0.0, 0.0, 0.2, 1.0);
        }

        return interpolator;
    }

}
