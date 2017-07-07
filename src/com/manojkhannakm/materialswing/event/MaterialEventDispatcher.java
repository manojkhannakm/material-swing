package com.manojkhannakm.materialswing.event;

import com.manojkhannakm.materialswing.swing.Material;
import com.manojkhannakm.materialswing.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Manoj Khanna
 */

public class MaterialEventDispatcher {

    private final HashMap<String, ArrayList<MaterialListener>> listenerMap = new HashMap<>();

    public MaterialEventDispatcher(Material material) {
        ObjectUtils.verifyNotNull(material, "material");
    }

    public void addMaterialListener(String eventName, MaterialListener listener) {
        ObjectUtils.verifyNotNull(eventName, "eventName");
        ObjectUtils.verifyNotNull(listener, "listener");

        listenerMap.computeIfAbsent(eventName, key -> new ArrayList<>()).add(listener);
    }

    public void removeMaterialListener(String eventName, MaterialListener listener) {
        ObjectUtils.verifyNotNull(eventName, "eventName");
        ObjectUtils.verifyNotNull(listener, "listener");

        if (listenerMap.containsKey(eventName)) {
            listenerMap.get(eventName).remove(listener);
        }
    }

    public void dispatchEvent(String eventName, MaterialEvent event) {
        ObjectUtils.verifyNotNull(eventName, "eventName");
        ObjectUtils.verifyNotNull(event, "event");

        if (event instanceof MaterialPropertyEvent) {
            MaterialPropertyEvent propertyEvent = (MaterialPropertyEvent) event;

            Object oldValue = propertyEvent.getOldValue(), newValue = propertyEvent.getNewValue();

            if (oldValue == newValue || oldValue != null && oldValue.equals(newValue)
                    || newValue != null && newValue.equals(oldValue)) {
                return;
            }
        }

        if (listenerMap.containsKey(eventName)) {
            for (MaterialListener listener : listenerMap.get(eventName)) {
                listener.materialChanged(event);
            }
        }
    }

    public MaterialListener[] getMaterialListeners(String eventName) {
        ObjectUtils.verifyNotNull(eventName, "eventName");

        if (!listenerMap.containsKey(eventName)) {
            return new MaterialListener[0];
        }

        List<MaterialListener> listenerList = listenerMap.get(eventName);

        return listenerList.toArray(new MaterialListener[listenerList.size()]);
    }

}
