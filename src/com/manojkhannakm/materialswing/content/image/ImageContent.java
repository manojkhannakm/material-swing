package com.manojkhannakm.materialswing.content.image;

import com.manojkhannakm.materialswing.content.MaterialContent;
import com.manojkhannakm.materialswing.property.MaterialProperty;
import com.manojkhannakm.materialswing.style.MaterialColor;
import com.manojkhannakm.materialswing.style.MaterialImage;
import com.manojkhannakm.materialswing.swing.Material;
import com.manojkhannakm.materialswing.util.GraphicUtils;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;

/**
 * @author Manoj Khanna
 */

public class ImageContent extends MaterialContent implements Image {

    private java.awt.Image image;
    private float scaleX, scaleY, rotation, alpha;
    private Color color;
    private Shape shape;

    public ImageContent(java.awt.Image image, float scaleX, float scaleY, float rotation, float alpha, Color color, Shape shape) {
        if (image == null) {
            image = MaterialImage.TRANSPARENT;
        }

        if (scaleX < 0.0f) {
            scaleX = 0.0f;
        }

        if (scaleY < 0.0f) {
            scaleY = 0.0f;
        }

        if (alpha < 0.0f) {
            alpha = 0.0f;
        } else if (alpha > 1.0f) {
            alpha = 1.0f;
        }

        if (color == null) {
            color = MaterialColor.TRANSPARENT;
        }

        if (shape == null) {
            shape = new Rectangle2D.Float();
        }

        if (shape instanceof RectangularShape) {
            RectangularShape rectShape = (RectangularShape) shape;

            if (rectShape.getX() == 0.0 && rectShape.getY() == 0.0
                    && rectShape.getWidth() == 0.0 && rectShape.getHeight() == 0.0) {
                rectShape.setFrame(0.0, 0.0, image.getWidth(null), image.getHeight(null));
            }
        }

        this.image = image;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.rotation = rotation;
        this.alpha = alpha;
        this.color = color;
        this.shape = shape;
    }

    public ImageContent(java.awt.Image image) {
        this(image, 1.0f, 1.0f, 0.0f, 1.0f, null, null);
    }

    @Override
    public void contentPaint(Graphics2D graphics) {
        AlphaComposite composite = (AlphaComposite) graphics.getComposite();
        graphics.setComposite(composite.derive(composite.getAlpha() * alpha));

        int width = image.getWidth(null), height = image.getHeight(null);

        MaterialImage image = new MaterialImage(width, height);

        float scaledWidth = width * scaleX, scaledHeight = height * scaleY;

        Graphics2D imageGraphics = GraphicUtils.getImageGraphics(image.createGraphics());
        imageGraphics.translate((width - scaledWidth) / 2.0, (height - scaledHeight) / 2.0);
        imageGraphics.rotate(2.0 * Math.PI * rotation, scaledWidth / 2.0, scaledHeight / 2.0);
        imageGraphics.scale(scaleX, scaleY);

        if (shape instanceof Rectangle2D) {
            Rectangle2D rectangle = (Rectangle2D) shape;

            if (rectangle.getX() != 0.0 || rectangle.getY() != 0.0
                    || rectangle.getWidth() != width || rectangle.getHeight() != height) {
                imageGraphics.fill(shape);
                imageGraphics.setComposite(AlphaComposite.SrcIn);
            }
        } else {
            imageGraphics.fill(shape);
            imageGraphics.setComposite(AlphaComposite.SrcIn);
        }

        imageGraphics.drawImage(this.image, 0, 0, null);

        if (!color.equals(MaterialColor.TRANSPARENT)) {
            imageGraphics.setComposite(AlphaComposite.SrcIn);
            imageGraphics.setColor(color);
            imageGraphics.fillRect(0, 0, width, height);
        }

        imageGraphics.dispose();

        graphics.drawImage(image, 0, 0, null);
    }

    @Override
    public int getContentWidth() {
        return image.getWidth(null);
    }

    @Override
    public int getContentHeight() {
        return image.getHeight(null);
    }

    @Override
    public java.awt.Image getImage() {
        return image;
    }

    @Override
    public void setImage(java.awt.Image image) {
        if (image == null) {
            image = MaterialImage.TRANSPARENT;
        }

        java.awt.Image oldImage = this.image;

        this.image = image;

        if (container != null) {
            Material material = container.getMaterial();
            material.materialPropertyChanged(MaterialProperty.IMAGE, this, oldImage, image);
            material.materialRevalidate();
        }

        if (shape instanceof RectangularShape) {
            RectangularShape oldShape = (RectangularShape) shape;

            if (oldShape.getX() == 0.0 && oldShape.getY() == 0.0
                    && oldShape.getWidth() == oldImage.getWidth(null)
                    && oldShape.getHeight() == oldImage.getHeight(null)) {
                RectangularShape newShape = (RectangularShape) oldShape.clone();
                newShape.setFrame(0.0, 0.0, image.getWidth(null), image.getHeight(null));

                shape = newShape;

                if (container != null) {
                    Material material = container.getMaterial();
                    material.materialPropertyChanged(MaterialProperty.IMAGE_SHAPE, this, oldShape, newShape);
                    material.materialRepaint();
                }
            }
        }
    }

    @Override
    public float getImageScaleX() {
        return scaleX;
    }

    @Override
    public void setImageScaleX(float scaleX) {
        if (scaleX < 0.0f) {
            scaleX = 0.0f;
        }

        float oldScaleX = this.scaleX;

        this.scaleX = scaleX;

        if (container != null) {
            Material material = container.getMaterial();
            material.materialPropertyChanged(MaterialProperty.IMAGE_SCALE_X, this, oldScaleX, scaleX);
            material.materialRepaint();
        }
    }

    @Override
    public float getImageScaleY() {
        return scaleY;
    }

    @Override
    public void setImageScaleY(float scaleY) {
        if (scaleY < 0.0f) {
            scaleY = 0.0f;
        }

        float oldScaleY = this.scaleY;

        this.scaleY = scaleY;

        if (container != null) {
            Material material = container.getMaterial();
            material.materialPropertyChanged(MaterialProperty.IMAGE_SCALE_Y, this, oldScaleY, scaleY);
            material.materialRepaint();
        }
    }

    @Override
    public float getImageRotation() {
        return rotation;
    }

    @Override
    public void setImageRotation(float rotation) {
        float oldRotation = this.rotation;

        this.rotation = rotation;

        if (container != null) {
            Material material = container.getMaterial();
            material.materialPropertyChanged(MaterialProperty.IMAGE_ROTATION, this, oldRotation, rotation);
            material.materialRepaint();
        }
    }

    @Override
    public float getImageAlpha() {
        return alpha;
    }

    @Override
    public void setImageAlpha(float alpha) {
        if (alpha < 0.0f) {
            alpha = 0.0f;
        } else if (alpha > 1.0f) {
            alpha = 1.0f;
        }

        float oldAlpha = this.alpha;

        this.alpha = alpha;

        if (container != null) {
            Material material = container.getMaterial();
            material.materialPropertyChanged(MaterialProperty.IMAGE_ALPHA, this, oldAlpha, alpha);
            material.materialRepaint();
        }
    }

    @Override
    public Color getImageColor() {
        return color;
    }

    @Override
    public void setImageColor(Color color) {
        if (color == null) {
            color = MaterialColor.TRANSPARENT;
        }

        Color oldColor = this.color;

        this.color = color;

        if (container != null) {
            Material material = container.getMaterial();
            material.materialPropertyChanged(MaterialProperty.IMAGE_COLOR, this, oldColor, color);
            material.materialRepaint();
        }
    }

    @Override
    public Shape getImageShape() {
        return shape;
    }

    @Override
    public void setImageShape(Shape shape) {
        if (shape == null) {
            shape = new Rectangle2D.Float();
        }

        if (shape instanceof RectangularShape) {
            RectangularShape rectShape = (RectangularShape) shape;

            if (rectShape.getX() == 0.0 && rectShape.getY() == 0.0
                    && rectShape.getWidth() == 0.0 && rectShape.getHeight() == 0.0) {
                rectShape.setFrame(0.0, 0.0, image.getWidth(null), image.getHeight(null));
            }
        }

        Shape oldShape = this.shape;

        this.shape = shape;

        if (container != null) {
            Material material = container.getMaterial();
            material.materialPropertyChanged(MaterialProperty.IMAGE_SHAPE, this, oldShape, shape);
            material.materialRepaint();
        }
    }

}
