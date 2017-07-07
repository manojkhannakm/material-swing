package com.manojkhannakm.materialswing.swing.component;

import com.manojkhannakm.materialswing.event.MaterialListener;
import com.manojkhannakm.materialswing.layout.MaterialLayout;
import com.manojkhannakm.materialswing.style.MaterialColor;

import javax.swing.*;
import java.awt.*;

/**
 * @author Manoj Khanna
 */

public class MaterialPanel extends JPanel implements MaterialComponent {

    private final MaterialComponentDelegate delegate;

    public MaterialPanel() {
        delegate = new MaterialComponentDelegate(this) {

            @Override
            protected void delegatePaint(Graphics2D graphics) {
                MaterialPanel.super.paint(graphics);
            }

            @Override
            protected MaterialUI newDelegateUI() {
                return null;
            }

            @Override
            protected MaterialUI getDelegateUI() {
                return null;
            }

            @Override
            protected void setDelegateUI(MaterialUI componentUI) {

            }

        };

        setLayout(new MaterialLayout());

        setMaterialColor(MaterialColor.TRANSPARENT);
    }

    @Override
    public void paint(Graphics g) {
        delegate.paint(g);
    }

    @Override
    public Dimension getPreferredSize() {
        return delegate.getPreferredSize();
    }

    @Override
    public Dimension getMinimumSize() {
        return delegate.getMinimumSize();
    }

    @Override
    public Dimension getMaximumSize() {
        return delegate.getMaximumSize();
    }

    @Override
    public void materialPaint(Graphics2D graphics) {
        delegate.materialPaint(graphics);
    }

    @Override
    public void materialRepaint() {
        delegate.materialRepaint();
    }

    @Override
    public void materialRevalidate() {
        delegate.materialRevalidate();
    }

    @Override
    public void addMaterialListener(String eventName, MaterialListener listener) {
        delegate.addMaterialListener(eventName, listener);
    }

    @Override
    public void removeMaterialListener(String eventName, MaterialListener listener) {
        delegate.removeMaterialListener(eventName, listener);
    }

    @Override
    public void materialChanged(String eventName) {
        delegate.materialChanged(eventName);
    }

    @Override
    public <O, V> void materialPropertyChanged(String propertyName, O object, V oldValue, V newValue) {
        delegate.materialPropertyChanged(propertyName, object, oldValue, newValue);
    }

    @Override
    public int getMaterialX() {
        return delegate.getMaterialX();
    }

    @Override
    public void setMaterialX(int x) {
        delegate.setMaterialX(x);
    }

    @Override
    public int getMaterialY() {
        return delegate.getMaterialY();
    }

    @Override
    public void setMaterialY(int y) {
        delegate.setMaterialY(y);
    }

    @Override
    public int getMaterialZ() {
        return delegate.getMaterialZ();
    }

    @Override
    public void setMaterialZ(int z) {
        delegate.setMaterialZ(z);
    }

    @Override
    public int getMaterialWidth() {
        return delegate.getMaterialWidth();
    }

    @Override
    public void setMaterialWidth(int width) {
        delegate.setMaterialWidth(width);
    }

    @Override
    public int getMaterialHeight() {
        return delegate.getMaterialHeight();
    }

    @Override
    public void setMaterialHeight(int height) {
        delegate.setMaterialHeight(height);
    }

    @Override
    public int getMaterialTranslationX() {
        return delegate.getMaterialTranslationX();
    }

    @Override
    public void setMaterialTranslationX(int translationX) {
        delegate.setMaterialTranslationX(translationX);
    }

    @Override
    public int getMaterialTranslationY() {
        return delegate.getMaterialTranslationY();
    }

    @Override
    public void setMaterialTranslationY(int translationY) {
        delegate.setMaterialTranslationY(translationY);
    }

    @Override
    public int getMaterialTranslationZ() {
        return delegate.getMaterialTranslationZ();
    }

    @Override
    public void setMaterialTranslationZ(int translationZ) {
        delegate.setMaterialTranslationZ(translationZ);
    }

    @Override
    public float getMaterialPivotX() {
        return delegate.getMaterialPivotX();
    }

    @Override
    public void setMaterialPivotX(float pivotX) {
        delegate.setMaterialPivotX(pivotX);
    }

    @Override
    public float getMaterialPivotY() {
        return delegate.getMaterialPivotY();
    }

    @Override
    public void setMaterialPivotY(float pivotY) {
        delegate.setMaterialPivotY(pivotY);
    }

    @Override
    public float getMaterialScaleX() {
        return delegate.getMaterialScaleX();
    }

    @Override
    public void setMaterialScaleX(float scaleX) {
        delegate.setMaterialScaleX(scaleX);
    }

    @Override
    public float getMaterialScaleY() {
        return delegate.getMaterialScaleY();
    }

    @Override
    public void setMaterialScaleY(float scaleY) {
        delegate.setMaterialScaleY(scaleY);
    }

    @Override
    public float getMaterialRotation() {
        return delegate.getMaterialRotation();
    }

    @Override
    public void setMaterialRotation(float rotation) {
        delegate.setMaterialRotation(rotation);
    }

    @Override
    public float getMaterialAlpha() {
        return delegate.getMaterialAlpha();
    }

    @Override
    public void setMaterialAlpha(float alpha) {
        delegate.setMaterialAlpha(alpha);
    }

    @Override
    public Color getMaterialColor() {
        return delegate.getMaterialColor();
    }

    @Override
    public void setMaterialColor(Color color) {
        delegate.setMaterialColor(color);
    }

    @Override
    public Shape getMaterialShape() {
        return delegate.getMaterialShape();
    }

    @Override
    public void setMaterialShape(Shape shape) {
        delegate.setMaterialShape(shape);
    }

    @Override
    public Rectangle getMaterialBounds() {
        return delegate.getMaterialBounds();
    }

    @Override
    public void setMaterialBounds(Rectangle bounds) {
        delegate.setMaterialBounds(bounds);
    }

    @Override
    public MaterialListener[] getMaterialListeners(String eventName) {
        return delegate.getMaterialListeners(eventName);
    }

    @Override
    public int getEffectOffset() {
        return delegate.getEffectOffset();
    }

}
