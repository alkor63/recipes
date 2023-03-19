package pro.sky.recipesbook.services.Impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pro.sky.recipesbook.services.FileService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FileServiceImpl implements FileService {
    @Value("${path.to.data.file}")
    private String dataFilePath;

    @Value("${name.of.recipes.file}")
//    private String recipesFileName;
//    @Value("${name.of.recipes.map.file}")
    private String recipesMapFileName;

    @Value("${name.of.ingredients.file}")
    private String ingredientsFileName;

    @Override
    public boolean saveRecipeToFile(String json) {
        try {
            cleanRecipeFile();
            Files.writeString(Path.of(dataFilePath, recipesMapFileName), json);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

//    @Override
//    public String readRecipeFromFile() {
//        try {
//            return Files.readString(Path.of(dataFilePath, recipesFileName));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

    @Override
    public String readRecipesMapFromFile() {
        try {
            return Files.readString(Path.of(dataFilePath, recipesMapFileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //public Path createAnyReport(Integer ingredientId) {
//        Path path = createTempFile();
//        return path;
//}
    @Override
    public File getRecipeFile() {
        return new File(dataFilePath + "/" + recipesMapFileName);
    }

    @Override
    public File getIngredientFile() {
        return new File(dataFilePath + "/" + ingredientsFileName);

    }

    @Override
    public boolean cleanRecipeFile() {
        try {
            Path path = Path.of(dataFilePath, recipesMapFileName);
            Files.deleteIfExists(path);
            Files.createFile(path);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
