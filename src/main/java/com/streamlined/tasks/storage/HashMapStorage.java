package com.streamlined.tasks.storage;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import com.streamlined.tasks.entity.Entity;
import com.streamlined.tasks.parser.Parser;

import jakarta.annotation.PostConstruct;

public class HashMapStorage<K, T extends Entity<K>> {

    private final Map<K, T> entityMap;
    private Parser<K, T> parser;

    public HashMapStorage() {
        entityMap = new HashMap<>();
    }

    public HashMapStorage(Parser<K, T> parser) {
        this();
        this.parser = parser;
    }

    @PostConstruct
    protected void initilialize() {
        if (Objects.nonNull(parser)) {
            entityMap.putAll(parser.parse());
        }
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

    public void clear() {
        entityMap.clear();
    }

    public int size() {
        return entityMap.size();
    }

}
