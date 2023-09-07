package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Test class for BodyWeightsExercise
public class BodyWeightsExerciseTest {
    private BodyWeightsExercise bodyWeightsExerciseTest1;
    private BodyWeightsExercise bodyWeightsExerciseTest2;

    @BeforeEach
    public void runBefore() {
        bodyWeightsExerciseTest1 = new BodyWeightsExercise("Pullups", MuscleGroup.SHOULDERS,
                3, 5, Difficulty.INTENSE, 30);
        bodyWeightsExerciseTest2 = new BodyWeightsExercise("Situps", MuscleGroup.CORE,
                2, 20, Difficulty.LIGHT, 15);
    }

    @Test
    public void testConstructorTypical() {
        assertEquals("Pullups", bodyWeightsExerciseTest1.getName());
        assertEquals(MuscleGroup.SHOULDERS, bodyWeightsExerciseTest1.getMuscleGroup());
        assertEquals(3, bodyWeightsExerciseTest1.getSets());
        assertEquals(5, bodyWeightsExerciseTest1.getReps());
        assertEquals(Difficulty.INTENSE, bodyWeightsExerciseTest1.getDifficulty());
        assertEquals(30, bodyWeightsExerciseTest1.getTimeMinutes());
        assertFalse(bodyWeightsExerciseTest1.isFavourite());

        assertEquals("Situps", bodyWeightsExerciseTest2.getName());
        assertEquals(MuscleGroup.CORE, bodyWeightsExerciseTest2.getMuscleGroup());
        assertEquals(2, bodyWeightsExerciseTest2.getSets());
        assertEquals(20, bodyWeightsExerciseTest2.getReps());
        assertEquals(Difficulty.LIGHT, bodyWeightsExerciseTest2.getDifficulty());
        assertEquals(15, bodyWeightsExerciseTest2.getTimeMinutes());
        assertFalse(bodyWeightsExerciseTest1.isFavourite());
    }

    @Test
    public void testConstructorBoundary() {
        BodyWeightsExercise bodyWeightsExerciseTest3 = new BodyWeightsExercise("Plank", MuscleGroup.CORE,
                1, 1, Difficulty.LIGHT, 1);

        assertEquals("Plank", bodyWeightsExerciseTest3.getName());
        assertEquals(MuscleGroup.CORE, bodyWeightsExerciseTest3.getMuscleGroup());
        assertEquals(1, bodyWeightsExerciseTest3.getSets());
        assertEquals(1, bodyWeightsExerciseTest3.getReps());
        assertEquals(Difficulty.LIGHT, bodyWeightsExerciseTest3.getDifficulty());
        assertEquals(1, bodyWeightsExerciseTest3.getTimeMinutes());
        assertFalse(bodyWeightsExerciseTest1.isFavourite());
    }

    @Test
    public void testToString() {
        assertEquals("Exercise Name: " + bodyWeightsExerciseTest1.getName() + "\n"
                        + "Muscle Group: " + bodyWeightsExerciseTest1.getMuscleGroup().getMuscleGroupAsString() + "\n"
                        + "Difficulty: " + bodyWeightsExerciseTest1.getDifficulty().getDifficultyAsInt() + "\n"
                        + "Time (min): " + bodyWeightsExerciseTest1.getTimeMinutes() + "\n"
                        + "Favourite?: " + bodyWeightsExerciseTest1.isFavourite() + "\n"
                        + "Sets: " + bodyWeightsExerciseTest1.getSets() + "\n"
                        + "Reps: " + bodyWeightsExerciseTest1.getReps(),
                bodyWeightsExerciseTest1.toString());

        assertEquals("Exercise Name: " + bodyWeightsExerciseTest2.getName() + "\n"
                        + "Muscle Group: " + bodyWeightsExerciseTest2.getMuscleGroup().getMuscleGroupAsString() + "\n"
                        + "Difficulty: " + bodyWeightsExerciseTest2.getDifficulty().getDifficultyAsInt() + "\n"
                        + "Time (min): " + bodyWeightsExerciseTest2.getTimeMinutes() + "\n"
                        + "Favourite?: " + bodyWeightsExerciseTest2.isFavourite() + "\n"
                        + "Sets: " + bodyWeightsExerciseTest2.getSets() + "\n"
                        + "Reps: " + bodyWeightsExerciseTest2.getReps(),
                bodyWeightsExerciseTest2.toString());
    }

    @Test
    public void testSetSets() {
        bodyWeightsExerciseTest1.setSets(1);
        bodyWeightsExerciseTest2.setSets(100);

        assertEquals(1, bodyWeightsExerciseTest1.getSets());
        assertEquals(100, bodyWeightsExerciseTest2.getSets());
    }

    @Test
    public void testSetReps() {
        bodyWeightsExerciseTest1.setReps(1);
        bodyWeightsExerciseTest2.setReps(100);

        assertEquals(1, bodyWeightsExerciseTest1.getReps());
        assertEquals(100, bodyWeightsExerciseTest2.getReps());
    }

    @Test
    public void testToJson() {
        JSONObject jsonObjectTest1 = bodyWeightsExerciseTest1.toJson();
        JSONObject jsonObjectTest2 = bodyWeightsExerciseTest2.toJson();

        assertEquals("BodyWeightsExercise", jsonObjectTest1.getString("exerciseType"));
        assertEquals("Pullups", jsonObjectTest1.getString("name"));
        assertEquals("Shoulders", jsonObjectTest1.get("muscleGroup"));
        assertEquals(3, jsonObjectTest1.getInt("sets"));
        assertEquals(5, jsonObjectTest1.getInt("reps"));
        assertEquals(3, jsonObjectTest1.get("difficulty"));
        assertEquals(30, jsonObjectTest1.getInt("time"));
        assertFalse(jsonObjectTest1.getBoolean("favourite"));

        assertEquals("BodyWeightsExercise", jsonObjectTest2.getString("exerciseType"));
        assertEquals("Situps", jsonObjectTest2.getString("name"));
        assertEquals("Core", jsonObjectTest2.get("muscleGroup"));
        assertEquals(2, jsonObjectTest2.getInt("sets"));
        assertEquals(20, jsonObjectTest2.getInt("reps"));
        assertEquals(1, jsonObjectTest2.get("difficulty"));
        assertEquals(15, jsonObjectTest2.getInt("time"));
        assertFalse(jsonObjectTest2.getBoolean("favourite"));
    }
}
