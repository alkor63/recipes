package pro.sky.recipesbook.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ingredient {
    private String name;
    private int quantity;
    private String measureUnit;

    @Override
    public String toString() {if (name != "[" && measureUnit != "]"){
        return "\n- " + name + " : " + quantity + " " + measureUnit;}
        return "";
    }
}