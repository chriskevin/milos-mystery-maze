package se.chriskevin.mysterymaze.geometry;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by Chris Sundberg on 2017-06-12.
 */
public class Point3DTest {

    @Test
    public void verifyEquals() {
        final Point3D a = new Point3D(0, 0 , 0);
        final Point3D b = new Point3D(0, 0 , 0);
        assertTrue(a.equals(b));
    }

    @Test
    public void verifyNotEquals() {
        final Point3D a = new Point3D(0, 0 , 0);
        final Point3D b = new Point3D(2, 2 , 2);
        assertFalse(a.equals(b));
    }

    @Test
    public void verifyMove() {
        final Point3D p = new Point3D(2, 2, 3);
        assertEquals(p.move(1, 0, 2), new Point3D(1, 0,  2));
    }

    @Test
    public void verifyTranslate() {
        final Point3D p = new Point3D(0, 0, 0);
        assertEquals(p.translate(2, 0, -2), new Point3D(2, 0, -2));
        assertEquals(p, p);
    }

    @Test
    public void verifyToString() {
        assertEquals((new Point3D(2, 2, 2)).toString(), Point3D.class.getName() + "[x=2,y=2,z=2]");
    }
}
