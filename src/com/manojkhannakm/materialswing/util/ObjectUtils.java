package com.manojkhannakm.materialswing.util;

/**
 * @author Manoj Khanna
 */

public class ObjectUtils {

    private ObjectUtils() {
    }

    public static void verifyCondition(boolean condition, String errorMessage) {
        if (!condition) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    public static void verifyCondition(Condition condition, String errorMessage) {
        verifyNotNull(condition, "condition");

        verifyCondition(condition.verify(), errorMessage);
    }

    public static void verifyNotNull(Object object, String objectName) {
        verifyCondition(objectName != null, "objectName should not be null!");

        verifyCondition(object != null, objectName + " should not be null!");
    }

    public static void verifyInstanceOf(Object object, Class<?> objectClass, String objectName) {
        verifyNotNull(objectClass, "objectClass");
        verifyNotNull(objectName, "objectName");

        verifyCondition(objectClass.isInstance(object), objectName
                + " should be an instance of " + objectClass.getSimpleName() + "!");
    }

    public interface Condition {

        boolean verify();

    }

}
