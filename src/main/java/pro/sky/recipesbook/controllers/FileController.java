package pro.sky.recipesbook.controllers;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.recipesbook.services.FileService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("/files")
public class FileController {
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/recipes/export")
    public ResponseEntity<InputStreamResource> downloadRecipeFile() throws FileNotFoundException {
        File file = fileService.getRecipeFile();
        if (file.exists()) {
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                    .contentLength(file.length())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"RecipesBook.json\"")
                    .body(resource);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PutMapping(value = "/recipes/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadRecipeFile(@RequestParam MultipartFile file) {
        fileService.cleanRecipeFile();
        File dataFile = fileService.getRecipeFile();

        try (FileOutputStream fos = new FileOutputStream(dataFile)) {
            IOUtils.copy(file.getInputStream(), fos);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping("/ingredients/export")
    public ResponseEntity<InputStreamResource> downloadIngredientFile() throws FileNotFoundException {
        File file = fileService.getIngredientFile();
        if (file.exists()) {
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                    .contentLength(file.length())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"IngredientList.json\"")
                    .body(resource);
        } else {
            return ResponseEntity.noContent().build();
        }
    }
    /*
@GetMapping("/RecipesWithIngredient/{ingredientId}")
    public ResponseEntity<Object> editIngredientFile(@PathVariable Integer ingredientId) {
    try {
        Path path = fileService.getDataFile().toPath();
        if (Files.size(path)==0){
            return ResponseEntity.noContent().build();
        }
        InputStreamResource resource = new InputStreamResource(new FileInputStream(path.toFile()));
        return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN)
                .contentLength(Files.size(path))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachmet: filename = \""+ingredientId+"newList.txt")
                .body(resource);

    } catch (IOException e) {
        e.printStackTrace();
        return ResponseEntity.internalServerError().body(e.toString());
    }
}
     */
}
