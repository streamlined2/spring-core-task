package com.streamlined.tasks.storage;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import com.streamlined.tasks.entity.Entity;

public class HashMapStorage<K, T extends Entity<K>> {

    private final Map<K, T> entityMap;

    public HashMapStorage() {
        entityMap = new HashMap<>();
    }

    public T saveNew(T entity) {
        return entityMap.putIfAbsent(entity.getPrimaryKey(), entity);
    }

    public void save(K id, T entity) {
        entityMap.put(id, entity);
    }

    public T get(K id) {
        return entityMap.get(id);
    }

    public void remove(K id) {
        entityMap.remove(id);
    }

    public Stream<T> getAll() {
        return entityMap.values().stream();
    }

    public void addAll(Map<K, T> map) {
        entityMap.putAll(map);
    }

    public void deleteAll() {
        entityMap.clear();
    }

    public int size() {
        return entityMap.size();
    }

}
