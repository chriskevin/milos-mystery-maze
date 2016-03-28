package se.chriskevin.mysterymaze;

import se.chriskevin.mysterymaze.behavior.MoveBehavior;

import java.awt.*;
import java.util.Map;
import java.util.HashMap;

/**
 * Created by CHSU7648 on 2016-03-08.
 */
public class GameCharacterFactory {

    public static GameCharacter createHero(Point location, int scale) {
        final GameCharacter hero = new GameCharacter(location);
        hero.setScale(scale);
        final Map<String, String> imageMap = new HashMap<>();
        imageMap.put("STOPPED_DOWN", "/images/milo/STOPPED_DOWN.gif");
        imageMap.put("STOPPED_LEFT", "/images/milo/STOPPED_LEFT.gif");
        imageMap.put("STOPPED_RIGHT", "/images/milo/STOPPED_RIGHT.gif");
        imageMap.put("STOPPED_UP", "/images/milo/STOPPED_UP.gif");
        imageMap.put("WALKING_DOWN", "/images/milo/WALKING_DOWN.gif");
        imageMap.put("WALKING_LEFT", "/images/milo/WALKING_LEFT.gif");
        imageMap.put("WALKING_RIGHT", "/images/milo/WALKING_RIGHT.gif");
        imageMap.put("WALKING_UP", "/images/milo/WALKING_UP.gif");
        hero.setImages(imageMap);
        return hero;
    }

    public static GameCharacter createBat(Point location, int scale) {
        final GameCharacter bat = new GameCharacter(location);
        bat.setScale(scale);
        bat.setAnimationState(AnimationState.WALKING);
        final Map<String, String> imageMap = new HashMap<>();
        imageMap.put("STOPPED_DOWN", "/images/bat/WALKING_DOWN.gif");
        imageMap.put("STOPPED_LEFT", "/images/bat/WALKING_LEFT.gif");
        imageMap.put("STOPPED_RIGHT", "/images/bat/WALKING_RIGHT.gif");
        imageMap.put("STOPPED_UP", "/images/bat/WALKING_UP.gif");
        imageMap.put("WALKING_DOWN", "/images/bat/WALKING_DOWN.gif");
        imageMap.put("WALKING_LEFT", "/images/bat/WALKING_LEFT.gif");
        imageMap.put("WALKING_RIGHT", "/images/bat/WALKING_RIGHT.gif");
        imageMap.put("WALKING_UP", "/images/bat/WALKING_UP.gif");
        bat.setImages(imageMap);
        bat.setBehavior(MoveBehavior.MOVE_UP);
        return bat;
    }

    public static GameCharacter createRat(Point location, int scale) {
        final GameCharacter rat = new GameCharacter(location);
        rat.setScale(scale);
        final Map<String, String> imageMap = new HashMap<>();
        imageMap.put("STOPPED_DOWN", "/images/rat/STOPPED_LEFT.gif");
        imageMap.put("STOPPED_LEFT", "/images/rat/STOPPED_LEFT.gif");
        imageMap.put("STOPPED_RIGHT", "/images/rat/STOPPED_RIGHT.gif");
        imageMap.put("STOPPED_UP", "/images/rat/STOPPED_LEFT.gif");
        imageMap.put("WALKING_DOWN", "/images/rat/WALKING_LEFT.gif");
        imageMap.put("WALKING_LEFT", "/images/rat/WALKING_LEFT.gif");
        imageMap.put("WALKING_RIGHT", "/images/rat/WALKING_RIGHT.gif");
        imageMap.put("WALKING_UP", "/images/rat/WALKING_RIGHT.gif");
        rat.setImages(imageMap);
        return rat;
    }

    public static GameCharacter createZombie(Point location, int scale) {
        final GameCharacter zombie = new GameCharacter(location);
        zombie.setScale(scale);
        final Map<String, String> imageMap = new HashMap<>();
        imageMap.put("STOPPED_DOWN", "/images/zombie/STOPPED_DOWN.gif");
        imageMap.put("STOPPED_UP", "/images/zombie/STOPPED_UP.gif");
        zombie.setImages(imageMap);
        return zombie;
    }
}
