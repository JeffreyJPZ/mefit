package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.lang.Boolean.*;
import static model.ExercisesByName.ADDITIONAL_EXERCISE_MESSAGE;
import static model.ExercisesByName.DISPLAY_NUMBER_OF_EXERCISES;
import static org.junit.jupiter.api.Assertions.*;

// Test class for ExercisesByNameTest
public class ExercisesByNameTest {
    private ExercisesByName exercisesByNameTest1;

    @BeforeEach
    public void runBefore() {
        exercisesByNameTest1 = new ExercisesByName();
    }

    @Test
    public void testConstructorEmptyMap() {
        assertTrue(exercisesByNameTest1.isEmpty());
    }

    @Test
    public void testAddExerciseOnceEmptyMap() {
        addExerciseHelper(exercisesByNameTest1, 1);

        assertEquals(1, exercisesByNameTest1.length());

        Exercise exercise1 = exercisesByNameTest1.getExercise("1");

        assertEquals("1", exercise1.getName());
        assertEquals("Chest", exercise1.getMuscleGroup());
        assertEquals(1, exercise1.getDifficulty());
        assertEquals(1, exercise1.getTime());
        assertFalse(exercise1.isFavourite());
    }

    @Test
    public void testAddExerciseOnceNonEmptyMap() {
        addExerciseHelper(exercisesByNameTest1, 2);

        assertEquals(2, exercisesByNameTest1.length());

        exercisesByNameTest1.addExercise(new CardioExercise("3", "Legs", 3, 3, 3));

        assertEquals(3, exercisesByNameTest1.length());

        Exercise exercise3 = exercisesByNameTest1.getExercise("3");

        assertEquals("3", exercise3.getName());
        assertEquals("Legs", exercise3.getMuscleGroup());
        assertEquals(3, exercise3.getDifficulty());
        assertEquals(3, exercise3.getTime());
        assertFalse(exercise3.isFavourite());
    }

    @Test
    public void testAddExerciseMultipleEmptyMap() {
        exercisesByNameTest1.addExercise(new WeightsExercise("1", "Chest", 1, 1, 1,
                                                            1, 1));
        exercisesByNameTest1.addExercise(new BodyWeightsExercise("2", "Core", 2, 2,
                                                            2, 2));

        assertEquals(2, exercisesByNameTest1.length());

        Exercise exercise1 = exercisesByNameTest1.getExercise("1");
        Exercise exercise2 = exercisesByNameTest1.getExercise("2");

        assertEquals("1", exercise1.getName());
        assertEquals("Chest", exercise1.getMuscleGroup());
        assertEquals(1, exercise1.getDifficulty());
        assertEquals(1, exercise1.getTime());
        assertFalse(exercise1.isFavourite());

        assertEquals("2", exercise2.getName());
        assertEquals("Core", exercise2.getMuscleGroup());
        assertEquals(2, exercise2.getDifficulty());
        assertEquals(2, exercise2.getTime());
        assertFalse(exercise2.isFavourite());
    }

    @Test
    public void testAddExerciseMultipleNonEmptyMap() {
        addExerciseHelper(exercisesByNameTest1, 2);

        assertEquals(2, exercisesByNameTest1.length());

        exercisesByNameTest1.addExercise(new CardioExercise("3", "Legs", 3, 3, 3));
        exercisesByNameTest1.addExercise(new WeightsExercise("4", "Chest", 4, 4, 4,
                                                            1, 4));

        Exercise exercise3 = exercisesByNameTest1.getExercise("3");
        Exercise exercise4 = exercisesByNameTest1.getExercise("4");

        assertEquals("3", exercise3.getName());
        assertEquals("Legs", exercise3.getMuscleGroup());
        assertEquals(3, exercise3.getDifficulty());
        assertEquals(3, exercise3.getTime());
        assertFalse(exercise3.isFavourite());

        assertEquals("4", exercise4.getName());
        assertEquals("Chest", exercise4.getMuscleGroup());
        assertEquals(1, exercise4.getDifficulty());
        assertEquals(4, exercise4.getTime());
        assertFalse(exercise4.isFavourite());
    }

    @Test
    public void testRemoveExerciseOnceSingleProfileMap() {
        addExerciseHelper(exercisesByNameTest1, 1);

        assertEquals(1, exercisesByNameTest1.length());

        exercisesByNameTest1.removeExercise("1");

        assertEquals(0, exercisesByNameTest1.length());
    }

    @Test
    public void testRemoveExerciseOnceMultipleProfileMap() {
        addExerciseHelper(exercisesByNameTest1, 2);

        assertEquals(2, exercisesByNameTest1.length());

        exercisesByNameTest1.removeExercise("1");

        assertEquals(1, exercisesByNameTest1.length());
        assertEquals("2", exercisesByNameTest1.getExercise("2").getName());
    }

    @Test
    public void testRemoveExerciseMultipleProfileMap() {
        addExerciseHelper(exercisesByNameTest1, 5);

        assertEquals(5, exercisesByNameTest1.length());

        exercisesByNameTest1.removeExercise("1");
        exercisesByNameTest1.removeExercise("3");
        exercisesByNameTest1.removeExercise("5");

        assertEquals(2, exercisesByNameTest1.length());
        assertEquals("2", exercisesByNameTest1.getExercise("2").getName());
        assertEquals("4", exercisesByNameTest1.getExercise("4").getName());
    }

    @Test
    public void testToStringOneExerciseInMap() {
        addExerciseHelper(exercisesByNameTest1, 1);

        assertEquals("Name\tMuscle Group\tDifficulty\tTime (min)\t Favourite?" + "\n"
                            + "[1]\tChest\t1\t1\tfalse" + "\n", exercisesByNameTest1.toString());
    }

    @Test
    public void testToStringMultipleExerciseInMap() {
        addExerciseHelper(exercisesByNameTest1, 2);

        assertEquals("Name\tMuscle Group\tDifficulty\tTime (min)\t Favourite?" + "\n"
                            + "[1]\tChest\t1\t1\tfalse" + "\n"
                            + "[2]\tCore\t2\t2\tfalse" + "\n", exercisesByNameTest1.toString());
    }

    @Test
    public void testToStringMultipleExerciseInMapBoundary() {
        addExerciseHelper(exercisesByNameTest1, DISPLAY_NUMBER_OF_EXERCISES);

        assertEquals("Name\tMuscle Group\tDifficulty\tTime (min)\t Favourite?" + "\n"
                            + "[1]\tChest\t1\t1\tfalse" + "\n"
                            + "[2]\tCore\t2\t2\tfalse" + "\n"
                            + "[3]\tChest\t1\t3\tfalse" + "\n"
                            + "[4]\tCore\t2\t4\tfalse" + "\n"
                            + "[5]\tChest\t1\t5\tfalse" + "\n"
                            + "[6]\tCore\t2\t6\tfalse" + "\n"
                            + "[7]\tChest\t1\t7\tfalse" + "\n"
                            + "[8]\tCore\t2\t8\tfalse" + "\n"
                            + "[9]\tChest\t1\t9\tfalse" + "\n"
                            + "[10]\tCore\t2\t10\tfalse" + "\n", exercisesByNameTest1.toString());
    }

    @Test
    public void testToStringMultipleExerciseInMapGreaterThanBoundary() {
        addExerciseHelper(exercisesByNameTest1, DISPLAY_NUMBER_OF_EXERCISES + 1);

        assertEquals("Name\tMuscle Group\tDifficulty\tTime (min)\t Favourite?" + "\n"
                            + "[1]\tChest\t1\t1\tfalse" + "\n"
                            + "[2]\tCore\t2\t2\tfalse" + "\n"
                            + "[3]\tChest\t1\t3\tfalse" + "\n"
                            + "[4]\tCore\t2\t4\tfalse" + "\n"
                            + "[5]\tChest\t1\t5\tfalse" + "\n"
                            + "[6]\tCore\t2\t6\tfalse" + "\n"
                            + "[7]\tChest\t1\t7\tfalse" + "\n"
                            + "[8]\tCore\t2\t8\tfalse" + "\n"
                            + "[9]\tChest\t1\t9\tfalse" + "\n"
                            + "[10]\tCore\t2\t10\tfalse" + "\n"
                            + "... with " + 1 + ADDITIONAL_EXERCISE_MESSAGE, exercisesByNameTest1.toString());
    }

    @Test
    public void testFilterSingleExerciseInMapMatches() {
        addExerciseHelper(exercisesByNameTest1, 1);
        ExercisesByName exercisesByName = exercisesByNameTest1.filter("1");

        assertEquals(1, exercisesByName.getExercises().size());
        assertEquals("1", exercisesByName.getExercises().get("1").getName());
    }

    @Test
    public void testFilterMultipleExerciseInMapMatches() {
        addExerciseHelper(exercisesByNameTest1, 11);
        ExercisesByName exercisesByName = exercisesByNameTest1.filter("1");

        assertEquals(3, exercisesByName.getExercises().size());
        assertEquals("1", exercisesByName.getExercises().get("1").getName());
        assertEquals("10", exercisesByName.getExercises().get("10").getName());
        assertEquals("11", exercisesByName.getExercises().get("11").getName());
    }

    @Test
    public void testFilterMultipleExerciseInMapMatchesCaseInsensitive() {
        exercisesByNameTest1.addExercise(new BodyWeightsExercise("abc", "Core",
                1, 1, 1, 1));
        exercisesByNameTest1.addExercise(new BodyWeightsExercise("ABC", "Core",
                1, 1, 1, 1));
        exercisesByNameTest1.addExercise(new BodyWeightsExercise("DEF", "Core",
                2, 2, 2, 2));
        exercisesByNameTest1.addExercise(new BodyWeightsExercise("def", "Core",
                2, 2, 2, 2));

        ExercisesByName exercisesByName1 = exercisesByNameTest1.filter("ABC");
        ExercisesByName exercisesByName2 = exercisesByNameTest1.filter("D");

        assertEquals(2, exercisesByName1.getExercises().size());
        assertEquals("abc", exercisesByName1.getExercises().get("abc").getName());
        assertEquals("ABC", exercisesByName1.getExercises().get("ABC").getName());

        assertEquals(2, exercisesByName2.getExercises().size());
        assertEquals("DEF", exercisesByName2.getExercises().get("DEF").getName());
        assertEquals("def", exercisesByName2.getExercises().get("def").getName());
    }

    @Test
    public void testFilterMuscleGroupSingleExerciseInMapMatches() {
        addExerciseHelper(exercisesByNameTest1, 1);
        ExercisesByName exercisesByName1 = exercisesByNameTest1.filterMuscleGroup("Chest");

        assertEquals(1, exercisesByName1.getExercises().size());
        assertEquals("Chest", exercisesByName1.getExercises().get("1").getMuscleGroup());
    }

    @Test
    public void testFilterMuscleGroupMultipleExerciseInMapMatches() {
        addExerciseHelper(exercisesByNameTest1, 4);
        ExercisesByName exercisesByName1 = exercisesByNameTest1.filterMuscleGroup("Core");

        assertEquals(2, exercisesByName1.getExercises().size());
        assertEquals("Core", exercisesByName1.getExercises().get("2").getMuscleGroup());
        assertEquals("Core", exercisesByName1.getExercises().get("4").getMuscleGroup());
    }

    @Test
    public void testFilterMuscleGroupMultipleExerciseInMapMatchesCaseInsensitive() {
        exercisesByNameTest1.addExercise(new BodyWeightsExercise("abc", "Core",
                1, 1, 1, 1));
        exercisesByNameTest1.addExercise(new BodyWeightsExercise("def", "Core",
                2, 2, 2, 2));

        ExercisesByName exercisesByName1 = exercisesByNameTest1.filterMuscleGroup("cORE");
        ExercisesByName exercisesByName2 = exercisesByNameTest1.filterMuscleGroup("CO");

        assertEquals(2, exercisesByName1.getExercises().size());
        assertEquals("Core", exercisesByName1.getExercises().get("abc").getMuscleGroup());
        assertEquals("Core", exercisesByName1.getExercises().get("def").getMuscleGroup());

        assertEquals(2, exercisesByName2.getExercises().size());
        assertEquals("Core", exercisesByName2.getExercises().get("abc").getMuscleGroup());
        assertEquals("Core", exercisesByName2.getExercises().get("def").getMuscleGroup());
    }

    @Test
    public void testFilterDifficultySingleExerciseInMapMatches() {
        addExerciseHelper(exercisesByNameTest1, 1);
        ExercisesByName exercisesByName1 = exercisesByNameTest1.filterDifficulty(1);

        assertEquals(1, exercisesByName1.getExercises().size());
        assertEquals(1, exercisesByName1.getExercises().get("1").getDifficulty());
    }

    @Test
    public void testFilterDifficultyMultipleExerciseInMapMatches() {
        addExerciseHelper(exercisesByNameTest1, 4);
        ExercisesByName exercisesByName1 = exercisesByNameTest1.filterDifficulty(2);

        assertEquals(2, exercisesByName1.getExercises().size());
        assertEquals(2, exercisesByName1.getExercises().get("2").getDifficulty());
        assertEquals(2, exercisesByName1.getExercises().get("4").getDifficulty());
    }

    @Test
    public void testFilterTimeSingleExerciseInMapMatches() {
        addExerciseHelper(exercisesByNameTest1, 1);
        ExercisesByName exercisesByName1 = exercisesByNameTest1.filterTime(5);

        assertEquals(1, exercisesByName1.getExercises().size());
        assertEquals(1, exercisesByName1.getExercises().get("1").getTime());
    }

    @Test
    public void testFilterTimeMultipleExerciseInMapMatches() {
        addExerciseHelper(exercisesByNameTest1, 9);
        ExercisesByName exercisesByName1 = exercisesByNameTest1.filterTime(3);

        assertEquals(3, exercisesByName1.getExercises().size());
        assertEquals(1, exercisesByName1.getExercises().get("1").getTime());
        assertEquals(2, exercisesByName1.getExercises().get("2").getTime());
        assertEquals(3, exercisesByName1.getExercises().get("3").getTime());
    }

    @Test
    public void testFilterFavouriteSingleExerciseInMapMatches() {
        addExerciseHelper(exercisesByNameTest1, 1);
        exercisesByNameTest1.getExercise("1").setFavourite(TRUE);

        ExercisesByName exercisesByName1 = exercisesByNameTest1.filterFavourite();

        assertEquals(1, exercisesByName1.getExercises().size());
        assertTrue(exercisesByName1.getExercises().get("1").isFavourite());
    }

    @Test
    public void testFilterFavouriteMultipleExerciseInMapMatches() {
        addExerciseHelper(exercisesByNameTest1, 9);
        exercisesByNameTest1.getExercise("3").setFavourite(TRUE);
        exercisesByNameTest1.getExercise("6").setFavourite(TRUE);
        exercisesByNameTest1.getExercise("9").setFavourite(TRUE);

        ExercisesByName exercisesByName1 = exercisesByNameTest1.filterFavourite();

        assertEquals(3, exercisesByName1.getExercises().size());
        assertTrue(exercisesByName1.getExercises().get("3").isFavourite());
        assertTrue(exercisesByName1.getExercises().get("6").isFavourite());
        assertTrue(exercisesByName1.getExercises().get("9").isFavourite());
    }

    @Test
    public void testContainsEmptyMap() {
        assertFalse(exercisesByNameTest1.contains("1"));
    }

    @Test
    public void testContainsSingleExerciseInMapAndDoesContain() {
        addExerciseHelper(exercisesByNameTest1, 1);

        assertTrue(exercisesByNameTest1.contains("1"));
    }

    @Test
    public void testContainsSingleExerciseInMapAndDoesNotContain() {
        addExerciseHelper(exercisesByNameTest1, 1);

        assertFalse(exercisesByNameTest1.contains("2"));
    }

    @Test
    public void testContainsMultipleExerciseInMapAndDoesContain() {
        addExerciseHelper(exercisesByNameTest1, 5);

        assertTrue(exercisesByNameTest1.contains("1"));
        assertTrue(exercisesByNameTest1.contains("3"));
    }

    @Test
    public void testContainsMultipleExerciseInMapAndDoesNotContain() {
        addExerciseHelper(exercisesByNameTest1, 5);

        assertFalse(exercisesByNameTest1.contains("55"));
        assertFalse(exercisesByNameTest1.contains("6"));
        assertFalse(exercisesByNameTest1.contains("7"));
    }

    @Test
    public void testContainsMultipleExerciseInMapAndDoesContainCaseInsensitive() {
        exercisesByNameTest1.addExercise(new CardioExercise("abc", "Legs",
                1, 1, 1));
        exercisesByNameTest1.addExercise(new BodyWeightsExercise("DEF", "Core",
                2, 2, 2, 2));

        assertFalse(exercisesByNameTest1.contains("abcd"));
        assertTrue(exercisesByNameTest1.contains("aBc"));
        assertTrue(exercisesByNameTest1.contains("ABC"));
        assertTrue(exercisesByNameTest1.contains("def"));
        assertTrue(exercisesByNameTest1.contains("DEF"));
    }


    @Test
    public void isEmptyEmptyMap() {
        assertTrue(exercisesByNameTest1.isEmpty());
    }

    @Test
    public void isEmptySingleExerciseInMap() {
        addExerciseHelper(exercisesByNameTest1, 1);

        assertFalse(exercisesByNameTest1.isEmpty());
    }

    @Test
    public void isEmptyMultipleExerciseInMap() {
        addExerciseHelper(exercisesByNameTest1, 3);

        assertFalse(exercisesByNameTest1.isEmpty());
    }

    @Test
    public void lengthEmptyMap() {
        assertEquals(0, exercisesByNameTest1.length());
    }

    @Test
    public void lengthSingleExerciseInMap() {
        addExerciseHelper(exercisesByNameTest1, 1);

        assertEquals(1, exercisesByNameTest1.length());
    }

    @Test
    public void lengthMultipleExerciseInMap() {
        addExerciseHelper(exercisesByNameTest1, 2);

        assertEquals(2, exercisesByNameTest1.length());
    }

    @Test
    public void getExerciseOneExerciseInMap() {
        addExerciseHelper(exercisesByNameTest1, 1);

        assertEquals("1", exercisesByNameTest1.getExercise("1").getName());
    }

    @Test
    public void getExerciseMultipleExerciseInMap() {
        addExerciseHelper(exercisesByNameTest1, 2);

        assertEquals("1", exercisesByNameTest1.getExercise("1").getName());
        assertEquals("2", exercisesByNameTest1.getExercise("2").getName());
    }

    private void addExerciseHelper(ExercisesByName exercisesByName, int repeats) {
        for (int i = 0; i < repeats; i++) {
            if ((i + 1) % 2 == 0) {
                exercisesByName.addExercise(new BodyWeightsExercise(Integer.toString(i + 1), "Core",
                        i + 1, i + 1, 2, i + 1));
            } else {
                exercisesByName.addExercise(new WeightsExercise(Integer.toString(i + 1), "Chest",
                        i + 1, i + 1, i + 1, 1, i + 1));
            }
        }
    }
}
