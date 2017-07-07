package com.manojkhannakm.materialswing.swing;

import com.manojkhannakm.materialswing.event.MaterialListener;
import com.manojkhannakm.materialswing.event.MaterialPropertyListener;
import com.manojkhannakm.materialswing.property.MaterialProperty;
import com.manojkhannakm.materialswing.util.MathUtils;
import com.manojkhannakm.materialswing.util.ObjectUtils;

import java.awt.*;

/**
 * @author Manoj Khanna
 */

public interface Material {

    void paint(Graphics g);

    void materialPaint(Graphics2D graphics);

    void materialRepaint();

    void materialRevalidate();

    void addMaterialListener(String eventName, MaterialListener listener);

    void removeMaterialListener(String eventName, MaterialListener listener);

    void materialChanged(String eventName);

    <O, V> void materialPropertyChanged(String propertyName, O object, V oldValue, V newValue);

    int getMaterialX();

    void setMaterialX(int x);

    int getMaterialY();

    void setMaterialY(int y);

    int getMaterialZ();

    void setMaterialZ(int z);

    int getMaterialWidth();

    void setMaterialWidth(int width);

    int getMaterialHeight();

    void setMaterialHeight(int height);

    int getMaterialTranslationX();

    void setMaterialTranslationX(int translationX);

    int getMaterialTranslationY();

    void setMaterialTranslationY(int translationY);

    int getMaterialTranslationZ();

    void setMaterialTranslationZ(int translationZ);

    float getMaterialPivotX();

    void setMaterialPivotX(float pivotX);

    float getMaterialPivotY();

    void setMaterialPivotY(float pivotY);

    float getMaterialScaleX();

    void setMaterialScaleX(float scaleX);

    float getMaterialScaleY();

    void setMaterialScaleY(float scaleY);

    float getMaterialRotation();

    void setMaterialRotation(float rotation);

    float getMaterialAlpha();

    void setMaterialAlpha(float alpha);

    Color getMaterialColor();

    void setMaterialColor(Color color);

    Shape getMaterialShape();

    void setMaterialShape(Shape shape);

    Rectangle getMaterialBounds();

    void setMaterialBounds(Rectangle bounds);

    MaterialListener[] getMaterialListeners(String eventName);

    int getEffectOffset();

    default <O, V> void addMaterialListener(MaterialProperty<O, V> property, O object, MaterialPropertyListener<O, V> listener) {
        ObjectUtils.verifyNotNull(property, "property");

        addMaterialListener(property.getName(object), listener);
    }

    default <O, V> void addMaterialListener(String propertyName, O object, MaterialPropertyListener<O, V> listener) {
        MaterialProperty<O, V> property = MaterialProperty.getProperty(propertyName, object);

        if (property != null) {
            addMaterialListener(property, object, listener);
        } else {
            addMaterialListener(propertyName, listener);
        }
    }

    default <O, V> void removeMaterialListener(MaterialProperty<O, V> property, O object, MaterialPropertyListener<O, V> listener) {
        ObjectUtils.verifyNotNull(property, "property");

        removeMaterialListener(property.getName(object), listener);
    }

    default <O, V> void removeMaterialListener(String propertyName, O object, MaterialPropertyListener<O, V> listener) {
        MaterialProperty<O, V> property = MaterialProperty.getProperty(propertyName, object);

        if (property != null) {
            removeMaterialListener(property, object, listener);
        } else {
            removeMaterialListener(propertyName, listener);
        }
    }

    default <O, V> void materialPropertyChanged(MaterialProperty<O, V> property, O object, V oldValue, V newValue) {
        ObjectUtils.verifyNotNull(property, "property");

        materialPropertyChanged(property.getName(object), object, oldValue, newValue);
    }

    default float getMaterialScaledWidth() {
        return getMaterialWidth() * getMaterialScaleX();
    }

    default float getMaterialScaledHeight() {
        return getMaterialHeight() * getMaterialScaleY();
    }

    default float getMaterialRotatedWidth() {
        return MathUtils.getRotatedWidth(getMaterialScaledWidth(), getMaterialScaledHeight(), getMaterialRotation());
    }

    default float getMaterialRotatedHeight() {
        return MathUtils.getRotatedHeight(getMaterialScaledWidth(), getMaterialScaledHeight(), getMaterialRotation());
    }

    default int getEffectWidth() {
        return getMaterialWidth() + 2 * getEffectOffset();
    }

    default int getEffectHeight() {
        return getMaterialHeight() + 2 * getEffectOffset();
    }

    default float getEffectScaledWidth() {
        return getEffectWidth() * getMaterialScaleX();
    }

    default float getEffectScaledHeight() {
        return getEffectHeight() * getMaterialScaleY();
    }

    default float getEffectRotatedWidth() {
        return MathUtils.getRotatedWidth(getEffectScaledWidth(), getEffectScaledHeight(), getMaterialRotation());
    }

    default float getEffectRotatedHeight() {
        return MathUtils.getRotatedHeight(getEffectScaledWidth(), getEffectScaledHeight(), getMaterialRotation());
    }

    default <O, V> MaterialPropertyListener<O, V>[] getMaterialListeners(MaterialProperty<O, V> property, O object) {
        ObjectUtils.verifyNotNull(property, "property");

        MaterialListener[] listeners = getMaterialListeners(property.getName(object));

        //noinspection unchecked
        MaterialPropertyListener<O, V>[] propertyListeners = new MaterialPropertyListener[listeners.length];

        for (int i = 0; i < listeners.length; i++) {
            //noinspection unchecked
            propertyListeners[i] = (MaterialPropertyListener<O, V>) listeners[i];
        }

        return propertyListeners;
    }

    default <O, V> MaterialPropertyListener<O, V>[] getMaterialListeners(String propertyName, O object) {
        MaterialProperty<O, V> property = MaterialProperty.getProperty(propertyName, object);

        if (property != null) {
            return getMaterialListeners(property, object);
        } else {
            MaterialListener[] listeners = getMaterialListeners(propertyName);

            //noinspection unchecked
            MaterialPropertyListener<O, V>[] propertyListeners = new MaterialPropertyListener[listeners.length];

            for (int i = 0; i < listeners.length; i++) {
                //noinspection unchecked
                propertyListeners[i] = (MaterialPropertyListener<O, V>) listeners[i];
            }

            return propertyListeners;
        }
    }

}
