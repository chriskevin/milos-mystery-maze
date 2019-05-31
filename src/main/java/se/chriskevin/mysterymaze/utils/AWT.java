package se.chriskevin.mysterymaze.utils;

import se.chriskevin.mysterymaze.geometry.Point3D;

public class AWT {

  public static final class Dimension {
    public static java.awt.Dimension of(final int width, final int height) {
      return new java.awt.Dimension(width, height);
    }

    public static java.awt.Dimension of(
        final se.chriskevin.mysterymaze.geometry.Dimension dimension) {
      return new java.awt.Dimension(dimension.width.intValue(), dimension.height.intValue());
    }

    public static se.chriskevin.mysterymaze.geometry.Dimension of(
        final java.awt.Dimension dimension) {
      return se.chriskevin.mysterymaze.geometry.Dimension.of(
          (long) dimension.width, (long) dimension.height);
    }
  }

  public static final class Point {
    public static java.awt.Point of(final int x, final int y) {
      return new java.awt.Point(x, y);
    }

    public static java.awt.Point of(final Point3D point3D) {
      return new java.awt.Point(point3D.x.intValue(), point3D.y.intValue());
    }
  }

  public static final class Rectangle {
    public static java.awt.Rectangle of(
        final int x, final int y, final int width, final int height) {
      return new java.awt.Rectangle(x, y, width, height);
    }

    public static java.awt.Rectangle of(
        final Point3D point3D, final se.chriskevin.mysterymaze.geometry.Dimension dimension) {
      return new java.awt.Rectangle(
          point3D.x.intValue(),
          point3D.y.intValue(),
          dimension.width.intValue(),
          dimension.height.intValue());
    }
  }
}
