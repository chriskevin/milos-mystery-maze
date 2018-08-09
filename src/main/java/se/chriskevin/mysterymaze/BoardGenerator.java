package se.chriskevin.mysterymaze;

import io.vavr.*;
import io.vavr.collection.List;
import io.vavr.control.Try;

import java.util.ArrayList;
import java.util.Scanner;

public final class BoardGenerator {

    final static String IMAGE_PATH = "/images/";
    final static String TILE_PATH = IMAGE_PATH + "/tiles/";

    public static List<String> parseLevelFile(String filename) {
        var resourceAsStream = BoardGenerator.class.getResourceAsStream(filename);
        var sc = new Scanner(resourceAsStream);

        return Try.of(() -> {
            var mapData = new ArrayList<String>();
            sc.forEachRemaining(mapData::add);
            return List.ofAll(mapData);
        })
        .andFinally(sc::close)
        .getOrElse(List::empty);
    }

    public static final String createTileImagePath(Character type, Character typeId) {
        return new StringBuilder().append(TILE_PATH).append(type).append(typeId).append(".gif").toString();
    }
}
