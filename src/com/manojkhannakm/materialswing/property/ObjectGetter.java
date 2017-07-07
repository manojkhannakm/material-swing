package com.manojkhannakm.materialswing.property;

import com.manojkhannakm.materialswing.util.ObjectUtils;
import com.manojkhannakm.materialswing.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Manoj Khanna
 */

public interface ObjectGetter<O, V> {

    static <O, V> ObjectGetter<O, V> getGetter(String getterName, Class<O> objectClass, Class<V> valueClass) {
        ObjectUtils.verifyNotNull(getterName, "getterName");
        ObjectUtils.verifyNotNull(objectClass, "objectClass");
        ObjectUtils.verifyNotNull(valueClass, "valueClass");

        String methodName = "get" + StringUtils.getPascalCase(getterName);

        for (Method method : objectClass.getMethods()) {
            if (method.getName().equals(methodName) && method.getParameterCount() == 0
                    && method.getReturnType().isAssignableFrom(valueClass)) {
                return object -> {
                    try {
                        //noinspection unchecked
                        return (V) method.invoke(object);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                };
            }
        }

        return null;
    }

    V getValue(O object);

}
