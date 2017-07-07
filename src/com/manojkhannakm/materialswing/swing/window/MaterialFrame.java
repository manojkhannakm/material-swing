package com.manojkhannakm.materialswing.swing.window;

import com.manojkhannakm.materialswing.effect.overlay.Overlay;
import com.manojkhannakm.materialswing.effect.overlay.OverlayEffect;
import com.manojkhannakm.materialswing.effect.shadow.Shadow;
import com.manojkhannakm.materialswing.effect.shadow.ShadowEffect;
import com.manojkhannakm.materialswing.event.MaterialListener;
import com.manojkhannakm.materialswing.style.MaterialColor;

import javax.swing.*;
import java.awt.*;

/**
 * @author Manoj Khanna
 */

public class MaterialFrame extends JFrame implements MaterialWindow, Shadow, Overlay {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private final MaterialWindowDelegate delegate;

    private final ShadowEffect shadowEffect;
    private final OverlayEffect overlayEffect;

    public MaterialFrame() {
        delegate = new MaterialWindowDelegate(this) {

            @Override
            protected void delegatePaint(Graphics2D graphics) {
                MaterialFrame.super.paint(graphics);
            }

            @Override
            protected void delegatePack() {
                MaterialFrame.super.pack();
            }

            @Override
            protected int getDelegateX() {
                return GraphicsEnvironment.getLocalGraphicsEnvironment()
                        .getCenterPoint().x - Math.round(getMaterialWidth() / 2.0f);
            }

            @Override
            protected int getDelegateY() {
                return GraphicsEnvironment.getLocalGraphicsEnvironment()
                        .getCenterPoint().y - Math.round(getMaterialHeight() / 2.0f);
            }

            @Override
            protected int getDelegateWidth() {
                return WIDTH;
            }

            @Override
            protected int getDelegateHeight() {
                return HEIGHT;
            }

        };

        shadowEffect = new ShadowEffect(this, false);
        overlayEffect = new OverlayEffect(this, false);

        frameInit();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setMaterialColor(MaterialColor.GREY_50);

        setShadowSize(24);
        setShadowColor(MaterialColor.BLACK.withAlpha(0.4f));
        setShadowType(Shadow.Type.AMBIENT);

        setOverlayColor(MaterialColor.BLACK.withAlpha(0.4f));
    }

    @Override
    protected void frameInit() {
        if (delegate == null) {
            return;
        }

        super.frameInit();
    }

    @Override
    public JRootPane createRootPane() {
        return delegate.createRootPane();
    }

    @Override
    public void paint(Graphics g) {
        delegate.paint(g);
    }

    @Override
    public void pack() {
        delegate.pack();
    }

    @Override
    public boolean isOpaque() {
        return delegate.isOpaque();
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
    public void shadowPaint(Graphics2D graphics) {
        shadowEffect.shadowPaint(graphics);
    }

    @Override
    public int getShadowSize() {
        return shadowEffect.getShadowSize();
    }

    @Override
    public void setShadowSize(int size) {
        shadowEffect.setShadowSize(size);
    }

    @Override
    public Color getShadowColor() {
        return shadowEffect.getShadowColor();
    }

    @Override
    public void setShadowColor(Color color) {
        shadowEffect.setShadowColor(color);
    }

    @Override
    public Shadow.Type getShadowType() {
        return shadowEffect.getShadowType();
    }

    @Override
    public void setShadowType(Shadow.Type type) {
        shadowEffect.setShadowType(type);
    }

    @Override
    public int getShadowOffset() {
        return shadowEffect.getShadowOffset();
    }

    @Override
    public void overlayPaint(Graphics2D graphics) {
        overlayEffect.overlayPaint(graphics);
    }

    @Override
    public Color getOverlayColor() {
        return overlayEffect.getOverlayColor();
    }

    @Override
    public void setOverlayColor(Color color) {
        overlayEffect.setOverlayColor(color);
    }

    @Override
    public Overlay.Type getOverlayType() {
        return overlayEffect.getOverlayType();
    }

    @Override
    public void setOverlayType(Overlay.Type type) {
        overlayEffect.setOverlayType(type);
    }

}
