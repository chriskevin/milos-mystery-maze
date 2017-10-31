package se.chriskevin.mysterymaze.environment;

import io.vavr.Function3;
import se.chriskevin.mysterymaze.animation.AnimationState;
import se.chriskevin.mysterymaze.animation.Direction;
import se.chriskevin.mysterymaze.behavior.Behavior;
import se.chriskevin.mysterymaze.geometry.Dimension;
import se.chriskevin.mysterymaze.geometry.Point3D;

import java.awt.*;
import java.util.Map;
import java.util.Optional;

import static java.lang.Math.multiplyExact;
import static se.chriskevin.mysterymaze.environment.ImageUtil.getImage;
import static se.chriskevin.mysterymaze.environment.ImageUtil.imageMapKey;
import static se.chriskevin.mysterymaze.geometry.Dimension.ZERO_DIMENSION;

/**
 * Created by Chris Sundberg on 2016-03-12.
 */
public class GameSprite {

    public Boolean blocking;
    public Boolean colliding;

    public final AnimationState animationState;
    public final Optional<Behavior> behavior;
    public final Direction direction;
    public final Map<String, Image> images;
    public final Point3D position;
    public final Long scale;
    public final Dimension size;
    public final Long speed;
    public final SpriteType type;

    public GameSprite(
        SpriteType type,
        Long scale,
        Boolean blocking,
        Point3D position,
        Long speed,
        Direction direction,
        Boolean colliding,
        Behavior behavior,
        Map<String, Image> images,
        AnimationState animationState
    ) {
        this.type = type;
        this.position = position;
        this.scale = scale;
        this.animationState = animationState;
        this.blocking = blocking;
        this.colliding = colliding;
        this.direction = direction;
        this.speed = multiplyExact(speed, scale);
        this.behavior = Optional.ofNullable(behavior);
        this.images = images;
        this.size = getSizeFromImageDimensions.apply(animationState).apply(direction).apply(images);
    }

    private static Function3<AnimationState, Direction, Map<String, Image>, Dimension> getSizeFromImageDimensions =
        (animationState, direction, images) ->
            Optional.ofNullable(getImage.apply(imageMapKey.apply(animationState, direction), images))
                .map(x -> new Dimension(Long.valueOf(x.getWidth(null)), Long.valueOf(x.getHeight(null))))
                .orElse(ZERO_DIMENSION);

    public Rectangle getBounds() {
        return new Rectangle(position.x.intValue(), position.y.intValue(), size.width.intValue(), size.height.intValue());
    }
}
