package se.chriskevin.mysterymaze.environment.utils;

import io.vavr.collection.HashMap;
import io.vavr.collection.Map;
import io.vavr.control.Option;
import se.chriskevin.mysterymaze.animation.AnimationState;
import se.chriskevin.mysterymaze.animation.Direction;
import se.chriskevin.mysterymaze.behavior.MoveBehavior;
import se.chriskevin.mysterymaze.environment.GameSprite;
import se.chriskevin.mysterymaze.environment.SpriteType;
import se.chriskevin.mysterymaze.geometry.Point3D;

import static se.chriskevin.mysterymaze.environment.ImageUtil.convertToImages;
import static se.chriskevin.mysterymaze.environment.ImageUtil.getSizeFromImageDimensions;
import static se.chriskevin.mysterymaze.environment.ImageUtil.resize;
import static se.chriskevin.mysterymaze.geometry.Point3D.translateY;

public final class GameCharacterFactory {

    public static Option<GameSprite> createEnemy(Long enemyNo, Point3D currentLocation, Long scale) {
        switch (enemyNo.intValue()) {
            case 1:
                return Option.of(createBat(scale, currentLocation));
            case 2:
                return Option.of(createRat(scale, currentLocation));
            case 3:
                return Option.of(createZombie(scale, translateY(-(14L * scale), currentLocation)));
        }

        return Option.none();
    }

    public static GameSprite createHero(Long scale, Point3D location) {
        final Map<String, String> imageMap = HashMap.of(
            "STOPPED_DOWN", "/images/milo/STOPPED_DOWN.gif",
            "STOPPED_LEFT", "/images/milo/STOPPED_LEFT.gif",
            "STOPPED_RIGHT", "/images/milo/STOPPED_RIGHT.gif",
            "STOPPED_UP", "/images/milo/STOPPED_UP.gif",
            "WALKING_DOWN", "/images/milo/WALKING_DOWN.gif",
            "WALKING_LEFT", "/images/milo/WALKING_LEFT.gif",
            "WALKING_RIGHT", "/images/milo/WALKING_RIGHT.gif",
            "WALKING_UP", "/images/milo/WALKING_UP.gif"
        );

        var animationState = AnimationState.STOPPED;
        var direction = Direction.DOWN;
        var images = convertToImages(imageMap)
                .filterValues(Option::isDefined)
                .mapValues(x -> resize(scale, x.get()));

        return GameSprite.of(
            SpriteType.PLAYER,
            scale,
            false,
            location,
            8L,
            direction,
            false,
            null,
            images,
            animationState,
            getSizeFromImageDimensions(animationState, direction, images)
        );
    }

    public static GameSprite createBat(Long scale, Point3D location) {
        var imageMap = HashMap.of(
            "STOPPED_DOWN", "/images/bat/WALKING_DOWN.gif",
            "STOPPED_LEFT", "/images/bat/WALKING_LEFT.gif",
            "STOPPED_RIGHT", "/images/bat/WALKING_RIGHT.gif",
            "STOPPED_UP", "/images/bat/WALKING_UP.gif",
            "WALKING_DOWN", "/images/bat/WALKING_DOWN.gif",
            "WALKING_LEFT", "/images/bat/WALKING_LEFT.gif",
            "WALKING_RIGHT", "/images/bat/WALKING_RIGHT.gif",
            "WALKING_UP", "/images/bat/WALKING_UP.gif"
        );

        var animationState = AnimationState.WALKING;
        var direction = Direction.DOWN;
        var images = convertToImages(imageMap)
                .filterValues(Option::isDefined)
                .mapValues(x -> resize(scale, x.get()));

        return GameSprite.of(
            SpriteType.ENEMY,
            scale,
            false,
            location,
            8L,
            direction,
            false,
            MoveBehavior.HORIZONTAL_MOVEMENT,
            images,
            animationState,
            getSizeFromImageDimensions(animationState, direction, images)
        );
    }

    public static GameSprite createRat(Long scale, Point3D location) {
        var imageMap = HashMap.of(
            "STOPPED_DOWN", "/images/rat/STOPPED_LEFT.gif",
            "STOPPED_LEFT", "/images/rat/STOPPED_LEFT.gif",
            "STOPPED_RIGHT", "/images/rat/STOPPED_RIGHT.gif",
            "STOPPED_UP", "/images/rat/STOPPED_LEFT.gif",
            "WALKING_DOWN", "/images/rat/WALKING_LEFT.gif",
            "WALKING_LEFT", "/images/rat/WALKING_LEFT.gif",
            "WALKING_RIGHT", "/images/rat/WALKING_RIGHT.gif",
            "WALKING_UP", "/images/rat/WALKING_RIGHT.gif"
        );

        var animationState = AnimationState.STOPPED;
        var direction = Direction.DOWN;
        var images = convertToImages(imageMap)
                .filterValues(Option::isDefined)
                .mapValues(x -> resize(scale, x.get()));

        return GameSprite.of(
            SpriteType.ENEMY,
            scale,
            false,
            location,
            8L,
            direction,
            false,
            null,
            images,
            AnimationState.STOPPED,
            getSizeFromImageDimensions(animationState, direction, images)
        );
    }

    public static GameSprite createTile(Long scale, Point3D currentLocation, Boolean blocking, String imageFilename) {
        var imageMap = HashMap.of(
            "STOPPED_DOWN", imageFilename
        );

        var animationState = AnimationState.STOPPED;
        var direction = Direction.DOWN;
        var images = convertToImages(imageMap)
                .filterValues(Option::isDefined)
                .mapValues(Option::get)
                .mapValues(x -> resize(scale, x));

        return GameSprite.of(
            SpriteType.TILE,
            scale,
            blocking,
            currentLocation,
            0L,
            direction,
            false,
            null,
            images,
            AnimationState.STOPPED,
            getSizeFromImageDimensions(animationState, direction, images)
        );
    }

    public static GameSprite createZombie(Long scale, Point3D location) {
        var imageMap = HashMap.of(
            "STOPPED_DOWN", "/images/zombie/STOPPED_DOWN.gif",
            "STOPPED_UP", "/images/zombie/STOPPED_UP.gif"
        );

        var animationState = AnimationState.STOPPED;
        var direction = Direction.DOWN;
        var images = convertToImages(imageMap)
                .filterValues(Option::isDefined)
                .mapValues(x -> resize(scale, x.get()));

        return GameSprite.of(
            SpriteType.ENEMY,
            scale,
            false,
            location,
            8L,
            direction,
            false,
            MoveBehavior.MOVE_UP,
            images,
            AnimationState.STOPPED,
            getSizeFromImageDimensions(animationState, direction, images)
        );
    }
}
