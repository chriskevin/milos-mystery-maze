package se.chriskevin.mysterymaze.environment.utils;

import se.chriskevin.mysterymaze.animation.AnimationState;
import se.chriskevin.mysterymaze.animation.Direction;
import se.chriskevin.mysterymaze.behavior.MoveBehavior;
import se.chriskevin.mysterymaze.environment.GameSprite;
import se.chriskevin.mysterymaze.environment.ImageUtil;
import se.chriskevin.mysterymaze.environment.SpriteType;
import se.chriskevin.mysterymaze.geometry.Point3D;

import java.util.Map;
import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Collectors;

import static se.chriskevin.mysterymaze.environment.ImageUtil.convertToImages;
import static se.chriskevin.mysterymaze.environment.ImageUtil.resize;
import static se.chriskevin.mysterymaze.geometry.Point3D.translateY;
import static se.chriskevin.mysterymaze.utils.Common.identity;

/**
 * Created by Chris Sundberg on 2016-03-08.
 */
public final class GameCharacterFactory {

    public static Optional<GameSprite> createEnemy(Long enemyNo, Point3D currentLocation, Long scale) {
        switch (enemyNo.intValue()) {
            case 1:
                return Optional.of(createBat(scale, currentLocation));
            case 2:
                return Optional.of(createRat(scale, currentLocation));
            case 3:
                return Optional.of(createZombie(scale, translateY.apply(-(14L * scale), currentLocation)));
        }

        return Optional.empty();
    }

    public static GameSprite createHero(Long scale, Point3D location) {
        final Map<String, String> imageMap = new HashMap<>();
        imageMap.put("STOPPED_DOWN", "/images/milo/STOPPED_DOWN.gif");
        imageMap.put("STOPPED_LEFT", "/images/milo/STOPPED_LEFT.gif");
        imageMap.put("STOPPED_RIGHT", "/images/milo/STOPPED_RIGHT.gif");
        imageMap.put("STOPPED_UP", "/images/milo/STOPPED_UP.gif");
        imageMap.put("WALKING_DOWN", "/images/milo/WALKING_DOWN.gif");
        imageMap.put("WALKING_LEFT", "/images/milo/WALKING_LEFT.gif");
        imageMap.put("WALKING_RIGHT", "/images/milo/WALKING_RIGHT.gif");
        imageMap.put("WALKING_UP", "/images/milo/WALKING_UP.gif");
        return new GameSprite(
            SpriteType.PLAYER,
            scale,
            false,
            location,
            8L,
            Direction.DOWN,
            false,
            null,
            convertToImages.apply(imageMap)
                .entrySet()
                .stream()
                .filter(x -> x.getValue().isPresent())
                .map(x -> resize.apply(scale.intValue()))
                .collect(Collectors.toMap(
                    Map.Entry::getKey,
                    identity
                )
            )),
            AnimationState.STOPPED
        );
    }

    public static GameSprite createBat(Long scale, Point3D location) {
        final Map<String, String> imageMap = new HashMap<>();
        imageMap.put("STOPPED_DOWN", "/images/bat/WALKING_DOWN.gif");
        imageMap.put("STOPPED_LEFT", "/images/bat/WALKING_LEFT.gif");
        imageMap.put("STOPPED_RIGHT", "/images/bat/WALKING_RIGHT.gif");
        imageMap.put("STOPPED_UP", "/images/bat/WALKING_UP.gif");
        imageMap.put("WALKING_DOWN", "/images/bat/WALKING_DOWN.gif");
        imageMap.put("WALKING_LEFT", "/images/bat/WALKING_LEFT.gif");
        imageMap.put("WALKING_RIGHT", "/images/bat/WALKING_RIGHT.gif");
        imageMap.put("WALKING_UP", "/images/bat/WALKING_UP.gif");
        return new GameSprite(SpriteType.ENEMY, scale, false, location, 8, Direction.DOWN, false, MoveBehavior.HORIZONTAL_MOVEMENT, convertToImages.apply(imageMap, scale), AnimationState.WALKING);
    }

    public static GameSprite createRat(Long scale, Point3D location) {
        final Map<String, String> imageMap = new HashMap<>();
        imageMap.put("STOPPED_DOWN", "/images/rat/STOPPED_LEFT.gif");
        imageMap.put("STOPPED_LEFT", "/images/rat/STOPPED_LEFT.gif");
        imageMap.put("STOPPED_RIGHT", "/images/rat/STOPPED_RIGHT.gif");
        imageMap.put("STOPPED_UP", "/images/rat/STOPPED_LEFT.gif");
        imageMap.put("WALKING_DOWN", "/images/rat/WALKING_LEFT.gif");
        imageMap.put("WALKING_LEFT", "/images/rat/WALKING_LEFT.gif");
        imageMap.put("WALKING_RIGHT", "/images/rat/WALKING_RIGHT.gif");
        imageMap.put("WALKING_UP", "/images/rat/WALKING_RIGHT.gif");
        return new GameSprite(SpriteType.ENEMY, scale, false, location, 8, Direction.DOWN, false, null, convertToImages.apply(imageMap, scale), AnimationState.STOPPED);
    }

    public static GameSprite createTile(Long scale, Point3D currentLocation, Boolean blocking, String imageFilename) {
        final Map<String, String> imageMap = new HashMap<>();
        imageMap.put("STOPPED_DOWN", imageFilename);
        return new GameSprite(SpriteType.TILE, scale, blocking, currentLocation, 0, Direction.DOWN, false, null, convertToImages.apply(imageMap, scale), AnimationState.STOPPED);
    }

    public static GameSprite createZombie(Long scale, Point3D location) {
        final Map<String, String> imageMap = new HashMap<>();
        imageMap.put("STOPPED_DOWN", "/images/zombie/STOPPED_DOWN.gif");
        imageMap.put("STOPPED_UP", "/images/zombie/STOPPED_UP.gif");
        return new GameSprite(SpriteType.ENEMY, scale, false, location, 8, Direction.DOWN, false, MoveBehavior.MOVE_UP, convertToImages.apply(imageMap, scale), AnimationState.STOPPED);
    }
}
