package com.bytesmyth.util;

import java.util.ArrayList;

public class Pool<T> {

    private Factory<T> elementFactory;
    private ArrayList<T> freeObjects;
    private Class<T> clazz;

    public Pool(Factory<T> elementFactory, int initialCapacity) {
        this.elementFactory = elementFactory;
        freeObjects = new ArrayList<>(initialCapacity);
        for (int i = 0; i < initialCapacity; i++) {
            freeObjects.add(elementFactory.create());
        }

        clazz = (Class<T>) freeObjects.get(0).getClass();
    }

    public T get() {
        if (freeObjects.size() == 0) {
            return elementFactory.create();
        } else {
            return freeObjects.remove(freeObjects.size() - 1);
        }
    }

    public void free(Object t) {
        assert t != null;
        assert t.getClass().isAssignableFrom(clazz);
        freeObjects.add((T) t);
    }

}
