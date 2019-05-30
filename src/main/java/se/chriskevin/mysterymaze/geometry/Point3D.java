package se.chriskevin.mysterymaze.geometry;

import java.io.Serializable;

public final class Point3D implements Serializable {

    public final Long x;
    public final Long y;
    public final Long z;

    public static final Point3D ZERO_POINT3D = of(0L, 0L, 0L);

    private Point3D(Long x, Long y, Long z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static final Point3D of(Long x, Long y, Long z) { return new Point3D(x, y, z); }

    public static final Point3D add(Point3D a, Point3D b) { return of(a.x + b.x, a.y + b.y, a.z + b.z); }

    public static final Point3D subtract(Point3D a, Point3D b) { return of(a.x - b.x, a.y - b.y, a.z - b.z); }

    public static final Point3D moveX(Long x, Point3D point) {
        return of(x, point.y, point.z);
    }

    public static final Point3D moveY(Long y, Point3D point) {
        return of(point.x, y, point.z);
    }

    public static final Point3D moveZ(Long z, Point3D point) {
        return of(point.x, point.y, z);
    }

    public static final Point3D move(Long x, Long y, Long z, Point3D point) {
        return of(x, y, z);
    }

    public static final Point3D translateX(Long x, Point3D point) {
        return of(point.x + x, point.y, point.z);
    }

    public static final Point3D translateY(Long y, Point3D point) {
        return of(point.x, point.y + y, point.z);
    }

    public static final Point3D translateZ(Long z, Point3D point) {
        return of(point.x, point.y, point.z + z);
    }

    public static final Point3D translate(Long x, Long y, Long z, Point3D point) {
        return of(point.x + x, point.y + y, point.z + z);
    }

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
