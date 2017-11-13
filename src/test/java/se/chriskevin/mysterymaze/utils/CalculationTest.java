package se.chriskevin.mysterymaze.utils;


import org.junit.Test;


import static org.junit.Assert.assertEquals;
import static se.chriskevin.mysterymaze.utils.Calculation.add;
import static se.chriskevin.mysterymaze.utils.Calculation.half;
import static se.chriskevin.mysterymaze.utils.Calculation.subtract;

public class CalculationTest {

    @Test
    public void verifyAdd() {
        assertEquals(add.apply(5L, 5L).longValue(), 10L);
    }

    @Test
    public void verifySubtract() {
        assertEquals(subtract.apply(15L, 5L).longValue(), 10L);
    }

    @Test
    public void verifyHalf() {
        assertEquals(half.apply(10L).longValue(), 5L);
    }
}
