package se.chriskevin.mysterymaze.geometry;

import java.io.Serializable;

/**
 * Created by Chris Sundberg on 2017-06-12.
 */
public final class Dimension<T extends Number> implements Serializable {
    public final Long height;
    public final Long width;

    public static final Dimension ZERO_DIMENSION = new Dimension(0L, 0L);

    public Dimension(Long width, Long height) {
        this.height = height;
        this.width = width;
    }

    public boolean equals(Object other) {
        if (other instanceof Dimension){
            return (((Dimension)other).height.equals(this.height) && ((Dimension)other).width.equals(this.width));
        }
        return false;
    }

    public int hashCode() {
        final int var1 = this.width.intValue() + this.height.intValue();
        return var1 * (var1 + 1) / 2 + this.width.intValue();
    }

    public String toString() {
        return this.getClass().getName() + "[width=" + this.width + ",height=" + this.height + "]";
    }
}
