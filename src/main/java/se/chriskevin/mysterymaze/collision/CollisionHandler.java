package se.chriskevin.mysterymaze.collision;

import com.google.common.base.Supplier;
import com.sun.org.apache.xpath.internal.operations.Bool;
import io.vavr.Function2;
import io.vavr.Tuple2;
import se.chriskevin.mysterymaze.behavior.MoveBehavior;
import se.chriskevin.mysterymaze.environment.GameSprite;
import se.chriskevin.mysterymaze.environment.SpriteType;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

import static se.chriskevin.mysterymaze.environment.utils.GameSpriteUtil.getByType;
import static se.chriskevin.mysterymaze.environment.utils.GameSpriteUtil.getPlayer;

/**
 * Created by Chris Sundberg on 2017-06-11.
 */
public final class CollisionHandler {

    public static final Function2<GameSprite, GameSprite, Boolean> isColliding =
        (a, b) -> a.getBounds().intersects(b.getBounds());

    public static final Function2<GameSprite, GameSprite, GameSprite> checkAndHandleCollision =
            (a, b) -> (!a.equals(b) && isColliding.apply(a, b)) ? MoveBehavior.BOUNCE_BACK.execute(a) : a;

    public static final Consumer<List<GameSprite>> setCollisions = sprites -> {
        getByType.apply(SpriteType.TILE, sprites)
            .stream()
            .filter(x -> x.blocking)
            .forEach(tile -> {
                checkAndHandleCollision.apply(getPlayer.apply(sprites)).apply(tile);
                getByType.apply(SpriteType.ENEMY).apply(sprites).map(checkAndHandleCollision.apply(tile));
            });

        // Enemy checks
        getByType.apply(SpriteType.ENEMY).apply(sprites).forEach(enemyA -> {
            checkAndHandleCollision.apply(getPlayer.apply(sprites), enemyA);
            getByType.apply(SpriteType.ENEMY, sprites).map(checkAndHandleCollision.apply(enemyA));
        });
    };

    public static final Function2<GameSprite, GameSprite, Boolean> checkCollision =
        (a, b) -> {
            while (!a.equals(b) && isColliding.apply(a).apply(b)) {
                MoveBehavior.BOUNCE_BACK.execute(a);
                a.colliding = isColliding.apply(a).apply(b);
                b.colliding = isColliding.apply(a).apply(b);
            }
        };
}
