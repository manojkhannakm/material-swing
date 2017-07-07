package com.manojkhannakm.materialswing.swing.component;

import com.manojkhannakm.materialswing.swing.Material;

import javax.swing.*;
import java.awt.*;

/**
 * @author Manoj Khanna
 */

public interface MaterialComponent extends Material {

    Dimension getPreferredSize();

    Dimension getMinimumSize();

    Dimension getMaximumSize();

    interface MaterialUI {

        void paint(Graphics g, JComponent c);

        boolean contains(JComponent c, int x, int y);

        Dimension getPreferredSize(JComponent c);

        Dimension getMinimumSize(JComponent c);

        Dimension getMaximumSize(JComponent c);

        int getMaterialWidth(MaterialComponent component);

        int getMaterialHeight(MaterialComponent component);

    }

}
