package coffeeMachine;
import java.util.*;

public class Beverage{
    private String name;
    Map<String,Long> ingredientList;
    Map<String, Ingredient> ingredientObjects;

    public Beverage(String name, Map<String, Long> ingredientList, Map<String, Ingredient> ingredients){
        this.name  = name;
        this.ingredientList = ingredientList;
        this.ingredientObjects = ingredients;
    }

    public boolean readyBeverage() {
        Printer.notify("preparing "+this.name);
        try{
            List<String> used = new ArrayList<>();
            for(Map.Entry<String, Long> entry: ingredientList.entrySet()) {
                String ingredient = entry.getKey();
                if(!ingredientObjects.containsKey(ingredient) ||
                        !ingredientObjects.get(ingredient).checkAmount(entry.getValue().intValue())){
                    if(!ingredientObjects.containsKey(ingredient))
                        Printer.print(this.name, ingredient, -1);
                    else
                        Printer.print(this.name, ingredient, 0);
                    return false;
                }
                used.add(ingredient);
            }

            for(String item:used){
                ingredientObjects.get(item).reduceAmount(ingredientList.get(item).intValue());
            }
        }
        catch(Exception e){
            Printer.notify(this.name+" is failed");
            e.printStackTrace();
        }
        return  true;
    }

}
