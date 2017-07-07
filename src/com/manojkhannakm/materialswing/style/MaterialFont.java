package com.manojkhannakm.materialswing.style;

import com.manojkhannakm.materialswing.util.ResourceUtils;

import java.awt.*;
import java.awt.font.TextAttribute;
import java.util.Collections;

/**
 * @author Manoj Khanna
 */

public class MaterialFont extends Font {

    public static final MaterialFont ROBOTO_THIN = ResourceUtils.getFont(ResourceUtils.Paths.Fonts.ROBOTO_THIN);
    public static final MaterialFont ROBOTO_THIN_ITALIC = ResourceUtils.getFont(ResourceUtils.Paths.Fonts.ROBOTO_THIN_ITALIC);
    public static final MaterialFont ROBOTO_LIGHT = ResourceUtils.getFont(ResourceUtils.Paths.Fonts.ROBOTO_LIGHT);
    public static final MaterialFont ROBOTO_LIGHT_ITALIC = ResourceUtils.getFont(ResourceUtils.Paths.Fonts.ROBOTO_LIGHT_ITALIC);
    public static final MaterialFont ROBOTO_REGULAR = ResourceUtils.getFont(ResourceUtils.Paths.Fonts.ROBOTO_REGULAR);
    public static final MaterialFont ROBOTO_REGULAR_ITALIC = ResourceUtils.getFont(ResourceUtils.Paths.Fonts.ROBOTO_REGULAR_ITALIC);
    public static final MaterialFont ROBOTO_MEDIUM = ResourceUtils.getFont(ResourceUtils.Paths.Fonts.ROBOTO_MEDIUM);
    public static final MaterialFont ROBOTO_MEDIUM_ITALIC = ResourceUtils.getFont(ResourceUtils.Paths.Fonts.ROBOTO_MEDIUM_ITALIC);
    public static final MaterialFont ROBOTO_BOLD = ResourceUtils.getFont(ResourceUtils.Paths.Fonts.ROBOTO_BOLD);
    public static final MaterialFont ROBOTO_BOLD_ITALIC = ResourceUtils.getFont(ResourceUtils.Paths.Fonts.ROBOTO_BOLD_ITALIC);
    public static final MaterialFont ROBOTO_BLACK = ResourceUtils.getFont(ResourceUtils.Paths.Fonts.ROBOTO_BLACK);
    public static final MaterialFont ROBOTO_BLACK_ITALIC = ResourceUtils.getFont(ResourceUtils.Paths.Fonts.ROBOTO_BLACK_ITALIC);

    public static final MaterialFont ROBOTO_CONDENSED_LIGHT = ResourceUtils.getFont(ResourceUtils.Paths.Fonts.ROBOTO_CONDENSED_LIGHT);
    public static final MaterialFont ROBOTO_CONDENSED_LIGHT_ITALIC = ResourceUtils.getFont(ResourceUtils.Paths.Fonts.ROBOTO_CONDENSED_LIGHT_ITALIC);
    public static final MaterialFont ROBOTO_CONDENSED_REGULAR = ResourceUtils.getFont(ResourceUtils.Paths.Fonts.ROBOTO_CONDENSED_REGULAR);
    public static final MaterialFont ROBOTO_CONDENSED_REGULAR_ITALIC = ResourceUtils.getFont(ResourceUtils.Paths.Fonts.ROBOTO_CONDENSED_REGULAR_ITALIC);
    public static final MaterialFont ROBOTO_CONDENSED_BOLD = ResourceUtils.getFont(ResourceUtils.Paths.Fonts.ROBOTO_CONDENSED_BOLD);
    public static final MaterialFont ROBOTO_CONDENSED_BOLD_ITALIC = ResourceUtils.getFont(ResourceUtils.Paths.Fonts.ROBOTO_CONDENSED_BOLD_ITALIC);

    public MaterialFont(Font font) {
        super(font);
    }

    public MaterialFont(String fontName) {
        super(fontName, Font.PLAIN, 0);
    }

    public MaterialFont withSize(float size) {
        if (size < 0.0f) {
            size = 0.0f;
        }

        return new MaterialFont(deriveFont(size));
    }

    public MaterialFont withStyle(int style) {
        if (style < Font.PLAIN || style > (Font.BOLD | Font.ITALIC)) {
            style = Font.PLAIN;
        }

        return new MaterialFont(deriveFont(style));
    }

    public MaterialFont withAttribute(TextAttribute attributeKey, Object attributeValue) {
        return new MaterialFont(deriveFont(Collections.singletonMap(attributeKey, attributeValue)));
    }

}
