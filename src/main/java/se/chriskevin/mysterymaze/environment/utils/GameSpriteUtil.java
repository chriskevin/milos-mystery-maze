package se.chriskevin.mysterymaze.environment.utils;

import io.vavr.collection.List;
import se.chriskevin.mysterymaze.environment.GameSprite;
import se.chriskevin.mysterymaze.environment.SpriteType;

public class GameSpriteUtil {

    public static final List<GameSprite> getByType(SpriteType type, List<GameSprite> sprites) {
        return sprites.filter(x -> type.equals(x.type));
    }

    public static final GameSprite getPlayer(List<GameSprite> sprites) {
        return getByType(SpriteType.PLAYER, sprites).get(0);
    }
}
