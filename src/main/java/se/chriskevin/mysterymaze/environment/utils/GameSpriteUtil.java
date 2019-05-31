package se.chriskevin.mysterymaze.environment.utils;

import io.vavr.collection.List;
import se.chriskevin.mysterymaze.environment.GameSprite;
import se.chriskevin.mysterymaze.environment.SpriteType;

public class GameSpriteUtil {

  public static List<GameSprite> getByType(final SpriteType type, final List<GameSprite> sprites) {
    return sprites.filter(x -> type.equals(x.type));
  }

  public static GameSprite getPlayer(final List<GameSprite> sprites) {
    return getByType(SpriteType.PLAYER, sprites).get(0);
  }
}
