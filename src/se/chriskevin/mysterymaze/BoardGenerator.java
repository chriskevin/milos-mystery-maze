package se.chriskevin.mysterymaze;

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
        final InputStream resourceAsStream = this.getClass().getResourceAsStream(filename);

        try {
            final Scanner sc = new Scanner(resourceAsStream);
            sc.forEachRemaining(d -> mapData.add(d));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return mapData;
    }
}
