package com.manojkhannakm.materialswing.animator.target;

import org.jdesktop.core.animation.timing.Animator;
import org.jdesktop.core.animation.timing.TimingTarget;

import java.lang.reflect.Field;

/**
 * @author Manoj Khanna
 */

public abstract class AnimatorTarget implements TimingTarget {

    private boolean started;

    @Override
    public final void begin(Animator source) {
        started = false;

        animatorCreated(source);
    }

    @Override
    public final void end(Animator source) {
        animatorStopped(source);
    }

    @Override
    public final void repeat(Animator source) {
        animatorRepeated(source);
    }

    @Override
    public final void reverse(Animator source) {
        animatorReversed(source);
    }

    @Override
    public final void timingEvent(Animator source, double fraction) {
        if (!started) {
            animatorStarted(source);

            started = true;

            try {
                Field stoppingField = Animator.class.getDeclaredField("f_stopping");
                stoppingField.setAccessible(true);

                if (stoppingField.getBoolean(source)) {
                    return;
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        animatorTicked(source, fraction);
    }

    public void animatorTicked(Animator animator, double fraction) {
    }

    public void animatorCreated(Animator animator) {
    }

    public void animatorStarted(Animator animator) {
    }

    public void animatorReversed(Animator animator) {
    }

    public void animatorRepeated(Animator animator) {
    }

    public void animatorStopped(Animator animator) {
    }

}
