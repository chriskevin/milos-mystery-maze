package se.chriskevin.mysterymaze.behavior;

import se.chriskevin.mysterymaze.animation.AnimationState;
import se.chriskevin.mysterymaze.animation.Direction;
import se.chriskevin.mysterymaze.environment.GameSprite;

import java.awt.*;

/**
 * Created by CHSU7648 on 2016-03-13.
 */
public enum MoveBehavior implements Behavior {

    BOUNCE_BACK() {
        @Override
        public void execute(GameSprite sprite) {
            final Point dl = sprite.getDLocation();
            int fullSpeed = sprite.getSpeed();
            int halfSpeed = sprite.getSpeed() / 2;

            if (sprite.getDirection() == Direction.DOWN) {
                sprite.getLocation().translate(0, (int) (dl.getY() - halfSpeed));
            } else if (sprite.getDirection() == Direction.LEFT) {
                sprite.getLocation().translate((int) (dl.getX() + halfSpeed), 0);
            } else if (sprite.getDirection() == Direction.RIGHT) {
                sprite.getLocation().translate((int) -(dl.getX() + halfSpeed), 0);
            } else if (sprite.getDirection() == Direction.UP) {
                sprite.getLocation().translate(0, (int) -(dl.getY() - halfSpeed));
            }

            sprite.getDLocation().setLocation(0, 0);
        }
    },

    MOVE() {
        @Override
        public void execute(GameSprite sprite) {
            final Point dl = sprite.getDLocation();
            sprite.getLocation().translate((int) dl.getX(), (int) dl.getY());
        }
    },

    MOVE_DOWN() {
        @Override
        public void execute(GameSprite sprite) {
            move(sprite, 0, sprite.getSpeed(), Direction.DOWN, StopBehavior.STOP_DOWN);
        }
    },

    MOVE_LEFT() {
        @Override
        public void execute(GameSprite sprite) {
            move(sprite, -sprite.getSpeed(), 0, Direction.LEFT, StopBehavior.STOP_LEFT);
        }
    },

    MOVE_RIGHT() {
        @Override
        public void execute(GameSprite sprite) {
            move(sprite, sprite.getSpeed(), 0, Direction.RIGHT, StopBehavior.STOP_RIGHT);
        }
    },

    MOVE_UP() {
        @Override
        public void execute(GameSprite sprite) {
            move(sprite, 0, -sprite.getSpeed(), Direction.UP, StopBehavior.STOP_UP);
        }
    },


    HORIZONTAL_MOVEMENT {
        @Override
        public void execute(GameSprite sprite) {
            if (sprite.getDirection() == Direction.DOWN) {
                sprite.act(MOVE_DOWN);
            } else if (sprite.getDirection() == Direction.UP) {
                sprite.act(MOVE_UP);
            }

            if (sprite.isColliding()) {
                if (sprite.getDirection() == Direction.DOWN) {
                    sprite.act(MOVE_UP);
                } else if (sprite.getDirection() == Direction.UP) {
                    sprite.act(MOVE_DOWN);
                }
            }
        }
    },

    VERTICAL_MOVEMENT {
        @Override
        public void execute(GameSprite sprite) {

        }
    };

    public void execute(GameSprite sprite) {

    }

    private static void move(GameSprite sprite, int x, int y, Direction direction, StopBehavior stopBehavior) {
        sprite.getDLocation().translate(x, y);
        sprite.setDirection(direction);
        sprite.setAnimationState(AnimationState.WALKING);
        sprite.act(MOVE);
        sprite.act(stopBehavior);
    }
}
