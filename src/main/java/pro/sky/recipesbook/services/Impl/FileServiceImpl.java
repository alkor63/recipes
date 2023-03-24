package pro.sky.recipesbook.services.Impl;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.recipesbook.model.Recipe;
import pro.sky.recipesbook.services.FileService;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {
//    private final RecipeService recipeService;

    @Value("${path.to.data.file}")
    private String dataFilePath;

    @Value("${name.of.recipes.file}")

    private String recipesMapFileName;

//    public FileServiceImpl(RecipeService recipeService) {
//        this.recipeService = recipeService;
//    }

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

    @Override
    public Path saveTxt() throws IOException {
        Path path = createTempFile(".txt");
////        File dataFile = getRecipeFile();
//        List<Recipe> allRecipes = new ArrayList<>(RecipeServiceImpl.getRecipes().values());
//        Path path = Files.createTempFile(Path.of(dataFilePath), "tempFile", "txt");
        List<Recipe> allRecipes = new ArrayList<>(RecipeServiceImpl.getRecipes().values());
        for (Recipe recipe : allRecipes) {
            System.out.println("РЕЦЕПТ = " + recipe.toString());
            try {
String s = recipe.getName() + "\nвремя приготовления: " + recipe.getCookingTimeInMinutes() + " мин.\nингредиенты: " +
                        recipe.getIngredients().toString() + "\nИнструкция приготовления:\n";
                List<String> steps = recipe.getCookingSteps();
                for (int i = 0; i < steps.size(); i++) {
                     s = s + (i + 1) + " " + steps.get(i) + "\n";}
                    BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8, StandardOpenOption.APPEND);
                    writer.write(s);

                return path;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return path;
    }

    @Override
    public Path createTempFile(String suffix) {
        try {
            return Files.createTempFile(Path.of(dataFilePath), "tempFile", suffix);
        } catch (IOException e) {
            throw new RuntimeException(e);
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

    @Override
    public void uploadRecipeFile(MultipartFile file) {
        cleanRecipeFile();
        File dataFile = getRecipeFile();

        try (FileOutputStream fos = new FileOutputStream(dataFile)) {
            IOUtils.copy(file.getInputStream(), fos);
            ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public InputStreamResource downloadRecipeFile() throws FileNotFoundException {
        File file = getRecipeFile();
        System.out.println("file в методе файлСервиса downloadRecipeFile = " + file);
        if (file.exists()) {
            return new InputStreamResource(new FileInputStream(file));
        }
        return null;
    }

    @Override
    public InputStreamResource downloadTxtFile() throws IOException {
        Path path = saveTxt();
        if (Files.exists(path)) {

            try {
                return new InputStreamResource(new FileInputStream(path.toFile()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
}
