package se.chriskevin.mysterymaze.environment.utils;

import se.chriskevin.mysterymaze.animation.AnimationState;
import se.chriskevin.mysterymaze.behavior.MoveBehavior;
import se.chriskevin.mysterymaze.environment.GameSprite;

import java.awt.*;
import java.util.Map;
import java.util.HashMap;

/**
 * Created by CHSU7648 on 2016-03-08.
 */
public class GameCharacterFactory {

    public static GameSprite createHero(Point location, int scale) {
        final GameSprite hero = new GameSprite(location);
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

    public static GameSprite createBat(Point location, int scale) {
        final GameSprite bat = new GameSprite(location);
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
        bat.setBehavior(MoveBehavior.HORIZONTAL_MOVEMENT);
        return bat;
    }

    public static GameSprite createRat(Point location, int scale) {
        final GameSprite rat = new GameSprite(location);
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

    public static GameSprite createZombie(Point location, int scale) {
        final GameSprite zombie = new GameSprite(location);
        zombie.setScale(scale);
        final Map<String, String> imageMap = new HashMap<>();
        imageMap.put("STOPPED_DOWN", "/images/zombie/STOPPED_DOWN.gif");
        imageMap.put("STOPPED_UP", "/images/zombie/STOPPED_UP.gif");
        zombie.setImages(imageMap);
        zombie.setBehavior(MoveBehavior.MOVE_UP);
        return zombie;
    }
}
