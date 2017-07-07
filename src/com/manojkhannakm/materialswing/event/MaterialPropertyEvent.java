package com.manojkhannakm.materialswing.event;

import com.manojkhannakm.materialswing.swing.Material;
import com.manojkhannakm.materialswing.util.ObjectUtils;

/**
 * @author Manoj Khanna
 */

public class MaterialPropertyEvent<O, V> extends MaterialEvent {

    private final O object;
    private final V oldValue, newValue;

    public MaterialPropertyEvent(Material material, O object, V oldValue, V newValue) {
        super(material);

        ObjectUtils.verifyNotNull(object, "object");

        this.object = object;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    public O getObject() {
        return object;
    }

    public V getOldValue() {
        return oldValue;
    }

    public V getNewValue() {
        return newValue;
    }

}
