package com.manojkhannakm.materialswing.effect.shadow;

import java.awt.*;

/**
 * @author Manoj Khanna
 */

public interface Shadow {

    void shadowPaint(Graphics2D graphics);

    int getShadowSize();

    void setShadowSize(int size);

    Color getShadowColor();

    void setShadowColor(Color color);

    Type getShadowType();

    void setShadowType(Type type);

    int getShadowOffset();

    enum Type {

        KEY, AMBIENT, KEY_AMBIENT

    }

}
