package se.chriskevin.mysterymaze.behavior;

import io.vavr.Function1;
import io.vavr.Function5;
import se.chriskevin.mysterymaze.animation.AnimationState;
import se.chriskevin.mysterymaze.animation.Direction;
import se.chriskevin.mysterymaze.environment.GameSprite;
import static se.chriskevin.mysterymaze.geometry.Point3D.translate;
import static se.chriskevin.mysterymaze.utils.Calculation.half;

public class MoveBehavior {

    public static final Function5<Long, Long, Direction, Behavior, GameSprite, GameSprite> move =
        (x, y, direction, stopBehavior, sprite) ->
            GameSprite.of(
                sprite.type,
                sprite.scale,
                sprite.blocking,
                translate(Long.valueOf(x), Long.valueOf(y),0L, sprite.position),
                sprite.speed,
                direction,
                sprite.colliding,
                sprite.behavior.getOrNull(),
                sprite.images,
                AnimationState.WALKING,
                sprite.size
            );

    public static final Function1<GameSprite, GameSprite> bounceBack = (sprite) -> {
        var dl = sprite.position;
        var halfSpeed = half(sprite.speed);

        /*return Option.of(ZERO_POINT3D)
            .map(transformWhen(equals(Direction.DOWN, sprite.direction), translateY(subtract(dl.y, halfSpeed))))
            .map(transformWhen(equals(Direction.LEFT, sprite.direction), translateX(add(dl.x, halfSpeed))))
            .map(transformWhen(equals(Direction.RIGHT, sprite.direction), translateX(-add(dl.x, halfSpeed))))
            .map(transformWhen(equals(Direction.UP, sprite.direction), translateY(subtract(-dl.y, halfSpeed))))
            .map(x -> new GameSprite(sprite.type, sprite.scale, sprite.blocking, x, sprite.speed, sprite.direction, sprite.colliding, sprite.behavior.get(), sprite.images, AnimationState.WALKING)))
            .get();*/

        return sprite;
    };

    public static final Function1<GameSprite, GameSprite> moveDown = (sprite) ->
        move.apply(0L, sprite.speed, Direction.DOWN, Behavior.STOP_DOWN, sprite);

    public static final Function1<GameSprite, GameSprite> moveLeft = (sprite) ->
        move.apply(-sprite.speed, 0L, Direction.LEFT, Behavior.STOP_LEFT, sprite);

    public static final Function1<GameSprite, GameSprite> moveRight = (sprite) ->
        move.apply(sprite.speed, 0L, Direction.RIGHT, Behavior.STOP_RIGHT, sprite);

    public static final Function1<GameSprite, GameSprite> moveUp = (sprite) ->
        move.apply(0L, -sprite.speed, Direction.UP, Behavior.STOP_UP, sprite);

    /* public static final Function1<GameSprite, GameSprite> moveHorizontal = (sprite) -> {
        if (Direction.DOWN.equals(sprite.direction)) {
            return MOVE_DOWN.execute(sprite);
        } else if (Direction.UP.equals(sprite.direction)) {
            return MOVE_UP.execute(sprite);
        }

        if (sprite.colliding) {
            if (Direction.DOWN.equals(sprite.direction)) {
                return MOVE_UP.execute(sprite);
            } else if (Direction.UP.equals(sprite.direction)) {
                return MOVE_DOWN.execute(sprite);
            }
        }

        return sprite.behavior.get().execute(sprite);
    };*/

    public static final Function1<GameSprite, GameSprite> moveVertical = (sprite) ->
        sprite;
}
