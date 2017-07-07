package com.manojkhannakm.materialswing.swing;

import com.manojkhannakm.materialswing.effect.glow.Glow;
import com.manojkhannakm.materialswing.effect.overlay.Overlay;
import com.manojkhannakm.materialswing.effect.ripple.Ripple;
import com.manojkhannakm.materialswing.effect.shadow.Shadow;
import com.manojkhannakm.materialswing.event.MaterialEvent;
import com.manojkhannakm.materialswing.event.MaterialEventDispatcher;
import com.manojkhannakm.materialswing.event.MaterialListener;
import com.manojkhannakm.materialswing.event.MaterialPropertyEvent;
import com.manojkhannakm.materialswing.property.MaterialProperty;
import com.manojkhannakm.materialswing.style.MaterialColor;
import com.manojkhannakm.materialswing.swing.component.MaterialComponent;
import com.manojkhannakm.materialswing.swing.window.MaterialWindow;
import com.manojkhannakm.materialswing.util.GraphicUtils;
import com.manojkhannakm.materialswing.util.MathUtils;
import com.manojkhannakm.materialswing.util.ObjectUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;

/**
 * @author Manoj Khanna
 */

public abstract class MaterialDelegate implements Material {

    private static final int REPAINT_DELAY = 8;
    private static final int REVALIDATE_DELAY = 8;

    protected final Material material;

    private final MaterialEventDispatcher eventDispatcher;

    private int x = 0, y = 0, z = 0, width = 0, height = 0, translationX = 0, translationY = 0, translationZ = 0;
    private float pivotX = 0.5f, pivotY = 0.5f, scaleX = 1.0f, scaleY = 1.0f, rotation = 0.0f, alpha = 1.0f;
    private Color color = MaterialColor.WHITE;
    private Shape shape = new Rectangle2D.Float();

    private boolean xChanged, yChanged, widthChanged, heightChanged;
    private float scaleXOffset, scaleYOffset, rotationXOffset, rotationYOffset;

    private boolean repaintQueued, revalidateQueued;
    private long repaintTime, revalidateTime;

    protected MaterialDelegate(Material material) {
        if (this instanceof MaterialWindow) {
            ObjectUtils.verifyNotNull(material, "window");
        } else if (this instanceof MaterialComponent) {
            ObjectUtils.verifyNotNull(material, "component");
        } else {
            ObjectUtils.verifyNotNull(material, "material");
        }

        ObjectUtils.verifyInstanceOf(material, Component.class, "material");

        this.material = material;

        eventDispatcher = new MaterialEventDispatcher(material);
    }

    protected abstract void delegatePaint(Graphics2D graphics);

    protected abstract int getDelegateX();

    protected abstract int getDelegateY();

    protected abstract int getDelegateWidth();

    protected abstract int getDelegateHeight();

    @Override
    public void paint(Graphics g) {
        if (width == 0 || height == 0 || alpha == 0.0f) {
            return;
        }

        Rectangle2D bounds = shape.getBounds2D();

        if (bounds.getWidth() == 0.0 || bounds.getHeight() == 0.0) {
            return;
        }

        Graphics2D graphics = GraphicUtils.getSwingGraphics(g);

        float scaledWidth = getEffectScaledWidth(), scaledHeight = getEffectScaledHeight(),
                rotatedWidth = getEffectRotatedWidth(), rotatedHeight = getEffectRotatedHeight();

        if (material instanceof MaterialWindow) {
            graphics.setBackground(MaterialColor.TRANSPARENT);
            graphics.clearRect(0, 0, Math.round(rotatedWidth), Math.round(rotatedHeight));
        }

        AlphaComposite composite = (AlphaComposite) graphics.getComposite();
        graphics.setComposite(composite.derive(composite.getAlpha() * alpha));

        graphics.translate((rotatedWidth - scaledWidth) / 2.0, (rotatedHeight - scaledHeight) / 2.0);
        graphics.rotate(2.0 * Math.PI * rotation, scaledWidth / 2.0, scaledHeight / 2.0);
        graphics.scale(scaleX, scaleY);

        int offset = getEffectOffset();

        if (material instanceof Shadow) {
            int shadowOffset = ((Shadow) material).getShadowOffset();

            Graphics2D shadowGraphics = (Graphics2D) graphics.create(offset - shadowOffset,
                    offset - shadowOffset, width + 2 * shadowOffset, height + 2 * shadowOffset);

            ((Shadow) material).shadowPaint(shadowGraphics);

            shadowGraphics.dispose();
        }

        Glow.Type glowType = null;

        if (material instanceof Glow) {
            glowType = ((Glow) material).getGlowType();
        }

        if (glowType != null && glowType == Glow.Type.OUTER) {
            int glowOffset = ((Glow) material).getGlowOffset();

            Graphics2D glowGraphics = (Graphics2D) graphics.create(offset - glowOffset,
                    offset - glowOffset, width + 2 * glowOffset, height + 2 * glowOffset);

            ((Glow) material).glowPaint(glowGraphics);

            glowGraphics.dispose();
        }

        Graphics2D materialGraphics = (Graphics2D) graphics.create(offset, offset, width, height);

        materialPaint(materialGraphics);

        materialGraphics.dispose();

        if (glowType != null && glowType == Glow.Type.INNER) {
            Graphics2D glowGraphics = (Graphics2D) graphics.create(offset, offset, width, height);

            ((Glow) material).glowPaint(glowGraphics);

            glowGraphics.dispose();
        }

        if (material instanceof Overlay) {
            Graphics2D overlayGraphics = (Graphics2D) graphics.create(offset, offset, width, height);

            ((Overlay) material).overlayPaint(overlayGraphics);

            overlayGraphics.dispose();
        }

        if (material instanceof Ripple) {
            Graphics2D rippleGraphics = (Graphics2D) graphics.create(offset, offset, width, height);

            ((Ripple) material).ripplePaint(rippleGraphics);

            rippleGraphics.dispose();
        }
    }

    @Override
    public void materialPaint(Graphics2D graphics) {
        graphics.setColor(color);
        graphics.fill(shape);
        graphics.clip(shape);

        double offset = getEffectOffset();

        graphics.translate(-offset, -offset);

        delegatePaint(graphics);
    }

    @Override
    public void materialRepaint() {
        long currentTime = System.currentTimeMillis(), elapsedTime = currentTime - repaintTime;

        if (elapsedTime >= REPAINT_DELAY) {
            if (!repaintQueued) {
                repaintTime = currentTime;

                ((Component) material).repaint();

                materialChanged(MaterialEvent.REPAINT);
            }

            return;
        }

        repaintQueued = true;

        Timer timer = new Timer((int) (REPAINT_DELAY - elapsedTime), e -> {
            repaintTime = System.currentTimeMillis();

            ((Component) material).repaint();

            repaintQueued = false;

            materialChanged(MaterialEvent.REPAINT);
        });
        timer.setRepeats(false);
        timer.start();
    }

    @Override
    public void materialRevalidate() {
        long currentTime = System.currentTimeMillis(), elapsedTime = currentTime - revalidateTime;

        if (elapsedTime >= REVALIDATE_DELAY) {
            if (!revalidateQueued) {
                revalidateTime = currentTime;

                ((Component) material).revalidate();

                materialChanged(MaterialEvent.REVALIDATE);
            }

            return;
        }

        revalidateQueued = true;

        Timer timer = new Timer((int) (REVALIDATE_DELAY - elapsedTime), e -> {
            revalidateTime = System.currentTimeMillis();

            ((Component) material).revalidate();

            revalidateQueued = false;

            materialChanged(MaterialEvent.REVALIDATE);
        });
        timer.setRepeats(false);
        timer.start();
    }

    @Override
    public void addMaterialListener(String eventName, MaterialListener listener) {
        eventDispatcher.addMaterialListener(eventName, listener);
    }

    @Override
    public void removeMaterialListener(String eventName, MaterialListener listener) {
        eventDispatcher.removeMaterialListener(eventName, listener);
    }

    @Override
    public void materialChanged(String eventName) {
        eventDispatcher.dispatchEvent(eventName, new MaterialEvent(material));
    }

    @Override
    public <O, V> void materialPropertyChanged(String propertyName, O object, V oldValue, V newValue) {
        eventDispatcher.dispatchEvent(propertyName, new MaterialPropertyEvent<>(material, object, oldValue, newValue));
    }

    @Override
    public int getMaterialX() {
        return x;
    }

    @Override
    public void setMaterialX(int x) {
        int oldX = this.x;

        this.x = x;

        xChanged = x != -1;

        materialPropertyChanged(MaterialProperty.X, material, oldX, x);
        materialRevalidate();
    }

    @Override
    public int getMaterialY() {
        return y;
    }

    @Override
    public void setMaterialY(int y) {
        int oldY = this.y;

        this.y = y;

        yChanged = y != -1;

        materialPropertyChanged(MaterialProperty.Y, material, oldY, y);
        materialRevalidate();
    }

    @Override
    public int getMaterialZ() {
        return z;
    }

    @Override
    public void setMaterialZ(int z) {
        if (z < 0) {
            z = 0;
        }

        int oldZ = this.z;

        this.z = z;

        materialPropertyChanged(MaterialProperty.Z, material, oldZ, z);
        materialRevalidate();
    }

    @Override
    public int getMaterialWidth() {
        return width;
    }

    @Override
    public void setMaterialWidth(int width) {
        if (width < -1) {
            width = 0;
        }

        int oldWidth = this.width;

        this.width = width;

        widthChanged = width != -1;

        materialPropertyChanged(MaterialProperty.WIDTH, material, oldWidth, width);
        materialRevalidate();

        if (shape instanceof RectangularShape) {
            RectangularShape oldShape = (RectangularShape) shape;

            if (oldShape.getX() == 0.0 && oldShape.getY() == 0.0
                    && oldShape.getWidth() == oldWidth && oldShape.getHeight() == height) {
                RectangularShape newShape = (RectangularShape) oldShape.clone();
                newShape.setFrame(0.0, 0.0, width, height);

                shape = newShape;

                materialPropertyChanged(MaterialProperty.SHAPE, material, oldShape, newShape);
                materialRepaint();
            }
        }
    }

    @Override
    public int getMaterialHeight() {
        return height;
    }

    @Override
    public void setMaterialHeight(int height) {
        if (height < -1) {
            height = 0;
        }

        int oldHeight = this.height;

        this.height = height;

        heightChanged = height != -1;

        materialPropertyChanged(MaterialProperty.HEIGHT, material, oldHeight, height);
        materialRevalidate();

        if (shape instanceof RectangularShape) {
            RectangularShape oldShape = (RectangularShape) shape;

            if (oldShape.getX() == 0.0 && oldShape.getY() == 0.0
                    && oldShape.getWidth() == width && oldShape.getHeight() == oldHeight) {
                RectangularShape newShape = (RectangularShape) oldShape.clone();
                newShape.setFrame(0.0, 0.0, width, height);

                shape = newShape;

                materialPropertyChanged(MaterialProperty.SHAPE, material, oldShape, newShape);
                materialRepaint();
            }
        }
    }

    @Override
    public int getMaterialTranslationX() {
        return translationX;
    }

    @Override
    public void setMaterialTranslationX(int translationX) {
        int oldTranslationX = this.translationX;

        this.translationX = translationX;

        materialPropertyChanged(MaterialProperty.TRANSLATION_X, material, oldTranslationX, translationX);
        materialRevalidate();
    }

    @Override
    public int getMaterialTranslationY() {
        return translationY;
    }

    @Override
    public void setMaterialTranslationY(int translationY) {
        int oldTranslationY = this.translationY;

        this.translationY = translationY;

        materialPropertyChanged(MaterialProperty.TRANSLATION_Y, material, oldTranslationY, translationY);
        materialRevalidate();
    }

    @Override
    public int getMaterialTranslationZ() {
        return translationZ;
    }

    @Override
    public void setMaterialTranslationZ(int translationZ) {
        int oldTranslationZ = this.translationZ;

        this.translationZ = translationZ;

        materialPropertyChanged(MaterialProperty.TRANSLATION_Z, material, oldTranslationZ, translationZ);
        materialRevalidate();
    }

    @Override
    public float getMaterialPivotX() {
        return pivotX;
    }

    @Override
    public void setMaterialPivotX(float pivotX) {
        if (pivotX < 0.0f) {
            pivotX = 0.0f;
        } else if (pivotX > 1.0f) {
            pivotX = 1.0f;
        }

        float oldPivotX = this.pivotX;

        this.pivotX = pivotX;

        materialPropertyChanged(MaterialProperty.PIVOT_X, material, oldPivotX, pivotX);
    }

    @Override
    public float getMaterialPivotY() {
        return pivotY;
    }

    @Override
    public void setMaterialPivotY(float pivotY) {
        if (pivotY < 0.0f) {
            pivotY = 0.0f;
        } else if (pivotY > 1.0f) {
            pivotY = 1.0f;
        }

        float oldPivotY = this.pivotY;

        this.pivotY = pivotY;

        materialPropertyChanged(MaterialProperty.PIVOT_Y, material, oldPivotY, pivotY);
    }

    @Override
    public float getMaterialScaleX() {
        return scaleX;
    }

    @Override
    public void setMaterialScaleX(float scaleX) {
        if (scaleX < 0.0f) {
            scaleX = 0.0f;
        }

        float oldScaleX = this.scaleX, scaledWidth = getMaterialScaledWidth();

        this.scaleX = scaleX;

        scaleXOffset -= (getMaterialScaledWidth() - scaledWidth) * pivotX;

        materialPropertyChanged(MaterialProperty.SCALE_X, material, oldScaleX, scaleX);
        materialRevalidate();
    }

    @Override
    public float getMaterialScaleY() {
        return scaleY;
    }

    @Override
    public void setMaterialScaleY(float scaleY) {
        if (scaleY < 0.0f) {
            scaleY = 0.0f;
        }

        float oldScaleY = this.scaleY, scaledHeight = getMaterialScaledHeight();

        this.scaleY = scaleY;

        scaleYOffset -= (getMaterialScaledHeight() - scaledHeight) * pivotY;

        materialPropertyChanged(MaterialProperty.SCALE_Y, material, oldScaleY, scaleY);
        materialRevalidate();
    }

    @Override
    public float getMaterialRotation() {
        return rotation;
    }

    @Override
    public void setMaterialRotation(float rotation) {
        float oldRotation = this.rotation,
                scaledWidth = getMaterialScaledWidth(), scaledHeight = getMaterialScaledHeight(),
                x = scaledWidth * pivotX, y = scaledHeight * pivotY,
                centerX = scaledWidth / 2.0f, centerY = scaledHeight / 2.0f,
                rotatedX = MathUtils.getRotatedX(x, y, centerX, centerY, oldRotation),
                rotatedY = MathUtils.getRotatedY(x, y, centerX, centerY, oldRotation);

        this.rotation = rotation;

        rotationXOffset -= MathUtils.getRotatedX(x, y, centerX, centerY, rotation) - rotatedX;
        rotationYOffset -= MathUtils.getRotatedY(x, y, centerX, centerY, rotation) - rotatedY;

        materialPropertyChanged(MaterialProperty.ROTATION, material, oldRotation, rotation);
        materialRevalidate();
    }

    @Override
    public float getMaterialAlpha() {
        return alpha;
    }

    @Override
    public void setMaterialAlpha(float alpha) {
        if (alpha < 0.0f) {
            alpha = 0.0f;
        } else if (alpha > 1.0f) {
            alpha = 1.0f;
        }

        float oldAlpha = this.alpha;

        this.alpha = alpha;

        materialPropertyChanged(MaterialProperty.ALPHA, material, oldAlpha, alpha);
        materialRepaint();
    }

    @Override
    public Color getMaterialColor() {
        return color;
    }

    @Override
    public void setMaterialColor(Color color) {
        if (color == null) {
            color = MaterialColor.WHITE;
        }

        Color oldColor = this.color;

        this.color = color;

        materialPropertyChanged(MaterialProperty.COLOR, material, oldColor, color);
        materialRepaint();
    }

    @Override
    public Shape getMaterialShape() {
        return shape;
    }

    @Override
    public void setMaterialShape(Shape shape) {
        if (shape == null) {
            shape = new Rectangle2D.Float();
        }

        if (shape instanceof RectangularShape) {
            RectangularShape rectShape = (RectangularShape) shape;

            if (rectShape.getX() == 0.0 && rectShape.getY() == 0.0
                    && rectShape.getWidth() == 0.0 && rectShape.getHeight() == 0.0) {
                rectShape.setFrame(0.0, 0.0, width, height);
            }
        }

        Shape oldShape = this.shape;

        this.shape = shape;

        materialPropertyChanged(MaterialProperty.SHAPE, material, oldShape, shape);
        materialRepaint();
    }

    @Override
    public Rectangle getMaterialBounds() {
        int x = this.x, y = this.y, width = this.width, height = this.height;

        if (!xChanged) {
            x = getDelegateX();
        }

        if (!yChanged) {
            y = getDelegateY();
        }

        if (!widthChanged) {
            width = getDelegateWidth();
        }

        if (!heightChanged) {
            height = getDelegateHeight();
        }

        return new Rectangle(x, y, width, height);
    }

    @Override
    public void setMaterialBounds(Rectangle bounds) {
        int oldX = x;

        x = bounds.x;

        materialPropertyChanged(MaterialProperty.X, material, oldX, x);

        int oldY = y;

        y = bounds.y;

        materialPropertyChanged(MaterialProperty.Y, material, oldY, y);

        int oldWidth = width;

        width = bounds.width;

        materialPropertyChanged(MaterialProperty.WIDTH, material, oldWidth, width);

        int oldHeight = height;

        height = bounds.height;

        materialPropertyChanged(MaterialProperty.HEIGHT, material, oldHeight, height);

        if (shape instanceof RectangularShape) {
            RectangularShape oldShape = (RectangularShape) shape;

            if (oldShape.getX() == 0.0 && oldShape.getY() == 0.0
                    && oldShape.getWidth() == oldWidth && oldShape.getHeight() == oldHeight) {
                RectangularShape newShape = (RectangularShape) oldShape.clone();
                newShape.setFrame(0.0, 0.0, width, height);

                shape = newShape;

                materialPropertyChanged(MaterialProperty.SHAPE, material, oldShape, newShape);
            }
        }

        float scaledWidth = getMaterialScaledWidth(), scaledHeight = getMaterialScaledHeight(),
                rotatedWidth = getEffectRotatedWidth(), rotatedHeight = getEffectRotatedHeight(),
                x = this.x + translationX + scaleXOffset + rotationXOffset + (scaledWidth - rotatedWidth) / 2.0f,
                y = this.y + translationY + scaleYOffset + rotationYOffset + (scaledHeight - rotatedHeight) / 2.0f;

        Component component = (Component) material;

        if (material instanceof MaterialComponent) {
            Container container = component.getParent();

            if (container instanceof Material) {
                int offset = ((Material) container).getEffectOffset();

                x += offset;
                y += offset;
            }
        }

        component.setBounds(Math.round(x), Math.round(y), Math.round(rotatedWidth), Math.round(rotatedHeight));
    }

    @Override
    public MaterialListener[] getMaterialListeners(String eventName) {
        return eventDispatcher.getMaterialListeners(eventName);
    }

    @Override
    public int getEffectOffset() {
        int shadowOffset = 0;

        if (material instanceof Shadow) {
            shadowOffset = ((Shadow) material).getShadowOffset();
        }

        int glowOffset = 0;

        if (material instanceof Glow) {
            glowOffset = ((Glow) material).getGlowOffset();
        }

        return Math.max(shadowOffset, glowOffset);
    }

}
