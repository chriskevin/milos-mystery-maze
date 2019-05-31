package se.chriskevin.mysterymaze.utils;

public final class Calculation {

  public static Long add(final Long x, final Long y) {
    return x + y;
  }

  public static Long subtract(final Long x, final Long y) {
    return x - y;
  }

  public static Long divide(final Long x, final Long y) {
    return x / y;
  }

  public static Long multiply(final Long x, final Long y) {
    return x * y;
  }

  public static Long dec(final Long x) {
    return add(x, -1L);
  }

  public static Long half(final Long x) {
    return divide(x, 2L);
  }

  public static Long inc(final Long x) {
    return add(x, 1L);
  }
}
