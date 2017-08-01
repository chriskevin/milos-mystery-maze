package se.chriskevin.mysterymaze.geometry;

import java.io.Serializable;

/**
 * Created by Chris Sundberg on 2017-06-12.
 */
public final class Point3D implements Serializable {
    public final Integer x;
    public final Integer y;
    public final Integer z;

    public Point3D(Integer x, Integer y, Integer z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Point3D move(Integer x, Integer y, Integer z) {
        return new Point3D(x, y, z);
    }

    public Point3D translate(Integer x, Integer y, Integer z) {
        return new Point3D(this.x + x, this.y + y, this.z + z);
    }

    public boolean equals(Object other) {
        if (other instanceof Point3D) {
            return (((Point3D)other).x.equals(this.x) && ((Point3D)other).y.equals(this.y) && ((Point3D)other).z.equals(this.z));
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
