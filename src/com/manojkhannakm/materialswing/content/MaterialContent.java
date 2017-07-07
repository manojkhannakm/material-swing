package com.manojkhannakm.materialswing.content;

import java.awt.*;

/**
 * @author Manoj Khanna
 */

public abstract class MaterialContent {

    protected MaterialContainer container;

    protected MaterialContent() {
    }

    public abstract void contentPaint(Graphics2D graphics);

    public abstract int getContentWidth();

    public abstract int getContentHeight();

    public MaterialContainer getContainer() {
        return container;
    }

    public void setContainer(MaterialContainer container) {
        this.container = container;
    }

}
