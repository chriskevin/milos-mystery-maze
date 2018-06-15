package se.chriskevin.mysterymaze.geometry;

import io.vavr.Function2;
import io.vavr.Function3;
import io.vavr.Function4;

import java.io.Serializable;

import static se.chriskevin.mysterymaze.utils.Calculation.add;

public final class Point3D implements Serializable {

    public final Long x;
    public final Long y;
    public final Long z;

    public static final Point3D ZERO_POINT3D = of(0L, 0L, 0L);

    public static final Function2<Long, Point3D, Point3D> moveX =
        (x, point) -> of(x, point.y, point.z);

    public static final Function2<Long, Point3D, Point3D> moveY =
        (y, point) -> of(point.x, y, point.z);

    public static final Function2<Long, Point3D, Point3D> moveZ =
        (z, point) -> of(point.x, point.y, z);

    public static final Function4<Long, Long, Long, Point3D, Point3D> move =
        (x, y, z, point) -> of(x, y, z);

    public static final Function2<Long, Point3D, Point3D> translateX =
        (x, point) -> of(add.apply(point.x, x), point.y, point.z);

    public static final Function2<Long, Point3D, Point3D> translateY =
        (y, point) -> of(point.x, add.apply(point.y, y), point.z);

    public static final Function2<Long, Point3D, Point3D> translateZ =
        (z, point) -> of(point.x, point.y, add.apply(point.z, z));

    public static final Function4<Long, Long, Long, Point3D, Point3D> translate =
        (x, y, z, point) -> of(add.apply(point.x, x), add.apply(point.y, y), add.apply(point.z, z));


    private Point3D(Long x, Long y, Long z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static final Point3D of(Long x, Long y, Long z) { return new Point3D(x, y, z); }

    public boolean equals(Object other) {
        if (other instanceof Point3D) {
            var castOther = ((Point3D)other);
            return castOther.x.equals(x) && castOther.y.equals(y) && castOther.z.equals(z);
        }
        return false;
    }

    public int hashCode() {
        return 0;
    }

    public String toString() {
        return getClass().getName() + "[x=" + x + ",y=" + y + ",z=" + z + "]";
    }
}
