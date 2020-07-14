package coffeeMachine;

import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class Store {
    FileWriter file;
    public Store() throws IOException {
        file = new FileWriter("src/main/resources/order.log");
    }
    public void storeState(Map<String,Ingredient> ingredientMap){
        JSONObject ingredientAmnt = new JSONObject();
        for(Map.Entry<String, Ingredient> entry : ingredientMap.entrySet()){
            int amnt = entry.getValue().getAmount();
            String name = entry.getKey();
            ingredientAmnt.put(name, amnt);
        }
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("total_items_quantity",ingredientAmnt);

        try (FileWriter file = new FileWriter("src/main/resources/remaining.json")) {

            file.write(jsonObj.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    synchronized public void writeLog(String orderCmpltd){
        try {
            file.write(orderCmpltd+"\n");
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
