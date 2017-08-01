package se.chriskevin.mysterymaze.collision;

import se.chriskevin.mysterymaze.behavior.MoveBehavior;
import se.chriskevin.mysterymaze.environment.GameSprite;
import se.chriskevin.mysterymaze.environment.SpriteType;

import java.util.List;
import java.util.Map;

import static se.chriskevin.mysterymaze.environment.utils.GameSpriteUtil.getByType;
import static se.chriskevin.mysterymaze.environment.utils.GameSpriteUtil.getPlayer;

/**
 * Created by Chris Sundberg on 2017-06-11.
 */
public final class CollisionHandler {

    public static Boolean isColliding(GameSprite a, GameSprite b) {
        return a.getBounds().intersects(b.getBounds());
    }

    public static void setCollisions(List<GameSprite> sprites) {
        getByType(SpriteType.TILE, sprites)
            .stream()
            .filter(x -> x.blocking)
            .forEach(tile -> {
                checkAndHandleCollision(getPlayer(sprites), tile);
                getByType(SpriteType.ENEMY, sprites).forEach(enemy -> checkAndHandleCollision(enemy, tile));
            });

        // Enemy checks
        getByType(SpriteType.ENEMY, sprites).forEach(enemyA -> {
            checkAndHandleCollision(getPlayer(sprites), enemyA);
            getByType(SpriteType.ENEMY, sprites).forEach(enemyB -> checkAndHandleCollision(enemyB, enemyA));
        });
    }

    public static void checkAndHandleCollision(GameSprite a, GameSprite b) {
        while (!a.equals(b) && CollisionHandler.isColliding(a, b)) {
            MoveBehavior.BOUNCE_BACK.execute(a);
            a.colliding = CollisionHandler.isColliding(a, b);
            b.colliding = CollisionHandler.isColliding(a, b);
        }
    }
}
