package se.chriskevin.mysterymaze.behavior;

import se.chriskevin.mysterymaze.AnimationState;
import se.chriskevin.mysterymaze.Behavior;
import se.chriskevin.mysterymaze.Character;
import se.chriskevin.mysterymaze.Direction;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by CHSU7648 on 2016-03-13.
 */
public enum MoveBehavior implements Behavior {

    MOVE() {
        @Override
        public void execute(Character character) {
            if (character.isColliding()) {
                int x = character.getX();
                x += -(character.getDx() * 1.5);
                character.setX(x);

                int y = character.getY();
                y += -(character.getDy() * 1.5);
                character.setY(y);

                character.setDx(0);
                character.setDy(0);

                character.isColliding(false);
            } else {
                character.setX(character.getX() + character.getDx());
                character.setY(character.getY() + character.getDy());
            }
        }
    },

    MOVE_UP() {
        @Override
        public void execute(Character character) {
            if (character.isColliding()) {
                character.setDy(-character.getSpeed());
                character.setDirection(Direction.UP);
                character.setAnimationState(AnimationState.WALKING);
            }
            character.act(MOVE);
            character.act(StopBehavior.STOP_UP);
        }
    },


    HORIZONTAL_MOVEMENT {
        @Override
        public void execute(Character character) {

        }
    },

    VERTICAL_MOVEMENT {
        @Override
        public void execute(Character character) {

        }
    };

    public void execute(Character character) {

    }
}
