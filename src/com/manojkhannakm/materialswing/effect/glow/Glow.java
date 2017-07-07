package com.manojkhannakm.materialswing.effect.glow;

import java.awt.*;

/**
 * @author Manoj Khanna
 */

public interface Glow {

    void glowPaint(Graphics2D graphics);

    int getGlowSize();

    void setGlowSize(int size);

    Color getGlowColor();

    void setGlowColor(Color color);

    Type getGlowType();

    void setGlowType(Type type);

    int getGlowOffset();

    enum Type {

        INNER, OUTER

    }

}
