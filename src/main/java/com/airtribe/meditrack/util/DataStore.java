package com.airtribe.meditrack.util;

import com.airtribe.meditrack.contract.Searchable;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Learning-only in-memory store kept for the OOP module.
 * The Spring backend persists data through Spring Data JPA repositories.
 */
public class DataStore<T> {
    private final Map<String, T> store = new LinkedHashMap<>();

    public void add(String id, T entity) {
        Validator.requireNonNull(id, "id");
        Validator.requireNonNull(entity, "entity");
        store.put(id, entity);
    }

    public Optional<T> get(String id) {
        return Optional.ofNullable(store.get(id));
    }

    public List<T> list() {
        return Collections.unmodifiableList(store.values().stream().toList());
    }

    public void remove(String id) {
        store.remove(id);
    }

    @SuppressWarnings("unchecked")
    public List<T> search(String query) {
        if (query == null || query.isBlank()) {
            return list();
        }
        return store.values().stream()
                .filter(entity -> entity instanceof Searchable)
                .map(entity -> (Searchable) entity)
                .filter(searchable -> searchable.matchesQuery(query))
                .map(entity -> (T) entity)
                .collect(Collectors.toList());
    }

    public boolean exists(String id) {
        return store.containsKey(id);
    }
}
