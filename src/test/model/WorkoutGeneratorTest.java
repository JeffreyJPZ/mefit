package model;

import exceptions.NoValidWorkoutException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Test class for WorkoutGenerator
public class WorkoutGeneratorTest {
    private Workout testWorkout;
    private WorkoutGenerator workoutGenerator;

    @BeforeEach
    public void runBefore() {
        testWorkout = null;
        workoutGenerator = WorkoutGenerator.getInstance();
    }

    @Test
    public void testNoExercisesWantedEmptyExercises() {
        try {
            testWorkout = workoutGenerator.generateWorkout("w1", 0, 0,
                    Difficulty.INTENSE, MuscleGroup.BACK, new ExercisesByName(), 0);
            fail("NoValidWorkoutException not caught when expected");
        } catch (NoValidWorkoutException e) {
            // success
        }
    }

    @Test
    public void testExercisesWantedEmptyExercises() {
        try {
            testWorkout = workoutGenerator.generateWorkout("w1", 2, 30,
                    Difficulty.INTENSE, MuscleGroup.BACK, new ExercisesByName(), 3);
            fail("NoValidWorkoutException not caught when expected");
        } catch (NoValidWorkoutException e) {
            // success
        }
    }

    @Test
    public void testNoExercisesWantedNonEmptyExercises() {
        ExercisesByName exercisesByName = new ExercisesByName();

        exercisesByName.addExercise(new CardioExercise("Sprints", MuscleGroup.LEGS, 10,
                Difficulty.INTENSE, 5));
        exercisesByName.addExercise(new BodyWeightsExercise("Pullups", MuscleGroup.SHOULDERS, 5, 5,
                Difficulty.MODERATE, 15));
        exercisesByName.addExercise(new WeightsExercise("Curls", MuscleGroup.ARMS, 50,
                3, 8, Difficulty.INTENSE, 15));

        try {
            testWorkout = workoutGenerator.generateWorkout("w1", 0, 5,
                    Difficulty.INTENSE, MuscleGroup.BACK, exercisesByName, 3);
        } catch (NoValidWorkoutException e) {
            fail("NoValidWorkoutException caught, none expected");
        }

        assertEquals(0, testWorkout.length());
    }

    @Test
    public void testExercisesWantedNonEmptyExercises() {
        ExercisesByName exercisesByName = new ExercisesByName();

        exercisesByName.addExercise(new CardioExercise("Sprints", MuscleGroup.LEGS, 10,
                Difficulty.MODERATE, 5));
        exercisesByName.addExercise(new BodyWeightsExercise("Pullups", MuscleGroup.BACK, 5, 5,
                Difficulty.INTENSE, 15));
        exercisesByName.addExercise(new WeightsExercise("Curls", MuscleGroup.ARMS, 50,
                3, 8, Difficulty.INTENSE, 15));

        try {
            testWorkout = workoutGenerator.generateWorkout("w1", 2, 20,
                    Difficulty.INTENSE, MuscleGroup.BACK, exercisesByName, 3);
        } catch (NoValidWorkoutException e) {
            fail("NoValidWorkoutException caught, none expected");
        }

        assertEquals(2, testWorkout.length());
        for (Exercise exercise : testWorkout.getExercises()) {
            System.out.println(exercise.toString());
        }
    }


    @Test
    public void testExercisesWantedNonEmptyExercisesLaterBranch() {
        ExercisesByName exercisesByName = new ExercisesByName();

        exercisesByName.addExercise(new CardioExercise("Sprints", MuscleGroup.LEGS, 10,
                Difficulty.MODERATE, 10));
        exercisesByName.addExercise(new BodyWeightsExercise("Pullups", MuscleGroup.BACK, 5, 5,
                Difficulty.INTENSE, 15));
        exercisesByName.addExercise(new WeightsExercise("Curls", MuscleGroup.ARMS, 50,
                3, 8, Difficulty.LIGHT, 3));

        try {
            testWorkout = workoutGenerator.generateWorkout("w1", 2, 20,
                    Difficulty.INTENSE, MuscleGroup.BACK, exercisesByName, 3);
        } catch (NoValidWorkoutException e) {
            fail("NoValidWorkoutException caught, none expected");
        }

        assertEquals(2, testWorkout.length());
        for (Exercise exercise : testWorkout.getExercises()) {
            System.out.println(exercise.toString());
        }
    }

    @Test
    public void testExercisesWantedNonEmptyExercisesNoSolution() {
        ExercisesByName exercisesByName = new ExercisesByName();

        exercisesByName.addExercise(new CardioExercise("Sprints", MuscleGroup.LEGS, 10,
                Difficulty.MODERATE, 10));
        exercisesByName.addExercise(new BodyWeightsExercise("Pullups", MuscleGroup.BACK, 5, 5,
                Difficulty.INTENSE, 15));
        exercisesByName.addExercise(new WeightsExercise("Curls", MuscleGroup.ARMS, 50,
                3, 8, Difficulty.LIGHT, 30));

        try {
            testWorkout = workoutGenerator.generateWorkout("w1", 2, 20,
                    Difficulty.INTENSE, MuscleGroup.BACK, exercisesByName, 3);
            fail("NoValidWorkoutException not caught when expected");
        } catch (NoValidWorkoutException e) {
            // success
        }

        assertNull(testWorkout);
    }
}
