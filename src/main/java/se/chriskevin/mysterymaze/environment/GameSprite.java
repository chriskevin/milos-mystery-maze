package se.chriskevin.mysterymaze.environment;

import io.vavr.collection.Map;
import io.vavr.control.Option;
import se.chriskevin.mysterymaze.animation.AnimationState;
import se.chriskevin.mysterymaze.animation.Direction;
import se.chriskevin.mysterymaze.behavior.Behavior;
import se.chriskevin.mysterymaze.geometry.Dimension;
import se.chriskevin.mysterymaze.geometry.Point3D;

import java.awt.*;

import static java.lang.Math.multiplyExact;

/**
 * Created by Chris Sundberg on 2016-03-12.
 */
public class GameSprite {

    public Boolean blocking;
    public Boolean colliding;

    public final AnimationState animationState;
    public final Option<Behavior> behavior;
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
        AnimationState animationState,
        Dimension size
    ) {
        this.type = type;
        this.position = position;
        this.scale = scale;
        this.animationState = animationState;
        this.blocking = blocking;
        this.colliding = colliding;
        this.direction = direction;
        this.speed = multiplyExact(speed, scale);
        this.behavior = Option.of(behavior);
        this.images = images;
        this.size = size;
    }

    public Rectangle getBounds() {
        return new Rectangle(position.x.intValue(), position.y.intValue(), size.width.intValue(), size.height.intValue());
    }
}
