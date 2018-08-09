package se.chriskevin.mysterymaze.utils;

public final class Calculation {

    public static final Long add(Long x, Long y) { return x + y; }

    public static final Long subtract(Long x, Long y) { return x - y; }

    public static final Long divide(Long x, Long y) { return x / y; }

    public static final Long multiply(Long x, Long y) { return x * y; }

    public static final Long dec(Long x) { return add(x,-1L); }

    public static final Long half(Long x) { return divide(x, 2L); }

    public static final Long inc(Long x) { return add(x, 1L); }

}
