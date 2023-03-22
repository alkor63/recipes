package pro.sky.recipesbook.services.Impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import pro.sky.recipesbook.model.Ingredient;
import pro.sky.recipesbook.model.Recipe;
import pro.sky.recipesbook.services.FileService;
import pro.sky.recipesbook.services.IngredientService;
import pro.sky.recipesbook.services.RecipeService;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RecipeServiceImpl implements RecipeService {
    private Map<Long, Recipe> recipes = new HashMap<>();
    private Long recId = 0L;
    private final IngredientService ingredientService;
    private final FileService fileService;

    public RecipeServiceImpl(IngredientService ingredientService, FileService fileService) {
        this.ingredientService = ingredientService;
        this.fileService = fileService;
    }

    @PostConstruct
    private void init() {

        File file = fileService.getRecipeFile();
        if (file.exists()) {
            readRecipesFromMapFile();
        }
    }
    @Override
    public Long getRecId() {
        return recId;
    }

    @Override
    public Map<Long, Recipe> getRecipes() {
        return recipes;
    }

    @Override
    public Recipe addRecipe(Recipe recipe) throws JsonProcessingException {
        if (!recipes.containsValue(recipe)) {
            recipes.put(recId++, recipe);
            for (Ingredient ingredient : recipe.getIngredients()) {
                this.ingredientService.addIngredient(ingredient);

            }
            String json = new ObjectMapper().writeValueAsString(recipes);
            fileService.saveRecipeToFile(json);
        } else System.out.println("Рецепт " + recipe.getName() + " уже есть в этой книге");
        return recipe;
    }

    //@Override
//    public Recipe addRecipeFromFile(Recipe recipe) {
//        if (!recipes.containsValue(recipe)) {
//            recipes.put(recId++, recipe);
//            for (Ingredient ingredient : recipe.getIngredients()) {
//                this.ingredientService.addIngredient(ingredient);
//
//            }
//            saveRecipeToFile();
//        } else System.out.println("Рецепт " + recipe.getName() + " уже есть в этой книге");
//        return recipe;
//    }
    /*
        public List<Recipe> getRecipesByIngredientId(int ingId) {
            Ingredient ingredient = this.ingredientService.getIngredient(ingId);
            return this.recipes.entrySet().stream()
                    .filter(e -> e.getValue().getIngredients().stream()
                            .anyMatch(i -> i.getName().equals(ingredient.getName())))
                    .map(e -> e.getValue()).collect(Collectors.toList());
        }
    */
    @Override
    public Recipe getRecipe(Long recipeId) {
        return recipes.get(recipeId);
    }

    @Override
    public List<Recipe> getAllRecipes() {
        return new ArrayList<>(recipes.values());
    }

    @Override
    public Recipe editRecipe(Long recipeId, Recipe recipe) {
        if (recipes.containsKey(recipeId)) {
            recipes.put(recipeId, recipe);
            try {
                String json = new ObjectMapper().writeValueAsString(recipes);
                fileService.saveRecipeToFile(json);
                return recipe;
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    @Override
    public void deleteAllRecipes() {
        recipes.clear();
    }

    @Override
    public boolean deleteRecipe(Long recipeId) {
        if (recipes.containsKey(recipeId)) {
            recipes.remove(recipeId);
            try {
                String json = new ObjectMapper().writeValueAsString(recipes);
                fileService.saveRecipeToFile(json);
                return true;
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }

    private void saveRecipeToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(recipes);
            fileService.saveRecipeToFile(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void readRecipesFromMapFile() {
        try {
            String json = fileService.readRecipesMapFromFile();
            recipes = new ObjectMapper().readValue(json, new TypeReference<HashMap<Long, Recipe>>() {

            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
//    private void readRecipeFromJsonFile() {
//        try {
//            String json = fileService.readRecipeFromFile();
//            Recipe newRecipe = new ObjectMapper().readValue(json, new TypeReference<Recipe>() {
//            });
//            addRecipeFromFile(newRecipe);
//
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
