package se.chriskevin.mysterymaze.collision;

import io.vavr.Function2;
import io.vavr.collection.List;
import se.chriskevin.mysterymaze.behavior.MoveBehavior;
import se.chriskevin.mysterymaze.environment.GameSprite;
import se.chriskevin.mysterymaze.environment.SpriteType;

import java.util.function.Consumer;

import static se.chriskevin.mysterymaze.environment.utils.GameSpriteUtil.getByType;
import static se.chriskevin.mysterymaze.environment.utils.GameSpriteUtil.getPlayer;

public final class CollisionHandler {

    public static final Function2<GameSprite, GameSprite, Boolean> isColliding =
        (a, b) -> a.getBounds().intersects(b.getBounds());

    public static final Function2<GameSprite, GameSprite, GameSprite> checkAndHandleCollision =
            (a, b) -> (!a.equals(b) && isColliding.apply(a, b)) ? MoveBehavior.BOUNCE_BACK.execute(a) : a;

    public static final Consumer<List<GameSprite>> setCollisions = sprites -> {
        getByType.apply(SpriteType.TILE, sprites)
            .filter(x -> x.blocking)
            .forEach(tile -> {
                checkAndHandleCollision.apply(getPlayer.apply(sprites)).apply(tile);
                getByType.apply(SpriteType.ENEMY, sprites).map(checkAndHandleCollision.apply(tile));
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
            return true;
        };
}
