package com.manojkhannakm.materialswing.animator.target;

import com.manojkhannakm.materialswing.property.ObjectGetter;
import com.manojkhannakm.materialswing.property.ObjectSetter;
import com.manojkhannakm.materialswing.util.ObjectUtils;

/**
 * @author Manoj Khanna
 */

public class ObjectTarget {

    private ObjectTarget() {
    }

    @SafeVarargs
    public static <O, V> AnimatorTarget getToTarget(ObjectGetter<O, V> getter, ObjectSetter<O, V> setter, O object, V... values) {
        ObjectUtils.verifyNotNull(getter, "getter");
        ObjectUtils.verifyNotNull(setter, "setter");
        ObjectUtils.verifyNotNull(object, "object");

        return ValueTarget.getToTarget(() -> getter.getValue(object), value -> setter.setValue(object, value), values);
    }

    @SafeVarargs
    public static <O, V> AnimatorTarget getToTarget(String propertyName, O object, V... values) {
        ObjectUtils.verifyNotNull(propertyName, "propertyName");
        ObjectUtils.verifyNotNull(object, "object");
        ObjectUtils.verifyNotNull(values, "values");

        //noinspection unchecked
        ObjectGetter<O, V> getter = ObjectGetter.getGetter(propertyName,
                (Class<O>) object.getClass(), (Class<V>) values.getClass().getComponentType());

        ObjectUtils.verifyNotNull(getter, "propertyName");

        //noinspection unchecked
        ObjectSetter<O, V> setter = ObjectSetter.getSetter(propertyName,
                (Class<O>) object.getClass(), (Class<V>) values.getClass().getComponentType());

        ObjectUtils.verifyNotNull(setter, "propertyName");

        return getToTarget(getter, setter, object, values);
    }

    @SafeVarargs
    public static <O, V> AnimatorTarget getOfTarget(ObjectSetter<O, V> setter, O object, V... values) {
        ObjectUtils.verifyNotNull(setter, "setter");
        ObjectUtils.verifyNotNull(object, "object");

        return ValueTarget.getOfTarget(value -> setter.setValue(object, value), values);
    }

    @SafeVarargs
    public static <O, V> AnimatorTarget getOfTarget(String propertyName, O object, V... values) {
        ObjectUtils.verifyNotNull(propertyName, "propertyName");
        ObjectUtils.verifyNotNull(object, "object");
        ObjectUtils.verifyNotNull(values, "values");

        //noinspection unchecked
        ObjectSetter<O, V> setter = ObjectSetter.getSetter(propertyName,
                (Class<O>) object.getClass(), (Class<V>) values.getClass().getComponentType());

        ObjectUtils.verifyNotNull(setter, "propertyName");

        return getOfTarget(setter, object, values);
    }

}
