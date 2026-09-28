package com.eli.tacocloud.repository.deprecated;

import com.eli.tacocloud.model.Ingredient;

import java.util.Optional;

@Deprecated
public interface OldIngredientRepository {

    Iterable<Ingredient> findAll();

    Optional<Ingredient> findById(String id);

    Ingredient save(Ingredient ingredient);
}
