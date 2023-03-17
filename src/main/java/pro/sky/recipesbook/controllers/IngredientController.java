package pro.sky.recipesbook.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.recipesbook.model.Ingredient;
import pro.sky.recipesbook.model.Recipe;
import pro.sky.recipesbook.services.IngredientService;

import java.util.List;

@RestController
@RequestMapping("/ingredient")
@Tag(name = "Игредиенты для рецептов", description = "CRUD-операции с ингредиентами")

public class IngredientController {

    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @PostMapping
    @Operation(summary = "Добавить ингредиент", description = "нужно заполнить все поля ингредиента в Body")
    public ResponseEntity<Ingredient> createIngredient(@RequestBody Ingredient ingredient) throws JsonProcessingException {
        Ingredient newIngredient = ingredientService.addIngredient(ingredient);
        return ResponseEntity.ok(newIngredient);
    }

    @GetMapping("/{ingredientId}")
    @Operation(summary = "Показать ингредиент", description = "нужно указать id ингредиента")
    public ResponseEntity<Ingredient> getIngredient(@PathVariable int ingredientId) {
        Ingredient ingredient = ingredientService.getIngredient(ingredientId);
        if (ingredient == null) {
            return ResponseEntity.notFound().build();
        } else return ResponseEntity.ok(ingredient);
    }

    @PutMapping("/{ingredientId}")
    @Operation(summary = "Отредактировать ингредиент", description = "нужно указать id и заполнить все поля ингредиента в Body")
    public ResponseEntity<Ingredient> editIngredient(@PathVariable int ingredientId, @RequestBody Ingredient ingredient) throws JsonProcessingException {
        Ingredient newIngredient = ingredientService.editIngredient(ingredientId, ingredient);
        if (newIngredient == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(newIngredient);
    }

    @DeleteMapping("/{ingredientId}")
    @Operation(summary = "Удалить ингредиент", description = "нужно указать id ингредиента")
    public ResponseEntity<Void> deleteIngredient(@PathVariable int ingredientId) throws JsonProcessingException {
        if (ingredientService.deleteIngredient(ingredientId)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping
    @Operation(summary = "Удалить все ингредиенты")
    public ResponseEntity<Void> deleteAllIngredient() {
        ingredientService.deleteAllIngredients();
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @Operation(summary = "Показать список всех ингредиентов")
    public ResponseEntity<List<Ingredient>> getAllIngredients() {
        List<Ingredient> allIngredients = ingredientService.getAllIngredients();
        if (allIngredients.size() > 0) {
            return ResponseEntity.ok(allIngredients);
        }
        return ResponseEntity.notFound().build();
    }

}
