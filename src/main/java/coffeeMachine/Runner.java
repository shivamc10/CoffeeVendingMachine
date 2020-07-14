package coffeeMachine;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
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

    // start the machine by initializing the ingredients and orders
    public List<String> start() throws IOException, ParseException {
        Store store = new Store();
        BlockingQueue<String> orders = initialize(fileName);
        Outlet[] outlets = new Outlet[outletCount];
        Thread[] threads = new Thread[outletCount];
        CountDownLatch latch = new CountDownLatch(outletCount);
        for (int i = 0; i < outletCount; i++) {
            outlets[i] = new Outlet(i + 1, beverageObjects, orders, latch, ingredientObjects, store);
            threads[i] = new Thread(outlets[i]);
            threads[i].start();
        }
        try{
            latch.await();
            return Arrays.stream(outlets).flatMap(c -> c.results.stream()).collect(Collectors.toList());
        }
        catch(InterruptedException e) {
            PrintWriter pw = new PrintWriter(new File("src/main/resources/error.log"));
            e.printStackTrace(pw);
            store.storeState(ingredientObjects);
            return Collections.emptyList();
        }
    }

    // initialize the parameters from reading json file and queue the orders in blockingqueue to work in parallel
    @SuppressWarnings("unchecked")
    public BlockingQueue<String> initialize(String fileName) throws IOException, ParseException {
        List<String> orderList = new ArrayList<>();

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
//            System.out.println(beverages);
            for (Map.Entry pair : (Iterable<Map.Entry>) beverages.entrySet()) {
                String name = (String) pair.getKey();
                Map<String, Long> ingredientList = (Map<String, Long>) pair.getValue();
                orderList.add(name);

//                System.out.println(name+ " "+ ingredientList);
                beverageObjects.put(name, new Beverage(name, ingredientList, ingredientObjects));
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        File f = new File("src/main/resources/error.log");
        // in case of any failure it will re initialize the data stored in file and remove the completed order and restart
        // real life situation will be power cut or something component stopped working causing failure
        if (f.exists()){
            f.delete();             //delete the file so that next start will not consider it as a restart
            f = new File("src/main/resources/remaining.json");
            FileReader reader = new FileReader("src/main/resources/remaining.json");
            JSONObject remaining = (JSONObject) jsonParser.parse(reader);
            ingredientObjects = new HashMap<>();
            Map remIngredients = (Map) remaining.get("total_items_quantity");
            for (Map.Entry pair : (Iterable<Map.Entry>) remIngredients.entrySet()) {
                String name = (String) pair.getKey();
                int amnt = ((Long) pair.getValue()).intValue();
                ingredientObjects.put(name, new Ingredient(name, amnt));
            }
            f.delete();
            File file=new File("src/main/resources/order.log");
            FileReader fr=new FileReader(file);
            BufferedReader br=new BufferedReader(fr);
            String item;
            while((item=br.readLine())!=null){
                orderList.remove(item);
            }
            file.delete();
        }
        Collections.shuffle(orderList);
        BlockingQueue<String> orders = new LinkedBlockingDeque<>(orderList);

        return orders;
    }

    // main function to call the coffee machine
    public static void main(String[] args) throws IOException, ParseException {
        String fileName = "src/main/resources/input.json";
        Runner runner = new Runner(fileName);
        List<String> result = runner.start();
        Collections.sort(result);
        for(String res:result)
            System.out.println(res);
    }
}
