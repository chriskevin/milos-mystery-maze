package se.chriskevin.mysterymaze.geometry;

import java.io.Serializable;

public final class Dimension implements Serializable {
    public final Long height;
    public final Long width;

    public static final Dimension ZERO_DIMENSION = of(0L, 0L);

    private Dimension(final Long w, final Long h) {
        this.height = h;
        this.width = w;
    }

    public static Dimension of(final Long width, final Long height) {
        return new Dimension(width, height);
    }

    public boolean equals(final Object other) {
        if (other instanceof Dimension){
            return (((Dimension)other).height.equals(height) && ((Dimension)other).width.equals(width));
        }
        return false;
    }

    public int hashCode() {
        final var var1 = width.intValue() + height.intValue();
        return var1 * (var1 + 1) / 2 + width.intValue();
    }

    public String toString() {
        return getClass().getName() + "[width=" + width + ",height=" + height + "]";
    }
}
