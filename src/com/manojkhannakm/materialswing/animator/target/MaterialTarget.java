package com.manojkhannakm.materialswing.animator.target;

import com.manojkhannakm.materialswing.property.MaterialProperty;
import com.manojkhannakm.materialswing.util.ObjectUtils;

/**
 * @author Manoj Khanna
 */

public class MaterialTarget {

    private MaterialTarget() {
    }

    @SafeVarargs
    public static <O, V> AnimatorTarget getToTarget(MaterialProperty<O, V> property, O object, V... values) {
        ObjectUtils.verifyNotNull(property, "property");
        ObjectUtils.verifyNotNull(object, "object");

        return ValueTarget.getToTarget(() -> property.getValue(object), value -> property.setValue(object, value), values);
    }

    @SafeVarargs
    public static <O, V> AnimatorTarget getToTarget(String propertyName, O object, V... values) {
        MaterialProperty<O, V> property = MaterialProperty.getProperty(propertyName, object);

        ObjectUtils.verifyNotNull(property, "propertyName");

        return getToTarget(property, object, values);
    }

    @SafeVarargs
    public static <O, V> AnimatorTarget getOfTarget(MaterialProperty<O, V> property, O object, V... values) {
        ObjectUtils.verifyNotNull(property, "property");
        ObjectUtils.verifyNotNull(object, "object");

        return ValueTarget.getOfTarget(value -> property.setValue(object, value), values);
    }

    @SafeVarargs
    public static <O, V> AnimatorTarget getOfTarget(String propertyName, O object, V... values) {
        MaterialProperty<O, V> property = MaterialProperty.getProperty(propertyName, object);

        ObjectUtils.verifyNotNull(property, "propertyName");

        return getOfTarget(property, object, values);
    }

}
