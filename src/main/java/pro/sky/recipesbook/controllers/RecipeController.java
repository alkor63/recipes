package pro.sky.recipesbook.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.recipesbook.model.Ingredient;
import pro.sky.recipesbook.model.Recipe;
import pro.sky.recipesbook.services.Impl.RecipeServiceImpl;
import pro.sky.recipesbook.services.RecipeService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/recipe")
@Tag(name = "Книга рецептова", description = "CRUD-операции с рецептами")
public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping
    @Operation(summary = "Добавление рецепта в книгу", description = "нужно заполнить все поля рецепта в Body")
    public ResponseEntity<Recipe> createRecipe(@RequestBody Recipe recipe) throws JsonProcessingException {
        Recipe newRecipe = recipeService.addRecipe(recipe);
        return ResponseEntity.ok(newRecipe);
    }
//    @PostMapping("/json")
//    @Operation(summary = "Добавление рецепта из файла в книгу", description = "будем читать файл recipes.json")
//    public ResponseEntity<Recipe> readRecipeFromJsonFile() {
//        return ResponseEntity.ok().build();
//    }
    @GetMapping("/{recipeId}")
    @Operation(summary = "Показать один рецепт", description = "нужно указать id рецепта")
    public ResponseEntity<Recipe> getRecipe(@PathVariable Long recipeId) {
        Recipe recipe = recipeService.getRecipe(recipeId);
        if (recipe == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipe);
    }

    @PutMapping("/{recipeId}")
    @Operation(summary = "Отредактировать рецепт", description = "нужно указать id и заполнить все поля рецепта в Body")
    public ResponseEntity<Recipe> editRecipe(@PathVariable Long recipeId, @RequestBody Recipe recipe) {
        Recipe newRecipe = recipeService.editRecipe(recipeId, recipe);
        if (newRecipe == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(newRecipe);
    }

    @DeleteMapping("/{recipeId}")
    @Operation(summary = "Удалить один рецепт", description = "нужно указать id рецепта")
    public ResponseEntity<Void> deleteRecipe(@PathVariable long recipeId) {
        if (recipeService.deleteRecipe(recipeId)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping
    @Operation(summary = "Удалить все рецепты")
    public ResponseEntity<Void> deleteAllRecipes() {
        recipeService.deleteAllRecipes();
        return ResponseEntity.ok().build();
    }
    @GetMapping
    @Operation(summary = "Показать все рецепты книги")
    public ResponseEntity<List<Recipe>> getAllRecipes() {
        List<Recipe> allRecipes = recipeService.getAllRecipes();
        if (allRecipes.size() > 0) {
            return ResponseEntity.ok(allRecipes);
        }
        return ResponseEntity.notFound().build();
    }


}
