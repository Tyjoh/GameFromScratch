package com.bytesmyth.lifegame.tilemap;

import com.bytesmyth.graphics.texture.TextureRegion;

import java.util.HashMap;
import java.util.Map;

public class TileRegistry {

    private Map<String, Entry> nameToEntry = new HashMap<>();
    private Map<Integer, Entry> idToEntry = new HashMap<>();

    public void register(String name, int id, TextureRegion region) {
        Entry entry = new Entry(region, name, id);
        nameToEntry.put(name, entry);
        idToEntry.put(id, entry);
    }

    public Entry get(int id) {
        return idToEntry.get(id);
    }

    public Entry get(String name) {
        return nameToEntry.get(name);
    }

    public int toId(String name) {
        if (nameToEntry.containsKey(name)) {
            return nameToEntry.get(name).id;
        }
        return -1;
    }

    public String toName(int id) {
        if (idToEntry.containsKey(id)) {
            return idToEntry.get(id).friendlyName;
        }
        return null;
    }

    public static class Entry {
        public final TextureRegion region;
        public final String friendlyName;
        public final int id;

        private Entry(TextureRegion region, String friendlyName, int id) {
            this.region = region;
            this.friendlyName = friendlyName;
            this.id = id;
        }
    }

}
