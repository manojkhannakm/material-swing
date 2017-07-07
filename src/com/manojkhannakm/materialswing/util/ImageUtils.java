package com.manojkhannakm.materialswing.util;

import com.jhlabs.image.BoxBlurFilter;
import com.manojkhannakm.materialswing.style.MaterialColor;
import com.manojkhannakm.materialswing.style.MaterialImage;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Objects;

/**
 * @author Manoj Khanna
 */

public class ImageUtils {

    private static final HashMap<Integer, BoxBlurFilter> BLUR_FILTER_MAP = new HashMap<>();
    private static final HashMap<ImageKey, ImageValue> BLUR_IMAGE_MAP = new HashMap<>();

    private ImageUtils() {
    }

    public static void createBlurImage(int width, int height, Shape shape) {
        ObjectUtils.verifyCondition(width > 0, "width should be > 0!");
        ObjectUtils.verifyCondition(height > 0, "height should be > 0!");
        ObjectUtils.verifyNotNull(shape, "shape");
        ObjectUtils.verifyCondition(() -> {
            Rectangle2D bounds = shape.getBounds2D();

            return bounds.getWidth() > 0.0 && bounds.getHeight() > 0.0;
        }, "shape should be of width and height > 0!");

        BLUR_IMAGE_MAP.computeIfAbsent(new ImageKey(width, height, shape), key -> new ImageValue()).increaseCount();
    }

    public static void destroyBlurImage(int width, int height, Shape shape) {
        ObjectUtils.verifyCondition(width > 0, "width should be > 0!");
        ObjectUtils.verifyCondition(height > 0, "height should be > 0!");
        ObjectUtils.verifyNotNull(shape, "shape");
        ObjectUtils.verifyCondition(() -> {
            Rectangle2D bounds = shape.getBounds2D();

            return bounds.getWidth() > 0.0 && bounds.getHeight() > 0.0;
        }, "shape should be of width and height > 0!");

        ImageKey imageKey = new ImageKey(width, height, shape);

        if (BLUR_IMAGE_MAP.containsKey(imageKey)) {
            ImageValue imageValue = BLUR_IMAGE_MAP.get(imageKey);
            imageValue.decreaseCount();

            if (imageValue.isCountZero()) {
                BLUR_IMAGE_MAP.remove(imageKey);
            }
        }
    }

    public static BoxBlurFilter getBlurFilter(int radius) {
        ObjectUtils.verifyCondition(radius > 0, "radius should be > 0!");

        return BLUR_FILTER_MAP.computeIfAbsent(radius, key -> new BoxBlurFilter(radius, radius, 2));
    }

    public static MaterialImage getBlurImage(int width, int height, Shape shape, int radius) {
        ObjectUtils.verifyCondition(width > 0, "width should be > 0!");
        ObjectUtils.verifyCondition(height > 0, "height should be > 0!");
        ObjectUtils.verifyNotNull(shape, "shape");
        ObjectUtils.verifyCondition(() -> {
            Rectangle2D bounds = shape.getBounds2D();

            return bounds.getWidth() > 0.0 && bounds.getHeight() > 0.0;
        }, "shape should be of width and height > 0!");
        ObjectUtils.verifyCondition(radius > 0, "radius should be > 0!");

        ImageKey imageKey = new ImageKey(width, height, shape);

        if (!BLUR_IMAGE_MAP.containsKey(imageKey)) {
            return null;
        }

        ImageValue imageValue = BLUR_IMAGE_MAP.get(imageKey);

        radius = Math.round(radius / 3.3f);

        MaterialImage image = imageValue.getImage(radius);

        if (image == null) {
            int offset = Math.round(radius * 3.3f);

            image = new MaterialImage(width + 2 * offset, height + 2 * offset);

            Graphics2D graphics = GraphicUtils.getImageGraphics(image.createGraphics());
            graphics.translate((double) offset, (double) offset);
            graphics.setColor(MaterialColor.BLACK);
            graphics.fill(shape);
            graphics.dispose();

            getBlurFilter(radius).filter(image, image);

            imageValue.setImage(radius, image);
        }

        return image;
    }

    public static int getBlurOffset(int radius) {
        ObjectUtils.verifyCondition(radius > 0, "radius should be > 0!");

        return Math.round(Math.round(radius / 3.3f) * 3.3f);
    }

    private static class ImageKey {

        private final int width, height;
        private final Shape shape;

        private ImageKey(int width, int height, Shape shape) {
            this.width = width;
            this.height = height;
            this.shape = shape;
        }

        @Override
        public int hashCode() {
            return Objects.hash(width, height, shape);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }

            if (!(obj instanceof ImageKey)) {
                return false;
            }

            ImageKey imageKey = (ImageKey) obj;

            return imageKey.width == width && imageKey.height == height && Objects.equals(imageKey.shape, shape);
        }

    }

    private static class ImageValue {

        private final HashMap<Integer, MaterialImage> imageMap = new HashMap<>();

        private int count;

        public void increaseCount() {
            count++;
        }

        public void decreaseCount() {
            count--;
        }

        public boolean isCountZero() {
            return count == 0;
        }

        public MaterialImage getImage(int radius) {
            return imageMap.get(radius);
        }

        public void setImage(int radius, MaterialImage image) {
            imageMap.put(radius, image);
        }

    }

}
