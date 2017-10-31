package se.chriskevin.mysterymaze.geometry;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Chris Sundberg on 2017-06-12.
 */
public class DimensionTest {

    @Test
    public void verifyEquals() {
        final Dimension a = new Dimension(2L, 2L);
        final Dimension b = new Dimension(2L, 2L);
        assertTrue(a.equals(b));
    }

    @Test
    public void verifyNotEquals() {
        final Dimension a = new Dimension(2L, 2L);
        final Dimension<Double> b = new Dimension(2L, 2L);
        assertFalse(a.equals(b));
        assertFalse(a.equals(null));
    }

    @Test
    public void verifyHashCode() {
        assertEquals((new Dimension(2L,2L)).hashCode(), 12);
    }

    @Test
    public void verifyToString() {
        assertEquals(Dimension.class.getName() + "[width=2,height=2]", new Dimension(2L, 2L).toString());
    }
}
