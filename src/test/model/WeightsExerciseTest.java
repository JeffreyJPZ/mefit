package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Test class for WeightsExercise
public class WeightsExerciseTest {
    private WeightsExercise weightsExerciseTest1;
    private WeightsExercise weightsExerciseTest2;

    @BeforeEach
    public void runBefore() {
        weightsExerciseTest1 = new WeightsExercise("Bench Press", MuscleGroup.CHEST,
                150, 3, 5, Difficulty.INTENSE, 30);
        weightsExerciseTest2 = new WeightsExercise("Squats", MuscleGroup.LEGS,
                225, 5, 8, Difficulty.MODERATE, 60);
    }

    @Test
    public void testConstructorTypical() {
        assertEquals("Bench Press", weightsExerciseTest1.getName());
        assertEquals(MuscleGroup.CHEST, weightsExerciseTest1.getMuscleGroup());
        assertEquals(150, weightsExerciseTest1.getWeight());
        assertEquals(3, weightsExerciseTest1.getSets());
        assertEquals(5, weightsExerciseTest1.getReps());
        assertEquals(Difficulty.INTENSE, weightsExerciseTest1.getDifficulty());
        assertEquals(30, weightsExerciseTest1.getTime());
        assertFalse(weightsExerciseTest1.isFavourite());

        assertEquals("Squats", weightsExerciseTest2.getName());
        assertEquals(MuscleGroup.LEGS, weightsExerciseTest2.getMuscleGroup());
        assertEquals(225, weightsExerciseTest2.getWeight());
        assertEquals(5, weightsExerciseTest2.getSets());
        assertEquals(8, weightsExerciseTest2.getReps());
        assertEquals(Difficulty.MODERATE, weightsExerciseTest2.getDifficulty());
        assertEquals(60, weightsExerciseTest2.getTime());
        assertFalse(weightsExerciseTest1.isFavourite());
    }

    @Test
    public void testConstructorBoundary() {
        WeightsExercise weightsExerciseTest3 = new WeightsExercise("Bicep Curls", MuscleGroup.ARMS,
                1, 1, 1, Difficulty.LIGHT, 1);

        assertEquals("Bicep Curls", weightsExerciseTest3.getName());
        assertEquals(MuscleGroup.ARMS, weightsExerciseTest3.getMuscleGroup());
        assertEquals(1, weightsExerciseTest3.getWeight());
        assertEquals(1, weightsExerciseTest3.getSets());
        assertEquals(1, weightsExerciseTest3.getReps());
        assertEquals(Difficulty.LIGHT, weightsExerciseTest3.getDifficulty());
        assertEquals(1, weightsExerciseTest3.getTime());
        assertFalse(weightsExerciseTest1.isFavourite());
    }

    @Test
    public void testToString() {
        assertEquals("Exercise Name: " + weightsExerciseTest1.getName() + "\n"
                        + "Muscle Group: " + weightsExerciseTest1.getMuscleGroup().getMuscleGroup() + "\n"
                        + "Difficulty: " + weightsExerciseTest1.getDifficulty().getDifficulty() + "\n"
                        + "Time: " + weightsExerciseTest1.getTime() + "\n"
                        + "Favourite?: " + weightsExerciseTest1.isFavourite() + "\n"
                        + "Weight: " + weightsExerciseTest1.getWeight() + "\n"
                        + "Sets: " + weightsExerciseTest1.getSets() + "\n"
                        + "Reps: " + weightsExerciseTest1.getReps(),
                weightsExerciseTest1.toString());

        assertEquals("Exercise Name: " + weightsExerciseTest2.getName() + "\n"
                        + "Muscle Group: " + weightsExerciseTest2.getMuscleGroup().getMuscleGroup() + "\n"
                        + "Difficulty: " + weightsExerciseTest2.getDifficulty().getDifficulty() + "\n"
                        + "Time: " + weightsExerciseTest2.getTime() + "\n"
                        + "Favourite?: " + weightsExerciseTest2.isFavourite() + "\n"
                        + "Weight: " + weightsExerciseTest2.getWeight() + "\n"
                        + "Sets: " + weightsExerciseTest2.getSets() + "\n"
                        + "Reps: " + weightsExerciseTest2.getReps(),
                weightsExerciseTest2.toString());
    }
}