package com.manojkhannakm.materialswing.effect.ripple;

import com.manojkhannakm.materialswing.animator.TimingSource;
import com.manojkhannakm.materialswing.animator.interpolator.DecelerationInterpolator;
import com.manojkhannakm.materialswing.animator.interpolator.StandardInterpolator;
import com.manojkhannakm.materialswing.animator.target.AnimatorTarget;
import com.manojkhannakm.materialswing.animator.target.ValueTarget;
import com.manojkhannakm.materialswing.effect.MaterialEffect;
import com.manojkhannakm.materialswing.property.MaterialProperty;
import com.manojkhannakm.materialswing.style.MaterialColor;
import com.manojkhannakm.materialswing.style.MaterialImage;
import com.manojkhannakm.materialswing.swing.Material;
import com.manojkhannakm.materialswing.util.GraphicUtils;
import com.manojkhannakm.materialswing.util.MaterialUtils;
import org.jdesktop.core.animation.timing.Animator;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author Manoj Khanna
 */

public class RippleEffect extends MaterialEffect implements Ripple {

    private final Map<RippleKey, Set<RippleValue>> rippleMap = Collections.synchronizedMap(new LinkedHashMap<>());

    private Color color = MaterialColor.BLACK.withAlpha(0.1f);
    private Type type = Type.MEDIUM;

    private Listener listener;

    public RippleEffect(Material material, boolean attached) {
        super(material, attached);
    }

    public RippleEffect(Material material) {
        this(material, true);
    }

    @Override
    protected void effectAttached() {
        if (listener == null) {
            listener = new Listener();
        }

        Component component = (Component) this.material;

        component.addKeyListener(listener);
        component.addMouseListener(listener);
    }

    @Override
    protected void effectDetached() {
        if (listener == null) {
            return;
        }

        Component component = (Component) this.material;

        component.removeKeyListener(listener);
        component.removeMouseListener(listener);
    }

    @Override
    public void ripplePaint(Graphics2D graphics) {
        if (rippleMap.isEmpty()) {
            return;
        }

        int imageWidth = material.getMaterialWidth(), imageHeight = material.getMaterialHeight();

        MaterialImage rippleImage = new MaterialImage(imageWidth, imageHeight);

        Graphics2D rippleGraphics = GraphicUtils.getImageGraphics(rippleImage.createGraphics());

        synchronized (rippleMap) {
            for (Set<RippleValue> rippleSet : rippleMap.values()) {
                //noinspection SynchronizationOnLocalVariableOrMethodParameter
                synchronized (rippleSet) {
                    for (RippleValue rippleValue : rippleSet) {
                        rippleValue.paint(rippleGraphics);
                    }
                }
            }
        }

        rippleGraphics.dispose();

        MaterialImage image = new MaterialImage(imageWidth, imageHeight);

        Graphics2D imageGraphics = GraphicUtils.getImageGraphics(image.createGraphics());
        imageGraphics.fill(material.getMaterialShape());
        imageGraphics.setComposite(AlphaComposite.SrcIn);
        imageGraphics.drawImage(rippleImage, 0, 0, null);
        imageGraphics.dispose();

        graphics.drawImage(image, 0, 0, null);
    }

    @Override
    public Color getRippleColor() {
        return color;
    }

    @Override
    public void setRippleColor(Color color) {
        if (color == null) {
            color = MaterialColor.BLACK.withAlpha(0.1f);
        }

        Color oldColor = this.color;

        this.color = color;

        material.materialPropertyChanged(MaterialProperty.RIPPLE_COLOR, (Ripple) material, oldColor, color);
        material.materialRepaint();
    }

    @Override
    public Type getRippleType() {
        return type;
    }

    @Override
    public void setRippleType(Type type) {
        if (type == null) {
            type = Type.MEDIUM;
        }

        Type oldType = this.type;

        this.type = type;

        material.materialPropertyChanged(MaterialProperty.RIPPLE_TYPE, (Ripple) material, oldType, type);
        material.materialRepaint();
    }

    public void show(int x, int y) {
        RippleValue rippleValue = new RippleValue(x, y);

        rippleMap.computeIfAbsent(new RippleKey(x, y),
                key -> Collections.synchronizedSet(new LinkedHashSet<>())).add(rippleValue);

        rippleValue.show();
    }

    public void hide(int x, int y) {
        RippleKey rippleKey = new RippleKey(x, y);

        if (!rippleMap.containsKey(rippleKey)) {
            return;
        }

        Set<RippleValue> rippleSet = rippleMap.get(rippleKey);

        //noinspection SynchronizationOnLocalVariableOrMethodParameter
        synchronized (rippleSet) {
            ArrayList<RippleValue> rippleList = new ArrayList<>(rippleSet);

            for (int i = rippleList.size() - 1; i >= 0; i--) {
                RippleValue rippleValue = rippleList.get(i);

                if (!rippleValue.isHiding()) {
                    rippleValue.hide();
                    break;
                }
            }
        }
    }

    private class RippleKey {

        private final int x, y;

        private RippleKey(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }

            if (!(obj instanceof RippleKey)) {
                return false;
            }

            RippleKey rippleKey = (RippleKey) obj;

            return rippleKey.x == x && rippleKey.y == y;
        }

    }

    private class RippleValue {

        private final int x, y;

        private final Color color;
        private final Type type;

        private Animator alphaAnimator;
        private float alpha;
        private int size;

        private boolean hiding;

        private RippleValue(int x, int y) {
            this.x = x;
            this.y = y;

            color = RippleEffect.this.color;
            type = RippleEffect.this.type;
        }

        private void paint(Graphics2D graphics) {
            graphics.setColor(new MaterialColor(color).withAlpha(Math.round(color.getAlpha() * alpha)));
            graphics.fillOval(x - Math.round(size / 2.0f), y - Math.round(size / 2.0f), size, size);
        }

        private void show() {
            long alphaDuration = 0;

            if (type == Type.SLOW) {
                alphaDuration = 600;
            } else if (type == Type.MEDIUM) {
                alphaDuration = 400;
            } else if (type == Type.FAST) {
                alphaDuration = 200;
            }

            alphaAnimator = new Animator.Builder(TimingSource.getInstance())
                    .addTarget(ValueTarget.getOfTarget(value -> {
                        alpha = value;

                        material.materialRepaint();
                    }, 0.0f, 1.0f))
                    .setDuration(alphaDuration, TimeUnit.MILLISECONDS)
                    .setInterpolator(StandardInterpolator.getInstance())
                    .build();
            alphaAnimator.start();

            Rectangle2D bounds = material.getMaterialShape().getBounds2D();

            long sizeDuration = 0;

            if (type == Type.SLOW) {
                sizeDuration = 1200;
            } else if (type == Type.MEDIUM) {
                sizeDuration = 800;
            } else if (type == Type.FAST) {
                sizeDuration = 400;
            }

            new Animator.Builder(TimingSource.getInstance())
                    .addTarget(ValueTarget.getOfTarget(value -> {
                        size = value;

                        material.materialRepaint();
                    }, 0, (int) Math.round(2.0 * Math.hypot(bounds.getWidth() + 2.0, bounds.getHeight() + 2.0))))
                    .setDuration(sizeDuration, TimeUnit.MILLISECONDS)
                    .setInterpolator(DecelerationInterpolator.getInstance())
                    .build()
                    .start();
        }

        private void hide() {
            hiding = true;

            long alphaStartDelay = 0;

            if (alphaAnimator.isRunning()) {
                long alphaDuration = alphaAnimator.getDuration(), alphaElapsedTime = TimeUnit.MILLISECONDS
                        .convert(alphaAnimator.getTotalElapsedTime(), TimeUnit.NANOSECONDS);

                if (alphaDuration > alphaElapsedTime) {
                    alphaStartDelay = alphaDuration - alphaElapsedTime;
                }
            }

            long alphaDuration = 0;

            if (type == Type.SLOW) {
                alphaDuration = 600;
            } else if (type == Type.MEDIUM) {
                alphaDuration = 400;
            } else if (type == Type.FAST) {
                alphaDuration = 200;
            }

            alphaAnimator = new Animator.Builder(TimingSource.getInstance())
                    .addTarget(ValueTarget.getOfTarget(value -> {
                        alpha = value;

                        material.materialRepaint();
                    }, 1.0f, 0.0f))
                    .addTarget(new AnimatorTarget() {

                        @Override
                        public void animatorStopped(Animator animator) {
                            RippleKey rippleKey = new RippleKey(x, y);

                            Set<RippleValue> rippleSet = rippleMap.get(rippleKey);

                            if (rippleSet.contains(RippleValue.this)) {
                                rippleSet.remove(RippleValue.this);

                                if (rippleSet.isEmpty()) {
                                    rippleMap.remove(rippleKey);
                                }
                            }
                        }

                    })
                    .setStartDelay(alphaStartDelay, TimeUnit.MILLISECONDS)
                    .setDuration(alphaDuration, TimeUnit.MILLISECONDS)
                    .setInterpolator(StandardInterpolator.getInstance())
                    .build();
            alphaAnimator.start();
        }

        private boolean isHiding() {
            return hiding;
        }

    }

    private class Listener implements KeyListener, MouseListener {

        private int keyX, keyY, mouseX, mouseY;

        @Override
        public void keyTyped(KeyEvent e) {
            System.out.println("typed");
        }

        @Override
        public void keyPressed(KeyEvent e) {
            System.out.println("pressed");

            keyX = Math.round(material.getMaterialWidth() / 2.0f);
            keyY = Math.round(material.getMaterialHeight() / 2.0f);

            show(keyX, keyY);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            System.out.println("released");

            hide(keyX, keyY);
        }

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            Point point = MaterialUtils.getMaterialPoint(material, e.getPoint());

            mouseX = point.x;
            mouseY = point.y;

            show(mouseX, mouseY);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            hide(mouseX, mouseY);
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }

    }

}
