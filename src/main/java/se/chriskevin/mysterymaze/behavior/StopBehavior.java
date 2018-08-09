package se.chriskevin.mysterymaze.behavior;

import io.vavr.Function1;
import io.vavr.Function2;
import se.chriskevin.mysterymaze.animation.AnimationState;
import se.chriskevin.mysterymaze.animation.Direction;
import se.chriskevin.mysterymaze.environment.GameSprite;
import se.chriskevin.mysterymaze.geometry.Point3D;

public class StopBehavior {

    private static final Function2<Direction, GameSprite, GameSprite> stop = (direction, sprite) ->
        GameSprite.of(
            sprite.type,
            sprite.scale,
            sprite.blocking,
            Point3D.of(
                (direction.equals(Direction.DOWN) || direction.equals(Direction.UP)) ? sprite.position.x : 0L,
                (direction.equals(Direction.RIGHT) || direction.equals(Direction.LEFT)) ? sprite.position.y : 0L,
                0L
            ),
            sprite.speed,
            direction,
            sprite.colliding,
            sprite.behavior.getOrNull(),
            sprite.images,
            AnimationState.STOPPED,
            sprite.size
        );

    public static final Function1<GameSprite, GameSprite> stopDown = stop.apply(Direction.DOWN);

    public static final Function1<GameSprite, GameSprite> stopLeft = stop.apply(Direction.LEFT);

    public static final Function1<GameSprite, GameSprite> stopRight = stop.apply(Direction.RIGHT);

    public static final Function1<GameSprite, GameSprite> stopUp = stop.apply(Direction.UP);
}
