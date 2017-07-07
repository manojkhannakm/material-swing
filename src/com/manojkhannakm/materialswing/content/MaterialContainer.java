package com.manojkhannakm.materialswing.content;

import com.manojkhannakm.materialswing.content.image.Image;
import com.manojkhannakm.materialswing.content.image.ImageContent;
import com.manojkhannakm.materialswing.content.text.Text;
import com.manojkhannakm.materialswing.content.text.TextContent;
import com.manojkhannakm.materialswing.swing.Material;
import com.manojkhannakm.materialswing.util.ObjectUtils;
import com.manojkhannakm.materialswing.util.StringUtils;

import java.awt.*;

/**
 * @author Manoj Khanna
 */

public class MaterialContainer implements Container, Text, Image {

    private final Material material;

    private final String name;

    private MaterialContent content;

    public MaterialContainer(Material material, String name) {
        ObjectUtils.verifyNotNull(material, "material");

        if (name == null) {
            name = "";
        }

        this.material = material;

        this.name = name;
    }

    public MaterialContainer(Material material) {
        this(material, null);
    }

    @Override
    public MaterialContent getMaterialContent() {
        return content;
    }

    @Override
    public void setMaterialContent(MaterialContent content) {
        MaterialContent oldContent = this.content;

        this.content = content;

        if (oldContent != null) {
            oldContent.setContainer(null);
        }

        content.setContainer(this);

        material.materialPropertyChanged(StringUtils.getCamelCase(name + "Content"), this, oldContent, content);
        material.materialRevalidate();
    }

    @Override
    public String getText() {
        if (!(content instanceof TextContent)) {
            return null;
        }

        return ((TextContent) content).getText();
    }

    @Override
    public void setText(String text) {
        if (!(content instanceof TextContent)) {
            setMaterialContent(new TextContent(null));
        }

        ((TextContent) content).setText(text);
    }

    @Override
    public Color getTextColor() {
        if (!(content instanceof TextContent)) {
            return null;
        }

        return ((TextContent) content).getTextColor();
    }

    @Override
    public void setTextColor(Color color) {
        if (content instanceof TextContent) {
            ((TextContent) content).setTextColor(color);
        }
    }

    @Override
    public Font getTextFont() {
        if (!(content instanceof TextContent)) {
            return null;
        }

        return ((TextContent) content).getTextFont();
    }

    @Override
    public void setTextFont(Font font) {
        if (content instanceof TextContent) {
            ((TextContent) content).setTextFont(font);
        }
    }

    @Override
    public java.awt.Image getImage() {
        if (!(content instanceof ImageContent)) {
            return null;
        }

        return ((Image) content).getImage();
    }

    @Override
    public void setImage(java.awt.Image image) {
        if (!(content instanceof ImageContent)) {
            setMaterialContent(new ImageContent(null));
        }

        ((ImageContent) content).setImage(image);
    }

    @Override
    public float getImageScaleX() {
        if (!(content instanceof ImageContent)) {
            return 0.0f;
        }

        return ((ImageContent) content).getImageScaleX();
    }

    @Override
    public void setImageScaleX(float scaleX) {
        if (content instanceof ImageContent) {
            ((ImageContent) content).setImageScaleX(scaleX);
        }
    }

    @Override
    public float getImageScaleY() {
        if (!(content instanceof ImageContent)) {
            return 0.0f;
        }

        return ((ImageContent) content).getImageScaleY();
    }

    @Override
    public void setImageScaleY(float scaleY) {
        if (content instanceof ImageContent) {
            ((ImageContent) content).setImageScaleY(scaleY);
        }
    }

    @Override
    public float getImageRotation() {
        if (!(content instanceof ImageContent)) {
            return 0.0f;
        }

        return ((ImageContent) content).getImageRotation();
    }

    @Override
    public void setImageRotation(float rotation) {
        if (content instanceof ImageContent) {
            ((ImageContent) content).setImageRotation(rotation);
        }
    }

    @Override
    public float getImageAlpha() {
        if (!(content instanceof ImageContent)) {
            return 0.0f;
        }

        return ((ImageContent) content).getImageAlpha();
    }

    @Override
    public void setImageAlpha(float alpha) {
        if (content instanceof ImageContent) {
            ((ImageContent) content).setImageAlpha(alpha);
        }
    }

    @Override
    public Color getImageColor() {
        if (!(content instanceof ImageContent)) {
            return null;
        }

        return ((ImageContent) content).getImageColor();
    }

    @Override
    public void setImageColor(Color color) {
        if (content instanceof ImageContent) {
            ((ImageContent) content).setImageColor(color);
        }
    }

    @Override
    public Shape getImageShape() {
        if (!(content instanceof ImageContent)) {
            return null;
        }

        return ((ImageContent) content).getImageShape();
    }

    @Override
    public void setImageShape(Shape shape) {
        if (content instanceof ImageContent) {
            ((ImageContent) content).setImageShape(shape);
        }
    }

    public Material getMaterial() {
        return material;
    }

    public String getName() {
        return name;
    }

}
