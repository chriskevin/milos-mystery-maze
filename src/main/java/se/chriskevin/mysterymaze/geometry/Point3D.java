package se.chriskevin.mysterymaze.geometry;

import io.vavr.Function2;
import io.vavr.Function4;

import java.io.Serializable;

import static se.chriskevin.mysterymaze.utils.Calculation.add;

/**
 * Created by Chris Sundberg on 2017-06-12.
 */
public final class Point3D implements Serializable {

    public final Long x;
    public final Long y;
    public final Long z;

    public static final Point3D ZERO_POINT3D = new Point3D(0L, 0L, 0L);

    public static final Function2<Long, Point3D, Point3D> moveX =
        (x, point) -> new Point3D(x, point.y, point.z);

    public static final Function2<Long, Point3D, Point3D> moveY =
        (y, point) -> new Point3D(point.x, y, point.z);

    public static final Function2<Long, Point3D, Point3D> moveZ =
        (z, point) -> new Point3D(point.x, point.y, z);

    public static final Function4<Long, Long, Long, Point3D, Point3D> move =
        (x, y, z, point) -> new Point3D(point.x, point.y, point.z);

    public static final Function2<Long, Point3D, Point3D> translateX =
        (x, point) -> new Point3D(add.apply(point.x, x), point.y, point.z);

    public static final Function2<Long, Point3D, Point3D> translateY =
        (y, point) -> new Point3D(point.x, add.apply(point.y, y), point.z);

    public static final Function2<Long, Point3D, Point3D> translateZ =
        (z, point) -> new Point3D(point.x, point.y, add.apply(point.z, z));

    public static final Function4<Long, Long, Long, Point3D, Point3D> translate =
        (x, y, z, point) -> new Point3D(add.apply(point.x, x), add.apply(point.y, y), add.apply(point.z, z));


    public Point3D(Long x, Long y, Long z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public boolean equals(Object other) {
        if (other instanceof Point3D) {
            final Point3D castOther = ((Point3D)other);
            return castOther.x.equals(this.x) && castOther.y.equals(this.y) && castOther.z.equals(this.z);
        }
        return false;
    }

    public int hashCode() {
        return 0;
    }

    public String toString() {
        return this.getClass().getName() + "[x=" + this.x + ",y=" + this.y + ",z=" + this.z + "]";
    }
}
