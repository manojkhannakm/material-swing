package com.manojkhannakm.materialswing.property;

import com.manojkhannakm.materialswing.content.MaterialContainer;
import com.manojkhannakm.materialswing.content.MaterialContent;
import com.manojkhannakm.materialswing.content.image.Image;
import com.manojkhannakm.materialswing.content.text.Text;
import com.manojkhannakm.materialswing.effect.glow.Glow;
import com.manojkhannakm.materialswing.effect.overlay.Overlay;
import com.manojkhannakm.materialswing.effect.ripple.Ripple;
import com.manojkhannakm.materialswing.effect.shadow.Shadow;
import com.manojkhannakm.materialswing.swing.Material;
import com.manojkhannakm.materialswing.util.ObjectUtils;
import com.manojkhannakm.materialswing.util.StringUtils;

import java.awt.*;

/**
 * @author Manoj Khanna
 */

public abstract class MaterialProperty<O, V> implements ObjectGetter<O, V>, ObjectSetter<O, V> {

    public static final MaterialProperty<Material, Integer> X = new MaterialProperty<Material, Integer>("x") {

        @Override
        public Integer getValue(Material object) {
            return object.getMaterialX();
        }

        @Override
        public void setValue(Material object, Integer value) {
            object.setMaterialX(value);
        }

    };

    public static final MaterialProperty<Material, Integer> Y = new MaterialProperty<Material, Integer>("y") {

        @Override
        public Integer getValue(Material object) {
            return object.getMaterialY();
        }

        @Override
        public void setValue(Material object, Integer value) {
            object.setMaterialY(value);
        }

    };

    public static final MaterialProperty<Material, Integer> Z = new MaterialProperty<Material, Integer>("z") {

        @Override
        public Integer getValue(Material object) {
            return object.getMaterialZ();
        }

        @Override
        public void setValue(Material object, Integer value) {
            object.setMaterialZ(value);
        }

    };

    public static final MaterialProperty<Material, Integer> WIDTH = new MaterialProperty<Material, Integer>("width") {

        @Override
        public Integer getValue(Material object) {
            return object.getMaterialWidth();
        }

        @Override
        public void setValue(Material object, Integer value) {
            object.setMaterialWidth(value);
        }

    };

    public static final MaterialProperty<Material, Integer> HEIGHT = new MaterialProperty<Material, Integer>("height") {

        @Override
        public Integer getValue(Material object) {
            return object.getMaterialHeight();
        }

        @Override
        public void setValue(Material object, Integer value) {
            object.setMaterialHeight(value);
        }

    };

    public static final MaterialProperty<Material, Integer> TRANSLATION_X = new MaterialProperty<Material, Integer>("translationX") {

        @Override
        public Integer getValue(Material object) {
            return object.getMaterialTranslationX();
        }

        @Override
        public void setValue(Material object, Integer value) {
            object.setMaterialTranslationX(value);
        }

    };

    public static final MaterialProperty<Material, Integer> TRANSLATION_Y = new MaterialProperty<Material, Integer>("translationY") {

        @Override
        public Integer getValue(Material object) {
            return object.getMaterialTranslationY();
        }

        @Override
        public void setValue(Material object, Integer value) {
            object.setMaterialTranslationY(value);
        }

    };

    public static final MaterialProperty<Material, Integer> TRANSLATION_Z = new MaterialProperty<Material, Integer>("translationZ") {

        @Override
        public Integer getValue(Material object) {
            return object.getMaterialTranslationZ();
        }

        @Override
        public void setValue(Material object, Integer value) {
            object.setMaterialTranslationZ(value);
        }

    };

    public static final MaterialProperty<Material, Float> PIVOT_X = new MaterialProperty<Material, Float>("pivotX") {

        @Override
        public Float getValue(Material object) {
            return object.getMaterialPivotX();
        }

        @Override
        public void setValue(Material object, Float value) {
            object.setMaterialPivotX(value);
        }

    };

    public static final MaterialProperty<Material, Float> PIVOT_Y = new MaterialProperty<Material, Float>("pivotY") {

        @Override
        public Float getValue(Material object) {
            return object.getMaterialPivotY();
        }

        @Override
        public void setValue(Material object, Float value) {
            object.setMaterialPivotY(value);
        }

    };

    public static final MaterialProperty<Material, Float> SCALE_X = new MaterialProperty<Material, Float>("scaleX") {

        @Override
        public Float getValue(Material object) {
            return object.getMaterialScaleX();
        }

        @Override
        public void setValue(Material object, Float value) {
            object.setMaterialScaleX(value);
        }

    };

    public static final MaterialProperty<Material, Float> SCALE_Y = new MaterialProperty<Material, Float>("scaleY") {

        @Override
        public Float getValue(Material object) {
            return object.getMaterialScaleY();
        }

        @Override
        public void setValue(Material object, Float value) {
            object.setMaterialScaleY(value);
        }

    };

    public static final MaterialProperty<Material, Float> ROTATION = new MaterialProperty<Material, Float>("rotation") {

        @Override
        public Float getValue(Material object) {
            return object.getMaterialRotation();
        }

        @Override
        public void setValue(Material object, Float value) {
            object.setMaterialRotation(value);
        }

    };

    public static final MaterialProperty<Material, Float> ALPHA = new MaterialProperty<Material, Float>("alpha") {

        @Override
        public Float getValue(Material object) {
            return object.getMaterialAlpha();
        }

        @Override
        public void setValue(Material object, Float value) {
            object.setMaterialAlpha(value);
        }

    };

    public static final MaterialProperty<Material, Color> COLOR = new MaterialProperty<Material, Color>("color") {

        @Override
        public Color getValue(Material object) {
            return object.getMaterialColor();
        }

        @Override
        public void setValue(Material object, Color value) {
            object.setMaterialColor(value);
        }

    };

    public static final MaterialProperty<Material, Shape> SHAPE = new MaterialProperty<Material, Shape>("shape") {

        @Override
        public Shape getValue(Material object) {
            return object.getMaterialShape();
        }

        @Override
        public void setValue(Material object, Shape value) {
            object.setMaterialShape(value);
        }

    };

    public static final MaterialProperty<Shadow, Integer> SHADOW_SIZE = new MaterialProperty<Shadow, Integer>("shadowSize") {

        @Override
        public Integer getValue(Shadow object) {
            return object.getShadowSize();
        }

        @Override
        public void setValue(Shadow object, Integer value) {
            object.setShadowSize(value);
        }

    };

    public static final MaterialProperty<Shadow, Color> SHADOW_COLOR = new MaterialProperty<Shadow, Color>("shadowColor") {

        @Override
        public Color getValue(Shadow object) {
            return object.getShadowColor();
        }

        @Override
        public void setValue(Shadow object, Color value) {
            object.setShadowColor(value);
        }

    };

    public static final MaterialProperty<Shadow, Shadow.Type> SHADOW_TYPE = new MaterialProperty<Shadow, Shadow.Type>("shadowType") {

        @Override
        public Shadow.Type getValue(Shadow object) {
            return object.getShadowType();
        }

        @Override
        public void setValue(Shadow object, Shadow.Type value) {
            object.setShadowType(value);
        }

    };

    public static final MaterialProperty<Glow, Integer> GLOW_SIZE = new MaterialProperty<Glow, Integer>("glowSize") {

        @Override
        public Integer getValue(Glow object) {
            return object.getGlowSize();
        }

        @Override
        public void setValue(Glow object, Integer value) {
            object.setGlowSize(value);
        }

    };

    public static final MaterialProperty<Glow, Color> GLOW_COLOR = new MaterialProperty<Glow, Color>("glowColor") {

        @Override
        public Color getValue(Glow object) {
            return object.getGlowColor();
        }

        @Override
        public void setValue(Glow object, Color value) {
            object.setGlowColor(value);
        }

    };

    public static final MaterialProperty<Glow, Glow.Type> GLOW_TYPE = new MaterialProperty<Glow, Glow.Type>("glowType") {

        @Override
        public Glow.Type getValue(Glow object) {
            return object.getGlowType();
        }

        @Override
        public void setValue(Glow object, Glow.Type value) {
            object.setGlowType(value);
        }

    };

    public static final MaterialProperty<Overlay, Color> OVERLAY_COLOR = new MaterialProperty<Overlay, Color>("overlayColor") {

        @Override
        public Color getValue(Overlay object) {
            return object.getOverlayColor();
        }

        @Override
        public void setValue(Overlay object, Color value) {
            object.setOverlayColor(value);
        }

    };

    public static final MaterialProperty<Overlay, Overlay.Type> OVERLAY_TYPE = new MaterialProperty<Overlay, Overlay.Type>("overlayType") {

        @Override
        public Overlay.Type getValue(Overlay object) {
            return object.getOverlayType();
        }

        @Override
        public void setValue(Overlay object, Overlay.Type value) {
            object.setOverlayType(value);
        }

    };

    public static final MaterialProperty<Ripple, Color> RIPPLE_COLOR = new MaterialProperty<Ripple, Color>("rippleColor") {

        @Override
        public Color getValue(Ripple object) {
            return object.getRippleColor();
        }

        @Override
        public void setValue(Ripple object, Color value) {
            object.setRippleColor(value);
        }

    };

    public static final MaterialProperty<Ripple, Ripple.Type> RIPPLE_TYPE = new MaterialProperty<Ripple, Ripple.Type>("rippleType") {

        @Override
        public Ripple.Type getValue(Ripple object) {
            return object.getRippleType();
        }

        @Override
        public void setValue(Ripple object, Ripple.Type value) {
            object.setRippleType(value);
        }

    };

    public static final MaterialProperty<Text, String> TEXT = new MaterialProperty<Text, String>("text") {

        @Override
        public String getValue(Text object) {
            return object.getText();
        }

        @Override
        public void setValue(Text object, String value) {
            object.setText(value);
        }

    };

    public static final MaterialProperty<Text, Color> TEXT_COLOR = new MaterialProperty<Text, Color>("textColor") {

        @Override
        public Color getValue(Text object) {
            return object.getTextColor();
        }

        @Override
        public void setValue(Text object, Color value) {
            object.setTextColor(value);
        }

    };

    public static final MaterialProperty<Text, Font> TEXT_FONT = new MaterialProperty<Text, Font>("textFont") {

        @Override
        public Font getValue(Text object) {
            return object.getTextFont();
        }

        @Override
        public void setValue(Text object, Font value) {
            object.setTextFont(value);
        }

    };

    public static final MaterialProperty<Image, java.awt.Image> IMAGE = new MaterialProperty<Image, java.awt.Image>("image") {

        @Override
        public java.awt.Image getValue(Image object) {
            return object.getImage();
        }

        @Override
        public void setValue(Image object, java.awt.Image value) {
            object.setImage(value);
        }

    };

    public static final MaterialProperty<Image, Float> IMAGE_SCALE_X = new MaterialProperty<Image, Float>("imageScaleX") {

        @Override
        public Float getValue(Image object) {
            return object.getImageScaleX();
        }

        @Override
        public void setValue(Image object, Float value) {
            object.setImageScaleX(value);
        }

    };

    public static final MaterialProperty<Image, Float> IMAGE_SCALE_Y = new MaterialProperty<Image, Float>("imageScaleY") {

        @Override
        public Float getValue(Image object) {
            return object.getImageScaleY();
        }

        @Override
        public void setValue(Image object, Float value) {
            object.setImageScaleY(value);
        }

    };

    public static final MaterialProperty<Image, Float> IMAGE_ROTATION = new MaterialProperty<Image, Float>("imageRotation") {

        @Override
        public Float getValue(Image object) {
            return object.getImageRotation();
        }

        @Override
        public void setValue(Image object, Float value) {
            object.setImageRotation(value);
        }

    };

    public static final MaterialProperty<Image, Float> IMAGE_ALPHA = new MaterialProperty<Image, Float>("imageAlpha") {

        @Override
        public Float getValue(Image object) {
            return object.getImageAlpha();
        }

        @Override
        public void setValue(Image object, Float value) {
            object.setImageAlpha(value);
        }

    };

    public static final MaterialProperty<Image, Color> IMAGE_COLOR = new MaterialProperty<Image, Color>("imageColor") {

        @Override
        public Color getValue(Image object) {
            return object.getImageColor();
        }

        @Override
        public void setValue(Image object, Color value) {
            object.setImageColor(value);
        }

    };

    public static final MaterialProperty<Image, Shape> IMAGE_SHAPE = new MaterialProperty<Image, Shape>("imageShape") {

        @Override
        public Shape getValue(Image object) {
            return object.getImageShape();
        }

        @Override
        public void setValue(Image object, Shape value) {
            object.setImageShape(value);
        }

    };

    private final String name;

    private MaterialProperty(String name) {
        this.name = name;
    }

    public static <O, V> MaterialProperty<O, V> getProperty(String propertyName, O object) {
        ObjectUtils.verifyNotNull(propertyName, "propertyName");
        ObjectUtils.verifyNotNull(object, "object");

        //noinspection unchecked
        for (MaterialProperty<O, V> property : new MaterialProperty[]{
                X, Y, Z, WIDTH, HEIGHT, TRANSLATION_X, TRANSLATION_Y, TRANSLATION_Z,
                PIVOT_X, PIVOT_Y, SCALE_X, SCALE_Y, ROTATION, ALPHA, COLOR, SHAPE,
                SHADOW_SIZE, SHADOW_COLOR, SHADOW_TYPE, GLOW_SIZE, GLOW_COLOR, GLOW_TYPE,
                OVERLAY_COLOR, OVERLAY_TYPE, RIPPLE_COLOR, RIPPLE_TYPE, TEXT, TEXT_COLOR, TEXT_FONT,
                IMAGE, IMAGE_SCALE_X, IMAGE_SCALE_Y, IMAGE_ROTATION, IMAGE_ALPHA, IMAGE_COLOR, IMAGE_SHAPE
        }) {
            if (property.getName(object).equals(propertyName)) {
                return property;
            }
        }

        return null;
    }

    public String getName(O object) {
        ObjectUtils.verifyNotNull(object, "object");

        if (object instanceof MaterialContent) {
            MaterialContainer container = ((MaterialContent) object).getContainer();

            ObjectUtils.verifyNotNull(container, "object");

            return StringUtils.getCamelCase(container.getName() + StringUtils.getPascalCase(name));
        }

        return name;
    }

}
