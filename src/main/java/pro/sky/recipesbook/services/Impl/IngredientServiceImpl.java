package pro.sky.recipesbook.services.Impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import pro.sky.recipesbook.model.Ingredient;
import pro.sky.recipesbook.model.Recipe;
import pro.sky.recipesbook.services.FileService;
import pro.sky.recipesbook.services.IngredientFileService;
import pro.sky.recipesbook.services.IngredientService;

import javax.annotation.PostConstruct;
import java.io.File;
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

    @PostConstruct
    private void init() {
        File file = ingredientFileService.getIngredientFile();
        if (file.exists()) {
            readIngredientsFromFile();
        }
    }

    @Override
    public int getIngId() {
        return ingId;
    }

    @Override
    public Ingredient addIngredient(Ingredient ingredient) throws JsonProcessingException {

        ingredients.putIfAbsent(ingId++, ingredient);
        String json = new ObjectMapper().writeValueAsString(ingredients);
        ingredientFileService.saveIngredientToFile(json);
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
    public Ingredient editIngredient(int ingredientId, Ingredient ingredient) throws JsonProcessingException {
        if (ingredients.containsKey(ingredientId)) {
            ingredients.put(ingredientId, ingredient);
            String json = new ObjectMapper().writeValueAsString(ingredients);
            ingredientFileService.saveIngredientToFile(json);
            return ingredient;
        }
        return null;
    }

    @Override
    public void deleteAllIngredients() {
        ingredients.clear();
    }

    @Override
    public boolean deleteIngredient(int ingredientId) throws JsonProcessingException {
        if (ingredients.containsKey(ingredientId)) {
            ingredients.remove(ingredientId);
            String json = new ObjectMapper().writeValueAsString(ingredients);
            ingredientFileService.saveIngredientToFile(json);
            return true;
        }
        return false;
    }

    private void readIngredientsFromFile() {
        try {
            String json = ingredientFileService.readIngredientsFromFile();
            ingredients = new ObjectMapper().readValue(json, new TypeReference<HashMap<Integer, Ingredient>>() {

            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
