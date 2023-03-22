package pro.sky.recipesbook.services;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;

public interface FileService {
    boolean saveRecipeToFile(String json);

//    String readRecipeFromFile();

    String readRecipesMapFromFile();

    File getRecipeFile();

    boolean cleanRecipeFile();

    void uploadRecipeFile(MultipartFile file);

    boolean downloadRecipeFile() throws FileNotFoundException;
}
