package coffeeMachine;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.stream.Collectors;

public class Runner {
    Map<String, Beverage> beverageObjects = new HashMap<>();
    Map<String, Ingredient> ingredientObjects = new HashMap<>();
    int outletCount;
    String fileName;
    public Runner(String fileName) {
        this.fileName = fileName;
    }
    public List<String> start(){
        BlockingQueue<String> orders = initialize(fileName);
        Outlet[] outlets = new Outlet[outletCount];
        Thread[] threads = new Thread[outletCount];
        CountDownLatch latch = new CountDownLatch(outletCount);
        for (int i = 0; i < outletCount; i++) {
            outlets[i] = new Outlet(i + 1, beverageObjects, orders, latch);
            threads[i] = new Thread(outlets[i]);
            threads[i].start();
        }
        try{
            latch.await();
            return Arrays.stream(outlets).flatMap(c -> c.results.stream()).collect(Collectors.toList());
        }
        catch(InterruptedException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
    @SuppressWarnings("unchecked")
    public BlockingQueue<String> initialize(String fileName)
    {
        BlockingQueue<String> orders = new LinkedBlockingDeque<>();
        JSONParser jsonParser = new JSONParser();
        try{
            FileReader reader = new FileReader(fileName);
            JSONObject input = (JSONObject) jsonParser.parse(reader);
            JSONObject machine = (JSONObject) input.get("machine");

            outletCount =((Long) ((JSONObject) machine.get("outlets")).get("count_n")).intValue();

            Map ingredients = (Map)machine.get("total_items_quantity");
            for (Map.Entry pair : (Iterable<Map.Entry>) ingredients.entrySet()) {
                String name = (String) pair.getKey();
                int amnt = ((Long) pair.getValue()).intValue();
                ingredientObjects.put(name, new Ingredient(name, amnt));
            }
            System.out.println();
            Map beverages = (Map)machine.get("beverages");
            for (Map.Entry pair : (Iterable<Map.Entry>) beverages.entrySet()) {
                String name = (String) pair.getKey();
                Map<String, Long> ingredientList = (Map<String, Long>) pair.getValue();
                orders.add(name);
//                System.out.println(name+ " "+ ingredientList);
                beverageObjects.put(name, new Beverage(name, ingredientList, ingredientObjects));
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
//        System.out.println(orders);
        return orders;
    }

    public static void main(String[] args){
        String fileName = "src/main/resources/allAvailable.json";
        Runner runner = new Runner(fileName);
        List<String> result = runner.start();
        Collections.sort(result);
        for(String res:result)
            System.out.println(res);
    }
}
