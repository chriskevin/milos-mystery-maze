package se.chriskevin.mysterymaze.environment;

import se.chriskevin.mysterymaze.animation.AnimationState;
import se.chriskevin.mysterymaze.animation.Direction;
import se.chriskevin.mysterymaze.behavior.Behavior;
import se.chriskevin.mysterymaze.geometry.Dimension;
import se.chriskevin.mysterymaze.geometry.Point3D;

import java.awt.*;
import java.util.Map;
import java.util.Optional;

import static java.lang.Math.multiplyExact;

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
    public final Integer scale;
    public final Dimension<Integer> size;
    public final Integer speed;
    public final SpriteType type;

    public GameSprite(SpriteType type, Integer scale, Boolean blocking, Point3D position, Integer speed, Direction direction, Boolean colliding, Behavior behavior, Map<String, Image> images, AnimationState animationState) {
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
        this.size = getSizeFromImageDimensions(animationState, direction, images);
    }

    private Dimension<Integer> getSizeFromImageDimensions(AnimationState animationState, Direction direction, Map<String, Image> images) {
        final Image img = ImageUtil.getImage(animationState, direction, images);
        return new Dimension<>(img.getWidth(null), img.getHeight(null));
    }

    public Rectangle getBounds() {
        return new Rectangle(position.x, position.y, size.width, size.height);
    }
}
