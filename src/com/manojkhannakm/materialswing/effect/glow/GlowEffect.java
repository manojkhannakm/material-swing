package com.manojkhannakm.materialswing.effect.glow;

import com.manojkhannakm.materialswing.animator.TimingSource;
import com.manojkhannakm.materialswing.animator.interpolator.StandardInterpolator;
import com.manojkhannakm.materialswing.animator.target.ValueTarget;
import com.manojkhannakm.materialswing.effect.MaterialEffect;
import com.manojkhannakm.materialswing.property.MaterialProperty;
import com.manojkhannakm.materialswing.style.MaterialColor;
import com.manojkhannakm.materialswing.style.MaterialImage;
import com.manojkhannakm.materialswing.swing.Material;
import com.manojkhannakm.materialswing.util.GraphicUtils;
import com.manojkhannakm.materialswing.util.ImageUtils;
import org.jdesktop.core.animation.timing.Animator;

import java.awt.*;
import java.awt.event.HierarchyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.util.concurrent.TimeUnit;

/**
 * @author Manoj Khanna
 */

public class GlowEffect extends MaterialEffect implements Glow {

    private int size = 0;
    private Color color = MaterialColor.BLACK.withAlpha(0.1f);
    private Type type = Type.INNER;

    private Animator alphaAnimator;
    private float alphaOffset = 0.0f;

    private Animator sizeAnimator;
    private int sizeOffset = 0;

    private Listener listener;

    public GlowEffect(Material material, boolean attached) {
        super(material, attached);

        Component component = (Component) material;
        component.addHierarchyListener(e -> {
            if ((e.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) == 0) {
                return;
            }

            int width = material.getMaterialWidth(), height = material.getMaterialHeight();

            Shape shape = material.getMaterialShape();

            if (component.isShowing()) {
                createBlurImage(width, height, shape);
            } else {
                destroyBlurImage(width, height, shape);
            }
        });

        material.addMaterialListener(MaterialProperty.WIDTH, material, event -> {
            int height = material.getMaterialHeight();

            Shape shape = material.getMaterialShape();

            destroyBlurImage(event.getOldValue(), height, shape);
            createBlurImage(event.getNewValue(), height, shape);
        });
        material.addMaterialListener(MaterialProperty.HEIGHT, material, event -> {
            int width = material.getMaterialWidth();

            Shape shape = material.getMaterialShape();

            destroyBlurImage(width, event.getOldValue(), shape);
            createBlurImage(width, event.getNewValue(), shape);
        });
        material.addMaterialListener(MaterialProperty.SHAPE, material, event -> {
            int width = material.getMaterialWidth(), height = material.getMaterialHeight();

            destroyBlurImage(width, height, event.getOldValue());
            createBlurImage(width, height, event.getNewValue());
        });
    }

    public GlowEffect(Material material) {
        this(material, true);
    }

    @Override
    protected void effectAttached() {
        if (listener == null) {
            listener = new Listener();
        }

        ((Component) material).addMouseListener(listener);
    }

    @Override
    protected void effectDetached() {
        if (listener == null) {
            return;
        }

        ((Component) material).removeMouseListener(listener);
    }

    @Override
    public void glowPaint(Graphics2D graphics) {
        if (alphaOffset == 0.0f) {
            return;
        }

        int size = this.size + sizeOffset;

        if (size == 0) {
            return;
        }

        AlphaComposite composite = (AlphaComposite) graphics.getComposite();
        graphics.setComposite(composite.derive(composite.getAlpha() * alphaOffset));

        int width = material.getMaterialWidth(), height = material.getMaterialHeight(),
                offset = getGlowOffset(), imageWidth = width + 2 * offset, imageHeight = height + 2 * offset;

        Shape shape = material.getMaterialShape();

        MaterialImage image = new MaterialImage(imageWidth, imageHeight);

        Graphics2D imageGraphics = GraphicUtils.getImageGraphics(image.createGraphics());

        int radius = size + 1, blurOffset = ImageUtils.getBlurOffset(radius);

        if (type == Type.INNER) {
            imageGraphics.setColor(color);
            imageGraphics.fill(shape);
            imageGraphics.setComposite(AlphaComposite.DstOut);
            imageGraphics.drawImage(ImageUtils.getBlurImage(width, height,
                    shape, radius), offset - blurOffset, offset - blurOffset, null);
        } else if (type == Type.OUTER) {
            imageGraphics.drawImage(ImageUtils.getBlurImage(width, height,
                    shape, radius), offset - blurOffset, offset - blurOffset, null);
            imageGraphics.setComposite(AlphaComposite.SrcIn);
            imageGraphics.setColor(color);
            imageGraphics.fillRect(0, 0, imageWidth, imageHeight);
        }

        imageGraphics.dispose();

        graphics.drawImage(image, 0, 0, null);
    }

    @Override
    public int getGlowSize() {
        return size;
    }

    @Override
    public void setGlowSize(int size) {
        if (size < 0) {
            size = 0;
        }

        int oldSize = this.size;

        this.size = size;

        material.materialPropertyChanged(MaterialProperty.GLOW_SIZE, (Glow) material, oldSize, size);

        if (type == Type.INNER) {
            material.materialRepaint();
        } else if (type == Type.OUTER) {
            material.materialRevalidate();
        }
    }

    @Override
    public Color getGlowColor() {
        return color;
    }

    @Override
    public void setGlowColor(Color color) {
        if (color == null) {
            color = MaterialColor.BLACK.withAlpha(0.1f);
        }

        Color oldColor = this.color;

        this.color = color;

        material.materialPropertyChanged(MaterialProperty.GLOW_COLOR, (Glow) material, oldColor, color);
        material.materialRepaint();
    }

    @Override
    public Type getGlowType() {
        return type;
    }

    @Override
    public void setGlowType(Type type) {
        if (type == null) {
            type = Type.INNER;
        }

        Type oldType = this.type;

        this.type = type;

        material.materialPropertyChanged(MaterialProperty.GLOW_TYPE, (Glow) material, oldType, type);
        material.materialRepaint();
    }

    @Override
    public int getGlowOffset() {
        int size = this.size + sizeOffset;

        if (size == 0) {
            return 0;
        }

        if (type == Type.INNER) {
            return 0;
        } else if (type == Type.OUTER) {
            return ImageUtils.getBlurOffset(size + 1);
        }

        return -1;
    }

    private void createBlurImage(int width, int height, Shape shape) {
        if (!((Component) material).isShowing() || width == 0 || height == 0) {
            return;
        }

        Rectangle2D bounds = shape.getBounds2D();

        if (bounds.getWidth() == 0.0 || bounds.getHeight() == 0.0) {
            return;
        }

        ImageUtils.createBlurImage(width, height, shape);
    }

    private void destroyBlurImage(int width, int height, Shape shape) {
        if (width == 0 || height == 0) {
            return;
        }

        Rectangle2D bounds = shape.getBounds2D();

        if (bounds.getWidth() == 0.0 || bounds.getHeight() == 0.0) {
            return;
        }

        ImageUtils.destroyBlurImage(width, height, shape);
    }

    public void show(float alphaOffset) {
        if (alphaOffset < 0.0f) {
            alphaOffset = 0.0f;
        } else if (alphaOffset > 1.0f) {
            alphaOffset = 1.0f;
        }

        if (alphaAnimator != null && alphaAnimator.isRunning()) {
            alphaAnimator.cancel();
        }

        if (alphaOffset <= this.alphaOffset) {
            return;
        }

        int duration = Math.round(200.0f * (alphaOffset - this.alphaOffset));

        if (duration == 0) {
            this.alphaOffset = alphaOffset;
            return;
        }

        alphaAnimator = new Animator.Builder(TimingSource.getInstance())
                .addTarget(ValueTarget.getToTarget(() -> this.alphaOffset, value -> {
                    this.alphaOffset = value;

                    material.materialRepaint();
                }, alphaOffset))
                .setDuration(duration, TimeUnit.MILLISECONDS)
                .setInterpolator(StandardInterpolator.getInstance())
                .build();
        alphaAnimator.start();
    }

    public void hide(float alphaOffset) {
        if (alphaOffset < 0.0f) {
            alphaOffset = 0.0f;
        } else if (alphaOffset > 1.0f) {
            alphaOffset = 1.0f;
        }

        if (alphaAnimator != null && alphaAnimator.isRunning()) {
            alphaAnimator.cancel();
        }

        if (alphaOffset >= this.alphaOffset) {
            return;
        }

        int duration = Math.round(200.0f * (this.alphaOffset - alphaOffset));

        if (duration == 0) {
            this.alphaOffset = alphaOffset;
            return;
        }

        alphaAnimator = new Animator.Builder(TimingSource.getInstance())
                .addTarget(ValueTarget.getToTarget(() -> this.alphaOffset, value -> {
                    this.alphaOffset = value;

                    material.materialRepaint();
                }, alphaOffset))
                .setDuration(duration, TimeUnit.MILLISECONDS)
                .setInterpolator(StandardInterpolator.getInstance())
                .build();
        alphaAnimator.start();
    }

    public void grow(int sizeOffset) {
        if (sizeOffset < 0) {
            sizeOffset = 0;
        }

        if (sizeAnimator != null && sizeAnimator.isRunning()) {
            sizeAnimator.cancel();
        }

        if (sizeOffset <= this.sizeOffset) {
            return;
        }

        int duration = Math.round(200.0f * (sizeOffset - this.sizeOffset) / 6.0f);

        if (duration == 0) {
            this.sizeOffset = sizeOffset;
            return;
        }

        sizeAnimator = new Animator.Builder(TimingSource.getInstance())
                .addTarget(ValueTarget.getToTarget(() -> this.sizeOffset, value -> {
                    this.sizeOffset = value;

                    if (type == Type.INNER) {
                        material.materialRepaint();
                    } else if (type == Type.OUTER) {
                        material.materialRevalidate();
                    }
                }, sizeOffset))
                .setDuration(duration, TimeUnit.MILLISECONDS)
                .setInterpolator(StandardInterpolator.getInstance())
                .build();
        sizeAnimator.start();
    }

    public void shrink(int sizeOffset) {
        if (sizeOffset < 0) {
            sizeOffset = 0;
        }

        if (sizeAnimator != null && sizeAnimator.isRunning()) {
            sizeAnimator.cancel();
        }

        if (sizeOffset >= this.sizeOffset) {
            return;
        }

        int duration = Math.round(200.0f * (this.sizeOffset - sizeOffset) / 6.0f);

        if (duration == 0) {
            this.sizeOffset = sizeOffset;
            return;
        }

        sizeAnimator = new Animator.Builder(TimingSource.getInstance())
                .addTarget(ValueTarget.getToTarget(() -> this.sizeOffset, value -> {
                    this.sizeOffset = value;

                    if (type == Type.INNER) {
                        material.materialRepaint();
                    } else if (type == Type.OUTER) {
                        material.materialRevalidate();
                    }
                }, sizeOffset))
                .setDuration(duration, TimeUnit.MILLISECONDS)
                .setInterpolator(StandardInterpolator.getInstance())
                .build();
        sizeAnimator.start();
    }

    public float getAlphaOffset() {
        return alphaOffset;
    }

    public void setAlphaOffset(float alphaOffset) {
        if (alphaOffset < 0.0f) {
            alphaOffset = 0.0f;
        } else if (alphaOffset > 1.0f) {
            alphaOffset = 1.0f;
        }

        this.alphaOffset = alphaOffset;
    }

    public int getSizeOffset() {
        return sizeOffset;
    }

    public void setSizeOffset(int sizeOffset) {
        if (sizeOffset < 0) {
            sizeOffset = 0;
        }

        this.sizeOffset = sizeOffset;
    }

    private class Listener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            show(1.0f);
            grow(6);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            hide(0.0f);
            shrink(0);
        }

    }

}
