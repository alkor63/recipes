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
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RecipeServiceImpl implements RecipeService {
    private static Map<Long, Recipe> recipes = new HashMap<>();
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

    public static Map<Long, Recipe> getRecipes() {
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

    @Override
    public Path createTxtFile() throws IOException {
        Path path = fileService.createTempFile("txtRecipes");//мы генерируем файл с именем "recipesForUser"
        for (Recipe recipe : recipes.values()) {
            try (Writer writer = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
                // newBufferedWriter открывает поток по пути path для записи в файл,
                // StandardOpenOption.APPEND опция позволяет добавлять информацию в конец файла
                writer.append(recipe.getName()).append("\n").append("\nВремя приготовления: ")
                        .append(String.valueOf(recipe.getCookingTimeInMinutes())).append(" минут.\n")
                        .append("Ингредиенты:\n");
                recipe.getIngredients().forEach(ingredient -> {
                    try {
                        writer.append(" * ").append(ingredient.getName()).append(" ")
                                .append(String.valueOf(ingredient.getQuantity())).append(" ")
                                .append(ingredient.getMeasureUnit());
                        writer.append("\n");

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
                writer.append("\n");
                writer.append("Инструкция приготовления: \n");
                List<String> steps = recipe.getCookingSteps();
                for (int i = 0; i < steps.size(); i++) {
                    String s = (i + 1) + " " + steps.get(i) + "\n";
                    try {
                        writer.append(s);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                ;
                writer.append(" ==============================================\n");
            }
        }
        return path;
    }
}
