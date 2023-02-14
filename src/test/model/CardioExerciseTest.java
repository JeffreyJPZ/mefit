package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Test class for CardioExercise
public class CardioExerciseTest {
    private CardioExercise cardioExerciseTest1;
    private CardioExercise cardioExerciseTest2;
    private CardioExercise cardioExerciseTest3;

    @BeforeEach
    public void runBefore() {
        cardioExerciseTest1 = new CardioExercise("Treadmill", "Legs",
                5000, 2, 45);
        cardioExerciseTest2 = new CardioExercise("Rowing", "Back",
                2000, 3, 10);
    }

    @Test
    public void testConstructorTypical() {
        assertEquals("Treadmill", cardioExerciseTest1.getName());
        assertEquals("Legs", cardioExerciseTest1.getMuscleGroup());
        assertEquals(5000, cardioExerciseTest1.getDistance());
        assertEquals(2, cardioExerciseTest1.getDifficulty());
        assertEquals(45, cardioExerciseTest1.getTime());
        assertFalse(cardioExerciseTest1.isFavourite());

        assertEquals("Rowing", cardioExerciseTest2.getName());
        assertEquals("Back", cardioExerciseTest2.getMuscleGroup());
        assertEquals(2000, cardioExerciseTest2.getDistance());
        assertEquals(3, cardioExerciseTest2.getDifficulty());
        assertEquals(10, cardioExerciseTest2.getTime());
        assertFalse(cardioExerciseTest2.isFavourite());
    }

    @Test
    public void testConstructorBoundary() {
        cardioExerciseTest3 = new CardioExercise("Bike", "Legs", 1, 1, 1);

        assertEquals("Bike", cardioExerciseTest3.getName());
        assertEquals("Legs", cardioExerciseTest3.getMuscleGroup());
        assertEquals(1, cardioExerciseTest3.getDistance());
        assertEquals(1, cardioExerciseTest3.getDifficulty());
        assertEquals(1, cardioExerciseTest3.getTime());
        assertFalse(cardioExerciseTest3.isFavourite());
    }
}
