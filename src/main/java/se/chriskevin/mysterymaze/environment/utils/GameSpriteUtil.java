package se.chriskevin.mysterymaze.environment.utils;

import io.vavr.Function1;
import io.vavr.Function2;
import io.vavr.collection.List;
import se.chriskevin.mysterymaze.environment.GameSprite;
import se.chriskevin.mysterymaze.environment.SpriteType;

public class GameSpriteUtil {

    public static final Function2<SpriteType, List<GameSprite>, List<GameSprite>> getByType =
        (type, sprites) -> sprites.filter(x -> type.equals(x.type));

    public static final Function1<List<GameSprite>, GameSprite> getPlayer =
        sprites -> getByType.apply(SpriteType.PLAYER, sprites).get(0);
}
