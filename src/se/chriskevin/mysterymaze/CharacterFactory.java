package se.chriskevin.mysterymaze;

import se.chriskevin.mysterymaze.behavior.MoveBehavior;

import java.util.Map;
import java.util.HashMap;

/**
 * Created by CHSU7648 on 2016-03-08.
 */
public class CharacterFactory {

    public static Character createHero(int x, int y) {
        final Character hero = new Character(x, y);
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

    public static Character createBat(int x, int y) {
        final Character bat = new Character(x, y);
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

    public static Character createRat(int x, int y) {
        final Character hero = new Character(x, y);
        final Map<String, String> imageMap = new HashMap<>();
        imageMap.put("STOPPED_DOWN", "/images/rat/STOPPED_LEFT.gif");
        imageMap.put("STOPPED_LEFT", "/images/rat/STOPPED_LEFT.gif");
        imageMap.put("STOPPED_RIGHT", "/images/rat/STOPPED_RIGHT.gif");
        imageMap.put("STOPPED_UP", "/images/rat/STOPPED_LEFT.gif");
        imageMap.put("WALKING_DOWN", "/images/rat/WALKING_LEFT.gif");
        imageMap.put("WALKING_LEFT", "/images/rat/WALKING_LEFT.gif");
        imageMap.put("WALKING_RIGHT", "/images/rat/WALKING_RIGHT.gif");
        imageMap.put("WALKING_UP", "/images/rat/WALKING_RIGHT.gif");
        hero.setImages(imageMap);
        return hero;
    }

    public static Character createZombie(int x, int y) {
        final Character hero = new Character(x, y);
        final Map<String, String> imageMap = new HashMap<>();
        imageMap.put("STOPPED_DOWN", "/images/zombie/STOPPED_DOWN.gif");
        imageMap.put("STOPPED_UP", "/images/zombie/STOPPED_UP.gif");
        hero.setImages(imageMap);
        return hero;
    }
}
