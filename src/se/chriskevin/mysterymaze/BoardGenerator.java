package se.chriskevin.mysterymaze;

import se.chriskevin.mysterymaze.environment.GameSprite;
import se.chriskevin.mysterymaze.environment.SpriteType;
import se.chriskevin.mysterymaze.geometry.Point3D;

import java.io.InputStream;
import java.util.*;
import java.util.List;

import static java.lang.Math.addExact;
import static java.lang.Math.multiplyExact;
import static java.lang.Math.subtractExact;
import static se.chriskevin.mysterymaze.environment.utils.GameCharacterFactory.*;

/**
 * Created by Chris Sundberg on 2016-03-08.
 */
public final class BoardGenerator {

    public final static String IMAGE_PATH = "/images/";
    public final static String TILE_PATH = IMAGE_PATH + "/tiles/";

    public static Optional<List<String>> parseLevelFile(String filename) {
        final InputStream resourceAsStream = BoardGenerator.class.getResourceAsStream(filename);

        try {
            final List<String> mapData = new ArrayList<>();
            final Scanner sc = new Scanner(resourceAsStream);
            sc.forEachRemaining(mapData::add);
            sc.close();
            return Optional.ofNullable(mapData);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public static List<GameSprite> createLevel(Collection<String> mapData, Integer tileWidth, Integer scale) {
        final List<GameSprite> sprites = new ArrayList<>();
        final Point3D currentLocation = new Point3D(0, 0, 0);

        // Parse map data
        mapData.forEach(row -> {
            System.out.println(row);

            // Parse row
            for (Integer j = 0; j < row.length(); j += 3) {
                char type = row.charAt(j);
                char typeId = row.charAt(addExact(j, 1));

                // Select proper action
                switch (type) {
                    case 'e':
                    case 'f':
                    case 'w':
                        sprites.add(createTile(SpriteType.TILE, scale, currentLocation, false, (new StringBuilder()).append(TILE_PATH).append(type).append(typeId).append(".gif").toString()));
                        break;
                    case 'h':
                        sprites.add(createTile(SpriteType.TILE, scale, currentLocation, false, TILE_PATH + "f1.gif"));
                        sprites.add(createHero(SpriteType.PLAYER, scale, new Point3D(currentLocation.x, subtractExact(currentLocation.y, 14), 0)));
                        break;
                    case 'm':
                        sprites.add(createTile(SpriteType.TILE, scale, currentLocation, false, TILE_PATH + "f1.gif"));
                        Integer enemyNo = row.charAt(addExact(j, 1)) - '0';
                        Optional<GameSprite> sprite = createEnemy(SpriteType.ENEMY, enemyNo, currentLocation, scale);
                        if (sprite.isPresent()) {
                            sprites.add(sprite.get());
                        }
                        break;
                    case 't':
                        sprites.add(createTile(SpriteType.TILE, scale, currentLocation, true, (new StringBuilder()).append(TILE_PATH).append(type).append(typeId).append(".gif").toString()));
                        break;
                    default:
                        currentLocation.translate(-multiplyExact(tileWidth, scale), 0, 0);
                        break;
                }

                currentLocation.translate(multiplyExact(tileWidth, scale), 0, 0);

            }
            // currentLocation.setLocation(0, currentLocation.y);
            currentLocation.translate(0, multiplyExact(tileWidth, scale), 0);
        });

        return sprites;
    }

}
