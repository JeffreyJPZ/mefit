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
        WorkoutParameters parameters = new WorkoutParameters
                .WorkoutParametersBuilder(MuscleGroup.BACK, Difficulty.INTENSE, 0, 0, 0)
                .build();
        try {
            testWorkout = workoutGenerator.generateWorkout("w1", new ExercisesByName(),
                    parameters, new MockRandom(0));
            fail("NoValidWorkoutException not caught when expected");
        } catch (NoValidWorkoutException e) {
            // success
        }
    }

    @Test
    public void testExercisesWantedEmptyExercises() {
        WorkoutParameters parameters = new WorkoutParameters
                .WorkoutParametersBuilder(MuscleGroup.BACK, Difficulty.INTENSE, 2, 30, 3)
                .build();
        try {
            testWorkout = workoutGenerator.generateWorkout("w1", new ExercisesByName(),
                    parameters, new MockRandom(0));
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

        WorkoutParameters parameters = new WorkoutParameters
                .WorkoutParametersBuilder(MuscleGroup.BACK, Difficulty.INTENSE, 0, 5, 3)
                .build();

        try {
            testWorkout = workoutGenerator.generateWorkout("w1", exercisesByName,
                    parameters, new MockRandom(0));
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

        WorkoutParameters parameters = new WorkoutParameters
                .WorkoutParametersBuilder(MuscleGroup.BACK, Difficulty.INTENSE, 2, 20, 3)
                .build();

        try {
            testWorkout = workoutGenerator.generateWorkout("w1", exercisesByName,
                    parameters, new MockRandom(0));
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
                3, 8, Difficulty.MODERATE, 5);

        exercisesByName.addExercise(e1);
        exercisesByName.addExercise(e2);
        exercisesByName.addExercise(e3);

        WorkoutParameters parameters = new WorkoutParameters
                .WorkoutParametersBuilder(MuscleGroup.BACK, Difficulty.MODERATE, 2, 23, 3)
                .build();

        try {
            testWorkout = workoutGenerator.generateWorkout("w1", exercisesByName,
                    parameters, new MockRandom(0));
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

        WorkoutParameters parameters = new WorkoutParameters
                .WorkoutParametersBuilder(MuscleGroup.BACK, Difficulty.LIGHT, 2, 25, 5)
                .build();

        try {
            testWorkout = workoutGenerator.generateWorkout("w1", exercisesByName,
                    parameters, new MockRandom(0));
        } catch (NoValidWorkoutException e) {
            fail("NoValidWorkoutException caught, none expected");
        }

        assertEquals(2, testWorkout.length());
        for (Exercise e : testWorkout.getExercises()) {
            System.out.println(e.toString());
        }
        assertEquals(e4, testWorkout.getExercise("Pullups"));
        assertEquals(e5, testWorkout.getExercise("Curls"));
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

        WorkoutParameters parameters = new WorkoutParameters
                .WorkoutParametersBuilder(MuscleGroup.SHOULDERS, Difficulty.INTENSE, 2, 20, 3)
                .build();

        try {
            testWorkout = workoutGenerator.generateWorkout("w1", exercisesByName,
                    parameters, new MockRandom(0));
            fail("NoValidWorkoutException not caught when expected");
        } catch (NoValidWorkoutException e) {
            // success
        }

        assertNull(testWorkout);
    }

    @Test
    public void testExercisesWantedSampleSizeNotEnough() {
        ExercisesByName exercisesByName = new ExercisesByName();

        exercisesByName.addExercise(new CardioExercise("Sprints", MuscleGroup.LEGS, 10,
                Difficulty.MODERATE, 10));
        exercisesByName.addExercise(new BodyWeightsExercise("Pullups", MuscleGroup.BACK, 5, 5,
                Difficulty.INTENSE, 15));
        exercisesByName.addExercise(new WeightsExercise("Curls", MuscleGroup.ARMS, 50,
                3, 8, Difficulty.LIGHT, 30));

        WorkoutParameters parameters = new WorkoutParameters
                .WorkoutParametersBuilder(MuscleGroup.SHOULDERS, Difficulty.INTENSE, 3, 20, 1)
                .build();

        try {
            testWorkout = workoutGenerator.generateWorkout("w1", exercisesByName,
                    parameters, new MockRandom(0));
            fail("NoValidWorkoutException not caught when expected");
        } catch (NoValidWorkoutException e) {
            // success
        }

        assertNull(testWorkout);
    }
}
