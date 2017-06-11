package se.chriskevin.mysterymaze.collision;

import se.chriskevin.mysterymaze.behavior.MoveBehavior;
import se.chriskevin.mysterymaze.environment.GameSprite;

import java.util.List;
import java.util.Map;

/**
 * Created by chsu7648 on 2017-06-11.
 */
public final class CollisionHandler {

    public static boolean isColliding(GameSprite a, GameSprite b) {
        return a.getBounds().intersects(b.getBounds());
    }

    public static void setCollisions(Map<String, List<GameSprite>> sprites) {
        sprites.get("TILE").forEach(tile -> {
            if (tile.isBlocking()) {
                sprites.get("PLAYER").forEach(player -> checkAndHandleCollision(player, tile));
                sprites.get("ENEMY").forEach(enemy -> checkAndHandleCollision(enemy, tile));
            }
        });

        // Enemy checks
        sprites.get("ENEMY").forEach(enemyA -> {
            sprites.get("PLAYER").forEach(player -> checkAndHandleCollision(player, enemyA));
            sprites.get("ENEMY").forEach(enemyB -> checkAndHandleCollision(enemyB, enemyA));
        });
    }

    public static void checkAndHandleCollision(GameSprite a, GameSprite b) {
        while (!a.equals(b) && CollisionHandler.isColliding(a, b)) {
            a.act(MoveBehavior.BOUNCE_BACK);
            a.isColliding(CollisionHandler.isColliding(a, b));
            b.isColliding(CollisionHandler.isColliding(a, b));
        }
    }
}
