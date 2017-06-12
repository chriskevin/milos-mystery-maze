package se.chriskevin.mysterymaze.geometry;

import java.io.Serializable;

/**
 * Created by chsu7648 on 2017-06-12.
 */
public final class Dimension<T extends Number> implements Serializable {
    public final T height;
    public final T width;

    public Dimension(T width, T height) {
        this.height = height;
        this.width = width;
    }

    public boolean equals(Object other) {
        if (other instanceof Dimension<?>){
            return (((Dimension<?>)other).height.equals(this.height) && ((Dimension<?>)other).width.equals(this.width));
        }
        return false;
    }

    /*public int hashCode() {
        Double var1 = this.width. + this.height;
        return var1 * (var1 + 1) / 2 + this.width;
    }*/

    public String toString() {
        return this.getClass().getName() + "[width=" + this.width + ",height=" + this.height + "]";
    }
}
