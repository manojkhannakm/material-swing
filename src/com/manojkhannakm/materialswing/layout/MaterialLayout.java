package com.manojkhannakm.materialswing.layout;

import com.manojkhannakm.materialswing.event.MaterialEvent;
import com.manojkhannakm.materialswing.swing.Material;

import java.awt.*;

/**
 * @author Manoj Khanna
 */

public class MaterialLayout implements LayoutManager2 { //TODO: Finish later

    @Override
    public void addLayoutComponent(String componentName, Component component) {
    }

    @Override
    public void addLayoutComponent(Component component, Object constraint) {
    }

    @Override
    public void removeLayoutComponent(Component component) {
    }

    @Override
    public Dimension preferredLayoutSize(Container container) {
        return null;
    }

    @Override
    public Dimension minimumLayoutSize(Container container) {
        return null;
    }

    @Override
    public Dimension maximumLayoutSize(Container container) {
        return null;
    }

    @Override
    public float getLayoutAlignmentX(Container container) {
        return 0.5f;
    }

    @Override
    public float getLayoutAlignmentY(Container container) {
        return 0.5f;
    }

    @Override
    public void layoutContainer(Container container) {
        for (Component component : container.getComponents()) {
            if (component instanceof Material) {
                Material material = (Material) component;
                material.setMaterialBounds(material.getMaterialBounds());
                material.materialChanged(MaterialEvent.RELAYOUT);
            }
        }
    }

    @Override
    public void invalidateLayout(Container container) {
    }

}
