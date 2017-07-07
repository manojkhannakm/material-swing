package com.manojkhannakm.materialswing.effect.shadow;

import com.manojkhannakm.materialswing.animator.TimingSource;
import com.manojkhannakm.materialswing.animator.interpolator.StandardInterpolator;
import com.manojkhannakm.materialswing.animator.target.ValueTarget;
import com.manojkhannakm.materialswing.effect.MaterialEffect;
import com.manojkhannakm.materialswing.event.MaterialPropertyEvent;
import com.manojkhannakm.materialswing.event.MaterialPropertyListener;
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

public class ShadowEffect extends MaterialEffect implements Shadow {

    private int size = 2;
    private Color color = MaterialColor.BLACK.withAlpha(0.1f);
    private Type type = Type.KEY_AMBIENT;

    private Animator alphaAnimator;
    private float alphaOffset = 1.0f;

    private Animator sizeAnimator;
    private int sizeOffset = 0;

    private Listener listener;

    public ShadowEffect(Material material, boolean attached) {
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

    public ShadowEffect(Material material) {
        this(material, true);
    }

    @Override
    protected void effectAttached() {
        if (listener == null) {
            listener = new Listener();
        }

        ((Component) material).addMouseListener(listener);
        material.addMaterialListener(MaterialProperty.Z, material, listener);
        material.addMaterialListener(MaterialProperty.TRANSLATION_Z, material, listener);
    }

    @Override
    protected void effectDetached() {
        if (listener == null) {
            return;
        }

        ((Component) material).removeMouseListener(listener);
        material.removeMaterialListener(MaterialProperty.Z, material, listener);
        material.removeMaterialListener(MaterialProperty.TRANSLATION_Z, material, listener);
    }

    @Override
    public void shadowPaint(Graphics2D graphics) {
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
                offset = getShadowOffset(), imageWidth = width + 2 * offset, imageHeight = height + 2 * offset;

        Shape shape = material.getMaterialShape();

        if (type == Type.KEY || type == Type.KEY_AMBIENT) {
            MaterialImage image = new MaterialImage(imageWidth, imageHeight);

            Container container = (Container) material;

            float rotation = 0.0f;

            while (container instanceof Material) {
                rotation += ((Material) container).getMaterialRotation();

                container = container.getParent();
            }

            int radius = size + 2, blurOffset = ImageUtils.getBlurOffset(radius);

            Graphics2D imageGraphics = GraphicUtils.getImageGraphics(image.createGraphics());
            imageGraphics.rotate(2.0 * Math.PI * -rotation, imageWidth / 2.0, imageHeight / 2.0);
            imageGraphics.translate(0.0, size);
            imageGraphics.rotate(2.0 * Math.PI * rotation, imageWidth / 2.0, imageHeight / 2.0);
            imageGraphics.drawImage(ImageUtils.getBlurImage(width, height,
                    shape, radius), offset - blurOffset, offset - blurOffset, null);
            imageGraphics.setComposite(AlphaComposite.SrcIn);
            imageGraphics.setColor(new MaterialColor(color).withAlpha(Math.min(color.getAlpha() * 2, 255)));
            imageGraphics.fillRect(0, 0, imageWidth, imageHeight);
            imageGraphics.dispose();

            graphics.drawImage(image, 0, 0, null);
        }

        if (type == Type.AMBIENT || type == Type.KEY_AMBIENT) {
            MaterialImage image = new MaterialImage(imageWidth, imageHeight);

            int radius = size + 1, blurOffset = ImageUtils.getBlurOffset(radius);

            Graphics2D imageGraphics = GraphicUtils.getImageGraphics(image.createGraphics());
            imageGraphics.drawImage(ImageUtils.getBlurImage(width, height,
                    shape, radius), offset - blurOffset, offset - blurOffset, null);
            imageGraphics.setComposite(AlphaComposite.SrcIn);
            imageGraphics.setColor(color);
            imageGraphics.fillRect(0, 0, imageWidth, imageHeight);
            imageGraphics.dispose();

            graphics.drawImage(image, 0, 0, null);
        }
    }

    @Override
    public int getShadowSize() {
        return size;
    }

    @Override
    public void setShadowSize(int size) {
        if (size < 0) {
            size = 2;
        }

        int oldSize = this.size;

        this.size = size;

        material.materialPropertyChanged(MaterialProperty.SHADOW_SIZE, (Shadow) material, oldSize, size);
        material.materialRevalidate();
    }

    @Override
    public Color getShadowColor() {
        return color;
    }

    @Override
    public void setShadowColor(Color color) {
        if (color == null) {
            color = MaterialColor.BLACK.withAlpha(0.1f);
        }

        Color oldColor = this.color;

        this.color = color;

        material.materialPropertyChanged(MaterialProperty.SHADOW_COLOR, (Shadow) material, oldColor, color);
        material.materialRepaint();
    }

    @Override
    public Type getShadowType() {
        return type;
    }

    @Override
    public void setShadowType(Type type) {
        if (type == null) {
            type = Type.KEY_AMBIENT;
        }

        Type oldType = this.type;

        this.type = type;

        material.materialPropertyChanged(MaterialProperty.SHADOW_TYPE, (Shadow) material, oldType, type);
        material.materialRepaint();
    }

    @Override
    public int getShadowOffset() {
        int size = this.size + sizeOffset;

        if (size == 0) {
            return 0;
        }

        if (type == Type.KEY) {
            return 2 * ImageUtils.getBlurOffset(size + 2);
        } else if (type == Type.AMBIENT) {
            return ImageUtils.getBlurOffset(size + 1);
        } else if (type == Type.KEY_AMBIENT) {
            return 2 * ImageUtils.getBlurOffset(size + 2);
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

                    material.materialRevalidate();
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

                    material.materialRevalidate();
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

    private class Listener implements MouseListener, MaterialPropertyListener<Material, Integer> {

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (size > 0) {
                grow(6);
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (size > 0) {
                shrink(0);
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }

        @Override
        public void materialPropertyChanged(MaterialPropertyEvent<Material, Integer> event) {
            int z = material.getMaterialZ() + material.getMaterialTranslationZ();

            if (z < 0) {
                z = 0;
            }

            setShadowSize(z);
        }

    }

}
