package se.chriskevin.mysterymaze.geometry;

import java.io.Serializable;

import static se.chriskevin.mysterymaze.utils.Calculation.add;

public final class Point3D implements Serializable {

    public final Long x;
    public final Long y;
    public final Long z;

    public static final Point3D ZERO_POINT3D = of(0L, 0L, 0L);

    private Point3D(final Long x, final Long y, final Long z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static Point3D of(final Long x, final Long y, final Long z) { return new Point3D(x, y, z); }

    public static Point3D moveX(final Long x, final Point3D point) {
        return of(x, point.y, point.z);
    }

    public static Point3D moveY(final Long y, final Point3D point) {
        return of(point.x, y, point.z);
    }

    public static Point3D moveZ(final Long z, final Point3D point) {
        return of(point.x, point.y, z);
    }

    public static Point3D move(final Long x, final Long y, final Long z, final Point3D point) {
        return of(x, y, z);
    }

    public static Point3D translateX(final Long x, final Point3D point) {
        return of(add(point.x, x), point.y, point.z);
    }

    public static Point3D translateY(final Long y, final Point3D point) {
        return of(point.x, add(point.y, y), point.z);
    }

    public static Point3D translateZ(final Long z, final Point3D point) {
        return of(point.x, point.y, add(point.z, z));
    }

    public static Point3D translate(final Long x, final Long y, final Long z, final Point3D point) {
        return of(add(point.x, x), add(point.y, y), add(point.z, z));
    }

    public boolean equals(final Object other) {
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
