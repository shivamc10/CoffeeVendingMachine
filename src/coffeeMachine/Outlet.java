package coffeeMachine;
import java.util.*;
import java.util.concurrent.*;

public class Outlet implements Runnable{
    int id;
    Map<String, Beverage> beverages;
    BlockingQueue<String> orderList;
    CountDownLatch latch;
    public Outlet(int id, Map<String, Beverage> bv, BlockingQueue<String> orders, CountDownLatch latch){
        this.id = id;
        this.beverages = bv;
        this.orderList = orders;
        this.latch = latch;
    }

    @Override
    public void run(){
        try {
            String order = this.orderList.poll(100, TimeUnit.MILLISECONDS);
            while (order!=null){
                Printer.notify("starting "+order);
                if(beverages.get(order).readyBeverage()){
                    Thread.sleep(5000);
                    Printer.print(order,null,0);
                }
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
