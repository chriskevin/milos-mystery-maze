package se.chriskevin.mysterymaze.behavior;

import se.chriskevin.mysterymaze.animation.AnimationState;
import se.chriskevin.mysterymaze.animation.Direction;
import se.chriskevin.mysterymaze.environment.GameSprite;
import se.chriskevin.mysterymaze.geometry.Point3D;

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
        return stop(sprite, 0L, sprite.position.y, direction);
    }

    private static GameSprite stopVertical(GameSprite sprite, Direction direction) {
        return stop(sprite, sprite.position.x, 0L, direction);
    }

    private static GameSprite stop(GameSprite sprite, Long x, Long y, Direction direction) {
        return GameSprite.of(sprite.type, sprite.scale, sprite.blocking, Point3D.of(x, y, 0L), sprite.speed, direction, sprite.colliding, sprite.behavior.get(), sprite.images, AnimationState.STOPPED, sprite.size);
    }
}
