package se.chriskevin.mysterymaze.ui;

import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import org.junit.Test;
import se.chriskevin.mysterymaze.environment.GameSprite;
import se.chriskevin.mysterymaze.geometry.Dimension;
import se.chriskevin.mysterymaze.geometry.Point3D;

import java.awt.Rectangle;

import static org.junit.Assert.assertEquals;
import static se.chriskevin.mysterymaze.geometry.Point3D.ZERO_POINT3D;
import static se.chriskevin.mysterymaze.ui.Camera.getVisibleSprites;

public class CameraTest {

    @Test
    public void verifyGetVisibleSprites() {
        final Dimension size =  Dimension.of(30L, 30L);

        final List<GameSprite> unfilteredSprites = List.of(
            GameSprite.of(null, 1L, false, ZERO_POINT3D, 0L, null, false, null, HashMap.empty(), null, size),
            GameSprite.of(null, 1L, false, Point3D.of(400L, 400L, 0L), 0L, null, false, null, HashMap.empty(), null, size),
            GameSprite.of(null, 1L, false, Point3D.of(30L, 30L, 0L), 0L, null, false, null, HashMap.empty(), null, size)
        );

        final List<GameSprite> result = getVisibleSprites(unfilteredSprites, new Rectangle(0, 0, 300, 200));

        assertEquals(2, result.size());
        assertEquals(unfilteredSprites.get(0), result.get(0));
        assertEquals(unfilteredSprites.get(2), result.get(1));
    }
}
