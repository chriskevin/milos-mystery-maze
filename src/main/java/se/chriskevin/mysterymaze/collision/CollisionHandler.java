package se.chriskevin.mysterymaze.collision;

import static se.chriskevin.mysterymaze.behavior.MoveBehavior.bounceBack;
import static se.chriskevin.mysterymaze.environment.utils.GameSpriteUtil.getByType;
import static se.chriskevin.mysterymaze.environment.utils.GameSpriteUtil.getPlayer;

import io.vavr.collection.List;
import se.chriskevin.mysterymaze.environment.GameSprite;
import se.chriskevin.mysterymaze.environment.SpriteType;

public final class CollisionHandler {

  public static Boolean isColliding(final GameSprite a, final GameSprite b) {
    return a.getBounds().intersects(b.getBounds());
  }

  public static GameSprite checkAndHandleCollision(final GameSprite a, final GameSprite b) {
    return (!a.equals(b) && isColliding(a, b)) ? bounceBack.apply(a) : a;
  }

  public static void setCollisions(List<GameSprite> sprites) {
    getByType(SpriteType.TILE, sprites)
        .filter(x -> x.blocking)
        .forEach(
            tile -> {
              checkAndHandleCollision(getPlayer(sprites), tile);
              getByType(SpriteType.ENEMY, sprites).map(x -> checkAndHandleCollision(tile, x));
            });

    // Enemy checks
    getByType(SpriteType.ENEMY, sprites)
        .forEach(
            enemyA -> {
              checkAndHandleCollision(getPlayer(sprites), enemyA);
              getByType(SpriteType.ENEMY, sprites).map(x -> checkAndHandleCollision(enemyA, x));
            });
  }

  public static Boolean checkCollision(final GameSprite a, final GameSprite b) {
    while (!a.equals(b) && isColliding(a, b)) {
      bounceBack.apply(a);
      a.colliding = isColliding(a, b);
      b.colliding = isColliding(a, b);
    }
    return true;
  }
}
