package pro.sky.recipesbook.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Информация о приложении", description = "здесь только информация")

public class InfoController {
@GetMapping
@Operation(summary = "Приветствие", description = "для проверки запуска программы")
public String hello() {
    return "Приложение стартовало!";
}
@GetMapping("/info")
@Operation(summary = "Информация о приложении", description = "указаны автор, название и назначение приложения")
    public String info() {
    return "Web-приложение Книга рецептов, Алексей Корниенко, 04.03.2023г.";
}
}
