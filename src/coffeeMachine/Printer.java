package coffeeMachine;

public class Printer {
    synchronized public static void print(String name, String ingredient, int amnt){
        if(ingredient == null)
            System.out.println(name+" is prepared");
        else if(amnt ==-1)
            System.out.println(name+" cannot be prepared because "+ ingredient +" is not available");
        else
            System.out.println(name+" cannot be prepared because "+ ingredient +" is not sufficient");
    }
    synchronized public static void notify(String e){
        System.out.println(e);
    }
}
