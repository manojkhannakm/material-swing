package com.manojkhannakm.materialswing.style;

import com.manojkhannakm.materialswing.util.GraphicUtils;
import com.manojkhannakm.materialswing.util.MathUtils;
import com.manojkhannakm.materialswing.util.ResourceUtils;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;
import java.awt.image.BufferedImage;

/**
 * @author Manoj Khanna
 */

public class MaterialImage extends BufferedImage {

    public static final MaterialImage WINDOW_BACK = ResourceUtils.getImage(ResourceUtils.Paths.Images.WINDOW_BACK);
    public static final MaterialImage WINDOW_MINIMIZE = ResourceUtils.getImage(ResourceUtils.Paths.Images.WINDOW_MINIMIZE);
    public static final MaterialImage WINDOW_MAXIMIZE = ResourceUtils.getImage(ResourceUtils.Paths.Images.WINDOW_MAXIMIZE);
    public static final MaterialImage WINDOW_RESTORE = ResourceUtils.getImage(ResourceUtils.Paths.Images.WINDOW_RESTORE);
    public static final MaterialImage WINDOW_CLOSE = ResourceUtils.getImage(ResourceUtils.Paths.Images.WINDOW_CLOSE);

    public static final MaterialImage TRANSPARENT = new MaterialImage(1, 1);

    public MaterialImage(BufferedImage image) {
        super(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);

        Graphics2D graphics = GraphicUtils.getImageGraphics(createGraphics());
        graphics.drawImage(image, 0, 0, null);
        graphics.dispose();
    }

    public MaterialImage(int width, int height) {
        super(width, height, BufferedImage.TYPE_INT_ARGB);
    }

    public MaterialImage withScaleX(float scaleX) {
        if (scaleX < 0.0f) {
            scaleX = 0.0f;
        }

        int width = getWidth(), height = getHeight(), scaledWidth = Math.round(width * scaleX);

        MaterialImage scaledImage = this;

        while (width != scaledWidth) {
            width = Math.max(width / 2, scaledWidth);

            MaterialImage image = new MaterialImage(width, height);

            Graphics2D graphics = GraphicUtils.getImageGraphics(image.createGraphics());
            graphics.drawImage(scaledImage, 0, 0, width, height, null);
            graphics.dispose();

            scaledImage = image;
        }

        return scaledImage;
    }

    public MaterialImage withScaleY(float scaleY) {
        if (scaleY < 0.0f) {
            scaleY = 0.0f;
        }

        int width = getWidth(), height = getHeight(), scaledHeight = Math.round(height * scaleY);

        MaterialImage scaledImage = this;

        while (height != scaledHeight) {
            height = Math.max(height / 2, scaledHeight);

            MaterialImage image = new MaterialImage(width, height);

            Graphics2D graphics = GraphicUtils.getImageGraphics(image.createGraphics());
            graphics.drawImage(scaledImage, 0, 0, width, height, null);
            graphics.dispose();

            scaledImage = image;
        }

        return scaledImage;
    }

    public MaterialImage withRotation(float rotation) {
        int width = getWidth(), height = getHeight(),
                rotatedWidth = Math.round(MathUtils.getRotatedWidth(width, height, rotation)),
                rotatedHeight = Math.round(MathUtils.getRotatedHeight(width, height, rotation));

        MaterialImage image = new MaterialImage(rotatedWidth, rotatedHeight);

        Graphics2D graphics = GraphicUtils.getImageGraphics(image.createGraphics());
        graphics.translate((rotatedWidth - width) / 2.0, (rotatedHeight - height) / 2.0);
        graphics.rotate(2.0 * Math.PI * rotation, width / 2.0, height / 2.0);
        graphics.drawImage(this, 0, 0, null);
        graphics.dispose();

        return image;
    }

    public MaterialImage withAlpha(float alpha) {
        if (alpha < 0.0f) {
            alpha = 0.0f;
        } else if (alpha > 1.0f) {
            alpha = 1.0f;
        }

        MaterialImage image = new MaterialImage(getWidth(), getHeight());

        Graphics2D graphics = GraphicUtils.getImageGraphics(image.createGraphics());
        graphics.setComposite(AlphaComposite.SrcOver.derive(alpha));
        graphics.drawImage(this, 0, 0, null);
        graphics.dispose();

        return image;
    }

    public MaterialImage withColor(Color color) {
        if (color == null) {
            color = MaterialColor.TRANSPARENT;
        }

        int width = getWidth(), height = getHeight();

        MaterialImage image = new MaterialImage(width, height);

        Graphics2D graphics = GraphicUtils.getImageGraphics(image.createGraphics());
        graphics.drawImage(this, 0, 0, null);
        graphics.setComposite(AlphaComposite.SrcIn);
        graphics.setColor(color);
        graphics.fillRect(0, 0, width, height);
        graphics.dispose();

        return image;
    }

    public MaterialImage withShape(Shape shape) {
        if (shape == null) {
            shape = new Rectangle2D.Float();
        }

        int width = getWidth(), height = getHeight();

        if (shape instanceof RectangularShape) {
            RectangularShape rectShape = (RectangularShape) shape;

            if (rectShape.getX() == 0.0 && rectShape.getY() == 0.0
                    && rectShape.getWidth() == 0.0 && rectShape.getHeight() == 0.0) {
                rectShape.setFrame(0.0, 0.0, width, height);
            }
        }

        MaterialImage image = new MaterialImage(width, height);

        Graphics2D graphics = GraphicUtils.getImageGraphics(image.createGraphics());
        graphics.fill(shape);
        graphics.setComposite(AlphaComposite.SrcIn);
        graphics.drawImage(this, 0, 0, null);
        graphics.dispose();

        return image;
    }

}
