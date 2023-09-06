package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Test class for WeightsExercise
public class WeightsExerciseTest {
    private WeightsExercise weightsExerciseTest1;
    private WeightsExercise weightsExerciseTest2;

    @BeforeEach
    public void runBefore() {
        weightsExerciseTest1 = new WeightsExercise("Bench Press", MuscleGroup.CHEST,
                150, 3, 5, Difficulty.INTENSE, 30);
        weightsExerciseTest2 = new WeightsExercise("Squats", MuscleGroup.LEGS,
                225, 5, 8, Difficulty.MODERATE, 60);
    }

    @Test
    public void testConstructorTypical() {
        assertEquals("Bench Press", weightsExerciseTest1.getName());
        assertEquals(MuscleGroup.CHEST, weightsExerciseTest1.getMuscleGroup());
        assertEquals(150, weightsExerciseTest1.getWeight());
        assertEquals(3, weightsExerciseTest1.getSets());
        assertEquals(5, weightsExerciseTest1.getReps());
        assertEquals(Difficulty.INTENSE, weightsExerciseTest1.getDifficulty());
        assertEquals(30, weightsExerciseTest1.getTimeMinutes());
        assertFalse(weightsExerciseTest1.isFavourite());

        assertEquals("Squats", weightsExerciseTest2.getName());
        assertEquals(MuscleGroup.LEGS, weightsExerciseTest2.getMuscleGroup());
        assertEquals(225, weightsExerciseTest2.getWeight());
        assertEquals(5, weightsExerciseTest2.getSets());
        assertEquals(8, weightsExerciseTest2.getReps());
        assertEquals(Difficulty.MODERATE, weightsExerciseTest2.getDifficulty());
        assertEquals(60, weightsExerciseTest2.getTimeMinutes());
        assertFalse(weightsExerciseTest1.isFavourite());
    }

    @Test
    public void testConstructorBoundary() {
        WeightsExercise weightsExerciseTest3 = new WeightsExercise("Bicep Curls", MuscleGroup.ARMS,
                1, 1, 1, Difficulty.LIGHT, 1);

        assertEquals("Bicep Curls", weightsExerciseTest3.getName());
        assertEquals(MuscleGroup.ARMS, weightsExerciseTest3.getMuscleGroup());
        assertEquals(1, weightsExerciseTest3.getWeight());
        assertEquals(1, weightsExerciseTest3.getSets());
        assertEquals(1, weightsExerciseTest3.getReps());
        assertEquals(Difficulty.LIGHT, weightsExerciseTest3.getDifficulty());
        assertEquals(1, weightsExerciseTest3.getTimeMinutes());
        assertFalse(weightsExerciseTest1.isFavourite());
    }

    @Test
    public void testToString() {
        assertEquals("Exercise Name: " + weightsExerciseTest1.getName() + "\n"
                        + "Muscle Group: " + weightsExerciseTest1.getMuscleGroup().getMuscleGroup() + "\n"
                        + "Difficulty: " + weightsExerciseTest1.getDifficulty().getDifficulty() + "\n"
                        + "Time (min): " + weightsExerciseTest1.getTimeMinutes() + "\n"
                        + "Favourite?: " + weightsExerciseTest1.isFavourite() + "\n"
                        + "Weight (lbs): " + weightsExerciseTest1.getWeight() + "\n"
                        + "Sets: " + weightsExerciseTest1.getSets() + "\n"
                        + "Reps: " + weightsExerciseTest1.getReps(),
                weightsExerciseTest1.toString());

        assertEquals("Exercise Name: " + weightsExerciseTest2.getName() + "\n"
                        + "Muscle Group: " + weightsExerciseTest2.getMuscleGroup().getMuscleGroup() + "\n"
                        + "Difficulty: " + weightsExerciseTest2.getDifficulty().getDifficulty() + "\n"
                        + "Time (min): " + weightsExerciseTest2.getTimeMinutes() + "\n"
                        + "Favourite?: " + weightsExerciseTest2.isFavourite() + "\n"
                        + "Weight (lbs): " + weightsExerciseTest2.getWeight() + "\n"
                        + "Sets: " + weightsExerciseTest2.getSets() + "\n"
                        + "Reps: " + weightsExerciseTest2.getReps(),
                weightsExerciseTest2.toString());
    }

    @Test
    public void testSetName() {
        weightsExerciseTest1.setName("Jeff");
        weightsExerciseTest2.setName("A");

        assertEquals("Jeff", weightsExerciseTest1.getName());
        assertEquals("A", weightsExerciseTest2.getName());
    }

    @Test
    public void testSetMuscleGroup() {
        weightsExerciseTest1.setMuscleGroup(MuscleGroup.SHOULDERS);
        weightsExerciseTest2.setMuscleGroup(MuscleGroup.BACK);

        assertEquals(MuscleGroup.SHOULDERS, weightsExerciseTest1.getMuscleGroup());
        assertEquals(MuscleGroup.BACK, weightsExerciseTest2.getMuscleGroup());
    }

    @Test
    public void testSetDifficulty() {
        weightsExerciseTest1.setDifficulty(Difficulty.MODERATE);
        weightsExerciseTest2.setDifficulty(Difficulty.INTENSE);

        assertEquals(Difficulty.MODERATE, weightsExerciseTest1.getDifficulty());
        assertEquals(Difficulty.INTENSE, weightsExerciseTest2.getDifficulty());
    }

    @Test
    public void testSetTime() {
        weightsExerciseTest1.setTimeMinutes(1);
        weightsExerciseTest2.setTimeMinutes(100);

        assertEquals(1, weightsExerciseTest1.getTimeMinutes());
        assertEquals(100, weightsExerciseTest2.getTimeMinutes());
    }

    @Test
    public void testSetWeight() {
        weightsExerciseTest1.setWeight(1);
        weightsExerciseTest2.setWeight(100);

        assertEquals(1, weightsExerciseTest1.getWeight());
        assertEquals(100, weightsExerciseTest2.getWeight());
    }

    @Test
    public void testSetSets() {
        weightsExerciseTest1.setSets(1);
        weightsExerciseTest2.setSets(100);

        assertEquals(1, weightsExerciseTest1.getSets());
        assertEquals(100, weightsExerciseTest2.getSets());
    }

    @Test
    public void testSetReps() {
        weightsExerciseTest1.setReps(1);
        weightsExerciseTest2.setReps(100);

        assertEquals(1, weightsExerciseTest1.getReps());
        assertEquals(100, weightsExerciseTest2.getReps());
    }

    @Test
    public void testToJson() {
        JSONObject jsonObjectTest1 = weightsExerciseTest1.toJson();
        JSONObject jsonObjectTest2 = weightsExerciseTest2.toJson();

        assertEquals("WeightsExercise", jsonObjectTest1.getString("exerciseType"));
        assertEquals("Bench Press", jsonObjectTest1.getString("name"));
        assertEquals("Chest", jsonObjectTest1.get("muscleGroup"));
        assertEquals(150, jsonObjectTest1.getInt("weight"));
        assertEquals(3, jsonObjectTest1.getInt("sets"));
        assertEquals(5, jsonObjectTest1.getInt("reps"));
        assertEquals(3, jsonObjectTest1.get("difficulty"));
        assertEquals(30, jsonObjectTest1.getInt("time"));
        assertFalse(jsonObjectTest1.getBoolean("favourite"));

        assertEquals("WeightsExercise", jsonObjectTest2.getString("exerciseType"));
        assertEquals("Squats", jsonObjectTest2.getString("name"));
        assertEquals("Legs", jsonObjectTest2.get("muscleGroup"));
        assertEquals(225, jsonObjectTest2.getInt("weight"));
        assertEquals(5, jsonObjectTest2.getInt("sets"));
        assertEquals(8, jsonObjectTest2.getInt("reps"));
        assertEquals(2, jsonObjectTest2.get("difficulty"));
        assertEquals(60, jsonObjectTest2.getInt("time"));
        assertFalse(jsonObjectTest2.getBoolean("favourite"));
    }
}