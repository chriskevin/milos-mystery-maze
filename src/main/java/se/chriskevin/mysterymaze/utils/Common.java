package se.chriskevin.mysterymaze.utils;

import io.vavr.Function0;
import io.vavr.Function2;
import io.vavr.Function3;

import java.util.function.Function;

public final class Common {

    public static final Function2<Object, Object, Boolean> equals = Object::equals;

    public static Boolean equals(final Object x, final Object y) {return equals.apply(x, y); }

    public static final Function<Object, Object> identity = (x) -> x;

    public static final Function0<Boolean> F = () -> Boolean.FALSE;

    public static final Function0<Boolean> T = () -> Boolean.TRUE;

    public static final Function<Boolean, Function0<Boolean>> boolToPred = b -> (b ? T : F);

    public static final Function3<Function<Object, Boolean>, Function<Object, Object>, Object, Object> transformWhen =
        (pred, transform, obj) -> (pred.apply(obj) ? transform.apply(obj) : identity.apply(obj));
}