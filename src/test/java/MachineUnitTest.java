import coffeeMachine.Beverage;
import coffeeMachine.Ingredient;
import coffeeMachine.Runner;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MachineUnitTest {
    Beverage beverageTest;
    Ingredient ingredientTest;
    Runner runnerTest;
    @Test
    public void testBeverage() throws InterruptedException {
        String name = "hot_tea";
        Map<String,Long> ingredientList = new HashMap<>();
        ingredientList.put("hot_water", (long) 200);
        ingredientList.put("hot_milk", (long) 100);
        ingredientList.put("ginger_syrup", (long) 10);
        ingredientList.put("sugar_syrup", (long) 10);
        ingredientList.put("tea_leaves_syrup", (long) 30);

        Map<String, Ingredient> ingredientObjects = new HashMap<>();
        ingredientObjects.put("hot_water",new Ingredient("hot_water",500));
        ingredientObjects.put("hot_milk",new Ingredient("hot_milk",500));
        ingredientObjects.put("ginger_syrup",new Ingredient("ginger_syrup",200));
        ingredientObjects.put("tea_leaves_syrup",new Ingredient("tea_leaves_syrup",200));
        ingredientObjects.put("sugar_syrup",new Ingredient("tea_leaves_syrup",200));

        beverageTest = new Beverage(name, ingredientList, ingredientObjects);
        String expected = beverageTest.readyBeverage();

        String result = name + " is prepared";

        assertEquals(result, expected);
    }
    @Test
    public void testIngredient() throws InterruptedException {
        String name = "sugar_syrup";
        int amnt = 500;
        ingredientTest = new Ingredient(name, amnt);

        boolean result = ingredientTest.checkAmount(100);
        assertTrue(result);

        result = ingredientTest.reduceAmount(100);
        assertTrue(result);

        ingredientTest.getRefill(100);
        result = ingredientTest.checkAmount(500);
        assertTrue(result);

    }

    @Test
    public void testRunnerInitialize() throws InterruptedException {
        String fileName = "src/main/resources/input.json";
        runnerTest = new Runner(fileName);
        Queue<String> result = runnerTest.initialize(fileName);
        Queue<String> expected = new LinkedList<>();
        expected.add("hot_tea");
        expected.add("hot_coffee");
        expected.add("green_tea");
        expected.add("black_tea");
        assertEquals(result,expected);
    }
}
