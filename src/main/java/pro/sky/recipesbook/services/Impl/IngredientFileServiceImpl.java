package pro.sky.recipesbook.services.Impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pro.sky.recipesbook.services.IngredientFileService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
@Service
public class IngredientFileServiceImpl implements IngredientFileService {

    @Value("${path.to.data.file}")
    private String dataFilePath;

    @Value("${name.of.ingredients.file}")
    private String ingredientsFileName;

    @Override

    public boolean saveIngredientToFile(String json) {
        try {
            cleanIngredientFile();
            Files.writeString(Path.of(dataFilePath, ingredientsFileName), json);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
    @Override
    public String readIngredientsFromFile() {
        try {
            return Files.readString(Path.of(dataFilePath, ingredientsFileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean cleanIngredientFile() {
        try {
            Path path = Path.of(dataFilePath, ingredientsFileName);
            Files.deleteIfExists(path);
            Files.createFile(path);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
