package pro.sky.recipesbook.services;

import java.io.File;

public interface FileService {
    boolean saveRecipeToFile(String json);

//    String readRecipeFromFile();

    String readRecipesMapFromFile();

    File getRecipeFile();

    File getIngredientFile();

    boolean cleanRecipeFile();
}
