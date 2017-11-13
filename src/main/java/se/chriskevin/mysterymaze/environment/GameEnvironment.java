package se.chriskevin.mysterymaze.environment;

import io.vavr.Function1;
import io.vavr.Function2;
import io.vavr.collection.List;
import io.vavr.control.Option;
import se.chriskevin.mysterymaze.geometry.Dimension;

import static se.chriskevin.mysterymaze.BoardGenerator.createLevel;
import static se.chriskevin.mysterymaze.geometry.Dimension.ZERO_DIMENSION;
import static se.chriskevin.mysterymaze.geometry.Dimension.dimension;
import static se.chriskevin.mysterymaze.utils.Calculation.multiply;

public class GameEnvironment {

    private final static Long TILE_WIDTH = 32L;
    private final static Long SCALE = 2L;

    public final Dimension size;
    public final List<GameSprite> sprites;

    public static final Function2<Dimension, List<GameSprite>, GameEnvironment> gameEnvironment =
        (size, sprites) -> new GameEnvironment(size, sprites);

    public static final Function1<List<String>, GameEnvironment> createEnvironment =
        mapData -> Option.of(mapData)
            .map(x -> gameEnvironment.apply(
                dimension.apply(
                    multiply.apply(Long.valueOf(x.get(0).split("|").length), TILE_WIDTH),
                    multiply.apply(Long.valueOf(x.size()), TILE_WIDTH)
                ),
                createLevel.apply(SCALE, TILE_WIDTH, x)
            ))
            .getOrElse(gameEnvironment.apply(ZERO_DIMENSION, List.empty()));

    private GameEnvironment(Dimension size, List<GameSprite> sprites) {
        this.size = size;
        this.sprites = sprites;
    }
}