package pro.sky.recipesbook.services;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;

public interface FileService {
    boolean saveRecipeToFile(String json);

//    String readRecipeFromFile();

//    Path saveTxt() throws IOException;

    Path saveTxt() throws IOException;


    Path createTempFile(String suffix);

    String readRecipesMapFromFile();

    File getRecipeFile();

    boolean cleanRecipeFile();

    void uploadRecipeFile(MultipartFile file);

    InputStreamResource downloadRecipeFile() throws FileNotFoundException;

    InputStreamResource downloadTxtFile() throws IOException;
}
