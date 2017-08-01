package se.chriskevin.mysterymaze.behavior;

import se.chriskevin.mysterymaze.animation.AnimationState;
import se.chriskevin.mysterymaze.animation.Direction;
import se.chriskevin.mysterymaze.environment.GameSprite;
import se.chriskevin.mysterymaze.geometry.Point3D;

import static java.lang.Math.addExact;
import static java.lang.Math.subtractExact;

/**
 * Created by Chris Sundberg on 2016-03-13.
 */
public enum MoveBehavior implements Behavior {

    BOUNCE_BACK() {
        @Override
        public GameSprite execute(GameSprite sprite) {
            final Point3D dl = sprite.position;
            final Integer fullSpeed = sprite.speed;
            final Integer halfSpeed = sprite.speed / 2;

            Point3D dPosition;

            if (Direction.DOWN.equals(sprite.direction)) {
                dPosition = sprite.position.translate(0, subtractExact(dl.y, halfSpeed), 0);
            } else if (Direction.LEFT.equals(sprite.direction)) {
                dPosition = sprite.position.translate(addExact(dl.x, halfSpeed), 0, 0);
            } else if (Direction.RIGHT.equals(sprite.direction)) {
                dPosition = sprite.position.translate(-addExact(dl.x, halfSpeed), 0, 0);
            } else if (Direction.UP.equals(sprite.direction)) {
                dPosition = sprite.position.translate(0, subtractExact(-dl.y, halfSpeed), 0);
            } else {
                dPosition = new Point3D(0, 0, 0);
            }

            return new GameSprite(sprite.type, sprite.scale, sprite.blocking, dPosition, sprite.speed, sprite.direction, sprite.colliding, sprite.behavior.get(), sprite.images, AnimationState.WALKING);
        }
    },

    MOVE_DOWN() {
        @Override
        public GameSprite execute(GameSprite sprite) {
            return move(sprite, 0, sprite.speed, Direction.DOWN, StopBehavior.STOP_DOWN);
        }
    },

    MOVE_LEFT() {
        @Override
        public GameSprite execute(GameSprite sprite) {
            return move(sprite, -sprite.speed, 0, Direction.LEFT, StopBehavior.STOP_LEFT);
        }
    },

    MOVE_RIGHT() {
        @Override
        public GameSprite execute(GameSprite sprite) {
            return move(sprite, sprite.speed, 0, Direction.RIGHT, StopBehavior.STOP_RIGHT);
        }
    },

    MOVE_UP() {
        @Override
        public GameSprite execute(GameSprite sprite) {
            return move(sprite, 0, -sprite.speed, Direction.UP, StopBehavior.STOP_UP);
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

    private static GameSprite move(GameSprite sprite, Integer x, Integer y, Direction direction, StopBehavior stopBehavior) {
        return new GameSprite(sprite.type, sprite.scale, sprite.blocking, sprite.position.translate(x, y, 0), sprite.speed, direction, sprite.colliding, sprite.behavior.get(), sprite.images, AnimationState.WALKING);
    }
}
