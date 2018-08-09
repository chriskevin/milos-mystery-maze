package se.chriskevin.mysterymaze.behavior;

import se.chriskevin.mysterymaze.animation.AnimationState;
import se.chriskevin.mysterymaze.animation.Direction;
import se.chriskevin.mysterymaze.environment.GameSprite;
import static se.chriskevin.mysterymaze.geometry.Point3D.translate;
import static se.chriskevin.mysterymaze.utils.Calculation.half;

public enum MoveBehavior implements Behavior {

    BOUNCE_BACK() {
        @Override
        public GameSprite execute(GameSprite sprite) {
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
        }
    },

    MOVE_DOWN() {
        @Override
        public GameSprite execute(GameSprite sprite) {
            return move(sprite, 0L, sprite.speed, Direction.DOWN, StopBehavior.STOP_DOWN);
        }
    },

    MOVE_LEFT() {
        @Override
        public GameSprite execute(GameSprite sprite) {
            return move(sprite, -sprite.speed, 0L, Direction.LEFT, StopBehavior.STOP_LEFT);
        }
    },

    MOVE_RIGHT() {
        @Override
        public GameSprite execute(GameSprite sprite) {
            return move(sprite, sprite.speed, 0L, Direction.RIGHT, StopBehavior.STOP_RIGHT);
        }
    },

    MOVE_UP() {
        @Override
        public GameSprite execute(GameSprite sprite) {
            return move(sprite, 0L, -sprite.speed, Direction.UP, StopBehavior.STOP_UP);
        }
    },


    HORIZONTAL_MOVEMENT {
        @Override
        public GameSprite execute(GameSprite sprite) {
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
        }
    },

    VERTICAL_MOVEMENT {
        @Override
        public GameSprite execute(GameSprite sprite) {
            return sprite;
        }
    };

    public GameSprite execute(GameSprite sprite) {
        return sprite;
    }

    private static GameSprite move(GameSprite sprite, Long x, Long y, Direction direction, StopBehavior stopBehavior) {
        return GameSprite.of(sprite.type, sprite.scale, sprite.blocking, translate(Long.valueOf(x), Long.valueOf(y), 0L, sprite.position), sprite.speed, direction, sprite.colliding, sprite.behavior.getOrNull(), sprite.images, AnimationState.WALKING, sprite.size);
    }
}
