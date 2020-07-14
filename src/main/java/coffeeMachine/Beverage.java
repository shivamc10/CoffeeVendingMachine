package coffeeMachine;
import java.util.*;
import java.util.concurrent.Semaphore;

public class Beverage{
    // class object for beverage which has ingredient list and amount used to prepare for the beverage
    private String name;
    Map<String,Long> ingredientList;
    Map<String, Ingredient> ingredientObjects;
    static Semaphore sem = new Semaphore(1);
    public Beverage(String name, Map<String, Long> ingredientList, Map<String, Ingredient> ingredients){
        this.name  = name;
        this.ingredientList = ingredientList;
        this.ingredientObjects = ingredients;
    }
    // method to prepare the beverage by checking the ingredient amount and if present then reducing it
    // using semaphore as a lock to avoid dirty reads for the ingredient amount
    public String readyBeverage() throws InterruptedException {
        Printer.notify("preparing "+this.name);
        try{
            sem.acquire();
            List<String> used = new ArrayList<>();
            for(Map.Entry<String, Long> entry: ingredientList.entrySet()) {
                String ingredient = entry.getKey();
                if(!ingredientObjects.containsKey(ingredient) ||
                        !ingredientObjects.get(ingredient).checkAmount(entry.getValue().intValue())){
                    if(!ingredientObjects.containsKey(ingredient)){
//                        Printer.print(this.name, ingredient, -1);
                        sem.release();
                        return this.name+" cannot be prepared because "+ ingredient +" is not available";
                    }
                    else{
//                        Printer.print(this.name, ingredient, 0);
                        sem.release();
                        return this.name+" cannot be prepared because "+ ingredient +" is not sufficient";
                    }

                }
                used.add(ingredient);
            }

            for(String item:used){
                ingredientObjects.get(item).reduceAmount(ingredientList.get(item).intValue());
            }
            sem.release();
        }
        catch(Exception e){
            Printer.notify(this.name+" is failed");
            e.printStackTrace();
        }
        Thread.sleep(5000);
//        Printer.print(this.name,null,0);
        return this.name + " is prepared";
    }

}
