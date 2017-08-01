package se.chriskevin.mysterymaze.behavior;

import se.chriskevin.mysterymaze.animation.AnimationState;
import se.chriskevin.mysterymaze.animation.Direction;
import se.chriskevin.mysterymaze.environment.GameSprite;
import se.chriskevin.mysterymaze.geometry.Point3D;

/**
 * Created by Chris Sundberg on 2016-03-14.
 */
public enum StopBehavior implements Behavior {

    STOP_DOWN() {
        @Override
        public GameSprite execute(GameSprite sprite) {
            return stopVertical(sprite, Direction.DOWN);
        }
    },

    STOP_LEFT() {
        @Override
        public GameSprite execute(GameSprite sprite) {
            return stopHorizontal(sprite, Direction.LEFT);
        }
    },

    STOP_RIGHT() {
        @Override
        public GameSprite execute(GameSprite sprite) {
            return stopHorizontal(sprite, Direction.RIGHT);
        }
    },

    STOP_UP() {
        @Override
        public GameSprite execute(GameSprite sprite) {
            return stopVertical(sprite, Direction.UP);
        }
    };

    private static GameSprite stopHorizontal(GameSprite sprite, Direction direction) {
        return stop(sprite, 0, sprite.position.y, direction);
    }

    private static GameSprite stopVertical(GameSprite sprite, Direction direction) {
        return stop(sprite, sprite.position.x, 0, direction);
    }

    private static GameSprite stop(GameSprite sprite, Integer x, Integer y, Direction direction) {
        return new GameSprite(sprite.type, sprite.scale, sprite.blocking, new Point3D(x, y, 0), sprite.speed, direction, sprite.colliding, sprite.behavior.get(), sprite.images, AnimationState.STOPPED);
    }
}
