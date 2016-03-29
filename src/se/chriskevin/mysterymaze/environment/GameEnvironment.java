package se.chriskevin.mysterymaze.environment;

import se.chriskevin.mysterymaze.BoardGenerator;
import se.chriskevin.mysterymaze.behavior.MoveBehavior;
import se.chriskevin.mysterymaze.environment.utils.GameCharacterFactory;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;

public class GameEnvironment {

    private static final int TILE_WIDTH = 32;

    private Dimension dimension;

    private BoardGenerator generator;

    private Map<String, List<GameSprite>> sprites;

    private int zoomFactor = 2;

    public GameEnvironment() {
        generator = new BoardGenerator();
        final List<String> mapData = generator.parseLevelFile("/levels/level1.txt");
        dimension = new Dimension((mapData.get(0).split("|").length * TILE_WIDTH),(mapData.size() * TILE_WIDTH));
        createLevel(mapData);
    }

    public Dimension getDimension() {
        return dimension;
    }

    public Map<String, List<GameSprite>> getSprites() {
        return sprites;
    }

    public void detectCollision() {
        sprites.get("TILE").forEach(tile -> {
            if (tile.isBlocking()) {

                sprites.get("PLAYER").forEach(player -> {
                    boolean isColliding = player.getBounds().intersects(tile.getBounds());
                    while (isColliding) {
                        player.act(MoveBehavior.BOUNCE_BACK);
                        isColliding = player.getBounds().intersects(tile.getBounds());
                        player.isColliding(isColliding);
                    }
                });

                sprites.get("ENEMY").forEach(enemy -> {
                    boolean isColliding = enemy.getBounds().intersects(tile.getBounds());
                    while (isColliding) {
                        enemy.act(MoveBehavior.BOUNCE_BACK);
                        isColliding = enemy.getBounds().intersects(tile.getBounds());
                        enemy.isColliding(isColliding);
                    }
                });
            }
        });

        // Enemy checks
        sprites.get("ENEMY").forEach(enemyA -> {
            sprites.get("PLAYER").forEach(player -> {
                boolean isColliding = enemyA.getBounds().intersects(player.getBounds());
                while (isColliding) {
                    player.act(MoveBehavior.BOUNCE_BACK);
                    isColliding = enemyA.getBounds().intersects(player.getBounds());
                    player.isColliding(isColliding);
                }
            });

            sprites.get("ENEMY").forEach(enemyB -> {
                boolean isColliding = (!enemyA.equals(enemyB) && enemyB.getBounds().intersects(enemyA.getBounds()));
                while (isColliding) {
                    enemyA.act(MoveBehavior.BOUNCE_BACK);
                    isColliding = enemyB.getBounds().intersects(enemyA.getBounds());
                    enemyA.isColliding(isColliding);
                    enemyB.isColliding(isColliding);
                }
            });
        });
    }

    private void createLevel(Collection<String> mapData) {
        sprites = new HashMap<>();
        sprites.put("PLAYER", new ArrayList<>());
        sprites.put("TILE", new ArrayList<>());
        sprites.put("ENEMY", new ArrayList<>());

        final Point currentLocation = new Point(0, 0);

        // Parse map data
        mapData.forEach(row -> {
            System.out.println(row);

            // Parse row
            for (int j = 0; j < row.length(); j += 3) {
                char type = row.charAt(j);
                char typeId = row.charAt(j + 1);

                // Select proper action
                switch (type) {
                    case 'e':
                    case 'f':
                    case 'w':
                        createTile(currentLocation, "/images/tiles/" + type + typeId + ".gif", false);
                        break;
                    case 'h':
                        createTile(currentLocation, "/images/tiles/f1.gif", false);
                        sprites.get("PLAYER").add(GameCharacterFactory.createHero(new Point((int) currentLocation.getX(), (int) currentLocation.getY() - 14), zoomFactor));
                        sprites.get("PLAYER").get(0).setSpeed(8);
                        break;
                    case 'm':
                        createTile(currentLocation, "/images/tiles/f1.gif", false);
                        int enemyNo = row.charAt(j + 1) - '0';
                        createEnemy(enemyNo, currentLocation);
                        break;
                    case 't':
                        createTile(currentLocation, "/images/tiles/" + type + typeId + ".gif", true);
                        break;
                    default:
                        currentLocation.translate(-(TILE_WIDTH * zoomFactor), 0);
                        break;
                }

                currentLocation.translate((TILE_WIDTH * zoomFactor), 0);

            }
            currentLocation.setLocation(0, currentLocation.getY());
            currentLocation.translate(0, (TILE_WIDTH * zoomFactor));
        });
    }

    private void createEnemy(int enemyNo, Point currentLocation) {
        switch (enemyNo) {
            case 1:
                sprites.get("ENEMY").add(GameCharacterFactory.createBat(new Point(currentLocation), zoomFactor));
                break;
            case 2:
                sprites.get("ENEMY").add(GameCharacterFactory.createRat(new Point(currentLocation), zoomFactor));
                break;
            case 3:
                sprites.get("ENEMY").add(GameCharacterFactory.createZombie(new Point((int) currentLocation.getX(), (int) currentLocation.getY() - (14 * zoomFactor)), zoomFactor));
                break;
        }
    }

    private void createTile(Point currentLocation, String imageFilename, boolean blocking) {
        final GameSprite gameSprite = new GameSprite(new Point(currentLocation));
        gameSprite.setScale(zoomFactor);

        if (blocking) {
            gameSprite.isBlocking(blocking);
        }

        gameSprite.setImage(imageFilename);
        sprites.get("TILE").add(gameSprite);
    }
}