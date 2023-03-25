package pro.sky.recipesbook.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import pro.sky.recipesbook.model.Recipe;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public interface RecipeService {

    Long getRecId();

//    Map<Long, Recipe> getRecipes();

    Recipe addRecipe(Recipe recipe) throws JsonProcessingException;

//    Recipe addRecipeFromFile(Recipe recipe)
        /*
            public List<Recipe> getRecipesByIngredientId(int ingId) {
                Ingredient ingredient = this.ingredientService.getIngredient(ingId);
                return this.recipes.entrySet().stream()
                        .filter(e -> e.getValue().getIngredients().stream()
                                .anyMatch(i -> i.getName().equals(ingredient.getName())))
                        .map(e -> e.getValue()).collect(Collectors.toList());
            }
        */;

    Recipe getRecipe(Long recipeId);

    List<Recipe> getAllRecipes();

    Recipe editRecipe(Long recipeId, Recipe recipe);

    void deleteAllRecipes();

    boolean deleteRecipe(Long recipeId);

    Path createTxtFile() throws IOException;
}
