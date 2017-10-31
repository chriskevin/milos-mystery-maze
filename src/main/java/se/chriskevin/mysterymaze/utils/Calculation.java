package se.chriskevin.mysterymaze.utils;

import io.vavr.Function2;

import java.util.function.Function;

/**
 * Created by Chris Sundberg on 2017-07-29.
 */
public final class Calculation {

    public static final Function2<Long, Long, Long> add = (x, y) -> x + y;

    public static final Function2<Long, Long, Long> subtract = (x, y) -> x - y;

    public static final Function2<Long, Long, Long> divide = (x, y) -> x / y;

    public static final Function2<Long, Long, Long> multiply = (x, y) -> x * y;

    public static final Function<Long, Long> dec = add.apply(-1L);

    public static final Function<Long, Long> half = divide.reversed().apply(2L);

    public static final Function<Long, Long> inc = add.apply(1L);

}
