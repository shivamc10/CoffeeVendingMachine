import coffeeMachine.Runner;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
public class CoffeeMachineFunctionalTest {
    Runner runnerTest;
    @Test
    public void testMachine() {
        String fileName = "src/main/resources/input.json";
        runnerTest = new Runner(fileName);
        List<String> expected = runnerTest.start();
        Collections.sort(expected);
        List<String> result = new ArrayList<>();
        result.add("black_tea is prepared");
        result.add("green_tea cannot be prepared because green_mixture is not available");
        result.add("hot_coffee is prepared");
        result.add("hot_tea cannot be prepared because hot_water is not sufficient");
        assertEquals(result, expected);
    }

    @Test
    public void testAllZeroes(){
        String fileName = "src/main/resources/AllZeroes.json";
        runnerTest = new Runner(fileName);
        List<String> expected = runnerTest.start();
        Collections.sort(expected);
        List<String> result = new ArrayList<>();
        result.add("black_tea cannot be prepared because ginger_syrup is not sufficient");
        result.add("green_tea cannot be prepared because green_mixture is not available");
        result.add("hot_coffee cannot be prepared because hot_milk is not sufficient");
        result.add("hot_tea cannot be prepared because hot_milk is not sufficient");
        assertEquals(result, expected);
    }

    @Test
    public void testAllAvalaible(){
        String fileName = "src/main/resources/allAvailable.json";
        runnerTest = new Runner(fileName);
        List<String> expected = runnerTest.start();
        Collections.sort(expected);
        List<String> result = new ArrayList<>();
        result.add("black_tea is prepared");
        result.add("green_tea is prepared");
        result.add("hot_coffee is prepared");
        result.add("hot_tea is prepared");
        assertEquals(result, expected);
    }
}