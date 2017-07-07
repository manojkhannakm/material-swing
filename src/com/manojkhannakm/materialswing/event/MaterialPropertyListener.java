package com.manojkhannakm.materialswing.event;

/**
 * @author Manoj Khanna
 */

public interface MaterialPropertyListener<O, V> extends MaterialListener {

    void materialPropertyChanged(MaterialPropertyEvent<O, V> event);

    @Override
    default void materialChanged(MaterialEvent event) {
        //noinspection unchecked
        materialPropertyChanged((MaterialPropertyEvent<O, V>) event);
    }

}
