package pro.sky.recipesbook.services;

public interface FileService {
    boolean saveRecipeToFile(String json);

//    String readRecipeFromFile();

    String readRecipesMapFromFile();

}
