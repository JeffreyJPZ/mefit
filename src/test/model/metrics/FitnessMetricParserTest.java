package model.metrics;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Test class for FitnessMetricParser
public class FitnessMetricParserTest {

    @Test
    public void testGetMuscleGroupByName() {
        assertEquals(MuscleGroup.CHEST, FitnessMetricParser.getInstance()
                .getMuscleGroupByName("Chest"));
        assertEquals(MuscleGroup.ARMS, FitnessMetricParser.getInstance()
                .getMuscleGroupByName("Arms"));
        assertEquals(MuscleGroup.SHOULDERS, FitnessMetricParser.getInstance()
                .getMuscleGroupByName("Shoulders"));
        assertEquals(MuscleGroup.BACK, FitnessMetricParser.getInstance()
                .getMuscleGroupByName("Back"));
        assertEquals(MuscleGroup.LEGS, FitnessMetricParser.getInstance()
                .getMuscleGroupByName("Legs"));
        assertEquals(MuscleGroup.CORE, FitnessMetricParser.getInstance()
                .getMuscleGroupByName("Core"));
    }

    @Test
    public void testGetDifficultyByLevel() {
        assertEquals(Difficulty.LIGHT, FitnessMetricParser.getInstance()
                .getDifficultyByLevel(1));
        assertEquals(Difficulty.MODERATE, FitnessMetricParser.getInstance()
                .getDifficultyByLevel(2));
        assertEquals(Difficulty.INTENSE, FitnessMetricParser.getInstance()
                .getDifficultyByLevel(3));
    }

    @Test
    public void testGetExerciseTypeByName() {
        assertEquals(ExerciseType.WEIGHTS_EXERCISE, FitnessMetricParser.getInstance()
                .getExerciseTypeByName("WeightsExercise"));
        assertEquals(ExerciseType.BODYWEIGHTS_EXERCISE, FitnessMetricParser.getInstance()
                .getExerciseTypeByName("BodyWeightsExercise"));
        assertEquals(ExerciseType.CARDIO_EXERCISE, FitnessMetricParser.getInstance()
                .getExerciseTypeByName("CardioExercise"));
    }

}
