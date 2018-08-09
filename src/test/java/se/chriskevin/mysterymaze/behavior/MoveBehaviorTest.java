package se.chriskevin.mysterymaze.behavior;

import org.junit.Test;
import se.chriskevin.mysterymaze.animation.AnimationState;
import se.chriskevin.mysterymaze.animation.Direction;
import se.chriskevin.mysterymaze.environment.GameSprite;
import se.chriskevin.mysterymaze.environment.SpriteType;
import se.chriskevin.mysterymaze.geometry.Dimension;
import se.chriskevin.mysterymaze.geometry.Point3D;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static se.chriskevin.mysterymaze.behavior.MoveBehavior.*;

public class MoveBehaviorTest {

    @Test
    public void verifyStopUp() {
        var sprite = GameSprite.of(
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
                Dimension.ZERO_DIMENSION
        );
        var updatedSprite = moveUp.apply(sprite);
        assertNotEquals("Sprites should not be same", sprite, updatedSprite);
        assertEquals("Y coordinate should av decreased", Point3D.of(0L, -8L, 0L), updatedSprite.position);
        assertEquals("Direction should be UP", Direction.UP, updatedSprite.direction);
        assertEquals("Animation should be WALKING", AnimationState.WALKING, updatedSprite.animationState);
    }

    @Test
    public void verifyStopRight() {
        var sprite = GameSprite.of(
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
                Dimension.ZERO_DIMENSION
        );
        var updatedSprite = moveRight.apply(sprite);
        assertNotEquals("Sprites should not be same", sprite, updatedSprite);
        assertEquals("X coordinate should av increased", Point3D.of(8L, 0L, 0L), updatedSprite.position);
        assertEquals("Direction should be RIGHT", Direction.RIGHT, updatedSprite.direction);
        assertEquals("Animation should be WALKING", AnimationState.WALKING, updatedSprite.animationState);
    }

    @Test
    public void verifyStopDown() {
        var sprite = GameSprite.of(
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
                Dimension.ZERO_DIMENSION
        );
        var updatedSprite = moveDown.apply(sprite);
        assertNotEquals("Sprites should not be same", sprite, updatedSprite);
        assertEquals("Y coordinate should av increased", Point3D.of(0L, 8L, 0L), updatedSprite.position);
        assertEquals("Direction should be DOWN", Direction.DOWN, updatedSprite.direction);
        assertEquals("Animation should be WALKING", AnimationState.WALKING, updatedSprite.animationState);
    }

    @Test
    public void verifyStopLeft() {
        var sprite = GameSprite.of(
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
                Dimension.ZERO_DIMENSION
        );
        var updatedSprite = moveLeft.apply(sprite);
        assertNotEquals("Sprites should not be same", sprite, updatedSprite);
        assertEquals("X coordinate should av decreased", Point3D.of(-8L, 0L, 0L), updatedSprite.position);
        assertEquals("Direction should be LEFT", Direction.LEFT, updatedSprite.direction);
        assertEquals("Animation should be WALKING", AnimationState.WALKING, updatedSprite.animationState);
    }
}
