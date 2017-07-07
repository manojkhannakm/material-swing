package com.manojkhannakm.materialswing.util;

import com.manojkhannakm.materialswing.style.MaterialFont;
import com.manojkhannakm.materialswing.style.MaterialImage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

/**
 * @author Manoj Khanna
 */

public class ResourceUtils {

    private ResourceUtils() {
    }

    public static MaterialImage getImage(String imagePath) {
        ObjectUtils.verifyNotNull(imagePath, "imagePath");

        try {
            return new MaterialImage(ImageIO.read(ResourceUtils.class.getResourceAsStream(imagePath)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static MaterialFont getFont(String fontPath) {
        ObjectUtils.verifyNotNull(fontPath, "fontPath");

        try {
            return new MaterialFont(Font.createFont(Font.TRUETYPE_FONT, ResourceUtils.class.getResourceAsStream(fontPath)));
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static class Paths {

        public static class Images {

            private static final String IMAGES = "/images/";

            public static final String WINDOW_BACK = IMAGES + "window_back.png";
            public static final String WINDOW_MINIMIZE = IMAGES + "window_minimize.png";
            public static final String WINDOW_MAXIMIZE = IMAGES + "window_maximize.png";
            public static final String WINDOW_RESTORE = IMAGES + "window_restore.png";
            public static final String WINDOW_CLOSE = IMAGES + "window_close.png";

        }

        public static class Fonts {

            private static final String FONTS = "/fonts/";

            public static final String ROBOTO_THIN = FONTS + "Roboto-Thin.ttf";
            public static final String ROBOTO_THIN_ITALIC = FONTS + "Roboto-ThinItalic.ttf";
            public static final String ROBOTO_LIGHT = FONTS + "Roboto-Light.ttf";
            public static final String ROBOTO_LIGHT_ITALIC = FONTS + "Roboto-LightItalic.ttf";
            public static final String ROBOTO_REGULAR = FONTS + "Roboto-Regular.ttf";
            public static final String ROBOTO_REGULAR_ITALIC = FONTS + "Roboto-Italic.ttf";
            public static final String ROBOTO_MEDIUM = FONTS + "Roboto-Medium.ttf";
            public static final String ROBOTO_MEDIUM_ITALIC = FONTS + "Roboto-MediumItalic.ttf";
            public static final String ROBOTO_BOLD = FONTS + "Roboto-Bold.ttf";
            public static final String ROBOTO_BOLD_ITALIC = FONTS + "Roboto-BoldItalic.ttf";
            public static final String ROBOTO_BLACK = FONTS + "Roboto-Black.ttf";
            public static final String ROBOTO_BLACK_ITALIC = FONTS + "Roboto-BlackItalic.ttf";

            public static final String ROBOTO_CONDENSED_LIGHT = FONTS + "RobotoCondensed-Light.ttf";
            public static final String ROBOTO_CONDENSED_LIGHT_ITALIC = FONTS + "RobotoCondensed-LightItalic.ttf";
            public static final String ROBOTO_CONDENSED_REGULAR = FONTS + "RobotoCondensed-Regular.ttf";
            public static final String ROBOTO_CONDENSED_REGULAR_ITALIC = FONTS + "RobotoCondensed-Italic.ttf";
            public static final String ROBOTO_CONDENSED_BOLD = FONTS + "RobotoCondensed-Bold.ttf";
            public static final String ROBOTO_CONDENSED_BOLD_ITALIC = FONTS + "RobotoCondensed-BoldItalic.ttf";

        }

    }

}
