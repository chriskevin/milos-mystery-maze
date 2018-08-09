package se.chriskevin.mysterymaze.utils;

import se.chriskevin.mysterymaze.geometry.Point3D;

public class AWT {

    public static final class Dimension {
        public static final java.awt.Dimension of(int width, int height) {
            return new java.awt.Dimension(width, height);
        }

        public static final java.awt.Dimension of(se.chriskevin.mysterymaze.geometry.Dimension dimension) {
            return new java.awt.Dimension(dimension.width.intValue(), dimension.height.intValue());
        }

        public static final se.chriskevin.mysterymaze.geometry.Dimension of(java.awt.Dimension dimension) {
            return se.chriskevin.mysterymaze.geometry.Dimension.of((long) dimension.width, (long) dimension.height);
        }
    }

    public static final class Point {
        public static final java.awt.Point of(int x, int y) {
            return new java.awt.Point(x, y);
        }

        public static final java.awt.Point of(Point3D point3D) {
            return new java.awt.Point(point3D.x.intValue(), point3D.y.intValue());
        }
    }

    public static final class Rectangle {
        public static final java.awt.Rectangle of(int x, int y, int width, int height) {
            return new java.awt.Rectangle(x, y, width, height);
        }

        public static final java.awt.Rectangle of(Point3D point3D, se.chriskevin.mysterymaze.geometry.Dimension dimension) {
            return new java.awt.Rectangle(
                point3D.x.intValue(),
                point3D.y.intValue(),
                dimension.width.intValue(),
                dimension.height.intValue()
            );
        }
    }
}
