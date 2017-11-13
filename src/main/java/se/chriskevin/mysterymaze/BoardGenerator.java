package se.chriskevin.mysterymaze;

import io.vavr.Function1;
import io.vavr.Function2;
import io.vavr.Function3;
import io.vavr.collection.List;
import io.vavr.control.Try;
import se.chriskevin.mysterymaze.environment.GameSprite;
import se.chriskevin.mysterymaze.geometry.Point3D;

import java.io.InputStream;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import static se.chriskevin.mysterymaze.environment.utils.GameCharacterFactory.*;
import static se.chriskevin.mysterymaze.geometry.Point3D.ZERO_POINT3D;
import static se.chriskevin.mysterymaze.geometry.Point3D.point3D;
import static se.chriskevin.mysterymaze.geometry.Point3D.translate;
import static se.chriskevin.mysterymaze.utils.Calculation.add;
import static se.chriskevin.mysterymaze.utils.Calculation.subtract;
import static se.chriskevin.mysterymaze.utils.Calculation.multiply;

/**
 * Created by Chris Sundberg on 2016-03-08.
 */
public final class BoardGenerator {

    final static String IMAGE_PATH = "/images/";
    final static String TILE_PATH = IMAGE_PATH + "/tiles/";

    public static final Function1<String, List<String>> parseLevelFile =
        filename -> {
        final InputStream resourceAsStream = BoardGenerator.class.getResourceAsStream(filename);
        final Scanner sc = new Scanner(resourceAsStream);

        return Try.of(() -> {
                final List<String> mapData = List.empty();
                sc.forEachRemaining(mapData::push);
                return mapData;
            })
            .andFinally(() -> sc.close())
            .getOrElse(List::empty);
    };

    public static final Function3<Long, Long, List<String>, List<GameSprite>> createLevel =
        (scale, tileWidth, mapData) -> {
            AtomicInteger rowNo = new AtomicInteger(-1);
            return mapData
                    .flatMap(row -> {
                        System.out.println(row);
                        return createSprites(row, Long.valueOf(rowNo.incrementAndGet()),0L, tileWidth, scale, ZERO_POINT3D, List.empty());
                    });
        };

    public static List<GameSprite> createSprites(String row, Long rowNo, Long count, Long tileWidth, Long scale, Point3D currentLocation, List<GameSprite> sprites) {
        if (count < row.length()) {
            Character type = row.charAt(count.intValue());
            Character typeId = row.charAt(add.apply(count, 1L).intValue());

            sprites = createSprite(type, typeId, scale, currentLocation, sprites);

            currentLocation = translate.apply(
                multiply.apply(tileWidth, scale),
                (tileWidth * scale) * rowNo,
                0L,
                currentLocation
            );
            return createSprites(
                row,
                rowNo,
                add.apply(count, 3L),
                tileWidth,
                scale,
                currentLocation,
                sprites
            );
        } else {
            return sprites;
        }
    }

    public static List<GameSprite> createSprite(Character type, Character typeId, Long scale, Point3D currentLocation, List<GameSprite> sprites) {
        switch (type) {
            case 'e':
            case 'f':
            case 'w':
                return sprites.push(createTile(scale, currentLocation, false, createTileImagePath.apply(type, typeId)));
            case 'h':
                return sprites
                        .push(createTile(scale, currentLocation, false, createTileImagePath.apply(type, typeId)))
                        .push(createHero(scale, point3D.apply(currentLocation.x, subtract.apply(currentLocation.y, 14L), 0L)));
            case 'm':
                sprites
                    .push(createTile(scale, currentLocation, false, createTileImagePath.apply(type, typeId)));
                Optional<GameSprite> sprite = createEnemy(Long.valueOf(typeId - '0'), currentLocation, scale);
                if (sprite.isPresent()) {
                    sprites.push(sprite.get());
                }
                return sprites;
            case 't':
                return sprites.push(createTile(scale, currentLocation, true, createTileImagePath.apply(type, typeId)));
            default:
                return List.empty();
        }
    }

    public static Function2<Character, Character, String> createTileImagePath =
        (type, typeId) ->
            new StringBuilder().append(TILE_PATH).append(type).append(typeId).append(".gif").toString();
}
