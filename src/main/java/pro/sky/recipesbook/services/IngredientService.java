package pro.sky.recipesbook.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import pro.sky.recipesbook.model.Ingredient;
import pro.sky.recipesbook.model.Recipe;

import java.util.List;

public interface IngredientService {
    int getIngId();

    Ingredient addIngredient(Ingredient ingredient) throws JsonProcessingException;
    Ingredient getIngredient(int ingredientId);

    List<Ingredient> getAllIngredients();

    Ingredient editIngredient(int ingredientId, Ingredient ingredient) throws JsonProcessingException;

    void deleteAllIngredients();

    boolean deleteIngredient(int ingredientId) throws JsonProcessingException;
}
