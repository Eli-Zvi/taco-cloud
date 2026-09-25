package com.eli.tacocloud.model;

import org.springframework.data.annotation.Id;

public record Ingredient(@Id String id, String name, Type type) {

    public enum Type{
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }
}
