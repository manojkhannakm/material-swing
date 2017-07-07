package com.manojkhannakm.materialswing.swing.window;

import com.manojkhannakm.materialswing.swing.Material;

import javax.swing.*;
import java.awt.*;

/**
 * @author Manoj Khanna
 */

public interface MaterialWindow extends Material {

    JRootPane createRootPane();

    void pack();

    boolean isOpaque();

    Dimension getPreferredSize();

    Dimension getMinimumSize();

    Dimension getMaximumSize();

}
