package pro.sky.recipesbook.services.Impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pro.sky.recipesbook.services.FileService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FileServiceImpl implements FileService {
    @Value("${path.to.data.file}")
    private String dataFilePath;

    @Value("${name.of.recipes.file}")
    private String recipesFileName;

    @Override

    public boolean saveRecipeToFile(String json) {
        try {
            Files.writeString(Path.of(dataFilePath, recipesFileName), json);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public String readRecipeFromFile() {
        try {
            return Files.readString(Path.of(dataFilePath, recipesFileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean cleanRecipeFile() {
        try {
            Path path = Path.of(dataFilePath, recipesFileName);
            Files.deleteIfExists(path);
            Files.createFile(path);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    /*
    public class JsonHelper {
    private static final Gson gson = new GsonBuilder().disableHtmlEscaping().create();
    private static final Type TT_mapStringString = new TypeToken<Map<String,String>>(){}.getType();

    public static Map<String, String> jsonToMapStringString(String json) {
        Map<String, String> ret = new HashMap<String, String>();
        if (json == null || json.isEmpty())
            return ret;
         return gson.fromJson(json, TT_mapStringString);
    }
    public static String mapStringStringToJson(Map<String, String> map) {
        if (map == null)
            map = new HashMap<String, String>();
         return gson.toJson(map);
    }
}
variant 2:
Map<String,Object> map = .... // create a map
ObjectMapper mapper = new ObjectMapper()
String jsonFromMap = mapper.writeValueAsString(map);
variant 3:
String json = ConvertJsonToObject.toJSON(testMap);
and you can easily get your original Object back on the other side
Map<String, String> newTestMap = ConvertJsonToObject.getFromJSON(json,Map.class);

correct one :
"elementsToUpdate": {
    "1": {
      "country": "USA",
      "title": "string"
    }
}
     */
}
