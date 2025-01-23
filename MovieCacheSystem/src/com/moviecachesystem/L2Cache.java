package com.moviecachesystem;

import java.util.*;

public class L2Cache {
    private final int capacity;
    private Map<String, List<Movie>> globalCache;
    private Map<String, Integer> frequency;

    public L2Cache(int capacity) {
        this.capacity = capacity;
        this.globalCache = new HashMap<>();
        this.frequency = new HashMap<>();
    }

    public List<Movie> get(String searchKey) {
        if (!globalCache.containsKey(searchKey)) return null;
        frequency.put(searchKey, frequency.get(searchKey) + 1);
        return globalCache.get(searchKey);
    }

    public void put(String searchKey, List<Movie> movies) {
        if (globalCache.size() >= capacity) {
            String lfuKey = Collections.min(frequency.entrySet(), Map.Entry.comparingByValue()).getKey();
            globalCache.remove(lfuKey);
            frequency.remove(lfuKey);
        }
        globalCache.put(searchKey, movies);
        frequency.put(searchKey, 1);
    }

    public void clear() {
        globalCache.clear();
        frequency.clear();
    }
}