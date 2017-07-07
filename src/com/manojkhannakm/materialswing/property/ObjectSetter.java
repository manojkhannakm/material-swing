package com.manojkhannakm.materialswing.property;

import com.manojkhannakm.materialswing.util.ObjectUtils;
import com.manojkhannakm.materialswing.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Manoj Khanna
 */

public interface ObjectSetter<O, V> {

    static <O, V> ObjectSetter<O, V> getSetter(String setterName, Class<O> objectClass, Class<V> valueClass) {
        ObjectUtils.verifyNotNull(setterName, "setterName");
        ObjectUtils.verifyNotNull(objectClass, "objectClass");
        ObjectUtils.verifyNotNull(valueClass, "valueClass");

        String methodName = "set" + StringUtils.getPascalCase(setterName);

        for (Method method : objectClass.getMethods()) {
            if (method.getName().equals(methodName) && method.getParameterCount() == 1
                    && method.getParameterTypes()[0].isAssignableFrom(valueClass)) {
                return (object, value) -> {
                    try {
                        method.invoke(object, value);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                };
            }
        }

        return null;
    }

    void setValue(O object, V value);

}
