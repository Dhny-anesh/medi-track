package com.airtribe.meditrack.entity;

import com.airtribe.meditrack.contract.Searchable;
import jakarta.persistence.MappedSuperclass;
import java.util.Objects;

@MappedSuperclass
public abstract class MedicalEntity implements Searchable {
    @jakarta.persistence.Id
    private String id;
    private String name;

    protected MedicalEntity() {
        // JPA requires a no-arg constructor
    }

    protected MedicalEntity(String id, String name) {
        this.id = Objects.requireNonNull(id, "id cannot be null");
        this.name = Objects.requireNonNull(name, "name cannot be null");
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getIdentifier() {
        return id;
    }

    @Override
    public boolean matchesQuery(String query) {
        if (query == null || query.isBlank()) {
            return false;
        }
        String lower = query.toLowerCase();
        return id.toLowerCase().contains(lower) || name.toLowerCase().contains(lower);
    }

    @Override
    public String toString() {
        return String.format("%s[id=%s, name=%s]", getClass().getSimpleName(), id, name);
    }
}
