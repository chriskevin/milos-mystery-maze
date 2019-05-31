package se.chriskevin.mysterymaze.utils;

import static org.junit.Assert.assertEquals;
import static se.chriskevin.mysterymaze.utils.Calculation.add;
import static se.chriskevin.mysterymaze.utils.Calculation.half;
import static se.chriskevin.mysterymaze.utils.Calculation.subtract;

import org.junit.Test;

public class CalculationTest {

  @Test
  public void verifyAdd() {
    assertEquals(add(5L, 5L).longValue(), 10L);
  }

  @Test
  public void verifySubtract() {
    assertEquals(subtract(15L, 5L).longValue(), 10L);
  }

  @Test
  public void verifyHalf() {
    assertEquals(half(10L).longValue(), 5L);
  }
}
