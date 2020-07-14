package coffeeMachine;
import java.util.*;
import java.util.concurrent.*;

public class Outlet implements Runnable{
    int id;
    Map<String, Beverage> beverages;
    BlockingQueue<String> orderList;
    CountDownLatch latch;
    List<String> results;
    Map<String, Ingredient> ingredientObjects;
    public Outlet(int id, Map<String, Beverage> bv, BlockingQueue<String> orders, CountDownLatch latch, Map<String,
            Ingredient> ingObjects ){
        this.id = id;
        this.beverages = bv;
        this.orderList = orders;
        this.latch = latch;
        results = new ArrayList<>();

        this.ingredientObjects = ingObjects;
    }

    @Override
    public void run(){
        try {
            String order = this.orderList.poll(100, TimeUnit.MILLISECONDS);
            while (order!=null){
                Printer.notify("starting "+order);

                if(beverages.containsKey(order))
                    results.add(beverages.get(order).readyBeverage());
                else
                    Printer.notify(order + "is not a correct beverage");

                order = orderList.poll(100, TimeUnit.MILLISECONDS);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            latch.countDown();
        }
    }

}
