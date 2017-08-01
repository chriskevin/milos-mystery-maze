package se.chriskevin.mysterymaze.behavior;

import se.chriskevin.mysterymaze.environment.GameSprite;

/**
 * Created by Chris Sundberg on 2016-03-13.
 */
public interface Behavior {

    GameSprite execute(GameSprite sprite);
}
