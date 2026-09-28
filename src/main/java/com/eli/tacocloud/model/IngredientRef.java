package com.eli.tacocloud.model;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
public class IngredientRef {
    private final String ingredient;
}
