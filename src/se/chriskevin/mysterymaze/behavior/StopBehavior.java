package se.chriskevin.mysterymaze.behavior;

import se.chriskevin.mysterymaze.AnimationState;
import se.chriskevin.mysterymaze.Behavior;
import se.chriskevin.mysterymaze.Character;
import se.chriskevin.mysterymaze.Direction;

/**
 * Created by CHSU7648 on 2016-03-14.
 */
public enum StopBehavior implements Behavior {

    STOP_DOWN() {
        @Override
        public void execute(Character character) {
            character.setDy(0);
            character.setAnimationState(AnimationState.STOPPED);
            character.setDirection(Direction.DOWN);
        }
    },

    STOP_LEFT() {
        @Override
        public void execute(Character character) {
            character.setDx(0);
            character.setAnimationState(AnimationState.STOPPED);
            character.setDirection(Direction.LEFT);
        }
    },

    STOP_RIGHT() {
        @Override
        public void execute(Character character) {
            character.setDx(0);
            character.setAnimationState(AnimationState.STOPPED);
            character.setDirection(Direction.RIGHT);
        }
    },

    STOP_UP() {
        @Override
        public void execute(Character character) {
            character.setDy(0);
            character.setAnimationState(AnimationState.STOPPED);
            character.setDirection(Direction.UP);
        }
    }
}
