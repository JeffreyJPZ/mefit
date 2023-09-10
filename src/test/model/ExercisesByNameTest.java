package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.lang.Boolean.*;
import static model.ExercisesByName.ADDITIONAL_EXERCISE_MESSAGE;
import static model.ExercisesByName.DISPLAY_NUMBER_OF_EXERCISES;
import static org.junit.jupiter.api.Assertions.*;

// Test class for ExercisesByName
public class ExercisesByNameTest {
    private ExercisesByName exercisesByNameTest1;

    @BeforeEach
    public void runBefore() {
        exercisesByNameTest1 = new ExercisesByName();
    }

    @Test
    public void testConstructorEmptyMap() {
        ExercisesByName exercisesByNameTest2 = new ExercisesByName();

        assertTrue(exercisesByNameTest1.isEmpty());
        assertTrue(exercisesByNameTest2.isEmpty());
    }

    @Test
    public void testAddExerciseOnceEmptyMap() {
        addExerciseHelper(exercisesByNameTest1, 1);

        assertEquals(1, exercisesByNameTest1.length());

        Exercise exercise1 = exercisesByNameTest1.getExercise("1");

        assertEquals("1", exercise1.getName());
        assertEquals(MuscleGroup.CHEST, exercise1.getMuscleGroup());
        assertEquals(Difficulty.LIGHT, exercise1.getDifficulty());
        assertEquals(1, exercise1.getTimeMinutes());
        assertFalse(exercise1.isFavourite());
    }

    @Test
    public void testAddExerciseOnceNonEmptyMap() {
        addExerciseHelper(exercisesByNameTest1, 2);

        assertEquals(2, exercisesByNameTest1.length());

        exercisesByNameTest1.addExercise(new CardioExercise("3", MuscleGroup.LEGS, 3,
                Difficulty.INTENSE, 3));

        assertEquals(3, exercisesByNameTest1.length());

        Exercise exercise3 = exercisesByNameTest1.getExercise("3");

        assertEquals("3", exercise3.getName());
        assertEquals(MuscleGroup.LEGS, exercise3.getMuscleGroup());
        assertEquals(Difficulty.INTENSE, exercise3.getDifficulty());
        assertEquals(3, exercise3.getTimeMinutes());
        assertFalse(exercise3.isFavourite());
    }

    @Test
    public void testAddExerciseMultipleEmptyMap() {
        exercisesByNameTest1.addExercise(new WeightsExercise("1", MuscleGroup.CHEST, 1, 1, 1,
                                                            Difficulty.LIGHT, 1));
        exercisesByNameTest1.addExercise(new BodyWeightsExercise("2", MuscleGroup.CORE, 2, 2,
                                                            Difficulty.MODERATE, 2));

        assertEquals(2, exercisesByNameTest1.length());

        Exercise exercise1 = exercisesByNameTest1.getExercise("1");
        Exercise exercise2 = exercisesByNameTest1.getExercise("2");

        assertEquals("1", exercise1.getName());
        assertEquals(MuscleGroup.CHEST, exercise1.getMuscleGroup());
        assertEquals(Difficulty.LIGHT, exercise1.getDifficulty());
        assertEquals(1, exercise1.getTimeMinutes());
        assertFalse(exercise1.isFavourite());

        assertEquals("2", exercise2.getName());
        assertEquals(MuscleGroup.CORE, exercise2.getMuscleGroup());
        assertEquals(Difficulty.MODERATE, exercise2.getDifficulty());
        assertEquals(2, exercise2.getTimeMinutes());
        assertFalse(exercise2.isFavourite());
    }

    @Test
    public void testAddExerciseMultipleNonEmptyMap() {
        addExerciseHelper(exercisesByNameTest1, 2);

        assertEquals(2, exercisesByNameTest1.length());

        exercisesByNameTest1.addExercise(new CardioExercise("3", MuscleGroup.LEGS, 3,
                                                            Difficulty.INTENSE, 3));
        exercisesByNameTest1.addExercise(new WeightsExercise("4", MuscleGroup.CHEST, 4, 4, 4,
                                                            Difficulty.LIGHT, 4));

        Exercise exercise3 = exercisesByNameTest1.getExercise("3");
        Exercise exercise4 = exercisesByNameTest1.getExercise("4");

        assertEquals("3", exercise3.getName());
        assertEquals(MuscleGroup.LEGS, exercise3.getMuscleGroup());
        assertEquals(Difficulty.INTENSE, exercise3.getDifficulty());
        assertEquals(3, exercise3.getTimeMinutes());
        assertFalse(exercise3.isFavourite());

        assertEquals("4", exercise4.getName());
        assertEquals(MuscleGroup.CHEST, exercise4.getMuscleGroup());
        assertEquals(Difficulty.LIGHT, exercise4.getDifficulty());
        assertEquals(4, exercise4.getTimeMinutes());
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
    public void testToStringNoExercises() {
        assertEquals("Name\tMuscle Group\tDifficulty\tTime (min)\tFavourite?" + "\n",
                exercisesByNameTest1.toString());
    }

    @Test
    public void testToStringOneExerciseInMap() {
        addExerciseHelper(exercisesByNameTest1, 1);

        assertEquals("Name\tMuscle Group\tDifficulty\tTime (min)\tFavourite?" + "\n"
                            + "[1]\tChest\t1\t1\tfalse" + "\n", exercisesByNameTest1.toString());
    }

    @Test
    public void testToStringMultipleExerciseInMap() {
        addExerciseHelper(exercisesByNameTest1, 2);

        assertEquals("Name\tMuscle Group\tDifficulty\tTime (min)\tFavourite?" + "\n"
                            + "[1]\tChest\t1\t1\tfalse" + "\n"
                            + "[2]\tCore\t2\t2\tfalse" + "\n", exercisesByNameTest1.toString());
    }

    @Test
    public void testToStringMultipleExerciseInMapBoundary() {
        addExerciseHelper(exercisesByNameTest1, DISPLAY_NUMBER_OF_EXERCISES);

        assertEquals("Name\tMuscle Group\tDifficulty\tTime (min)\tFavourite?" + "\n"
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

        assertEquals("Name\tMuscle Group\tDifficulty\tTime (min)\tFavourite?" + "\n"
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
    public void testFilterEmptyExercisesNoMatch() {
        ExercisesByName exercisesByName = exercisesByNameTest1.filterName("1");

        assertEquals(0, exercisesByName.length());
    }

    @Test
    public void testFilterSingleExerciseInMapMatches() {
        addExerciseHelper(exercisesByNameTest1, 1);
        ExercisesByName exercisesByName = exercisesByNameTest1.filterName("1");

        assertEquals(1, exercisesByName.length());
        assertEquals("1", exercisesByName.getExercises().get("1").getName());
    }

    @Test
    public void testFilterMultipleExerciseInMapMatches() {
        addExerciseHelper(exercisesByNameTest1, 11);
        ExercisesByName exercisesByName = exercisesByNameTest1.filterName("1");

        assertEquals(3, exercisesByName.length());
        assertEquals("1", exercisesByName.getExercises().get("1").getName());
        assertEquals("10", exercisesByName.getExercises().get("10").getName());
        assertEquals("11", exercisesByName.getExercises().get("11").getName());
    }

    @Test
    public void testFilterMultipleExerciseInMapMatchesCaseInsensitive() {
        exercisesByNameTest1.addExercise(new BodyWeightsExercise("abc", MuscleGroup.CORE,
                1, 1, Difficulty.LIGHT, 1));
        exercisesByNameTest1.addExercise(new CardioExercise("ABC", MuscleGroup.CORE,
                1, Difficulty.LIGHT, 1));
        exercisesByNameTest1.addExercise(new BodyWeightsExercise("DEF", MuscleGroup.CORE,
                2, 2, Difficulty.MODERATE, 2));
        exercisesByNameTest1.addExercise(new WeightsExercise("def", MuscleGroup.CORE,
                2, 2, 2,  Difficulty.MODERATE, 2));
        exercisesByNameTest1.addExercise(new BodyWeightsExercise("d", MuscleGroup.CORE,
                2, 2, Difficulty.MODERATE, 2));

        ExercisesByName exercisesByName1 = exercisesByNameTest1.filterName("ABC");
        ExercisesByName exercisesByName2 = exercisesByNameTest1.filterName("De");

        assertEquals(2, exercisesByName1.length());
        assertEquals("abc", exercisesByName1.getExercises().get("abc").getName());
        assertEquals("ABC", exercisesByName1.getExercises().get("ABC").getName());

        assertEquals(2, exercisesByName2.length());
        assertEquals("DEF", exercisesByName2.getExercises().get("DEF").getName());
        assertEquals("def", exercisesByName2.getExercises().get("def").getName());
    }

    @Test
    public void testFilterMuscleGroupEmptyExercisesNoMatch() {
        ExercisesByName exercisesByName = exercisesByNameTest1.filterMuscleGroup(MuscleGroup.BACK);

        assertEquals(0, exercisesByName.length());
    }

    @Test
    public void testFilterMuscleGroupSingleExerciseInMapMatches() {
        addExerciseHelper(exercisesByNameTest1, 1);
        ExercisesByName exercisesByName1 = exercisesByNameTest1.filterMuscleGroup(MuscleGroup.CHEST);

        assertEquals(1, exercisesByName1.length());
        assertEquals(MuscleGroup.CHEST, exercisesByName1.getExercises().get("1").getMuscleGroup());
    }

    @Test
    public void testFilterMuscleGroupMultipleExerciseInMapMatches() {
        addExerciseHelper(exercisesByNameTest1, 4);
        ExercisesByName exercisesByName1 = exercisesByNameTest1.filterMuscleGroup(MuscleGroup.CORE);

        assertEquals(2, exercisesByName1.length());
        assertEquals(MuscleGroup.CORE, exercisesByName1.getExercises().get("2").getMuscleGroup());
        assertEquals(MuscleGroup.CORE, exercisesByName1.getExercises().get("4").getMuscleGroup());
    }

    @Test
    public void testFilterDifficultyEmptyExercisesNoMatch() {
        ExercisesByName exercisesByName = exercisesByNameTest1.filterDifficulty(Difficulty.INTENSE);

        assertEquals(0, exercisesByName.length());
    }

    @Test
    public void testFilterDifficultySingleExerciseInMapMatches() {
        addExerciseHelper(exercisesByNameTest1, 1);
        ExercisesByName exercisesByName1 = exercisesByNameTest1.filterDifficulty(Difficulty.LIGHT);

        assertEquals(1, exercisesByName1.length());
        assertEquals(Difficulty.LIGHT, exercisesByName1.getExercises().get("1").getDifficulty());
    }

    @Test
    public void testFilterDifficultyMultipleExerciseInMapMatches() {
        addExerciseHelper(exercisesByNameTest1, 4);
        ExercisesByName exercisesByName1 = exercisesByNameTest1.filterDifficulty(Difficulty.MODERATE);

        assertEquals(2, exercisesByName1.length());
        assertEquals(Difficulty.MODERATE, exercisesByName1.getExercises().get("2").getDifficulty());
        assertEquals(Difficulty.MODERATE, exercisesByName1.getExercises().get("4").getDifficulty());
    }

    @Test
    public void testFilterTimeEmptyExercisesNoMatch() {
        ExercisesByName exercisesByName = exercisesByNameTest1.filterTime(20);

        assertEquals(0, exercisesByName.length());
    }

    @Test
    public void testFilterTimeSingleExerciseInMapMatches() {
        addExerciseHelper(exercisesByNameTest1, 1);
        ExercisesByName exercisesByName1 = exercisesByNameTest1.filterTime(5);

        assertEquals(1, exercisesByName1.length());
        assertEquals(1, exercisesByName1.getExercises().get("1").getTimeMinutes());
    }

    @Test
    public void testFilterTimeMultipleExerciseInMapMatches() {
        addExerciseHelper(exercisesByNameTest1, 9);
        ExercisesByName exercisesByName1 = exercisesByNameTest1.filterTime(3);

        assertEquals(3, exercisesByName1.length());
        assertEquals(1, exercisesByName1.getExercises().get("1").getTimeMinutes());
        assertEquals(2, exercisesByName1.getExercises().get("2").getTimeMinutes());
        assertEquals(3, exercisesByName1.getExercises().get("3").getTimeMinutes());
    }

    @Test
    public void testFilterFavouriteEmptyExercisesNoMatch() {
        ExercisesByName exercisesByName = exercisesByNameTest1.filterFavourite();

        assertEquals(0, exercisesByName.length());
    }

    @Test
    public void testFilterFavouriteSingleExerciseInMapMatches() {
        addExerciseHelper(exercisesByNameTest1, 1);
        exercisesByNameTest1.getExercise("1").setFavourite(TRUE);

        ExercisesByName exercisesByName1 = exercisesByNameTest1.filterFavourite();

        assertEquals(1, exercisesByName1.length());
        assertTrue(exercisesByName1.getExercises().get("1").isFavourite());
    }

    @Test
    public void testFilterFavouriteMultipleExerciseInMapMatches() {
        addExerciseHelper(exercisesByNameTest1, 9);
        exercisesByNameTest1.getExercise("3").setFavourite(TRUE);
        exercisesByNameTest1.getExercise("6").setFavourite(TRUE);
        exercisesByNameTest1.getExercise("9").setFavourite(TRUE);

        ExercisesByName exercisesByName1 = exercisesByNameTest1.filterFavourite();

        assertEquals(3, exercisesByName1.length());
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
        Exercise exercise1 = new WeightsExercise("1", MuscleGroup.CHEST, 1, 1, 1,
                Difficulty.LIGHT, 1);

        exercisesByNameTest1.addExercise(exercise1);

        assertEquals(exercise1, exercisesByNameTest1.getExercise("1"));
    }

    @Test
    public void getExerciseMultipleExerciseInMap() {
        Exercise exercise1 = new WeightsExercise("1", MuscleGroup.CHEST, 1, 1, 1,
                Difficulty.LIGHT, 1);
        Exercise exercise2 = new BodyWeightsExercise("2", MuscleGroup.CORE, 2, 2,
                Difficulty.MODERATE, 2);

        exercisesByNameTest1.addExercise(exercise1);
        exercisesByNameTest1.addExercise(exercise2);

        assertEquals(exercise1, exercisesByNameTest1.getExercise("1"));
        assertEquals(exercise2, exercisesByNameTest1.getExercise("2"));
    }

    @Test
    public void testToJsonEmptyExercises() {
        JSONObject jsonObjectTest1 = exercisesByNameTest1.toJson();

        assertTrue(jsonObjectTest1.getJSONArray("exercises").isEmpty());
    }

    @Test
    public void testToJsonMultipleExercises() {
        addExerciseHelper(exercisesByNameTest1, 2);

        JSONObject jsonObjectTest1 = exercisesByNameTest1.toJson();

        JSONObject jsonObjectExercise1 = jsonObjectTest1.getJSONArray("exercises").getJSONObject(0);
        JSONObject jsonObjectExercise2 = jsonObjectTest1.getJSONArray("exercises").getJSONObject(1);

        assertEquals("1", jsonObjectExercise1.getString("name"));
        assertEquals("Chest", jsonObjectExercise1.get("muscleGroup"));
        assertEquals(1, jsonObjectExercise1.getInt("weight"));
        assertEquals(1, jsonObjectExercise1.getInt("sets"));
        assertEquals(1, jsonObjectExercise1.getInt("reps"));
        assertEquals(1, jsonObjectExercise1.get("difficulty"));
        assertEquals(1, jsonObjectExercise1.getInt("time"));
        assertFalse(jsonObjectExercise1.getBoolean("favourite"));

        assertEquals("2", jsonObjectExercise2.getString("name"));
        assertEquals("Core", jsonObjectExercise2.get("muscleGroup"));
        assertEquals(2, jsonObjectExercise2.getInt("sets"));
        assertEquals(2, jsonObjectExercise2.getInt("reps"));
        assertEquals(2, jsonObjectExercise2.get("difficulty"));
        assertEquals(2, jsonObjectExercise2.getInt("time"));
        assertFalse(jsonObjectExercise2.getBoolean("favourite"));
    }

    private void addExerciseHelper(ExercisesByName exercisesByName, int repeats) {
        for (int i = 0; i < repeats; i++) {
            if ((i + 1) % 2 == 0) {
                exercisesByName.addExercise(new BodyWeightsExercise(Integer.toString(i + 1), MuscleGroup.CORE,
                        i + 1, i + 1, Difficulty.MODERATE, i + 1));
            } else {
                exercisesByName.addExercise(new WeightsExercise(Integer.toString(i + 1), MuscleGroup.CHEST,
                        i + 1, i + 1, i + 1, Difficulty.LIGHT, i + 1));
            }
        }
    }
}
