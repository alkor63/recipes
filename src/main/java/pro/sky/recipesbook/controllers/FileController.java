package pro.sky.recipesbook.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.recipesbook.model.Recipe;
import pro.sky.recipesbook.services.FileService;
import pro.sky.recipesbook.services.IngredientFileService;
import pro.sky.recipesbook.services.RecipeService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("/files")
public class FileController {
    private final FileService fileService;
    private final IngredientFileService ingredientFileService;
    private final RecipeService recipeService;

    public FileController(FileService fileService, IngredientFileService ingredientFileService, RecipeService recipeService) {
        this.fileService = fileService;
        this.ingredientFileService = ingredientFileService;
        this.recipeService = recipeService;
    }


    @GetMapping(value = "/export/txt")
    @Operation(summary = "Экспорт файла рецептов в формате .txt",
            description = "Сохраняем список всех рецептов в текстовом файле")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "запрос на экспорт txt-файла выполнился без проблем",
                    content = {
                            @Content(
                                    mediaType = "application/txt",
                                    array = @ArraySchema(schema =
                                    @Schema(implementation = Recipe.class))
                            )
                    }),
            @ApiResponse(
                    responseCode = "400", description = "ошибка в параметрах запроса на экспорт txt-файла"
            ),
            @ApiResponse(
                    responseCode = "404", description = "ошибка в URL или такого действия нет в веб-приложении"
            ),
            @ApiResponse(
                    responseCode = "500", description = "во время выполнения запроса на экспорт txt-файла произошла ошибка на сервере"
            )
    }
    )

    public ResponseEntity<Object> downloadTxtFile() {
        try {
            Path path = recipeService.createTxtFile();
            if (Files.size(path) == 0) {
                return ResponseEntity.noContent().build();
            }
            InputStreamResource resource = new InputStreamResource(new FileInputStream(path.toFile()));
            return ResponseEntity.ok()
                    .contentType(MediaType.TEXT_PLAIN)
                    .contentLength(Files.size(path))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"recipes.txt\"")
                    .body(resource);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.toString());
        }
    }

    @GetMapping("/recipes/export")
    @Operation(
            summary = "Выгрузить файл рецептов",
            description = "Записать файл с рецептами на диск")

    public ResponseEntity<InputStreamResource> downloadRecipeFile() throws FileNotFoundException {
//        System.out.println("Добрались до FileController метод downloadRecipeFile");
        File file = fileService.getRecipeFile();
        InputStreamResource resource = fileService.downloadRecipeFile();
        if (resource == null) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                    .contentLength(file.length())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"RecipesBook.json\"")
                    .body(resource);
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

    public ResponseEntity<InputStreamResource> downloadIngredientFile() throws FileNotFoundException {
        File file = ingredientFileService.getIngredientFile();
        InputStreamResource resource = ingredientFileService.downloadIngredientFile();
        if (resource == null) {
            return ResponseEntity.noContent().build();
        }
        return
                ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                        .contentLength(file.length())
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"IngredientList.json\"")
                        .body(resource);
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
