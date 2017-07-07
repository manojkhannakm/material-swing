package com.manojkhannakm.materialswing.event;

import com.manojkhannakm.materialswing.swing.Material;
import com.manojkhannakm.materialswing.util.ObjectUtils;

/**
 * @author Manoj Khanna
 */

public class MaterialEvent {

    public static final String REPAINT = "repaint";
    public static final String REVALIDATE = "revalidate";
    public static final String RELAYOUT = "relayout";

    private final Material material;

    public MaterialEvent(Material material) {
        ObjectUtils.verifyNotNull(material, "material");

        this.material = material;
    }

    public Material getMaterial() {
        return material;
    }

}
