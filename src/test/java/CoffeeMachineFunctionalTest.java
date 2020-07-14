import coffeeMachine.Runner;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.core.AnyOf.anyOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class CoffeeMachineFunctionalTest {
    Runner runnerTest;
    @Test
    public void testMachine() {
        String fileName = "src/main/resources/input.json";
        runnerTest = new Runner(fileName);
        List<String> expected = runnerTest.start();
        Collections.sort(expected);

        List<String> result1 = new ArrayList<>();
        result1.add("black_tea is prepared");
        result1.add("green_tea cannot be prepared because green_mixture is not available");
        result1.add("hot_coffee is prepared");
        result1.add("hot_tea cannot be prepared because sugar_syrup is not sufficient");

        List<String> result2 = new ArrayList<>();
        result2.add("black_tea cannot be prepared because sugar_syrup is not sufficient");
        result2.add("green_tea cannot be prepared because green_mixture is not available");
        result2.add("hot_coffee is prepared");
        result2.add("hot_tea is prepared");

        List<String> result3 = new ArrayList<>();
        result3.add("black_tea is prepared");
        result3.add("green_tea cannot be prepared because green_mixture is not available");
        result3.add("hot_coffee cannot be prepared because sugar_syrup is not sufficient");
        result3.add("hot_tea is prepared");
        assertThat(expected.toString(), anyOf(containsString(result1.toString()), containsString(result2.toString()), containsString(result3.toString())));

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