package com.manojkhannakm.materialswing.effect.overlay;

import java.awt.*;

/**
 * @author Manoj Khanna
 */

public interface Overlay {

    void overlayPaint(Graphics2D graphics);

    Color getOverlayColor();

    void setOverlayColor(Color color);

    Type getOverlayType();

    void setOverlayType(Type type);

    enum Type {

        NORMAL, SOFT, MEDIUM, HARD

    }

}
