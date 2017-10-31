package se.chriskevin.mysterymaze.ui;

import org.junit.jupiter.api.Test;
import se.chriskevin.mysterymaze.environment.GameSprite;
import se.chriskevin.mysterymaze.geometry.Point3D;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.chriskevin.mysterymaze.ui.Camera.getVisibleSprites;

public class CameraTest {

    @Test
    public void verifyGetVisibleSprites() {
        final List<GameSprite> unfilteredSprites = new ArrayList<>();
        unfilteredSprites.add(new GameSprite(null, 1, false, new Point3D(0, 0, 0), 0, null, false, null, null, null));
        unfilteredSprites.add(new GameSprite(null, 1, false, new Point3D(400, 400, 0), 0, null, false, null, null, null));
        unfilteredSprites.add(new GameSprite(null, 1, false, new Point3D(30, 30, 0), 0, null, false, null, null, null));

        final List<GameSprite> result = getVisibleSprites(unfilteredSprites, new Rectangle(0, 0, 300, 200));

        assertEquals(result.size(), 2);
        assertEquals(result.get(0), unfilteredSprites.get(0));
        assertEquals(result.get(2), unfilteredSprites.get(3));
    }
}
