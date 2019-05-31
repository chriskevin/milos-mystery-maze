package se.chriskevin.mysterymaze;

import io.vavr.collection.List;
import io.vavr.control.Try;
import java.util.ArrayList;
import java.util.Scanner;

public final class BoardGenerator {

  static final String IMAGE_PATH = "/images/";
  static final String TILE_PATH = IMAGE_PATH + "/tiles/";

  public static List<String> parseLevelFile(String filename) {
    var resourceAsStream = BoardGenerator.class.getResourceAsStream(filename);
    var sc = new Scanner(resourceAsStream);

    return Try.of(
            () -> {
              var mapData = new ArrayList<String>();
              sc.forEachRemaining(mapData::add);
              return List.ofAll(mapData);
            })
        .andFinally(sc::close)
        .getOrElse(List::empty);
  }

  public static String createTileImagePath(Character type, Character typeId) {
    return TILE_PATH + type + typeId + ".gif";
  }
}
