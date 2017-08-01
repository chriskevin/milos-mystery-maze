package se.chriskevin.mysterymaze.geometry;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by Chris Sundberg on 2017-06-12.
 */
public class DimensionTest {
    @Test
    public void verifyEquals() {
        final Dimension<Integer> a = new Dimension<>(2, 2);
        final Dimension<Integer> b = new Dimension<>(2, 2);
        assertTrue(a.equals(b));
    }

    @Test
    public void verifyNotEquals() {
        final Dimension<Integer> a = new Dimension<>(2, 2);
        final Dimension<Double> b = new Dimension<>(2.30, 2.30);
        assertFalse(a.equals(b));
        assertFalse(a.equals(null));
    }

    @Test
    public void verifyHashCode() {
        assertEquals((new Dimension<Integer>(2,2)).hashCode(), 12);
    }

    @Test
    public void verifyToString() {
        assertEquals(Dimension.class.getName() + "[width=2,height=2]", new Dimension<>(2, 2).toString());
    }
}
