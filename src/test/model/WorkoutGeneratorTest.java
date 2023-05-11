package model;

import exceptions.NoValidWorkoutException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Test class for WorkoutGenerator
public class WorkoutGeneratorTest {
    private Workout testWorkout;
    private WorkoutGenerator workoutGenerator;

    private ExercisesByName exercisesByName;
    private Exercise e1;
    private Exercise e2;
    private Exercise e3;

    @BeforeEach
    public void runBefore() {
        testWorkout = null;
        workoutGenerator = WorkoutGenerator.getInstance();

        exercisesByName = new ExercisesByName();
        e1 = new CardioExercise("Sprints", MuscleGroup.LEGS, 10,
                Difficulty.LIGHT, 5);
        e2 = new BodyWeightsExercise("Pullups", MuscleGroup.BACK, 5, 5,
                Difficulty.INTENSE, 15);
        e3 = new WeightsExercise("Curls", MuscleGroup.ARMS, 50,
                3, 8, Difficulty.MODERATE, 15);
    }

    @Test
    public void testNoExercisesWantedEmptyExercises() {
        try {
            testWorkout = workoutGenerator.generateWorkout("w1", 0, 0,
                    Difficulty.INTENSE, MuscleGroup.BACK, new ExercisesByName(), 0, new MockRandom());
            fail("NoValidWorkoutException not caught when expected");
        } catch (NoValidWorkoutException e) {
            // success
        }
    }

    @Test
    public void testExercisesWantedEmptyExercises() {
        try {
            testWorkout = workoutGenerator.generateWorkout("w1", 2, 30,
                    Difficulty.INTENSE, MuscleGroup.BACK, new ExercisesByName(), 3, new MockRandom());
            fail("NoValidWorkoutException not caught when expected");
        } catch (NoValidWorkoutException e) {
            // success
        }

        assertNull(testWorkout);
    }

    @Test
    public void testNoExercisesWantedNonEmptyExercises() {
        exercisesByName.addExercise(e1);
        exercisesByName.addExercise(e2);
        exercisesByName.addExercise(e3);

        try {
            testWorkout = workoutGenerator.generateWorkout("w1", 0, 5,
                    Difficulty.INTENSE, MuscleGroup.LEGS, exercisesByName, 3, new MockRandom());
        } catch (NoValidWorkoutException e) {
            fail("NoValidWorkoutException caught, none expected");
        }

        assertEquals(0, testWorkout.length());
    }

    @Test
    public void testExercisesWantedNonEmptyExercises() {
        exercisesByName.addExercise(e1);
        exercisesByName.addExercise(e2);
        exercisesByName.addExercise(e3);

        try {
            testWorkout = workoutGenerator.generateWorkout("w1", 2, 20,
                    Difficulty.INTENSE, MuscleGroup.BACK, exercisesByName, 3, new MockRandom());
        } catch (NoValidWorkoutException e) {
            fail("NoValidWorkoutException caught, none expected");
        }

        assertEquals(2, testWorkout.length());
        assertEquals(e1, testWorkout.getExercise("Sprints"));
        assertEquals(e2, testWorkout.getExercise("Pullups"));
    }


    @Test
    public void testExercisesWantedNonEmptyExercisesLaterBranch() {
        Exercise e1 = new CardioExercise("Sprints", MuscleGroup.LEGS, 10,
                Difficulty.MODERATE, 10);
        Exercise e2 = new BodyWeightsExercise("Pullups", MuscleGroup.BACK, 5, 5,
                Difficulty.LIGHT, 15);
        Exercise e3 = new WeightsExercise("Curls", MuscleGroup.ARMS, 50,
                3, 8, Difficulty.LIGHT, 5);

        exercisesByName.addExercise(e1);
        exercisesByName.addExercise(e2);
        exercisesByName.addExercise(e3);

        try {
            testWorkout = workoutGenerator.generateWorkout("w1", 2, 23,
                    Difficulty.LIGHT, MuscleGroup.BACK, exercisesByName, 3, new MockRandom());
        } catch (NoValidWorkoutException e) {
            fail("NoValidWorkoutException caught, none expected");
        }

        assertEquals(2, testWorkout.length());
        assertEquals(e2, testWorkout.getExercise("Pullups"));
        assertEquals(e3, testWorkout.getExercise("Curls"));
    }

    @Test
    public void testExercisesWantedNonEmptyExercisesMultipleSolutions() {
        ExercisesByName exercisesByName = new ExercisesByName();
        Exercise e1 = new CardioExercise("Sprints", MuscleGroup.LEGS, 10,
                Difficulty.MODERATE, 10);
        Exercise e2 = new CardioExercise("Suicides", MuscleGroup.LEGS, 10,
                Difficulty.MODERATE, 10);
        Exercise e3 = new BodyWeightsExercise("Deadlifts", MuscleGroup.CHEST, 5, 5,
                Difficulty.LIGHT, 5);
        Exercise e4 = new BodyWeightsExercise("Pullups", MuscleGroup.BACK, 5, 5,
                Difficulty.INTENSE, 15);
        Exercise e5 = new WeightsExercise("Curls", MuscleGroup.ARMS, 50,
                3, 8, Difficulty.LIGHT, 3);

        exercisesByName.addExercise(e1);
        exercisesByName.addExercise(e2);
        exercisesByName.addExercise(e3);
        exercisesByName.addExercise(e4);
        exercisesByName.addExercise(e5);

        try {
            testWorkout = workoutGenerator.generateWorkout("w1", 2, 25,
                    Difficulty.LIGHT, MuscleGroup.BACK, exercisesByName, 5, new MockRandom());
        } catch (NoValidWorkoutException e) {
            fail("NoValidWorkoutException caught, none expected");
        }

        assertEquals(2, testWorkout.length());
        assertEquals(e3, testWorkout.getExercise("Deadlifts"));
        assertEquals(e4, testWorkout.getExercise("Pullups"));
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
                    Difficulty.INTENSE, MuscleGroup.SHOULDERS, exercisesByName, 3, new MockRandom());
            fail("NoValidWorkoutException not caught when expected");
        } catch (NoValidWorkoutException e) {
            // success
        }

        assertNull(testWorkout);
    }
}
