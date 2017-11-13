package se.chriskevin.mysterymaze.environment.utils;

import io.vavr.collection.HashMap;
import io.vavr.collection.Map;
import se.chriskevin.mysterymaze.animation.AnimationState;
import se.chriskevin.mysterymaze.animation.Direction;
import se.chriskevin.mysterymaze.behavior.MoveBehavior;
import se.chriskevin.mysterymaze.environment.GameSprite;
import se.chriskevin.mysterymaze.environment.SpriteType;
import se.chriskevin.mysterymaze.geometry.Point3D;

import java.awt.*;
import java.util.Optional;
import java.util.stream.Collectors;

import static se.chriskevin.mysterymaze.environment.ImageUtil.convertToImages;
import static se.chriskevin.mysterymaze.environment.ImageUtil.getSizeFromImageDimensions;
import static se.chriskevin.mysterymaze.environment.ImageUtil.resize;
import static se.chriskevin.mysterymaze.geometry.Point3D.translateY;

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

        final AnimationState animationState = AnimationState.STOPPED;
        final Direction direction = Direction.DOWN;
        final Map<String, Image> images = convertToImages.apply(imageMap)
                .filterValues(x -> x.isDefined())
                .mapValues(x -> resize.apply(scale.intValue(), x.get()));

        return new GameSprite(
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
            getSizeFromImageDimensions.apply(animationState, direction, images)
        );
    }

    public static GameSprite createBat(Long scale, Point3D location) {
        final Map<String, String> imageMap = HashMap.of(
            "STOPPED_DOWN", "/images/bat/WALKING_DOWN.gif",
            "STOPPED_LEFT", "/images/bat/WALKING_LEFT.gif",
            "STOPPED_RIGHT", "/images/bat/WALKING_RIGHT.gif",
            "STOPPED_UP", "/images/bat/WALKING_UP.gif",
            "WALKING_DOWN", "/images/bat/WALKING_DOWN.gif",
            "WALKING_LEFT", "/images/bat/WALKING_LEFT.gif",
            "WALKING_RIGHT", "/images/bat/WALKING_RIGHT.gif",
            "WALKING_UP", "/images/bat/WALKING_UP.gif"
        );

        final AnimationState animationState = AnimationState.WALKING;
        final Direction direction = Direction.DOWN;
        final Map<String, Image> images = convertToImages.apply(imageMap)
                .filterValues(x -> x.isDefined())
                .mapValues(x -> resize.apply(scale.intValue(), x.get()));

        return new GameSprite(
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
            getSizeFromImageDimensions.apply(animationState, direction, images)
        );
    }

    public static GameSprite createRat(Long scale, Point3D location) {
        final Map<String, String> imageMap = HashMap.of(
            "STOPPED_DOWN", "/images/rat/STOPPED_LEFT.gif",
            "STOPPED_LEFT", "/images/rat/STOPPED_LEFT.gif",
            "STOPPED_RIGHT", "/images/rat/STOPPED_RIGHT.gif",
            "STOPPED_UP", "/images/rat/STOPPED_LEFT.gif",
            "WALKING_DOWN", "/images/rat/WALKING_LEFT.gif",
            "WALKING_LEFT", "/images/rat/WALKING_LEFT.gif",
            "WALKING_RIGHT", "/images/rat/WALKING_RIGHT.gif",
            "WALKING_UP", "/images/rat/WALKING_RIGHT.gif"
        );

        final AnimationState animationState = AnimationState.STOPPED;
        final Direction direction = Direction.DOWN;
        final Map<String, Image> images = convertToImages.apply(imageMap)
                .filterValues(x -> x.isDefined())
                .mapValues(x -> resize.apply(scale.intValue(), x.get()));

        return new GameSprite(
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
            getSizeFromImageDimensions.apply(animationState, direction, images)
        );
    }

    public static GameSprite createTile(Long scale, Point3D currentLocation, Boolean blocking, String imageFilename) {
        final Map<String, String> imageMap = HashMap.of(
            "STOPPED_DOWN", imageFilename
        );

        final AnimationState animationState = AnimationState.STOPPED;
        final Direction direction = Direction.DOWN;
        final Map<String, Image> images = convertToImages.apply(imageMap)
                .filterValues(x -> x.isDefined())
                .mapValues(x -> resize.apply(scale.intValue(), x.get()));

        return new GameSprite(
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
            getSizeFromImageDimensions.apply(animationState, direction, images)
        );
    }

    public static GameSprite createZombie(Long scale, Point3D location) {
        final Map<String, String> imageMap = HashMap.of(
            "STOPPED_DOWN", "/images/zombie/STOPPED_DOWN.gif",
            "STOPPED_UP", "/images/zombie/STOPPED_UP.gif"
        );

        final AnimationState animationState = AnimationState.STOPPED;
        final Direction direction = Direction.DOWN;
        final Map<String, Image> images = convertToImages.apply(imageMap)
                .filterValues(x -> x.isDefined())
                .mapValues(x -> resize.apply(scale.intValue(), x.get()));

        return new GameSprite(
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
            getSizeFromImageDimensions.apply(animationState, direction, images)
        );
    }
}
