package pro.sky.recipesbook.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recipe {
    private String name;
    private int cookingTimeInMinutes;
    private List<Ingredient> ingredients;
    private List<String> cookingSteps;

    @Override
    public String toString() {
        String s = name + "\nВремя приготовления: " + cookingTimeInMinutes + "\n" +
                "Ингредиенты: \n" + ingredients + "\n" +
                "Инструкция приготовления: \n" + cookingSteps.stream();
        return s;

    }
}