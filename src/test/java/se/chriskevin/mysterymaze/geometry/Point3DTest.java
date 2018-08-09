package se.chriskevin.mysterymaze.geometry;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static se.chriskevin.mysterymaze.geometry.Point3D.*;

public class Point3DTest {

    @Test
    public void verifyEquals() {
        final Point3D a = Point3D.of(0L, 0L, 0L);
        final Point3D b = Point3D.of(0L, 0L, 0L);
        assertTrue(a.equals(b));
    }

    @Test
    public void verifyNotEquals() {
        final Point3D a = ZERO_POINT3D;
        final Point3D b = Point3D.of(2L, 2L, 2L);
        assertFalse(a.equals(b));
    }

    @Test
    public void verifyMove() {
        final Point3D p = Point3D.of(2L, 2L, 3L);
        assertEquals(move(1L, 0L, 2L, p), Point3D.of(1L, 0L,  2L));
    }

    @Test
    public void verifyTranslate() {
        final Point3D p = Point3D.of(0L, 0L, 0L);
        assertEquals(translate(2L, 0L, -2L, p), Point3D.of(2L, 0L, -2L));
        assertEquals(p, p);
    }

    @Test
    public void verifyToString() {
        assertEquals((Point3D.of(2L, 2L, 2L)).toString(), Point3D.class.getName() + "[x=2,y=2,z=2]");
    }
}
