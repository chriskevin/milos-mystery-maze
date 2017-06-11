package se.chriskevin.mysterymaze;

import se.chriskevin.mysterymaze.environment.GameSprite;
import se.chriskevin.mysterymaze.environment.utils.GameCharacterFactory;

import java.awt.*;
import java.io.InputStream;
import java.util.*;
import java.util.List;

/**
 * Created by CHSU7648 on 2016-03-08.
 */
public final class BoardGenerator {

    public static Optional<List<String>> parseLevelFile(String filename) {
        final InputStream resourceAsStream = BoardGenerator.class.getResourceAsStream(filename);

        try {
            final List<String> mapData = new ArrayList<>();
            final Scanner sc = new Scanner(resourceAsStream);
            sc.forEachRemaining(mapData::add);
            return Optional.ofNullable(mapData);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public static Map<String, List<GameSprite>> createLevel(Collection<String> mapData, int tileWidth, int zoomFactor) {
        final Map<String, List<GameSprite>> sprites = new HashMap<>();
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
                        sprites.get("TILE").add(createTile(currentLocation, "/images/tiles/" + type + typeId + ".gif", false, zoomFactor));
                        break;
                    case 'h':
                        sprites.get("TILE").add(createTile(currentLocation, "/images/tiles/f1.gif", false, zoomFactor));
                        sprites.get("PLAYER").add(GameCharacterFactory.createHero(new Point((int) currentLocation.getX(), (int) currentLocation.getY() - 14), zoomFactor));
                        sprites.get("PLAYER").get(0).setSpeed(8);
                        break;
                    case 'm':
                        sprites.get("TILE").add(createTile(currentLocation, "/images/tiles/f1.gif", false, zoomFactor));
                        int enemyNo = row.charAt(j + 1) - '0';
                        Optional<GameSprite> sprite = createEnemy(enemyNo, currentLocation, zoomFactor);
                        if (sprite.isPresent()) {
                            sprites.get("ENEMY").add(sprite.get());
                        }
                        break;
                    case 't':
                        sprites.get("TILE").add(createTile(currentLocation, "/images/tiles/" + type + typeId + ".gif", true, zoomFactor));
                        break;
                    default:
                        currentLocation.translate(-(tileWidth * zoomFactor), 0);
                        break;
                }

                currentLocation.translate((tileWidth * zoomFactor), 0);

            }
            currentLocation.setLocation(0, currentLocation.getY());
            currentLocation.translate(0, (tileWidth * zoomFactor));
        });

        return sprites;
    }

    public static Optional<GameSprite> createEnemy(int enemyNo, Point currentLocation, int zoomFactor) {
        switch (enemyNo) {
            case 1:
                return Optional.of(GameCharacterFactory.createBat(new Point(currentLocation), zoomFactor));
            case 2:
                return Optional.of(GameCharacterFactory.createRat(new Point(currentLocation), zoomFactor));
            case 3:
                return Optional.of(GameCharacterFactory.createZombie(new Point((int) currentLocation.getX(), (int) currentLocation.getY() - (14 * zoomFactor)), zoomFactor));
        }

        return Optional.empty();
    }

    public static GameSprite createTile(Point currentLocation, String imageFilename, boolean blocking, int zoomFactor) {
        final GameSprite gameSprite = new GameSprite(new Point(currentLocation));
        gameSprite.setScale(zoomFactor);

        if (blocking) {
            gameSprite.isBlocking(blocking);
        }

        gameSprite.setImage(imageFilename);
        return gameSprite;
    }
}
