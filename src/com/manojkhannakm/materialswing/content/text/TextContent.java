package com.manojkhannakm.materialswing.content.text;

import com.manojkhannakm.materialswing.content.MaterialContent;
import com.manojkhannakm.materialswing.property.MaterialProperty;
import com.manojkhannakm.materialswing.style.MaterialColor;
import com.manojkhannakm.materialswing.style.MaterialFont;
import com.manojkhannakm.materialswing.swing.Material;

import java.awt.*;

/**
 * @author Manoj Khanna
 */

public class TextContent extends MaterialContent implements Text {

    private String text;
    private Color color;
    private Font font;

    public TextContent(String text, Color color, Font font) {
        if (text == null) {
            text = "";
        }

        if (color == null) {
            color = MaterialColor.TRANSPARENT;
        }

        if (font == null) {
            font = MaterialFont.ROBOTO_REGULAR;
        }

        this.text = text;
        this.color = color;
        this.font = font;
    }

    public TextContent(String text) {
        this(text, null, null);
    }

    @Override
    public void contentPaint(Graphics2D graphics) {
        graphics.setColor(color);
        graphics.setFont(font);
        graphics.drawString(text, 0, graphics.getFontMetrics(font).getAscent());
    }

    @Override
    public int getContentWidth() {
        if (container == null) {
            return -1;
        }

        return ((Component) container.getMaterial()).getFontMetrics(font).stringWidth(text);
    }

    @Override
    public int getContentHeight() {
        if (container == null) {
            return -1;
        }

        return ((Component) container.getMaterial()).getFontMetrics(font).getHeight();
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public void setText(String text) {
        if (text == null) {
            text = "";
        }

        String oldText = this.text;

        this.text = text;

        if (container != null) {
            Material material = container.getMaterial();
            material.materialPropertyChanged(MaterialProperty.TEXT, this, oldText, text);
            material.materialRevalidate();
        }
    }

    @Override
    public Color getTextColor() {
        return color;
    }

    @Override
    public void setTextColor(Color color) {
        if (color == null) {
            color = MaterialColor.TRANSPARENT;
        }

        Color oldColor = this.color;

        this.color = color;

        if (container != null) {
            Material material = container.getMaterial();
            material.materialPropertyChanged(MaterialProperty.TEXT_COLOR, this, oldColor, color);
            material.materialRepaint();
        }
    }

    @Override
    public Font getTextFont() {
        return font;
    }

    @Override
    public void setTextFont(Font font) {
        if (font == null) {
            font = MaterialFont.ROBOTO_REGULAR;
        }

        Font oldFont = this.font;

        this.font = font;

        if (container != null) {
            Material material = container.getMaterial();
            material.materialPropertyChanged(MaterialProperty.TEXT_FONT, this, oldFont, font);
            material.materialRevalidate();
        }
    }

}
