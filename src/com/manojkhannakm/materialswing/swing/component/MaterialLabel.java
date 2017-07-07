package com.manojkhannakm.materialswing.swing.component;

import com.manojkhannakm.materialswing.content.MaterialContainer;
import com.manojkhannakm.materialswing.content.image.Image;
import com.manojkhannakm.materialswing.content.text.Text;
import com.manojkhannakm.materialswing.event.MaterialListener;
import com.manojkhannakm.materialswing.style.MaterialColor;
import com.manojkhannakm.materialswing.style.MaterialFont;

import javax.swing.*;
import java.awt.*;

/**
 * @author Manoj Khanna
 */

public class MaterialLabel extends JLabel implements MaterialComponent, Text, Image {

    private final MaterialComponentDelegate delegate;

    private final MaterialContainer container;

    public MaterialLabel() {
        delegate = new MaterialComponentDelegate(this) {

            @Override
            protected void delegatePaint(Graphics2D graphics) {
                MaterialLabel.super.paint(graphics);
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

        container = new MaterialContainer(this);

        setMaterialColor(MaterialColor.TRANSPARENT);

        setText(null);
        setTextColor(null);
        setTextFont(null);
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

    @Override
    public String getText() {
        if (container == null) {
            return null;
        }

        return container.getText();
    }

    @Override
    public void setText(String text) {
        if (container == null) {
            return;
        }

        if (text == null) {
            text = "Label";
        }

        container.setText(text);
    }

    @Override
    public Color getTextColor() {
        return container.getTextColor();
    }

    @Override
    public void setTextColor(Color color) {
        if (color == null) {
            color = MaterialColor.BLACK.withAlpha(0.8f);
        }

        container.setTextColor(color);
    }

    @Override
    public Font getTextFont() {
        return container.getTextFont();
    }

    @Override
    public void setTextFont(Font font) {
        if (font == null) {
            font = MaterialFont.ROBOTO_REGULAR.withSize(14.0f);
        }

        container.setTextFont(font);
    }

    @Override
    public java.awt.Image getImage() {
        return container.getImage();
    }

    @Override
    public void setImage(java.awt.Image image) {
        container.setImage(image);
    }

    @Override
    public float getImageScaleX() {
        return container.getImageScaleX();
    }

    @Override
    public void setImageScaleX(float scaleX) {
        container.setImageScaleX(scaleX);
    }

    @Override
    public float getImageScaleY() {
        return container.getImageScaleY();
    }

    @Override
    public void setImageScaleY(float scaleY) {
        container.setImageScaleY(scaleY);
    }

    @Override
    public float getImageRotation() {
        return container.getImageRotation();
    }

    @Override
    public void setImageRotation(float rotation) {
        container.setImageRotation(rotation);
    }

    @Override
    public float getImageAlpha() {
        return container.getImageAlpha();
    }

    @Override
    public void setImageAlpha(float alpha) {
        container.setImageAlpha(alpha);
    }

    @Override
    public Color getImageColor() {
        return container.getImageColor();
    }

    @Override
    public void setImageColor(Color color) {
        container.setImageColor(color);
    }

    @Override
    public Shape getImageShape() {
        return container.getImageShape();
    }

    @Override
    public void setImageShape(Shape shape) {
        container.setImageShape(shape);
    }

}
