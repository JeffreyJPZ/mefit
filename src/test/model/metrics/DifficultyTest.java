package model.metrics;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Test class for Difficulty
public class DifficultyTest {

    @Test
    public void testValues() {
        assertEquals(1, Difficulty.LIGHT.getDifficultyAsInt());
        assertEquals(2, Difficulty.MODERATE.getDifficultyAsInt());
        assertEquals(3, Difficulty.INTENSE.getDifficultyAsInt());
    }
}
