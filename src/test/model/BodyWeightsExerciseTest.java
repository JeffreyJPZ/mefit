package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Test class for BodyWeightsExercise
public class BodyWeightsExerciseTest {
    private BodyWeightsExercise bodyWeightsExerciseTest1;
    private BodyWeightsExercise bodyWeightsExerciseTest2;
    private BodyWeightsExercise bodyWeightsExerciseTest3;

    @BeforeEach
    public void runBefore() {
        bodyWeightsExerciseTest1 = new BodyWeightsExercise("Pullups", "Shoulders",
                3, 5, 3, 30);
        bodyWeightsExerciseTest2 = new BodyWeightsExercise("Situps", "Core",
                2, 20, 1, 15);
    }

    @Test
    public void testConstructorTypical() {
        assertEquals("Pullups", bodyWeightsExerciseTest1.getName());
        assertEquals("Shoulders", bodyWeightsExerciseTest1.getMuscleGroup());
        assertEquals(3, bodyWeightsExerciseTest1.getSets());
        assertEquals(5, bodyWeightsExerciseTest1.getReps());
        assertEquals(3, bodyWeightsExerciseTest1.getDifficulty());
        assertEquals(30, bodyWeightsExerciseTest1.getTime());
        assertEquals(15, bodyWeightsExerciseTest1.getMetric());

        assertEquals("Situps", bodyWeightsExerciseTest2.getName());
        assertEquals("Core", bodyWeightsExerciseTest2.getMuscleGroup());
        assertEquals(2, bodyWeightsExerciseTest2.getSets());
        assertEquals(20, bodyWeightsExerciseTest2.getReps());
        assertEquals(1, bodyWeightsExerciseTest2.getDifficulty());
        assertEquals(15, bodyWeightsExerciseTest2.getTime());
        assertEquals(40, bodyWeightsExerciseTest2.getMetric());
    }

    @Test
    public void testConstructorBoundary() {
        bodyWeightsExerciseTest3 = new BodyWeightsExercise("Plank", "Core",
                1, 1, 1, 1);

        assertEquals("Plank", bodyWeightsExerciseTest3.getName());
        assertEquals("Core", bodyWeightsExerciseTest3.getMuscleGroup());
        assertEquals(1, bodyWeightsExerciseTest3.getSets());
        assertEquals(1, bodyWeightsExerciseTest3.getReps());
        assertEquals(1, bodyWeightsExerciseTest3.getDifficulty());
        assertEquals(1, bodyWeightsExerciseTest3.getTime());
        assertEquals(1, bodyWeightsExerciseTest3.getMetric());
    }
}
