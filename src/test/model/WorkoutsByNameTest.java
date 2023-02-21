package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.lang.Boolean.*;
import static model.WorkoutsByName.ADDITIONAL_WORKOUT_MESSAGE;
import static model.WorkoutsByName.DISPLAY_NUMBER_OF_WORKOUTS;
import static org.junit.jupiter.api.Assertions.*;

// Test class for WorkoutsByNameTest
public class WorkoutsByNameTest {
    private WorkoutsByName workoutsByNameTest1;

    @BeforeEach
    public void runBefore() {
        workoutsByNameTest1 = new WorkoutsByName();
    }

    @Test
    public void testConstructor() {
        WorkoutsByName workoutsByNameTest2 = new WorkoutsByName();

        assertTrue(workoutsByNameTest1.isEmpty());
        assertTrue(workoutsByNameTest2.isEmpty());
    }

    @Test
    public void testAddWorkoutOnceEmptyMap() {
        workoutsByNameTest1.addWorkout(new Workout("1", Difficulty.LIGHT));

        assertEquals(1, workoutsByNameTest1.length());
        assertEquals("1", workoutsByNameTest1.getWorkout("1").getName());
    }

    @Test
    public void testAddWorkoutOnceNonEmptyMap() {
        addWorkoutHelper(workoutsByNameTest1, 2);

        assertEquals(2, workoutsByNameTest1.length());

        workoutsByNameTest1.addWorkout(new Workout("3", Difficulty.INTENSE));

        assertEquals(3, workoutsByNameTest1.length());
        assertEquals("3", workoutsByNameTest1.getWorkout("3").getName());
    }

    @Test
    public void testAddWorkoutMultipleWorkoutsToMap() {
        addWorkoutHelper(workoutsByNameTest1, 2);

        assertEquals(2, workoutsByNameTest1.length());

        workoutsByNameTest1.addWorkout(new Workout("3", Difficulty.INTENSE));
        workoutsByNameTest1.addWorkout(new Workout("4", Difficulty.INTENSE));

        assertEquals(4, workoutsByNameTest1.length());
        assertEquals("3", workoutsByNameTest1.getWorkout("3").getName());
        assertEquals("4", workoutsByNameTest1.getWorkout("4").getName());
    }

    @Test
    public void testRemoveWorkoutOnceSingleWorkoutInMap() {
        addWorkoutHelper(workoutsByNameTest1, 1);

        assertEquals(1, workoutsByNameTest1.length());

        workoutsByNameTest1.removeWorkout("1");

        assertEquals(0, workoutsByNameTest1.length());
    }

    @Test
    public void testRemoveWorkoutOnceMultipleWorkoutsInMap() {
        addWorkoutHelper(workoutsByNameTest1, 3);

        assertEquals(3, workoutsByNameTest1.length());

        workoutsByNameTest1.removeWorkout("1");

        assertEquals(2, workoutsByNameTest1.length());
        assertEquals("2", workoutsByNameTest1.getWorkout("2").getName());
        assertEquals("3", workoutsByNameTest1.getWorkout("3").getName());
    }

    @Test
    public void testRemoveWorkoutMultipleMultipleWorkoutsInMap() {
        addWorkoutHelper(workoutsByNameTest1, 3);

        assertEquals(3, workoutsByNameTest1.length());

        workoutsByNameTest1.removeWorkout("1");
        workoutsByNameTest1.removeWorkout("2");

        assertEquals(1, workoutsByNameTest1.length());
        assertEquals("3", workoutsByNameTest1.getWorkout("3").getName());
    }

    @Test
    public void testToStringSingleWorkout() {
        addWorkoutHelper(workoutsByNameTest1, 1);

        assertEquals("Name\tDifficulty\tTime (min)\t# of Exercises\tFavourite?" + "\n"
                + "[1]\t1\t1\t0\tfalse" + "\n", workoutsByNameTest1.toString());
    }

    @Test
    public void testToStringMultipleWorkout() {
        addWorkoutHelper(workoutsByNameTest1, 2);

        assertEquals("Name\tDifficulty\tTime (min)\t# of Exercises\tFavourite?" + "\n"
                + "[1]\t1\t1\t0\tfalse" + "\n"
                + "[2]\t2\t2\t0\tfalse" + "\n", workoutsByNameTest1.toString());
    }

    @Test
    public void testToStringMultipleWorkoutBoundary() {
        addWorkoutHelper(workoutsByNameTest1, DISPLAY_NUMBER_OF_WORKOUTS);

        assertEquals("Name\tDifficulty\tTime (min)\t# of Exercises\tFavourite?" + "\n"
                + "[1]\t1\t1\t0\tfalse" + "\n"
                + "[2]\t2\t2\t0\tfalse" + "\n"
                + "[3]\t1\t3\t0\tfalse" + "\n"
                + "[4]\t2\t4\t0\tfalse" + "\n"
                + "[5]\t1\t5\t0\tfalse" + "\n"
                + "[6]\t2\t6\t0\tfalse" + "\n"
                + "[7]\t1\t7\t0\tfalse" + "\n"
                + "[8]\t2\t8\t0\tfalse" + "\n"
                + "[9]\t1\t9\t0\tfalse" + "\n"
                + "[10]\t2\t10\t0\tfalse" + "\n", workoutsByNameTest1.toString());
    }

    @Test
    public void testToStringMultipleWorkoutGreaterThanBoundary() {
        addWorkoutHelper(workoutsByNameTest1, DISPLAY_NUMBER_OF_WORKOUTS + 1);

        assertEquals("Name\tDifficulty\tTime (min)\t# of Exercises\tFavourite?" + "\n"
                + "[1]\t1\t1\t0\tfalse" + "\n"
                + "[2]\t2\t2\t0\tfalse" + "\n"
                + "[3]\t1\t3\t0\tfalse" + "\n"
                + "[4]\t2\t4\t0\tfalse" + "\n"
                + "[5]\t1\t5\t0\tfalse" + "\n"
                + "[6]\t2\t6\t0\tfalse" + "\n"
                + "[7]\t1\t7\t0\tfalse" + "\n"
                + "[8]\t2\t8\t0\tfalse" + "\n"
                + "[9]\t1\t9\t0\tfalse" + "\n"
                + "[10]\t2\t10\t0\tfalse" + "\n"
                + "... with " + 1 + ADDITIONAL_WORKOUT_MESSAGE, workoutsByNameTest1.toString());
    }

    @Test
    public void testFilterByNameSingleWorkoutInMapAndMatches() {
        addWorkoutHelper(workoutsByNameTest1, 1);

        WorkoutsByName workoutsByName = workoutsByNameTest1.filter("1");

        assertEquals(1, workoutsByName.length());
        assertEquals("1", workoutsByName.getWorkout("1").getName());
    }

    @Test
    public void testFilterByNameMultipleWorkoutInMapAndMatches() {
        addWorkoutHelper(workoutsByNameTest1, 10);

        WorkoutsByName workoutsByName = workoutsByNameTest1.filter("1");

        assertEquals(2, workoutsByName.length());
        assertEquals("1", workoutsByName.getWorkout("1").getName());
        assertEquals("10", workoutsByName.getWorkout("10").getName());
    }

    @Test
    public void testFilterByNameMultipleWorkoutInMapCaseInsensitive() {
        workoutsByNameTest1.addWorkout(new Workout("abc", Difficulty.INTENSE));
        workoutsByNameTest1.addWorkout(new Workout("AB", Difficulty.LIGHT));
        workoutsByNameTest1.addWorkout(new Workout("aC", Difficulty.LIGHT));
        workoutsByNameTest1.addWorkout(new Workout("DEF", Difficulty.INTENSE));
        workoutsByNameTest1.addWorkout(new Workout("de", Difficulty.MODERATE));
        workoutsByNameTest1.addWorkout(new Workout("D", Difficulty.INTENSE));

        WorkoutsByName workoutsByName1 = workoutsByNameTest1.filter("a");
        WorkoutsByName workoutsByName2 = workoutsByNameTest1.filter("De");

        assertEquals(3, workoutsByName1.length());
        assertEquals("abc", workoutsByName1.getWorkout("abc").getName());
        assertEquals("AB", workoutsByName1.getWorkout("AB").getName());
        assertEquals("aC", workoutsByName1.getWorkout("aC").getName());

        assertEquals(2, workoutsByName2.length());
        assertEquals("DEF", workoutsByName1.getWorkout("DEF").getName());
        assertEquals("de", workoutsByName1.getWorkout("de").getName());
    }

    @Test
    public void testFilterByDifficultySingleWorkoutInMapAndMatches() {
        addWorkoutHelper(workoutsByNameTest1, 1);

        WorkoutsByName workoutsByName = workoutsByNameTest1.filterDifficulty(Difficulty.LIGHT);

        assertEquals(1, workoutsByName.length());
        assertEquals("1", workoutsByName.getWorkout("1").getName());
    }

    @Test
    public void testFilterByDifficultyMultipleWorkoutInMapAndMatches() {
        addWorkoutHelper(workoutsByNameTest1, 4);

        WorkoutsByName workoutsByName = workoutsByNameTest1.filterDifficulty(Difficulty.MODERATE);

        assertEquals(2, workoutsByName.length());
        assertEquals("2", workoutsByName.getWorkout("2").getName());
        assertEquals("4", workoutsByName.getWorkout("4").getName());
    }

    @Test
    public void testFilterByTimeSingleWorkoutInMapGreaterThan() {
        addWorkoutHelper(workoutsByNameTest1, 1);
        workoutsByNameTest1.getWorkout("1").addExercise(new CardioExercise("Running", MuscleGroup.LEGS,
                1, Difficulty.INTENSE, 5));

        WorkoutsByName workoutsByName = workoutsByNameTest1.filterTime(2);

        assertEquals(0, workoutsByName.length());
    }

    @Test
    public void testFilterByTimeSingleWorkoutInMapEqualTo() {
        addWorkoutHelper(workoutsByNameTest1, 1);
        workoutsByNameTest1.getWorkout("1").addExercise(new CardioExercise("Running", MuscleGroup.LEGS,
                1, Difficulty.INTENSE, 3));

        WorkoutsByName workoutsByName = workoutsByNameTest1.filterTime(3);

        assertEquals(1, workoutsByName.length());
        assertEquals("1", workoutsByName.getWorkout("1").getName());
    }

    @Test
    public void testFilterByTimeSingleWorkoutInMapLessThan() {
        addWorkoutHelper(workoutsByNameTest1, 1);
        workoutsByNameTest1.getWorkout("1").addExercise(new CardioExercise("Running", MuscleGroup.LEGS,
                1, Difficulty.INTENSE, 1));

        WorkoutsByName workoutsByName = workoutsByNameTest1.filterTime(2);

        assertEquals(1, workoutsByName.length());
        assertEquals("1", workoutsByName.getWorkout("1").getName());
    }

    @Test
    public void testFilterByTimeMultipleWorkoutInMap() {
        addWorkoutHelper(workoutsByNameTest1, 3);
        workoutsByNameTest1.getWorkout("1").addExercise(new CardioExercise("Running", MuscleGroup.LEGS,
                1, Difficulty.INTENSE, 1));
        workoutsByNameTest1.getWorkout("1").addExercise(new WeightsExercise("Press", MuscleGroup.CHEST,
                100, 1, 1,  Difficulty.INTENSE, 2));
        workoutsByNameTest1.getWorkout("2").addExercise(new CardioExercise("Sprints", MuscleGroup.LEGS,
                10, Difficulty.INTENSE, 5));
        workoutsByNameTest1.getWorkout("2").addExercise(new BodyWeightsExercise("Squats", MuscleGroup.LEGS,
                5, 3, Difficulty.INTENSE, 5));
        workoutsByNameTest1.getWorkout("3").addExercise(new CardioExercise("200m", MuscleGroup.LEGS,
                200, Difficulty.INTENSE, 10));
        workoutsByNameTest1.getWorkout("3").addExercise(new CardioExercise("1000m", MuscleGroup.LEGS,
                1000, Difficulty.INTENSE, 20));

        WorkoutsByName workoutsByName = workoutsByNameTest1.filterTime(10);

        assertEquals(2, workoutsByName.length());
        assertEquals("2", workoutsByName.getWorkout("2").getName());
        assertEquals("3", workoutsByName.getWorkout("3").getName());
    }

    @Test
    public void testFilterByNumberOfExercisesSingleWorkoutInMapGreaterThan() {
        addWorkoutHelper(workoutsByNameTest1, 3);
        workoutsByNameTest1.getWorkout("1").addExercise(new CardioExercise("Running", MuscleGroup.LEGS,
                1, Difficulty.INTENSE, 1));
        workoutsByNameTest1.getWorkout("1").addExercise(new WeightsExercise("Press", MuscleGroup.CHEST,
                100, 1, 1,  Difficulty.INTENSE, 2));
        workoutsByNameTest1.getWorkout("1").addExercise(new BodyWeightsExercise("Pullups",
                MuscleGroup.SHOULDERS, 3, 5, Difficulty.INTENSE, 2));

        WorkoutsByName workoutsByName = workoutsByNameTest1.filterNumberOfExercises(2);

        assertEquals(0, workoutsByName.length());
    }

    @Test
    public void testFilterByNumberOfExercisesSingleWorkoutInMapEqualTo() {
        addWorkoutHelper(workoutsByNameTest1, 1);
        workoutsByNameTest1.getWorkout("1").addExercise(new CardioExercise("Running", MuscleGroup.LEGS,
                1, Difficulty.INTENSE, 1));
        workoutsByNameTest1.getWorkout("1").addExercise(new WeightsExercise("Press", MuscleGroup.CHEST,
                100, 1, 1,  Difficulty.INTENSE, 2));
        workoutsByNameTest1.getWorkout("1").addExercise(new BodyWeightsExercise("Pullups",
                MuscleGroup.SHOULDERS, 3, 5, Difficulty.INTENSE, 2));

        WorkoutsByName workoutsByName = workoutsByNameTest1.filterNumberOfExercises(3);

        assertEquals(1, workoutsByName.length());
        assertEquals("1", workoutsByName.getWorkout("1").getName());
    }

    @Test
    public void testFilterByNumberOfExercisesSingleWorkoutInMapLessThan() {
        addWorkoutHelper(workoutsByNameTest1, 1);
        workoutsByNameTest1.getWorkout("1").addExercise(new CardioExercise("Running", MuscleGroup.LEGS,
                1, Difficulty.INTENSE, 1));
        workoutsByNameTest1.getWorkout("1").addExercise(new WeightsExercise("Press", MuscleGroup.CHEST,
                100, 1, 1,  Difficulty.INTENSE, 2));
        workoutsByNameTest1.getWorkout("1").addExercise(new BodyWeightsExercise("Pullups",
                MuscleGroup.SHOULDERS, 3, 5, Difficulty.INTENSE, 2));

        WorkoutsByName workoutsByName = workoutsByNameTest1.filterNumberOfExercises(4);

        assertEquals(1, workoutsByName.length());
        assertEquals("1", workoutsByName.getWorkout("1").getName());
    }

    @Test
    public void testFilterByNumberOfExercisesMultipleWorkoutInMap() {
        addWorkoutHelper(workoutsByNameTest1, 3);
        workoutsByNameTest1.getWorkout("1").addExercise(new CardioExercise("Running", MuscleGroup.LEGS,
                1, Difficulty.INTENSE, 1));
        workoutsByNameTest1.getWorkout("1").addExercise(new WeightsExercise("Press", MuscleGroup.CHEST,
                100, 1, 1,  Difficulty.INTENSE, 2));
        workoutsByNameTest1.getWorkout("1").addExercise(new BodyWeightsExercise("Pullups",
                MuscleGroup.SHOULDERS, 3, 5, Difficulty.INTENSE, 2));
        workoutsByNameTest1.getWorkout("1").addExercise(new BodyWeightsExercise("Pushups",
                MuscleGroup.ARMS, 3, 5, Difficulty.INTENSE, 2));
        workoutsByNameTest1.getWorkout("2").addExercise(new BodyWeightsExercise("Squats", MuscleGroup.LEGS,
                5, 3, Difficulty.INTENSE, 5));
        workoutsByNameTest1.getWorkout("3").addExercise(new CardioExercise("200m", MuscleGroup.LEGS,
                200, Difficulty.INTENSE, 10));
        workoutsByNameTest1.getWorkout("3").addExercise(new CardioExercise("1000m", MuscleGroup.LEGS,
                1000, Difficulty.INTENSE, 20));

        WorkoutsByName workoutsByName = workoutsByNameTest1.filterNumberOfExercises(2);

        assertEquals(2, workoutsByName.length());
        assertEquals("1", workoutsByName.getWorkout("1").getName());
        assertEquals("3", workoutsByName.getWorkout("3").getName());
    }

    @Test
    public void testFilterFavouriteSingleWorkoutInMapAndMatches() {
        addWorkoutHelper(workoutsByNameTest1, 1);

        workoutsByNameTest1.getWorkout("1").setFavourite(TRUE);

        WorkoutsByName workoutsByName = workoutsByNameTest1.filterFavourite();

        assertEquals(1, workoutsByName.length());
        assertEquals("1", workoutsByName.getWorkout("1").getName());
    }

    @Test
    public void testFilterFavouriteMultipleWorkoutInMapAndMatches() {
        addWorkoutHelper(workoutsByNameTest1, 5);

        workoutsByNameTest1.getWorkout("1").setFavourite(TRUE);
        workoutsByNameTest1.getWorkout("3").setFavourite(TRUE);
        workoutsByNameTest1.getWorkout("5").setFavourite(TRUE);

        WorkoutsByName workoutsByName = workoutsByNameTest1.filterFavourite();

        assertEquals(3, workoutsByName.length());
        assertEquals("1", workoutsByName.getWorkout("1").getName());
        assertEquals("3", workoutsByName.getWorkout("3").getName());
        assertEquals("5", workoutsByName.getWorkout("5").getName());
    }

    @Test
    public void testContainsSingleWorkoutAndDoesContain() {
        addWorkoutHelper(workoutsByNameTest1, 1);

        assertTrue(workoutsByNameTest1.contains("1"));
    }

    @Test
    public void testContainsSingleWorkoutAndDoesNotContain() {
        addWorkoutHelper(workoutsByNameTest1, 1);

        assertFalse(workoutsByNameTest1.contains("2"));
    }

    @Test
    public void testContainsMultipleWorkoutAndDoesContain() {
        addWorkoutHelper(workoutsByNameTest1, 3);

        assertTrue(workoutsByNameTest1.contains("1"));
        assertTrue(workoutsByNameTest1.contains("2"));
        assertTrue(workoutsByNameTest1.contains("3"));
    }

    @Test
    public void testContainsMultipleWorkoutAndDoesNotContain() {
        addWorkoutHelper(workoutsByNameTest1, 3);

        assertFalse(workoutsByNameTest1.contains("11"));
        assertFalse(workoutsByNameTest1.contains("4"));
        assertFalse(workoutsByNameTest1.contains("7"));
    }

    @Test
    public void testIsEmptyEmptyMap() {
        assertTrue(workoutsByNameTest1.isEmpty());
    }

    @Test
    public void testIsEmptyNonEmptyMap() {
        addWorkoutHelper(workoutsByNameTest1, 3);

        assertFalse(workoutsByNameTest1.isEmpty());
    }

    @Test
    public void testLengthEmptyMap() {
        assertEquals(0, workoutsByNameTest1.length());
    }

    @Test
    public void testLengthSingleWorkoutMap() {
        addWorkoutHelper(workoutsByNameTest1, 1);

        assertEquals(1, workoutsByNameTest1.length());
    }

    @Test
    public void testLengthMultipleWorkoutMap() {
        addWorkoutHelper(workoutsByNameTest1, 3);

        assertEquals(3, workoutsByNameTest1.length());
    }

    @Test
    public void testGetWorkoutOnce() {
        Workout workout1 = new Workout("1", Difficulty.LIGHT);

        workoutsByNameTest1.addWorkout(workout1);

        assertEquals(workout1, workoutsByNameTest1.getWorkout("1"));
    }

    @Test
    public void testGetWorkoutMultiple() {
        Workout workout1 = new Workout("1", Difficulty.LIGHT);
        Workout workout2 = new Workout("2", Difficulty.MODERATE);

        workoutsByNameTest1.addWorkout(workout1);
        workoutsByNameTest1.addWorkout(workout2);

        assertEquals(workout1, workoutsByNameTest1.getWorkout("1"));
        assertEquals(workout2, workoutsByNameTest1.getWorkout("2"));
    }

    private void addWorkoutHelper(WorkoutsByName workoutsByName, int repeats) {
        for (int i = 0; i < repeats; i++) {
            if ((i + 1) % 2 == 0) {
                workoutsByName.addWorkout(new Workout(Integer.toString(i + 1), Difficulty.MODERATE));
            } else {
                workoutsByName.addWorkout(new Workout(Integer.toString(i + 1), Difficulty.LIGHT));
            }
        }
    }
}
