package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Test class for WeightsExercise
public class WeightsExerciseTest {
    private WeightsExercise weightsExerciseTest1;
    private WeightsExercise weightsExerciseTest2;
    private WeightsExercise weightsExerciseTest3;

    @BeforeEach
    public void runBefore() {
        weightsExerciseTest1 = new WeightsExercise("Bench Press", "Chest",
                150, 3, 5, 3, 30);
        weightsExerciseTest2 = new WeightsExercise("Squats", "Legs",
                225, 5, 8, 2, 60);
    }

    @Test
    public void testConstructorTypical() {
        assertEquals("Bench Press", weightsExerciseTest1.getName());
        assertEquals("Chest", weightsExerciseTest1.getMuscleGroup());
        assertEquals(150, weightsExerciseTest1.getWeight());
        assertEquals(3, weightsExerciseTest1.getSets());
        assertEquals(5, weightsExerciseTest1.getReps());
        assertEquals(3, weightsExerciseTest1.getDifficulty());
        assertEquals(30, weightsExerciseTest1.getTime());
        assertFalse(weightsExerciseTest1.isFavourite());

        assertEquals("Squats", weightsExerciseTest2.getName());
        assertEquals("Legs", weightsExerciseTest2.getMuscleGroup());
        assertEquals(225, weightsExerciseTest2.getWeight());
        assertEquals(5, weightsExerciseTest2.getSets());
        assertEquals(8, weightsExerciseTest2.getReps());
        assertEquals(2, weightsExerciseTest2.getDifficulty());
        assertEquals(60, weightsExerciseTest2.getTime());
        assertFalse(weightsExerciseTest1.isFavourite());
    }

    @Test
    public void testConstructorBoundary() {
        weightsExerciseTest3 = new WeightsExercise("Bicep Curls", "Arms",
                1, 1, 1, 1, 1);

        assertEquals("Bicep Curls", weightsExerciseTest3.getName());
        assertEquals("Arms", weightsExerciseTest3.getMuscleGroup());
        assertEquals(1, weightsExerciseTest3.getWeight());
        assertEquals(1, weightsExerciseTest3.getSets());
        assertEquals(1, weightsExerciseTest3.getReps());
        assertEquals(1, weightsExerciseTest3.getDifficulty());
        assertEquals(1, weightsExerciseTest3.getTime());
        assertFalse(weightsExerciseTest1.isFavourite());
    }

    @Test
    public void testToString() {
        assertEquals("Exercise Name: " + weightsExerciseTest1.getName() + "\n"
                        + "Muscle Group: " + weightsExerciseTest1.getMuscleGroup() + "\n"
                        + "Difficulty: " + weightsExerciseTest1.getDifficulty() + "\n"
                        + "Time: " + weightsExerciseTest1.getTime() + "\n"
                        + "Favourite?: " + weightsExerciseTest1.isFavourite() + "\n"
                        + "Weight: " + weightsExerciseTest1.getWeight() + "\n"
                        + "Sets: " + weightsExerciseTest1.getSets() + "\n"
                        + "Reps: " + weightsExerciseTest1.getReps(),
                weightsExerciseTest1.toString());

        assertEquals("Exercise Name: " + weightsExerciseTest2.getName() + "\n"
                        + "Muscle Group: " + weightsExerciseTest2.getMuscleGroup() + "\n"
                        + "Difficulty: " + weightsExerciseTest2.getDifficulty() + "\n"
                        + "Time: " + weightsExerciseTest2.getTime() + "\n"
                        + "Favourite?: " + weightsExerciseTest2.isFavourite() + "\n"
                        + "Weight: " + weightsExerciseTest2.getWeight() + "\n"
                        + "Sets: " + weightsExerciseTest2.getSets() + "\n"
                        + "Reps: " + weightsExerciseTest2.getReps(),
                weightsExerciseTest2.toString());
    }
}