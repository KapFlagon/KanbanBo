package utils;

import java.util.HashMap;
import java.util.Map;

public class LocalDragboard {
    // From James_D
    // https://stackoverflow.com/questions/22820160/accessing-properties-of-custom-object-from-javafx-draganddrop-clipboard
    // https://community.oracle.com/tech/developers/discussion/2513382/drag-and-drop-objects-with-javafx-properties

    private final Map<Class<?>, Object> contents;

    private final static LocalDragboard instance = new LocalDragboard();

    private LocalDragboard() {
        this.contents = new HashMap<Class<?>, Object>();
    }

    public static LocalDragboard getInstance() {
        return instance ;
    }

    public <T> void putValue(Class<T> type, T value) {
        contents.put(type, type.cast(value));
    }

    public <T> T getValue(Class<T> type) {
        return type.cast(contents.get(type));
    }

    public boolean hasType(Class<?> type) {
        return contents.keySet().contains(type);
    }

    public void clear(Class<?> type) {
        contents.remove(type);
    }

    public void clearAll() {
        contents.clear();
    }
}
