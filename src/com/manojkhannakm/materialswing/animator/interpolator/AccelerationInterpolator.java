package com.manojkhannakm.materialswing.animator.interpolator;

import org.jdesktop.core.animation.timing.interpolators.SplineInterpolator;

/**
 * @author Manoj Khanna
 */

public class AccelerationInterpolator {

    private static SplineInterpolator interpolator;

    private AccelerationInterpolator() {
    }

    public static SplineInterpolator getInstance() {
        if (interpolator == null) {
            interpolator = new SplineInterpolator(0.4, 0.0, 1.0, 1.0);
        }

        return interpolator;
    }

}
