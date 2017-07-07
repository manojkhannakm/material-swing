package com.manojkhannakm.materialswing.animator.target;

import com.manojkhannakm.materialswing.property.ValueGetter;
import com.manojkhannakm.materialswing.property.ValueSetter;
import com.manojkhannakm.materialswing.util.ObjectUtils;
import org.jdesktop.core.animation.timing.Animator;
import org.jdesktop.core.animation.timing.KeyFrames;

/**
 * @author Manoj Khanna
 */

public class ValueTarget {

    private ValueTarget() {
    }

    @SafeVarargs
    public static <V> AnimatorTarget getToTarget(ValueGetter<V> getter, ValueSetter<V> setter, V... values) {
        ObjectUtils.verifyNotNull(getter, "getter");
        ObjectUtils.verifyNotNull(setter, "setter");
        ObjectUtils.verifyNotNull(values, "values");
        ObjectUtils.verifyCondition(values.length >= 1, "values should be of length >= 1!");

        KeyFrames<V> keyFrames = new KeyFrames.Builder<>(getter.getValue())
                .addFrames(values)
                .build();

        return new AnimatorTarget() {

            @Override
            public void animatorTicked(Animator animator, double fraction) {
                setter.setValue(keyFrames.getInterpolatedValueAt(fraction));
            }

            @Override
            public void animatorStarted(Animator animator) {
                double fraction;

                if (animator.getCurrentDirection() == Animator.Direction.FORWARD) {
                    fraction = 0.0f;
                } else {
                    fraction = 1.0f;
                }

                setter.setValue(keyFrames.getInterpolatedValueAt(fraction));
            }

            @Override
            public void animatorStopped(Animator animator) {
                double fraction;

                if (animator.getCurrentDirection() == Animator.Direction.FORWARD) {
                    fraction = 1.0f;
                } else {
                    fraction = 0.0f;
                }

                setter.setValue(keyFrames.getInterpolatedValueAt(fraction));
            }

        };
    }

    @SafeVarargs
    public static <V> AnimatorTarget getOfTarget(ValueSetter<V> setter, V... values) {
        ObjectUtils.verifyNotNull(setter, "setter");
        ObjectUtils.verifyNotNull(values, "values");
        ObjectUtils.verifyCondition(values.length >= 2, "values should be of length >= 2!");

        KeyFrames<V> keyFrames = new KeyFrames.Builder<V>()
                .addFrames(values)
                .build();

        return new AnimatorTarget() {

            @Override
            public void animatorTicked(Animator animator, double fraction) {
                setter.setValue(keyFrames.getInterpolatedValueAt(fraction));
            }

            @Override
            public void animatorStarted(Animator animator) {
                double fraction;

                if (animator.getCurrentDirection() == Animator.Direction.FORWARD) {
                    fraction = 0.0f;
                } else {
                    fraction = 1.0f;
                }

                setter.setValue(keyFrames.getInterpolatedValueAt(fraction));
            }

            @Override
            public void animatorStopped(Animator animator) {
                double fraction;

                if (animator.getCurrentDirection() == Animator.Direction.FORWARD) {
                    fraction = 1.0f;
                } else {
                    fraction = 0.0f;
                }

                setter.setValue(keyFrames.getInterpolatedValueAt(fraction));
            }

        };
    }

}
