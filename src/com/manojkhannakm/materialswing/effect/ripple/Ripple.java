package com.manojkhannakm.materialswing.effect.ripple;

import java.awt.*;

/**
 * @author Manoj Khanna
 */

public interface Ripple {

    void ripplePaint(Graphics2D graphics);

    Color getRippleColor();

    void setRippleColor(Color color);

    Type getRippleType();

    void setRippleType(Type type);

    enum Type {

        SLOW, MEDIUM, FAST

    }

}
