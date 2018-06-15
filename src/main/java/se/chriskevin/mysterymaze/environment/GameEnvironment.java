package se.chriskevin.mysterymaze.environment;

import io.vavr.Function1;
import io.vavr.Function2;
import io.vavr.collection.List;
import se.chriskevin.mysterymaze.geometry.Dimension;

import static se.chriskevin.mysterymaze.BoardGenerator.createLevel;
import static se.chriskevin.mysterymaze.geometry.Dimension.ZERO_DIMENSION;
import static se.chriskevin.mysterymaze.utils.Calculation.multiply;

public class GameEnvironment {

    private static final Long TILE_WIDTH = 32L;
    private static final Long SCALE = 2L;
    private static final GameEnvironment EMPTY_GAME_ENVIRONMENT = of(ZERO_DIMENSION, List.empty());

    public final Dimension size;
    public final List<GameSprite> sprites;

    public static final Function2<Dimension, List<GameSprite>, GameEnvironment> gameEnvironment =
        (size, sprites) -> of(size, sprites);

    public static final Function1<List<String>, GameEnvironment> createEnvironment =
        mapData -> mapData.isEmpty() ?
                EMPTY_GAME_ENVIRONMENT :
                of(
                    Dimension.of(
                        multiply.apply(Long.valueOf(mapData.get(0).split("|").length), TILE_WIDTH),
                        multiply.apply(Long.valueOf(mapData.size()), TILE_WIDTH)
                    ),
                    createLevel.apply(SCALE, TILE_WIDTH, mapData)
                );

    private GameEnvironment(Dimension size, List<GameSprite> sprites) {
        this.size = size;
        this.sprites = sprites;
    }

    public static final GameEnvironment of(Dimension size, List<GameSprite> sprites) {
        return new GameEnvironment(size, sprites);
    }

    public Boolean isEmpty() {
        return size.equals(ZERO_DIMENSION) || sprites.isEmpty();
    }
}