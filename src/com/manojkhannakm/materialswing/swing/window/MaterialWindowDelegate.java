package com.manojkhannakm.materialswing.swing.window;

import com.manojkhannakm.materialswing.event.MaterialEvent;
import com.manojkhannakm.materialswing.property.MaterialProperty;
import com.manojkhannakm.materialswing.style.MaterialColor;
import com.manojkhannakm.materialswing.swing.MaterialDelegate;
import com.manojkhannakm.materialswing.swing.component.MaterialPanel;
import com.manojkhannakm.materialswing.util.ObjectUtils;

import javax.swing.*;
import java.awt.*;

/**
 * @author Manoj Khanna
 */

public abstract class MaterialWindowDelegate extends MaterialDelegate implements MaterialWindow {

    private static final int WINDOW_PANE_HEIGHT = 32;

    private RootPane rootPane;

    public MaterialWindowDelegate(MaterialWindow window) {
        super(window);

        ObjectUtils.verifyInstanceOf(window, Window.class, "window");

        if (window instanceof JFrame) {
            ((JFrame) window).setUndecorated(true);
        } else if (window instanceof JDialog) {
            ((JDialog) window).setUndecorated(true);
        }

        Window swingWindow = (Window) window;
        swingWindow.setLayout(new LayoutManager() {

            @Override
            public void addLayoutComponent(String name, Component comp) {
            }

            @Override
            public void removeLayoutComponent(Component comp) {
            }

            @Override
            public Dimension preferredLayoutSize(Container parent) {
                return getPreferredSize();
            }

            @Override
            public Dimension minimumLayoutSize(Container parent) {
                return preferredLayoutSize(parent);
            }

            @Override
            public void layoutContainer(Container parent) {
                int offset = getEffectOffset();

                rootPane.setBounds(offset, offset, getMaterialWidth(), getMaterialHeight());
            }

        });

        addMaterialListener(MaterialEvent.REVALIDATE, event -> {
            setMaterialBounds(getMaterialBounds());

            materialRepaint();
        });
        addMaterialListener(MaterialProperty.ALPHA, window, event -> {
            Color color = getMaterialColor();

            swingWindow.setBackground(new MaterialColor(color)
                    .withAlpha(Math.round(color.getAlpha() * event.getNewValue())));
        });
        addMaterialListener(MaterialProperty.COLOR, window, event -> {
            Color color = event.getNewValue();

            swingWindow.setBackground(new MaterialColor(color)
                    .withAlpha(Math.round(color.getAlpha() * getMaterialAlpha())));
        });
    }

    protected abstract void delegatePack();

    @Override
    public JRootPane createRootPane() {
        rootPane = new RootPane();

        return rootPane;
    }

    @Override
    public void pack() {
        delegatePack();

        setMaterialBounds(getMaterialBounds());
    }

    @Override
    public boolean isOpaque() {
        return false;
    }

    @Override
    public Dimension getPreferredSize() {
        float rotatedWidth = getEffectRotatedWidth(), rotatedHeight = getEffectRotatedHeight();

        return new Dimension(Math.round(rotatedWidth), Math.round(rotatedHeight));
    }

    @Override
    public Dimension getMinimumSize() {
        return getPreferredSize();
    }

    @Override
    public Dimension getMaximumSize() {
        return getPreferredSize();
    }

    private class RootPane extends JRootPane {

        private final WindowPane windowPane;

        private RootPane() {
            windowPane = new WindowPane();

            layeredPane.add(windowPane, new Integer(-29999));
        }

        @Override
        protected LayoutManager createRootLayout() {
            return new RootLayout() {

                @Override
                public Dimension preferredLayoutSize(Container parent) {
                    return new Dimension(getMaterialWidth(), getMaterialHeight());
                }

                @Override
                public Dimension minimumLayoutSize(Container parent) {
                    return preferredLayoutSize(parent);
                }

                @Override
                public Dimension maximumLayoutSize(Container target) {
                    return preferredLayoutSize(target);
                }

                @Override
                public void layoutContainer(Container parent) {
                    super.layoutContainer(parent);

                    int width = getWidth();

                    windowPane.setMaterialBounds(new Rectangle(width, windowPane.getMaterialHeight()));
                    ((MaterialPanel) contentPane).setMaterialBounds(
                            new Rectangle(0, WINDOW_PANE_HEIGHT, width, getHeight() - WINDOW_PANE_HEIGHT));
                }

            };
        }

        @Override
        protected Container createContentPane() {
            return new MaterialPanel();
        }

        @Override
        public MaterialPanel getContentPane() {
            return (MaterialPanel) super.getContentPane();
        }

        @Override
        public void setContentPane(Container content) {
            ObjectUtils.verifyInstanceOf(content, MaterialPanel.class, "contentPane");

            super.setContentPane(content);
        }

        public WindowPane getWindowPane() {
            return windowPane;
        }

    }

    private class WindowPane extends MaterialPanel {

        private WindowPane() {
            setMaterialHeight(WINDOW_PANE_HEIGHT);

            //

            material.addMaterialListener(MaterialProperty.COLOR, material,
                    event -> setMaterialColor(new MaterialColor(event.getNewValue()).withDarkness(0.2f)));
        }

    }

}
