package coffeeMachine;

public class Ingredient {
    private String name;
    private int amount;
     public Ingredient(String nme, int amnt){
         this.name = nme;
         this.amount = amnt;
     }

    synchronized public void getRefill(int amnt){
         this.amount += amnt;
     }

    synchronized public boolean reduceAmount(int amnt){
         boolean res = true;
         if(this.amount >= amnt)
             this.amount -= amnt;
         else
             res = false;
         if(this.amount < 5)
             Printer.notify(this.name + " is running low");
         return res;
     }
    synchronized public boolean checkAmount(int amnt){
         boolean res = false;
         if(this.amount >= amnt) res =  true;
         return res;
    }
}
