package se.chriskevin.mysterymaze.behavior;

import se.chriskevin.mysterymaze.animation.AnimationState;
import se.chriskevin.mysterymaze.animation.Direction;
import se.chriskevin.mysterymaze.environment.GameSprite;

/**
 * Created by CHSU7648 on 2016-03-14.
 */
public enum StopBehavior implements Behavior {

    STOP_DOWN() {
        @Override
        public void execute(GameSprite sprite) {
            stopVertical(sprite, Direction.DOWN);
        }
    },

    STOP_LEFT() {
        @Override
        public void execute(GameSprite sprite) {
            stopHorizontal(sprite, Direction.LEFT);
        }
    },

    STOP_RIGHT() {
        @Override
        public void execute(GameSprite sprite) {
            stopHorizontal(sprite, Direction.RIGHT);
        }
    },

    STOP_UP() {
        @Override
        public void execute(GameSprite sprite) {
            stopVertical(sprite, Direction.UP);
        }
    };

    private static void stopHorizontal(GameSprite sprite, Direction direction) {
        stop(sprite, 0, (int) sprite.getDLocation().getY(), direction);
    }

    private static void stopVertical(GameSprite sprite, Direction direction) {
        stop(sprite, (int) sprite.getDLocation().getX(), 0, direction);
    }

    private static void stop(GameSprite sprite, int x, int y, Direction direction) {
        sprite.getDLocation().setLocation(x, y);
        sprite.setAnimationState(AnimationState.STOPPED);
        sprite.setDirection(direction);
    }
}
