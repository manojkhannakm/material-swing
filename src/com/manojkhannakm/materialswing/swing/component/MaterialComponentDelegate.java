package com.manojkhannakm.materialswing.swing.component;

import com.manojkhannakm.materialswing.style.MaterialColor;
import com.manojkhannakm.materialswing.swing.MaterialDelegate;
import com.manojkhannakm.materialswing.util.ObjectUtils;

import javax.swing.*;
import java.awt.*;

/**
 * @author Manoj Khanna
 */

public abstract class MaterialComponentDelegate extends MaterialDelegate implements MaterialComponent {

    public MaterialComponentDelegate(MaterialComponent component) {
        super(component);

        ObjectUtils.verifyInstanceOf(component, JComponent.class, "component");

        setDelegateUI(newDelegateUI());

        JComponent swingComponent = (JComponent) component;
        swingComponent.setOpaque(false);
        swingComponent.setForeground(MaterialColor.TRANSPARENT);
        swingComponent.setBackground(MaterialColor.TRANSPARENT);
        swingComponent.setFont(null);
        swingComponent.setBorder(null);
    }

    protected abstract MaterialUI newDelegateUI();

    protected abstract MaterialUI getDelegateUI();

    protected abstract void setDelegateUI(MaterialUI componentUI);

    @Override
    protected int getDelegateX() {
        return 0;
    }

    @Override
    protected int getDelegateY() {
        return 0;
    }

    @Override
    protected int getDelegateWidth() {
        return getDelegateUI().getMaterialWidth((MaterialComponent) material);
    }

    @Override
    protected int getDelegateHeight() {
        return getDelegateUI().getMaterialHeight((MaterialComponent) material);
    }

    @Override
    public Dimension getPreferredSize() {
        return getDelegateUI().getPreferredSize((JComponent) material);
    }

    @Override
    public Dimension getMinimumSize() {
        return getDelegateUI().getMinimumSize((JComponent) material);
    }

    @Override
    public Dimension getMaximumSize() {
        return getDelegateUI().getMaximumSize((JComponent) material);
    }

}
