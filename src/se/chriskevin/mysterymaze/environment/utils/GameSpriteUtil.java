package se.chriskevin.mysterymaze.environment.utils;

import se.chriskevin.mysterymaze.environment.GameSprite;
import se.chriskevin.mysterymaze.environment.SpriteType;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class GameSpriteUtil {

    public static List<GameSprite> getByType(SpriteType type, List<GameSprite> sprites) {
        return sprites.stream().filter(x -> type.equals(x.type)).collect(toList());
    }

    public static GameSprite getPlayer(List<GameSprite> sprites) {
        return getByType(SpriteType.PLAYER, sprites).get(0);
    }
}
