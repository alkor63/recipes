package pro.sky.recipesbook.services;

import java.io.File;
import java.io.FileNotFoundException;

public interface IngredientFileService {
    boolean saveIngredientToFile(String json);

    String readIngredientsFromFile();

    boolean downloadIngredientFile() throws FileNotFoundException;

    File getIngredientFile();
}
