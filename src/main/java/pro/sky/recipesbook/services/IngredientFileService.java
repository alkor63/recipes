package pro.sky.recipesbook.services;

public interface IngredientFileService {
    boolean saveIngredientToFile(String json);

    String readIngredientFromFile();
}
