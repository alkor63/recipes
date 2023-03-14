package pro.sky.recipesbook.services.Impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import pro.sky.recipesbook.model.Ingredient;
import pro.sky.recipesbook.services.FileService;
import pro.sky.recipesbook.services.IngredientFileService;
import pro.sky.recipesbook.services.IngredientService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IngredientServiceImpl implements IngredientService {
    private Map<Integer, Ingredient> ingredients = new HashMap<>();
    private int ingId = 0;
    private final IngredientFileService ingredientFileService;

    public IngredientServiceImpl(IngredientFileService ingredientFileService) {
        this.ingredientFileService = ingredientFileService;
    }

    @Override
    public int getIngId() {
        return ingId;
    }

    @Override
    public Ingredient addIngredient(Ingredient ingredient) {

        ingredients.putIfAbsent(ingId++, ingredient);
        ingredientFileService.saveIngredientToFile(String.valueOf(ingredient));
        return ingredient;
    }

    @Override
    public Ingredient getIngredient(int ingredientId) {
        return ingredients.get(ingredientId);
    }

    @Override
    public List<Ingredient> getAllIngredients() {
        return new ArrayList<>(ingredients.values());
    }

    @Override
    public Ingredient editIngredient(int ingredientId, Ingredient ingredient) {
        if (ingredients.containsKey(ingredientId)) {
            ingredients.put(ingredientId, ingredient);
            return ingredient;
        }
        return null;
    }

    @Override
    public void deleteAllIngredients() {
        ingredients.clear();
    }

    @Override
    public boolean deleteIngredient(int ingredientId) {
        if (ingredients.containsKey(ingredientId)) {
            ingredients.remove(ingredientId);
            return true;
        }
        return false;
    }

}
