package se.chriskevin.mysterymaze.behavior;

import se.chriskevin.mysterymaze.behavior.Behavior;

/**
 * Created by CHSU7648 on 2016-03-13.
 */
public interface Actor {

    void act();

    void act(Behavior behavior);
}
