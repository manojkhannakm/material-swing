package com.manojkhannakm.materialswing.util;

import java.awt.*;

/**
 * @author Manoj Khanna
 */

public class GraphicUtils {

    private GraphicUtils() {
    }

    public static Graphics2D getSwingGraphics(Graphics graphics) {
        ObjectUtils.verifyNotNull(graphics, "graphics");

        Graphics2D componentGraphics = (Graphics2D) graphics;
        componentGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        componentGraphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        return componentGraphics;
    }

    public static Graphics2D getImageGraphics(Graphics graphics) {
        ObjectUtils.verifyNotNull(graphics, "graphics");

        Graphics2D imageGraphics = (Graphics2D) graphics;
        imageGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        imageGraphics.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        imageGraphics.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        imageGraphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        imageGraphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        return imageGraphics;
    }

}
