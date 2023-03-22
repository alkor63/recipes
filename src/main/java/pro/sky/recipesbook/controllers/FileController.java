package pro.sky.recipesbook.controllers;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.recipesbook.services.FileService;
import pro.sky.recipesbook.services.IngredientFileService;

import java.io.*;

@RestController
@RequestMapping("/files")
public class FileController {
    private final FileService fileService;
    private final IngredientFileService ingredientFileService;

    public FileController(FileService fileService, IngredientFileService ingredientFileService) {
        this.fileService = fileService;
        this.ingredientFileService = ingredientFileService;
    }


//    public FileController(FileService fileService) {
//        this.fileService = fileService;
//    }

    @GetMapping("/recipes/export")
    @Operation(
            summary = "Выгрузить файл рецептов",
            description = "Записать файл с рецептами на диск")

    public ResponseEntity<InputStreamResource> downloadRecipeFile() throws FileNotFoundException {
        System.out.println("Добрались до FileController метод downloadRecipeFile");
        if (fileService.downloadRecipeFile()) {
           return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PutMapping(value = "/recipes/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "Добавить файл рецептов",
            description = "Залить файл с рецептами на сервер"
    )
    public ResponseEntity<String> uploadRecipeFile(@RequestParam MultipartFile file) {
        fileService.uploadRecipeFile(file);
        return ResponseEntity.ok(file.getName());
    }

    @GetMapping("/ingredients/export")
    @Operation(
            summary = "Выгрузить файл ингредиентов",
            description = "Записать файл с ингредиентами на диск")

    public ResponseEntity<Object> downloadIngredientFile() throws FileNotFoundException {
        if (ingredientFileService.downloadIngredientFile()) {
            return ResponseEntity.ok().build();
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
