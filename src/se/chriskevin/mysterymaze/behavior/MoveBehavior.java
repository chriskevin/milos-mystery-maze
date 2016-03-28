package se.chriskevin.mysterymaze.behavior;

import se.chriskevin.mysterymaze.AnimationState;
import se.chriskevin.mysterymaze.Behavior;
import se.chriskevin.mysterymaze.GameCharacter;
import se.chriskevin.mysterymaze.Direction;

import java.awt.*;

/**
 * Created by CHSU7648 on 2016-03-13.
 */
public enum MoveBehavior implements Behavior {

    MOVE() {
        @Override
        public void execute(GameCharacter gameCharacter) {
            final Point dl = gameCharacter.getDLocation();

            if (gameCharacter.isColliding()) {
                gameCharacter.getLocation().translate((int) -(dl.getX() * 1.5), (int) -(dl.getY() * 1.5));
                gameCharacter.getDLocation().setLocation(0, 0);
                gameCharacter.isColliding(false);
            } else {
                gameCharacter.getLocation().translate((int) dl.getX(), (int) dl.getY());
            }
        }
    },

    MOVE_UP() {
        @Override
        public void execute(GameCharacter gameCharacter) {
            if (gameCharacter.isColliding()) {
                gameCharacter.getDLocation().translate(0, -gameCharacter.getSpeed());
                gameCharacter.setDirection(Direction.UP);
                gameCharacter.setAnimationState(AnimationState.WALKING);
            }
            gameCharacter.act(MOVE);
            gameCharacter.act(StopBehavior.STOP_UP);
        }
    },


    HORIZONTAL_MOVEMENT {
        @Override
        public void execute(GameCharacter gameCharacter) {

        }
    },

    VERTICAL_MOVEMENT {
        @Override
        public void execute(GameCharacter gameCharacter) {

        }
    };

    public void execute(GameCharacter gameCharacter) {

    }
}
