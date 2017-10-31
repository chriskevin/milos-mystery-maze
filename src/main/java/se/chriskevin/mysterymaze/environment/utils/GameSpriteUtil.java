package se.chriskevin.mysterymaze.environment.utils;

import io.vavr.Function2;
import se.chriskevin.mysterymaze.environment.GameSprite;
import se.chriskevin.mysterymaze.environment.SpriteType;

import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

public class GameSpriteUtil {

    public static final Function2<SpriteType, List<GameSprite>, List<GameSprite>> getByType =
        (type, sprites) -> sprites.stream().filter(x -> type.equals(x.type)).collect(toList());

    public static final Function<List<GameSprite>, GameSprite> getPlayer =
        sprites -> getByType.apply(SpriteType.PLAYER, sprites).get(0);
}
