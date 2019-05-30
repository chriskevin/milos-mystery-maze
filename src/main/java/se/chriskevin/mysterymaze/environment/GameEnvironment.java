package se.chriskevin.mysterymaze.environment;

import io.vavr.Function0;
import io.vavr.Function1;
import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import io.vavr.control.Option;
import se.chriskevin.mysterymaze.geometry.Dimension;
import se.chriskevin.mysterymaze.geometry.Point3D;

import java.util.concurrent.atomic.AtomicInteger;

import static se.chriskevin.mysterymaze.BoardGenerator.createTileImagePath;
import static se.chriskevin.mysterymaze.environment.utils.GameCharacterFactory.createEnemy;
import static se.chriskevin.mysterymaze.environment.utils.GameCharacterFactory.createHero;
import static se.chriskevin.mysterymaze.environment.utils.GameCharacterFactory.createTile;
import static se.chriskevin.mysterymaze.geometry.Point3D.*;
import static se.chriskevin.mysterymaze.utils.Calculation.multiply;
import static se.chriskevin.mysterymaze.utils.Calculation.subtract;

public class GameEnvironment {

    public static final Long TILE_WIDTH = 32L;
    public static final Long SCALE = 2L;

    public final Dimension size;
    public final List<GameSprite> sprites;

    private GameEnvironment(Dimension size, List<GameSprite> sprites) {
        this.size = size;
        this.sprites = sprites;
    }

    public static GameEnvironment of(Dimension size, List<GameSprite> sprites) {
        return new GameEnvironment(size, sprites);
    }

    public static List<GameSprite> createSprite(Character type, Character typeId, Long scale, Point3D currentLocation, List<GameSprite> sprites) {
        /*var sps = HashMap.of(
            "efwhm", (Function0<GameSprite>) () -> createTile(scale, currentLocation, false, createTileImagePath(type, typeId)),
            "h", (Function0<GameSprite>) () -> createHero(scale, Point3D.of(currentLocation.x, subtract(currentLocation.y, 14L), 0L))
            //"m", (Function0<GameSprite>) () -> createEnemy(Long.valueOf(typeId - '0'), currentLocation, scale)
        )
        .filterKeys(x -> x.contains(type.toString()))
        .mapValues(x -> sprites.push(x.apply()))
        .mapValues(x -> {
            System.out.println(x);
            return x;
        })
        .values()
        .toList()
        .flatMap(x -> x);

        System.out.println(sps);*/

        switch (type) {
            case 'e':
            case 'f':
            case 'w':
                return sprites.push(createTile(scale, currentLocation, false, createTileImagePath(type, typeId)));
            case 'h':
                return sprites
                        .push(createTile(scale, currentLocation, false, createTileImagePath(type, typeId)))
                        .push(createHero(scale, Point3D.of(currentLocation.x, subtract(currentLocation.y, 14L), 0L)));
            case 'm':
                sprites
                        .push(createTile(scale, currentLocation, false, createTileImagePath(type, typeId)));
                var sprite = createEnemy((long) (typeId - '0'), currentLocation, scale);
                if (sprite.isDefined()) {
                    sprites.push(sprite.get());
                }
                return sprites;
            case 't':
                return sprites.push(createTile(scale, currentLocation, true, createTileImagePath(type, typeId)));
            default:
                return List.empty();
        }
    }

    public static final Function1<List<String>, List<GameSprite>> createLevel =
            (levelData) -> {
                var rowNo = new AtomicInteger(0);
                var currentLocation = ZERO_POINT3D;
                List<GameSprite> sprites = List.empty();

                for (String row : levelData) {
                    var rn = (long) rowNo.getAndIncrement();

                    for (int i = 0; i < row.length(); i += 3) {
                        var type = row.charAt(i);
                        var typeId = row.charAt((i + 1));

                        // System.out.println("Row: " + rn + ", Column: " + i / 3 + ", Type: " + type + typeId);
                        // System.out.println(currentLocation);

                        currentLocation = translateX(multiply(TILE_WIDTH, SCALE), currentLocation);
                        sprites = createSprite(type, typeId, SCALE, currentLocation, sprites);
                    }

                    currentLocation = move(0L, (TILE_WIDTH * SCALE) * (long) rowNo.get(), 0L, currentLocation);

                }

                return sprites;
            };

    public static Option<GameEnvironment> createEnvironment(List<String> levelData) {
        return levelData.isEmpty() ?
            Option.none() :
            Option.of(
                GameEnvironment.of(
                    Dimension.of(
                    multiply((long) levelData.get(0).split("|").length, TILE_WIDTH) * SCALE,
                    multiply((long) levelData.size(), TILE_WIDTH) * SCALE
                    ),
                    createLevel.apply(levelData)
                )
            );
    }
}