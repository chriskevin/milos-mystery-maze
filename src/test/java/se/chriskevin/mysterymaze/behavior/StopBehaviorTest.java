package se.chriskevin.mysterymaze.behavior;

import static org.junit.Assert.*;
import static se.chriskevin.mysterymaze.behavior.StopBehavior.*;

import org.junit.Test;
import se.chriskevin.mysterymaze.animation.AnimationState;
import se.chriskevin.mysterymaze.animation.Direction;
import se.chriskevin.mysterymaze.environment.GameSprite;
import se.chriskevin.mysterymaze.environment.SpriteType;
import se.chriskevin.mysterymaze.geometry.Dimension;
import se.chriskevin.mysterymaze.geometry.Point3D;

public class StopBehaviorTest {

  @Test
  public void verifyStopUp() {
    var sprite =
        GameSprite.of(
            SpriteType.PLAYER,
            1L,
            false,
            Point3D.ZERO_POINT3D,
            8L,
            Direction.DOWN,
            false,
            null,
            null,
            AnimationState.STOPPED,
            Dimension.ZERO_DIMENSION);
    var updatedSprite = stopUp.apply(sprite);
    assertNotEquals("Sprites should not be same", sprite, updatedSprite);
    assertEquals("Direction should be UP", Direction.UP, updatedSprite.direction);
    assertEquals(
        "Animation should be STOPPED", AnimationState.STOPPED, updatedSprite.animationState);
  }

  @Test
  public void verifyStopRight() {
    var sprite =
        GameSprite.of(
            SpriteType.PLAYER,
            1L,
            false,
            Point3D.ZERO_POINT3D,
            8L,
            Direction.DOWN,
            false,
            null,
            null,
            AnimationState.STOPPED,
            Dimension.ZERO_DIMENSION);
    var updatedSprite = stopRight.apply(sprite);
    assertNotEquals("Sprites should not be same", sprite, updatedSprite);
    assertEquals("Direction should be RIGHT", Direction.RIGHT, updatedSprite.direction);
    assertEquals(
        "Animation should be STOPPED", AnimationState.STOPPED, updatedSprite.animationState);
  }

  @Test
  public void verifyStopDown() {
    var sprite =
        GameSprite.of(
            SpriteType.PLAYER,
            1L,
            false,
            Point3D.ZERO_POINT3D,
            8L,
            Direction.DOWN,
            false,
            null,
            null,
            AnimationState.STOPPED,
            Dimension.ZERO_DIMENSION);
    var updatedSprite = stopDown.apply(sprite);
    assertNotEquals("Sprites should not be same", sprite, updatedSprite);
    assertEquals("Direction should be DOWN", Direction.DOWN, updatedSprite.direction);
    assertEquals(
        "Animation should be STOPPED", AnimationState.STOPPED, updatedSprite.animationState);
  }

  @Test
  public void verifyStopLeft() {
    var sprite =
        GameSprite.of(
            SpriteType.PLAYER,
            1L,
            false,
            Point3D.ZERO_POINT3D,
            8L,
            Direction.DOWN,
            false,
            null,
            null,
            AnimationState.STOPPED,
            Dimension.ZERO_DIMENSION);
    var updatedSprite = stopLeft.apply(sprite);
    assertNotEquals("Sprites should not be same", sprite, updatedSprite);
    assertEquals("Direction should be LEFT", Direction.LEFT, updatedSprite.direction);
    assertEquals(
        "Animation should be STOPPED", AnimationState.STOPPED, updatedSprite.animationState);
  }
}
