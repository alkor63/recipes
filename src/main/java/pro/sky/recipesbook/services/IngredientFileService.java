package pro.sky.recipesbook.services;

import org.springframework.core.io.InputStreamResource;

import java.io.File;
import java.io.FileNotFoundException;

public interface IngredientFileService {
    boolean saveIngredientToFile(String json);

    String readIngredientsFromFile();

    InputStreamResource downloadIngredientFile() throws FileNotFoundException;

    File getIngredientFile();
}
