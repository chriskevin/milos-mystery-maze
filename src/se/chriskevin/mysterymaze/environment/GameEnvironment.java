package se.chriskevin.mysterymaze.environment;

import se.chriskevin.mysterymaze.BoardGenerator;
import se.chriskevin.mysterymaze.behavior.MoveBehavior;
import se.chriskevin.mysterymaze.collision.CollisionHandler;
import se.chriskevin.mysterymaze.environment.utils.GameCharacterFactory;

import java.awt.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

public class GameEnvironment {

    private static final int TILE_WIDTH = 32;

    private Dimension dimension;

    private Map<String, List<GameSprite>> sprites;

    private int zoomFactor = 2;

    public GameEnvironment() {
        final Optional<List<String>> mapData = BoardGenerator.parseLevelFile("/levels/level1.txt");
        if (mapData.isPresent()) {
            dimension = new Dimension((mapData.get().get(0).split("|").length * TILE_WIDTH), (mapData.get().size() * TILE_WIDTH));
            sprites = BoardGenerator.createLevel(mapData.get(), TILE_WIDTH, zoomFactor);
        } else {
            dimension = new Dimension(0, 0);
            sprites = new HashMap<>();
        }
    }

    public Dimension getDimension() {
        return dimension;
    }

    public Map<String, List<GameSprite>> getSprites() {
        return sprites;
    }
}