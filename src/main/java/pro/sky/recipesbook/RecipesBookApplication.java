package pro.sky.recipesbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pro.sky.recipesbook.model.Ingredient;
import pro.sky.recipesbook.model.Recipe;
import pro.sky.recipesbook.services.Impl.RecipeServiceImpl;
import pro.sky.recipesbook.services.RecipeService;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class RecipesBookApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecipesBookApplication.class, args);
/*
		List<Ingredient> ingredients0 = new ArrayList<>();
		Ingredient ingredient = new Ingredient("Творог",350,"г");
		ingredients0.add(ingredient);
		ingredient = new Ingredient("Куриное яйцо",2,"штуки");
		ingredients0.add(ingredient);
		ingredient = new Ingredient("Пшеничная мука",6,"столовых ложек");
		ingredients0.add(ingredient);
		ingredient = new Ingredient("Сахар",2,"столовые ложки");
		ingredients0.add(ingredient);

		List<String> cookingSteps0 = new ArrayList<>();
		cookingSteps0.add("Смешайте весь творог с яйцами, сахаром и тщательно всё перемешайте");
		cookingSteps0.add("Всыпьте в творог муку и тщательно перемешайте");
		cookingSteps0.add("Поставьте сковороду на средний огонь и налейте в нее подсолнечное масло");
		cookingSteps0.add("Слепите несколько небольших шариков из получившейся творожной массы и положите их на тарелку. Затем по очереди обкатывайте творожные шарики в муке и выкладывайте на сковороду");
		cookingSteps0.add("Обжаривайте сырники 1–2 минуты до появления золотистой корочки. Затем переверните их на другую сторону и также обжарьте до золотистого состояния");
		cookingSteps0.add("Повторяйте, пока творог не закончится");

		Recipe recipe0 = new Recipe("Сырники из творога",40, ingredients0, cookingSteps0);
		RecipeServiceImpl.addRecipe(recipe0);
		System.out.println(RecipeServiceImpl.getRecipe(1));
*/

	}
}