package se.chriskevin.mysterymaze.behavior;

import se.chriskevin.mysterymaze.animation.AnimationState;
import se.chriskevin.mysterymaze.animation.Direction;
import se.chriskevin.mysterymaze.environment.GameSprite;
import se.chriskevin.mysterymaze.geometry.Point3D;

import static se.chriskevin.mysterymaze.geometry.Point3D.ZERO_POINT3D;
import static se.chriskevin.mysterymaze.geometry.Point3D.translate;
import static se.chriskevin.mysterymaze.geometry.Point3D.translateX;
import static se.chriskevin.mysterymaze.geometry.Point3D.translateY;
import static se.chriskevin.mysterymaze.utils.Calculation.add;
import static se.chriskevin.mysterymaze.utils.Calculation.half;
import static se.chriskevin.mysterymaze.utils.Calculation.subtract;
import static se.chriskevin.mysterymaze.utils.Common.equals;

public enum MoveBehavior implements Behavior {

    BOUNCE_BACK() {
        @Override
        public GameSprite execute(GameSprite sprite) {
            var dl = sprite.position;
            var halfSpeed = half.apply(sprite.speed);

            /*return Option.of(ZERO_POINT3D)
                .map(tranformWhen.apply(equals.apply(Direction.DOWN, sprite.direction), translateY.apply(subtract.apply(dl.y, halfSpeed))))
                .map(tranformWhen.apply(equals.apply(Direction.LEFT, sprite.direction), translateX.apply(add.apply(dl.x, halfSpeed))))
                .map(tranformWhen.apply(equals.apply(Direction.RIGHT, sprite.direction), translateX.apply(-add.apply(dl.x, halfSpeed))))
                .map(tranformWhen.apply(equals.apply(Direction.UP, sprite.direction), translateY.apply(subtract.apply(-dl.y, halfSpeed))))
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
        return GameSprite.of(sprite.type, sprite.scale, sprite.blocking, translate.apply(Long.valueOf(x), Long.valueOf(y), 0L, sprite.position), sprite.speed, direction, sprite.colliding, sprite.behavior.get(), sprite.images, AnimationState.WALKING, sprite.size);
    }
}
