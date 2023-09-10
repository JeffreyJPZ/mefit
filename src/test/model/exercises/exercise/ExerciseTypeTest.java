package model.exercises.exercise;

import model.metrics.ExerciseType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Test class for ExerciseType
public class ExerciseTypeTest {

    @Test
    public void testValues() {
        assertEquals("WeightsExercise", ExerciseType.WEIGHTS_EXERCISE.getType());
        assertEquals("BodyWeightsExercise", ExerciseType.BODYWEIGHTS_EXERCISE.getType());
        assertEquals("CardioExercise", ExerciseType.CARDIO_EXERCISE.getType());
    }
}
