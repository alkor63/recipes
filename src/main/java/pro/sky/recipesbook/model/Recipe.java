package pro.sky.recipesbook.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
@Data
@AllArgsConstructor
public class Recipe {
    private String recipeName;
    private int cookingTimeInMinutes;
    private ArrayList<Ingredient> ingredients;
    private ArrayList<String> cookingSteps;
}
