package se.chriskevin.mysterymaze.environment;

import se.chriskevin.mysterymaze.geometry.Dimension;

import java.util.*;
import java.util.List;
import java.util.function.Function;

import static se.chriskevin.mysterymaze.BoardGenerator.createLevel;
import static se.chriskevin.mysterymaze.geometry.Dimension.ZERO_DIMENSION;
import static se.chriskevin.mysterymaze.utils.Calculation.multiply;

public class GameEnvironment {

    private final static Long TILE_WIDTH = 32L;
    private final static Long SCALE = 2L;

    public final Dimension size;
    public final List<GameSprite> sprites;

    public static final Function<Optional<List<String>>, GameEnvironment> createEnvironment =
        mapData -> mapData
            .map(x -> new GameEnvironment(
                new Dimension(
                    multiply.apply(Long.valueOf(x.get(0).split("|").length), TILE_WIDTH),
                    multiply.apply(Long.valueOf(x.size()), TILE_WIDTH)
                ),
                createLevel.apply(SCALE, TILE_WIDTH, x)
            ))
            .orElse(new GameEnvironment(ZERO_DIMENSION, new ArrayList<>()));

    private GameEnvironment(Dimension size, List<GameSprite> sprites) {
        this.size = size;
        this.sprites = sprites;
    }
}