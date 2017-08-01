package se.chriskevin.mysterymaze.environment;

import se.chriskevin.mysterymaze.BoardGenerator;
import se.chriskevin.mysterymaze.geometry.Dimension;

import java.util.*;
import java.util.List;

import static java.lang.Math.multiplyExact;

public class GameEnvironment {

    private final static int TILE_WIDTH = 32;
    private final static int ZOOM_FACTOR = 2;

    public final Dimension size;
    public final List<GameSprite> sprites;

    public GameEnvironment() {
        final Optional<List<String>> mapData = BoardGenerator.parseLevelFile("/levels/level1.txt");

        if (mapData.isPresent()) {
            this.size = new Dimension(multiplyExact(mapData.get().get(0).split("|").length, TILE_WIDTH), multiplyExact(mapData.get().size(), TILE_WIDTH));
            this.sprites = BoardGenerator.createLevel(mapData.get(), TILE_WIDTH, ZOOM_FACTOR);
        } else {
            this.size = new Dimension(0, 0);
            this.sprites = new ArrayList<>();
        }
    }
}