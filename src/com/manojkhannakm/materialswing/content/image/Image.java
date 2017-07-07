package com.manojkhannakm.materialswing.content.image;

import java.awt.*;

/**
 * @author Manoj Khanna
 */

public interface Image {

    java.awt.Image getImage();

    void setImage(java.awt.Image image);

    float getImageScaleX();

    void setImageScaleX(float scaleX);

    float getImageScaleY();

    void setImageScaleY(float scaleY);

    float getImageRotation();

    void setImageRotation(float rotation);

    float getImageAlpha();

    void setImageAlpha(float alpha);

    Color getImageColor();

    void setImageColor(Color color);

    Shape getImageShape();

    void setImageShape(Shape shape);

}
