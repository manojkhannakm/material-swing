package com.manojkhannakm.materialswing.effect;

import com.manojkhannakm.materialswing.swing.Material;
import com.manojkhannakm.materialswing.util.ObjectUtils;

import java.awt.*;

/**
 * @author Manoj Khanna
 */

public abstract class MaterialEffect {

    protected final Material material;

    private boolean attached;

    protected MaterialEffect(Material material, boolean attached) {
        ObjectUtils.verifyNotNull(material, "material");

        this.material = material;

        if (attached) {
            setAttached(true);

            ((Component) material).addPropertyChangeListener(
                    "enabled", evt -> setAttached((Boolean) evt.getNewValue()));
        }
    }

    protected abstract void effectAttached();

    protected abstract void effectDetached();

    public boolean isAttached() {
        return attached;
    }

    public void setAttached(boolean attached) {
        this.attached = attached;

        if (attached) {
            effectAttached();
        } else {
            effectDetached();
        }
    }

}
