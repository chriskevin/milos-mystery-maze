package se.chriskevin.mysterymaze.environment;

import static se.chriskevin.mysterymaze.BoardGenerator.createTileImagePath;
import static se.chriskevin.mysterymaze.environment.utils.GameCharacterFactory.createEnemy;
import static se.chriskevin.mysterymaze.environment.utils.GameCharacterFactory.createHero;
import static se.chriskevin.mysterymaze.environment.utils.GameCharacterFactory.createTile;
import static se.chriskevin.mysterymaze.geometry.Point3D.*;
import static se.chriskevin.mysterymaze.utils.Calculation.multiply;
import static se.chriskevin.mysterymaze.utils.Calculation.subtract;

import io.vavr.Function1;
import io.vavr.collection.List;
import io.vavr.control.Option;
import java.util.concurrent.atomic.AtomicInteger;
import se.chriskevin.mysterymaze.geometry.Dimension;
import se.chriskevin.mysterymaze.geometry.Point3D;

public class GameEnvironment {

  private static final Long TILE_WIDTH = 32L;
  private static final Long SCALE = 2L;

  public final Dimension size;
  public final List<GameSprite> sprites;

  private GameEnvironment(final Dimension size, final List<GameSprite> sprites) {
    this.size = size;
    this.sprites = sprites;
  }

  public static GameEnvironment of(final Dimension size, final List<GameSprite> sprites) {
    return new GameEnvironment(size, sprites);
  }

  private static List<GameSprite> createSprite(
      final Character type,
      final Character typeId,
      final Long scale,
      final Point3D currentLocation,
      final List<GameSprite> sprites) {
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
        return sprites.push(
            createTile(scale, currentLocation, false, createTileImagePath(type, typeId)));
      case 'h':
        return sprites
            .push(createTile(scale, currentLocation, false, createTileImagePath(type, typeId)))
            .push(
                createHero(
                    scale, Point3D.of(currentLocation.x, subtract(currentLocation.y, 14L), 0L)));
      case 'm':
        sprites.push(createTile(scale, currentLocation, false, createTileImagePath(type, typeId)));
        var sprite = createEnemy((long) (typeId - '0'), currentLocation, scale);
        if (sprite.isDefined()) {
          sprites.push(sprite.get());
        }
        return sprites;
      case 't':
        return sprites.push(
            createTile(scale, currentLocation, true, createTileImagePath(type, typeId)));
      default:
        return List.empty();
    }
  }

  private static final Function1<List<String>, List<GameSprite>> createLevel =
      (levelData) -> {
        final var rowNo = new AtomicInteger(0);
        var currentLocation = ZERO_POINT3D;
        List<GameSprite> sprites = List.empty();

        for (String row : levelData) {
          var rn = Long.valueOf(rowNo.getAndIncrement());

          for (int i = 0; i < row.length(); i += 3) {
            var type = row.charAt(i);
            var typeId = row.charAt((i + 1));

            System.out.println("Row: " + rn + ", Column: " + i / 3 + ", Type: " + type + typeId);
            System.out.println(currentLocation);

            currentLocation = translateX(multiply(TILE_WIDTH, SCALE), currentLocation);
            sprites = createSprite(type, typeId, SCALE, currentLocation, sprites);
          }

          currentLocation =
              move(0L, (TILE_WIDTH * SCALE) * (long) rowNo.get(), 0L, currentLocation);
        }

        return sprites;
      };

  public static Option<GameEnvironment> createEnvironment(final List<String> levelData) {
    return levelData.isEmpty()
        ? Option.none()
        : Option.of(
            GameEnvironment.of(
                Dimension.of(
                    numberOfColumns.andThen(adjustToScale).apply(levelData),
                    numberOfRows.andThen(adjustToScale).apply(levelData)),
                createLevel.apply(levelData)));
  }

  private static final Function1<List<String>, Long> numberOfColumns =
      (levelData) -> (long) levelData.get(0).split("\\|").length;

  private static final Function1<List<String>, Long> numberOfRows =
      (levelData) -> (long) levelData.size();

  private static final Function1<Long, Long> adjustToScale =
      (dimension) -> multiply(dimension, TILE_WIDTH) * SCALE;
}
