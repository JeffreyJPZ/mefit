package model.workouts;

import model.metrics.Difficulty;
import model.metrics.MuscleGroup;
import model.workouts.workoutgenerator.WorkoutParameters;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Test class for WorkoutParameters
public class WorkoutParametersTest {
    private WorkoutParameters parameters1;
    private WorkoutParameters parameters2;

    @BeforeEach
    public void runBefore() {
        parameters1 = new WorkoutParameters
                .WorkoutParametersBuilder(MuscleGroup.BACK, Difficulty.LIGHT, 0, 0, 0)
                .build();
        parameters2 = new WorkoutParameters
                .WorkoutParametersBuilder(MuscleGroup.SHOULDERS, Difficulty.INTENSE, 300, 5, 10)
                .build();
    }

    @Test
    public void testConstructor() {
        assertEquals(MuscleGroup.BACK, parameters1.getMuscleGroup());
        assertEquals(Difficulty.LIGHT, parameters1.getDifficulty());
        assertEquals(0, parameters1.getSize());
        assertEquals(0, parameters1.getTime());
        assertEquals(0, parameters1.getSampleSize());

        assertEquals(MuscleGroup.SHOULDERS, parameters2.getMuscleGroup());
        assertEquals(Difficulty.INTENSE, parameters2.getDifficulty());
        assertEquals(5, parameters2.getSize());
        assertEquals(300, parameters2.getTime());
        assertEquals(10, parameters2.getSampleSize());
    }
}
