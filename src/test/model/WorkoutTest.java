package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Test class for Workout
public class WorkoutTest {
    private Workout workoutTest1;
    private Workout workoutTest2;

    @BeforeEach
    public void runBefore() {
        workoutTest1 = new Workout("1", Difficulty.LIGHT);
        workoutTest2 = new Workout("2", Difficulty.MODERATE);
    }

    @Test
    public void testConstructor() {
        assertEquals("1", workoutTest1.getName());
        assertEquals(Difficulty.LIGHT, workoutTest1.getDifficulty());
        assertEquals(0, workoutTest1.getTime());
        assertFalse(workoutTest1.isFavourite());
        assertTrue(workoutTest1.getExercises().isEmpty());

        assertEquals("2", workoutTest2.getName());
        assertEquals(Difficulty.MODERATE, workoutTest2.getDifficulty());
        assertEquals(0, workoutTest2.getTime());
        assertFalse(workoutTest2.isFavourite());
        assertTrue(workoutTest2.getExercises().isEmpty());
    }

    @Test
    public void testAddExerciseOnceEmptyList() {
        addExerciseHelper(workoutTest1, 1);

        assertEquals(1, workoutTest1.length());
        assertEquals("1", workoutTest1.getExercise(1).getName());
        assertEquals(1, workoutTest1.getTime());
    }

    @Test
    public void testAddExerciseOnceNonEmptyList() {
        addExerciseHelper(workoutTest1, 2);

        workoutTest1.addExercise(new CardioExercise("3", MuscleGroup.LEGS, 3, Difficulty.INTENSE, 3));

        assertEquals(3, workoutTest1.length());
        assertEquals("3", workoutTest1.getExercise(3).getName());
        assertEquals(6, workoutTest1.getTime());
    }

    @Test
    public void testAddExerciseMultipleToList() {
        addExerciseHelper(workoutTest1, 3);

        assertEquals(3, workoutTest1.length());
        assertEquals("1", workoutTest1.getExercise(1).getName());
        assertEquals("2", workoutTest1.getExercise(2).getName());
        assertEquals("3", workoutTest1.getExercise(3).getName());
        assertEquals(6, workoutTest1.getTime());
    }

    @Test
    public void testInsertExerciseOnceEmptyList() {
        workoutTest1.insertExercise(new WeightsExercise("1", MuscleGroup.CHEST, 1, 1, 1,
                Difficulty.LIGHT, 1), 1);

        assertEquals(1, workoutTest1.length());
        assertEquals("1", workoutTest1.getExercise(1).getName());
        assertEquals(1, workoutTest1.getTime());
    }

    @Test
    public void testInsertExerciseOnceNonEmptyList() {
        addExerciseHelper(workoutTest1, 3);

        workoutTest1.insertExercise(new CardioExercise("4", MuscleGroup.LEGS, 4,
                        Difficulty.INTENSE, 4),
                2);

        assertEquals(4, workoutTest1.length());
        assertEquals("4", workoutTest1.getExercise(2).getName());
        assertEquals(10, workoutTest1.getTime());
    }

    @Test
    public void testInsertExerciseMultipleToListDifferentPositions() {
        addExerciseHelper(workoutTest1, 3);

        workoutTest1.insertExercise(new CardioExercise("4", MuscleGroup.LEGS, 4,
                        Difficulty.INTENSE, 4),
                1);
        workoutTest1.insertExercise(new CardioExercise("5", MuscleGroup.LEGS, 4,
                        Difficulty.INTENSE, 4),
                2);
        workoutTest1.insertExercise(new CardioExercise("6", MuscleGroup.LEGS, 4,
                        Difficulty.INTENSE, 4),
                3);

        assertEquals(6, workoutTest1.length());
        assertEquals("4", workoutTest1.getExercise(1).getName());
        assertEquals("5", workoutTest1.getExercise(2).getName());
        assertEquals("6", workoutTest1.getExercise(3).getName());
        assertEquals(18, workoutTest1.getTime());
    }

    @Test
    public void testInsertExerciseMultipleToListSamePositions() {
        addExerciseHelper(workoutTest1, 3);

        workoutTest1.insertExercise(new CardioExercise("4", MuscleGroup.LEGS, 4,
                        Difficulty.INTENSE, 4),
                2);
        workoutTest1.insertExercise(new CardioExercise("5", MuscleGroup.LEGS, 4,
                        Difficulty.INTENSE, 4),
                2);
        workoutTest1.insertExercise(new CardioExercise("6", MuscleGroup.LEGS, 4,
                        Difficulty.INTENSE, 4),
                2);

        assertEquals(6, workoutTest1.length());
        assertEquals("4", workoutTest1.getExercise(4).getName());
        assertEquals("5", workoutTest1.getExercise(3).getName());
        assertEquals("6", workoutTest1.getExercise(2).getName());
        assertEquals(18, workoutTest1.getTime());
    }


    @Test
    public void testSetExerciseOnceGreaterThan() {
        addExerciseHelper(workoutTest1, 1);

        assertEquals(1, workoutTest1.length());
        assertEquals(1, workoutTest1.getTime());

        workoutTest1.setExercise(new CardioExercise("4", MuscleGroup.LEGS, 4, Difficulty.INTENSE, 4),
                1);

        assertEquals(1, workoutTest1.length());
        assertEquals("4", workoutTest1.getExercise(1).getName());
        assertEquals(4, workoutTest1.getTime());
    }

    @Test
    public void testSetExerciseOnceEqualTo() {
        addExerciseHelper(workoutTest1, 1);

        assertEquals(1, workoutTest1.length());
        assertEquals(1, workoutTest1.getTime());

        workoutTest1.setExercise(new CardioExercise("4", MuscleGroup.LEGS, 4, Difficulty.INTENSE, 1),
                1);

        assertEquals(1, workoutTest1.length());
        assertEquals("4", workoutTest1.getExercise(1).getName());
        assertEquals(1, workoutTest1.getTime());
    }

    @Test
    public void testSetExerciseLessThan() {
        addExerciseHelper(workoutTest1, 2);

        assertEquals(2, workoutTest1.length());
        assertEquals(3, workoutTest1.getTime());

        workoutTest1.setExercise(new CardioExercise("4", MuscleGroup.LEGS, 4, Difficulty.INTENSE, 1),
                2);

        assertEquals(2, workoutTest1.length());
        assertEquals("4", workoutTest1.getExercise(2).getName());
        assertEquals(2, workoutTest1.getTime());
    }

    @Test
    public void testSetExerciseMultipleInList() {
        addExerciseHelper(workoutTest1, 5);

        assertEquals(5, workoutTest1.length());
        assertEquals(15, workoutTest1.getTime());

        workoutTest1.setExercise(new CardioExercise("4", MuscleGroup.LEGS, 4, Difficulty.INTENSE, 1),
                1);
        workoutTest1.setExercise(new CardioExercise("5", MuscleGroup.LEGS, 4, Difficulty.INTENSE, 2),
                3);
        workoutTest1.setExercise(new CardioExercise("6", MuscleGroup.LEGS, 4, Difficulty.INTENSE, 7),
                5);

        assertEquals(5, workoutTest1.length());
        assertEquals("4", workoutTest1.getExercise(1).getName());
        assertEquals("5", workoutTest1.getExercise(3).getName());
        assertEquals("6", workoutTest1.getExercise(5).getName());
        assertEquals(16, workoutTest1.getTime());
    }

    @Test
    public void testRemoveByPositionExerciseOnce() {
        addExerciseHelper(workoutTest1, 1);

        assertEquals(1, workoutTest1.length());

        workoutTest1.removeExercise(1);

        assertEquals(0, workoutTest1.length());

    }

    @Test
    public void testRemoveByPositionExerciseMultipleInList() {
        addExerciseHelper(workoutTest1, 5);

        assertEquals(5, workoutTest1.length());

        workoutTest1.removeExercise(1);
        workoutTest1.removeExercise(2);
        workoutTest1.removeExercise(3);

        assertEquals(2, workoutTest1.length());
        assertEquals("2", workoutTest1.getExercise(1).getName());
        assertEquals("4", workoutTest1.getExercise(2).getName());
    }

    @Test
    public void testRemoveByNameNoMatch() {
        addExerciseHelper(workoutTest1, 3);

        assertEquals(3, workoutTest1.length());

        workoutTest1.removeExercise("5");

        assertEquals(3, workoutTest1.length());
    }

    @Test
    public void testRemoveByNameOnce() {
        addExerciseHelper(workoutTest1, 1);

        assertEquals(1, workoutTest1.length());

        workoutTest1.removeExercise("1");

        assertEquals(0, workoutTest1.length());
    }

    @Test
    public void testRemoveByNameMultipleInList() {
        addExerciseHelper(workoutTest1, 5);

        assertEquals(5, workoutTest1.length());

        workoutTest1.removeExercise("1");
        workoutTest1.removeExercise("3");
        workoutTest1.removeExercise("5");

        assertEquals(2, workoutTest1.length());
        assertEquals("2", workoutTest1.getExercise("2").getName());
        assertEquals("4", workoutTest1.getExercise("4").getName());
    }

    @Test
    public void testRemoveByNameMultipleInListCaseInsensitive() {
        addExerciseHelper(workoutTest1, 2);

        workoutTest1.addExercise(new CardioExercise("abc", MuscleGroup.LEGS, 3,
                Difficulty.INTENSE, 3));
        workoutTest1.addExercise(new CardioExercise("DEF", MuscleGroup.LEGS, 3,
                Difficulty.INTENSE, 3));
        workoutTest1.addExercise(new CardioExercise("cAt", MuscleGroup.LEGS, 3,
                Difficulty.INTENSE, 3));
        workoutTest1.addExercise(new CardioExercise("Sbin", MuscleGroup.LEGS, 3,
                Difficulty.INTENSE, 3));

        workoutTest1.removeExercise("ABC");
        workoutTest1.removeExercise("def");
        workoutTest1.removeExercise("CaT");
        workoutTest1.removeExercise("Sbin");

        assertEquals(2, workoutTest1.length());
        assertEquals("1", workoutTest1.getExercise("1").getName());
        assertEquals("2", workoutTest1.getExercise("2").getName());
    }

    @Test
    public void testClearEmptyList() {
        assertEquals(0, workoutTest1.length());

        workoutTest1.clear();

        assertEquals(0, workoutTest1.length());
    }

    @Test
    public void testClearSingleExerciseList() {
        addExerciseHelper(workoutTest1, 1);

        assertEquals(1, workoutTest1.length());

        workoutTest1.clear();

        assertEquals(0, workoutTest1.length());
    }

    @Test
    public void testClearMultipleExerciseList() {
        addExerciseHelper(workoutTest1, 3);

        assertEquals(3, workoutTest1.length());

        workoutTest1.clear();

        assertEquals(0, workoutTest1.length());
    }

    @Test
    public void testContainsEmptyList() {
        assertFalse(workoutTest1.contains("1"));
    }

    @Test
    public void testContainsSingleExerciseListAndDoesContain() {
        addExerciseHelper(workoutTest1, 1);

        assertTrue(workoutTest1.contains("1"));
    }

    @Test
    public void testContainsSingleExerciseListAndDoesNotContain() {
        addExerciseHelper(workoutTest1, 1);

        assertFalse(workoutTest1.contains("11"));
        assertFalse(workoutTest1.contains("2"));
    }

    @Test
    public void testContainsMultipleExerciseListAndDoesContain() {
        addExerciseHelper(workoutTest1, 3);

        assertTrue(workoutTest1.contains("1"));
        assertTrue(workoutTest1.contains("2"));
        assertTrue(workoutTest1.contains("3"));
    }

    @Test
    public void testContainsMultipleExerciseListAndDoesNotContain() {
        addExerciseHelper(workoutTest1, 3);

        assertFalse(workoutTest1.contains("22"));
        assertFalse(workoutTest1.contains("4"));
        assertFalse(workoutTest1.contains("7"));
    }

    @Test
    public void testContainsMultipleExerciseListAndDoesContainCaseInsensitive() {
        workoutTest1.addExercise(new CardioExercise("abc", MuscleGroup.LEGS, 1,
                Difficulty.INTENSE, 1));
        workoutTest1.addExercise(new CardioExercise("DEF", MuscleGroup.LEGS, 1,
                Difficulty.INTENSE, 1));

        assertTrue(workoutTest1.contains("abc"));
        assertTrue(workoutTest1.contains("ABC"));
        assertTrue(workoutTest1.contains("dEf"));
        assertTrue(workoutTest1.contains("DeF"));
    }

    @Test
    public void testToStringSingleExercise() {
        addExerciseHelper(workoutTest1, 1);

        assertEquals("Name\tDifficulty\tTime (min)\t # of Exercises\tFavourite?" + "\n"
                            + "[1]\t1\t1\t1\tfalse"
                            + "\n"
                            + "\n"
                            + "Exercises" + "\n"
                            + "Name\tMuscle Group\tDifficulty\tTime (min)\tFavourite?" + "\n"
                            + "[1]\tChest\t1\t1\tfalse" + "\n", workoutTest1.toString());
    }

    @Test
    public void testToStringMultipleExercise() {
        addExerciseHelper(workoutTest1, 2);

        assertEquals("Name\tDifficulty\tTime (min)\t # of Exercises\tFavourite?" + "\n"
                + "[1]\t1\t3\t2\tfalse"
                + "\n"
                + "\n"
                + "Exercises" + "\n"
                + "Name\tMuscle Group\tDifficulty\tTime (min)\tFavourite?" + "\n"
                + "[1]\tChest\t1\t1\tfalse" + "\n"
                + "[2]\tCore\t2\t2\tfalse" + "\n", workoutTest1.toString());
    }

    @Test
    public void testGetExerciseByNameNoMatch() {
        addExerciseHelper(workoutTest1, 3);

        assertEquals(null, workoutTest1.getExercise("5"));
    }

    @Test
    public void testGetExerciseByNameMatch() {
        addExerciseHelper(workoutTest1, 3);

        assertEquals("3", workoutTest1.getExercise("3").getName());
    }

    @Test
    public void testSetName() {
        workoutTest1.setName("Jeff");
        workoutTest2.setName("A");

        assertEquals("Jeff", workoutTest1.getName());
        assertEquals("A", workoutTest2.getName());
    }

    @Test
    public void testSetDifficulty() {
        workoutTest1.setDifficulty(Difficulty.INTENSE);
        workoutTest2.setDifficulty(Difficulty.LIGHT);

        assertEquals(Difficulty.INTENSE, workoutTest1.getDifficulty());
        assertEquals(Difficulty.LIGHT, workoutTest2.getDifficulty());
    }

    @Test
    public void testSetTime() {
        workoutTest1.setTime(1);
        workoutTest2.setTime(100);

        assertEquals(1, workoutTest1.getTime());
        assertEquals(100, workoutTest2.getTime());
    }

    private void addExerciseHelper(Workout workout, int repeats) {
        for (int i = 0; i < repeats; i++) {
            if ((i + 1) % 2 == 0) {
                workout.addExercise(new BodyWeightsExercise(Integer.toString(i + 1), MuscleGroup.CORE,
                        i + 1, i + 1, Difficulty.MODERATE, i + 1));
            } else {
                workout.addExercise(new WeightsExercise(Integer.toString(i + 1), MuscleGroup.CHEST,
                        i + 1, i + 1, i + 1, Difficulty.LIGHT, i + 1));
            }
        }
    }
}
