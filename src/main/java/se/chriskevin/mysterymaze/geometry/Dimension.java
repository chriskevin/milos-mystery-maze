package se.chriskevin.mysterymaze.geometry;

import java.io.Serializable;

public final class Dimension implements Serializable {
    public final Long height;
    public final Long width;

    public static final Dimension ZERO_DIMENSION = of(0L, 0L);

    private Dimension(Long w, Long h) {
        this.height = h;
        this.width = w;
    }

    public static final Dimension of(Long width, Long height) {
        return new Dimension(width, height);
    }

    public static final Dimension half(Dimension dimension) {
        return of(dimension.width / 2, dimension.height / 2);
    }

    public static final Dimension subtract(Dimension a, Dimension b) {
        return of(a.width - b.width, a.height - b.height);
    }

    public boolean equals(Object other) {
        if (other instanceof Dimension){
            return (((Dimension)other).height.equals(height) && ((Dimension)other).width.equals(width));
        }
        return false;
    }

    public int hashCode() {
        var var1 = width.intValue() + height.intValue();
        return var1 * (var1 + 1) / 2 + width.intValue();
    }

    public String toString() {
        return getClass().getName() + "[width=" + width + ",height=" + height + "]";
    }
}
