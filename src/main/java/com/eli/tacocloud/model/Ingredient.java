package com.eli.tacocloud.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

@Table
public record Ingredient(@Id String id, String name, Type type) implements Persistable<String> {
    public enum Type{
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return true;
    }
}
