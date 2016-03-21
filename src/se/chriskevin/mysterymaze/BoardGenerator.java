package se.chriskevin.mysterymaze;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

/**
 * Created by CHSU7648 on 2016-03-08.
 */
public class BoardGenerator {

    public BoardGenerator() {
    }

    public Collection<String> parseLevelFile(String filename) {
        final Collection<String> mapData = new ArrayList<>();
        Scanner sc = null;
        InputStream resourceAsStream = this.getClass().getResourceAsStream(filename);

        try {
            sc = new Scanner(resourceAsStream);

            while (sc.hasNext()) {
                mapData.add(sc.next());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return mapData;
    }

    public static Collection<Sprite> createTiles(Collection<String> mapData) {
        final Collection<Sprite> tiles = new ArrayList<>();

        return tiles;
    }
}
