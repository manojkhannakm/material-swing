package com.manojkhannakm.materialswing.effect.overlay;

import com.manojkhannakm.materialswing.animator.TimingSource;
import com.manojkhannakm.materialswing.animator.interpolator.StandardInterpolator;
import com.manojkhannakm.materialswing.animator.target.AnimatorTarget;
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
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.concurrent.TimeUnit;

/**
 * @author Manoj Khanna
 */

public class OverlayEffect extends MaterialEffect implements Overlay {

    private Color color = MaterialColor.BLACK.withAlpha(0.1f);
    private Type type = Type.NORMAL;

    private boolean painting = true;
    private MaterialImage image;

    private Animator alphaAnimator;
    private float alphaOffset = 0.0f;

    private Listener listener;

    public OverlayEffect(Material material, boolean attached) {
        super(material, attached);
    }

    public OverlayEffect(Material material) {
        this(material, true);
    }

    @Override
    protected void effectAttached() {
        if (listener == null) {
            listener = new Listener();
        }

        Component component = (Component) material;

        component.addMouseListener(listener);
        component.addFocusListener(listener);
    }

    @Override
    protected void effectDetached() {
        if (listener == null) {
            return;
        }

        Component component = (Component) material;

        component.removeMouseListener(listener);
        component.removeFocusListener(listener);
    }

    @Override
    public void overlayPaint(Graphics2D graphics) {
        if (!painting || alphaOffset == 0.0f) {
            return;
        }

        AlphaComposite composite = (AlphaComposite) graphics.getComposite();
        graphics.setComposite(composite.derive(composite.getAlpha() * alphaOffset));

        if (type != Type.NORMAL) {
            graphics.drawImage(image, 0, 0, null);
        }

        graphics.setColor(color);
        graphics.fill(material.getMaterialShape());
    }

    @Override
    public Color getOverlayColor() {
        return color;
    }

    @Override
    public void setOverlayColor(Color color) {
        if (color == null) {
            color = MaterialColor.BLACK.withAlpha(0.1f);
        }

        Color oldColor = this.color;

        this.color = color;

        material.materialPropertyChanged(MaterialProperty.OVERLAY_COLOR, (Overlay) material, oldColor, color);
        material.materialRepaint();
    }

    @Override
    public Type getOverlayType() {
        return type;
    }

    @Override
    public void setOverlayType(Type type) {
        if (type == null) {
            type = Type.NORMAL;
        }

        Type oldType = this.type;

        this.type = type;

        material.materialPropertyChanged(MaterialProperty.OVERLAY_TYPE, (Overlay) material, oldType, type);
        material.materialRepaint();
    }

    private void createBlurImage() {
        float scaledWidth = material.getMaterialScaledWidth(), scaledHeight = material.getMaterialScaledHeight(),
                rotatedWidth = material.getEffectRotatedWidth(), rotatedHeight = material.getEffectRotatedHeight();

        MaterialImage image = new MaterialImage(material.getMaterialWidth(), material.getMaterialHeight());

        Graphics2D graphics = GraphicUtils.getImageGraphics(image.createGraphics());
        graphics.scale(1.0 / material.getMaterialScaleX(), 1.0 / material.getMaterialScaleY());
        graphics.rotate(2.0 * Math.PI * -material.getMaterialRotation(), scaledWidth / 2.0, scaledHeight / 2.0);
        graphics.translate((scaledWidth - rotatedWidth) / 2.0, (scaledHeight - rotatedHeight) / 2.0);

        painting = false;

        ((Component) material).paintAll(graphics);

        painting = true;

        graphics.dispose();

        if (type == Type.SOFT) {
            ImageUtils.getBlurFilter(4).filter(image, image);
        } else if (type == Type.MEDIUM) {
            ImageUtils.getBlurFilter(8).filter(image, image);
        } else if (type == Type.HARD) {
            ImageUtils.getBlurFilter(12).filter(image, image);
        }

        this.image = image.withShape(material.getMaterialShape());
    }

    private void destroyBlurImage() {
        image = null;
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
                .addTarget(new AnimatorTarget() {

                    @Override
                    public void animatorStarted(Animator animator) {
                        if (OverlayEffect.this.alphaOffset > 0.0f) {
                            createBlurImage();
                        }
                    }

                })
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
                .addTarget(new AnimatorTarget() {

                    @Override
                    public void animatorStopped(Animator animator) {
                        if (OverlayEffect.this.alphaOffset == 0.0f) {
                            destroyBlurImage();
                        }
                    }

                })
                .setDuration(duration, TimeUnit.MILLISECONDS)
                .setInterpolator(StandardInterpolator.getInstance())
                .build();
        alphaAnimator.start();
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

        if (alphaOffset > 0.0f) {
            createBlurImage();
        } else if (alphaOffset == 0.0f) {
            destroyBlurImage();
        }
    }

    private class Listener implements MouseListener, FocusListener {

        private boolean hovered, focused;

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
            hovered = true;

            show(1.0f);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            hovered = false;

            if (!focused) {
                hide(0.0f);
            }
        }

        @Override
        public void focusGained(FocusEvent e) {
            focused = true;

            show(1.0f);
        }

        @Override
        public void focusLost(FocusEvent e) {
            focused = false;

            if (!hovered) {
                hide(0.0f);
            }
        }

    }

}
