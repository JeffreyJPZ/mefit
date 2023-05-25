package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Test class for Difficulty
public class DifficultyTest {

    @Test
    public void testValues() {
        assertEquals(1, Difficulty.LIGHT.getDifficulty());
        assertEquals(2, Difficulty.MODERATE.getDifficulty());
        assertEquals(3, Difficulty.INTENSE.getDifficulty());
    }
}
