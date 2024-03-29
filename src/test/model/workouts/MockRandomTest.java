package model.workouts;

import model.workouts.workoutgenerator.MockRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Test class for MockRandom
public class MockRandomTest {
    private MockRandom mockRandom;

    @BeforeEach
    public void runBefore() {
        mockRandom = new MockRandom(0);
    }

    @Test
    public void testConstructor() {
        assertEquals(0, mockRandom.getReturnNum());
    }

    @Test
    public void testNextInt() {
        assertEquals(0, mockRandom.nextInt(1));
    }

    @Test
    public void testNextIntMultiple() {
        assertEquals(0, mockRandom.nextInt(3));
        assertEquals(1, mockRandom.nextInt(5));
        assertEquals(2, mockRandom.nextInt(10));
    }
}
