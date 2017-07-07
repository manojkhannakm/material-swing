package com.manojkhannakm.materialswing.animator;

import org.jdesktop.swing.animation.timing.sources.SwingTimerTimingSource;

import java.util.concurrent.TimeUnit;

/**
 * @author Manoj Khanna
 */

public class TimingSource {

    private static SwingTimerTimingSource timingSource;

    private TimingSource() {
    }

    public static SwingTimerTimingSource getInstance() {
        if (timingSource == null) {
            timingSource = new SwingTimerTimingSource(16, TimeUnit.MILLISECONDS);
            timingSource.init();
        }

        return timingSource;
    }

}
