package se.chriskevin.mysterymaze.environment;

import static java.lang.Math.multiplyExact;

import io.vavr.collection.Map;
import io.vavr.control.Option;
import java.awt.*;
import se.chriskevin.mysterymaze.animation.AnimationState;
import se.chriskevin.mysterymaze.animation.Direction;
import se.chriskevin.mysterymaze.behavior.Behavior;
import se.chriskevin.mysterymaze.geometry.Dimension;
import se.chriskevin.mysterymaze.geometry.Point3D;

public class GameSprite {

  public Boolean blocking;
  public Boolean colliding;

  public final AnimationState animationState;
  public final Option<Behavior> behavior;
  public final Direction direction;
  public final Map<String, Image> images;
  public final Point3D position;
  public final Long scale;
  public final Dimension size;
  public final Long speed;
  public final SpriteType type;

  private GameSprite(
      final SpriteType type,
      final Long scale,
      final Boolean blocking,
      final Point3D position,
      final Long speed,
      final Direction direction,
      final Boolean colliding,
      final Behavior behavior,
      final Map<String, Image> images,
      final AnimationState animationState,
      final Dimension size) {
    this.type = type;
    this.position = position;
    this.scale = scale;
    this.animationState = animationState;
    this.blocking = blocking;
    this.colliding = colliding;
    this.direction = direction;
    this.speed = multiplyExact(speed, scale);
    this.behavior = Option.of(behavior);
    this.images = images;
    this.size = size;
  }

  public static GameSprite of(
      final SpriteType type,
      final Long scale,
      final Boolean blocking,
      final Point3D position,
      final Long speed,
      final Direction direction,
      final Boolean colliding,
      final Behavior behavior,
      final Map<String, Image> images,
      final AnimationState animationState,
      final Dimension size) {
    return new GameSprite(
        type,
        scale,
        blocking,
        position,
        speed,
        direction,
        colliding,
        behavior,
        images,
        animationState,
        size);
  }

  public Rectangle getBounds() {
    return new Rectangle(
        position.x.intValue(),
        position.y.intValue(),
        size.width.intValue(),
        size.height.intValue());
  }

  public String toString() {
    return getClass().getName()
        + "[type="
        + type
        + ",blocking="
        + blocking
        + ",position="
        + position
        + "]";
  }
}
