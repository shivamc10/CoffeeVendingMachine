package coffeeMachine;

public class Ingredient {
    // Ingredient class for the ingredients with name and amount
    private String name;
    private int amount;
     public Ingredient(String nme, int amnt){
         this.name = nme;
         this.amount = amnt;
     }
    // refill method to refill the ingredients
    public void refill(int amnt){
         this.amount += amnt;
     }
    // to use the ingredient and reduce the amount
    public void reduceAmount(int amnt){
         if(this.amount >= amnt)
             this.amount -= amnt;

         if(this.amount < 5)
             Printer.notify(this.name + " is running low");
     }
    // check if the valid amount is present or not
    public boolean checkAmount(int amnt){
         boolean res = false;
         if(this.amount >= amnt) res =  true;
         if(this.amount < 5)
             Printer.notify(this.name + " is running low");
         return res;
    }
}
