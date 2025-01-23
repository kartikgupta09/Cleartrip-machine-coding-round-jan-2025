package com.moviecachesystem;

import java.util.*;

public class L1Cache {
    private final int capacity;
    private Map<Integer, LinkedHashMap<String, List<Movie>>> userCache;

    public L1Cache(int capacity) {
        this.capacity = capacity;
        this.userCache = new HashMap<>();
    }

    public List<Movie> get(int userId, String searchKey) {
        if (!userCache.containsKey(userId)) return null;
        LinkedHashMap<String, List<Movie>> cache = userCache.get(userId);
        if (!cache.containsKey(searchKey)) return null;
        List<Movie> result = cache.remove(searchKey);
        cache.put(searchKey, result);
        return result;
    }

    public void put(int userId, String searchKey, List<Movie> movies) {
        userCache.putIfAbsent(userId, new LinkedHashMap<>(capacity, 0.75f, true));
        LinkedHashMap<String, List<Movie>> cache = userCache.get(userId);
        if (cache.size() >= capacity) {
            Iterator<String> it = cache.keySet().iterator();
            it.next();
            it.remove();
        }
        cache.put(searchKey, movies);
    }

    public void clear() {
        userCache.clear();
    }
}