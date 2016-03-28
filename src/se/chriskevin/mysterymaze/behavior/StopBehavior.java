package se.chriskevin.mysterymaze.behavior;

import se.chriskevin.mysterymaze.AnimationState;
import se.chriskevin.mysterymaze.Behavior;
import se.chriskevin.mysterymaze.GameCharacter;
import se.chriskevin.mysterymaze.Direction;

/**
 * Created by CHSU7648 on 2016-03-14.
 */
public enum StopBehavior implements Behavior {

    STOP_DOWN() {
        @Override
        public void execute(GameCharacter gameCharacter) {
            gameCharacter.getDLocation().setLocation(gameCharacter.getDLocation().getX(), 0);
            gameCharacter.setAnimationState(AnimationState.STOPPED);
            gameCharacter.setDirection(Direction.DOWN);
        }
    },

    STOP_LEFT() {
        @Override
        public void execute(GameCharacter gameCharacter) {
            gameCharacter.getDLocation().setLocation(0, gameCharacter.getDLocation().getX());
            gameCharacter.setAnimationState(AnimationState.STOPPED);
            gameCharacter.setDirection(Direction.LEFT);
        }
    },

    STOP_RIGHT() {
        @Override
        public void execute(GameCharacter gameCharacter) {
            gameCharacter.getDLocation().setLocation(0, gameCharacter.getDLocation().getX());
            gameCharacter.setAnimationState(AnimationState.STOPPED);
            gameCharacter.setDirection(Direction.RIGHT);
        }
    },

    STOP_UP() {
        @Override
        public void execute(GameCharacter gameCharacter) {
            gameCharacter.getDLocation().setLocation(gameCharacter.getDLocation().getX(), 0);
            gameCharacter.setAnimationState(AnimationState.STOPPED);
            gameCharacter.setDirection(Direction.UP);
        }
    }
}
