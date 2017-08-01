package se.chriskevin.mysterymaze.environment.utils;

import se.chriskevin.mysterymaze.animation.AnimationState;
import se.chriskevin.mysterymaze.behavior.MoveBehavior;
import se.chriskevin.mysterymaze.environment.GameSprite;
import se.chriskevin.mysterymaze.environment.ImageUtil;
import se.chriskevin.mysterymaze.environment.SpriteType;
import se.chriskevin.mysterymaze.geometry.Point3D;

import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

/**
 * Created by Chris Sundberg on 2016-03-08.
 */
public final class GameCharacterFactory {

    public static Optional<GameSprite> createEnemy(SpriteType type, Integer enemyNo, Point3D currentLocation, Integer scale) {
        switch (enemyNo) {
            case 1:
                return Optional.of(createBat(type, scale, currentLocation));
            case 2:
                return Optional.of(createRat(type, scale, currentLocation));
            case 3:
                return Optional.of(createZombie(type, scale, new Point3D(currentLocation.x, currentLocation.y - (14 * scale), 0)));
        }

        return Optional.empty();
    }

    public static GameSprite createHero(SpriteType type, Integer scale, Point3D location) {
        final Map<String, String> imageMap = new HashMap<>();
        imageMap.put("STOPPED_DOWN", "/images/milo/STOPPED_DOWN.gif");
        imageMap.put("STOPPED_LEFT", "/images/milo/STOPPED_LEFT.gif");
        imageMap.put("STOPPED_RIGHT", "/images/milo/STOPPED_RIGHT.gif");
        imageMap.put("STOPPED_UP", "/images/milo/STOPPED_UP.gif");
        imageMap.put("WALKING_DOWN", "/images/milo/WALKING_DOWN.gif");
        imageMap.put("WALKING_LEFT", "/images/milo/WALKING_LEFT.gif");
        imageMap.put("WALKING_RIGHT", "/images/milo/WALKING_RIGHT.gif");
        imageMap.put("WALKING_UP", "/images/milo/WALKING_UP.gif");
        return new GameSprite(type, scale, false, location, 8, null, false, null, ImageUtil.convertToImages(imageMap, scale), null);
    }

    public static GameSprite createBat(SpriteType type, Integer scale, Point3D location) {
        final Map<String, String> imageMap = new HashMap<>();
        imageMap.put("STOPPED_DOWN", "/images/bat/WALKING_DOWN.gif");
        imageMap.put("STOPPED_LEFT", "/images/bat/WALKING_LEFT.gif");
        imageMap.put("STOPPED_RIGHT", "/images/bat/WALKING_RIGHT.gif");
        imageMap.put("STOPPED_UP", "/images/bat/WALKING_UP.gif");
        imageMap.put("WALKING_DOWN", "/images/bat/WALKING_DOWN.gif");
        imageMap.put("WALKING_LEFT", "/images/bat/WALKING_LEFT.gif");
        imageMap.put("WALKING_RIGHT", "/images/bat/WALKING_RIGHT.gif");
        imageMap.put("WALKING_UP", "/images/bat/WALKING_UP.gif");
        return new GameSprite(type, scale, false, location, 8, null, false, MoveBehavior.HORIZONTAL_MOVEMENT, ImageUtil.convertToImages(imageMap, scale), AnimationState.WALKING);
    }

    public static GameSprite createRat(SpriteType type, Integer scale, Point3D location) {
        final Map<String, String> imageMap = new HashMap<>();
        imageMap.put("STOPPED_DOWN", "/images/rat/STOPPED_LEFT.gif");
        imageMap.put("STOPPED_LEFT", "/images/rat/STOPPED_LEFT.gif");
        imageMap.put("STOPPED_RIGHT", "/images/rat/STOPPED_RIGHT.gif");
        imageMap.put("STOPPED_UP", "/images/rat/STOPPED_LEFT.gif");
        imageMap.put("WALKING_DOWN", "/images/rat/WALKING_LEFT.gif");
        imageMap.put("WALKING_LEFT", "/images/rat/WALKING_LEFT.gif");
        imageMap.put("WALKING_RIGHT", "/images/rat/WALKING_RIGHT.gif");
        imageMap.put("WALKING_UP", "/images/rat/WALKING_RIGHT.gif");
        return new GameSprite(type, scale, false, location, 8, null, false, null, ImageUtil.convertToImages(imageMap, scale), null);
    }

    public static GameSprite createTile(SpriteType type, Integer scale, Point3D currentLocation, Boolean blocking, String imageFilename) {
        final Map<String, String> imageMap = new HashMap<>();
        imageMap.put("STOPPED_DOWN", imageFilename);
        return new GameSprite(type, scale, blocking, currentLocation, 0, null, false, null, ImageUtil.convertToImages(imageMap, scale), null);
    }

    public static GameSprite createZombie(SpriteType type, Integer scale, Point3D location) {
        final Map<String, String> imageMap = new HashMap<>();
        imageMap.put("STOPPED_DOWN", "/images/zombie/STOPPED_DOWN.gif");
        imageMap.put("STOPPED_UP", "/images/zombie/STOPPED_UP.gif");
        return new GameSprite(type, scale, false, location, 8, null, false, MoveBehavior.MOVE_UP, ImageUtil.convertToImages(imageMap, scale), null);
    }
}
