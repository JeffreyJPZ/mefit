package model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

// Test class for Profile
class ProfileTest {
    private Profile profileTest1;
    private Profile profileTest2;

    @BeforeEach
    public void runBefore() {
        Profile.setNextId(1);
        profileTest1 = new Profile("Shagua", "Female", 20, 160);
        profileTest2 = new Profile("Sukmai", "None", 95, 30);
    }

    @Test
    public void testConstructorTypical() {
        assertEquals("Shagua", profileTest1.getName());
        assertEquals("Female", profileTest1.getGender());
        assertEquals(20, profileTest1.getAgeYears());
        assertEquals(160, profileTest1.getWeightPounds());

        assertTrue(profileTest1.getExercises().isEmpty());
        assertTrue(profileTest1.getWorkouts().isEmpty());

        assertEquals(1, profileTest1.getId());

        assertEquals("Sukmai", profileTest2.getName());
        assertEquals("None", profileTest2.getGender());
        assertEquals(95, profileTest2.getAgeYears());
        assertEquals(30, profileTest2.getWeightPounds());

        assertTrue(profileTest2.getExercises().isEmpty());
        assertTrue(profileTest2.getWorkouts().isEmpty());

        assertEquals(2, profileTest2.getId());
    }

    @Test
    public void testConstructorBoundary() {
        Profile profileTest3 = new Profile("Boundary", "Male", 1, 1);

        assertEquals("Boundary", profileTest3.getName());
        assertEquals("Male", profileTest3.getGender());
        assertEquals(1, profileTest3.getAgeYears());
        assertEquals(1, profileTest3.getWeightPounds());

        assertTrue(profileTest3.getExercises().isEmpty());
        assertTrue(profileTest3.getWorkouts().isEmpty());

        assertEquals(3, profileTest3.getId());
    }

    @Test
    public void testToString() {
        String tempToString1 = "Name: " + profileTest1.getName() + "\n"
                            + "Gender: " + profileTest1.getGender() + "\n"
                            + "Age (yrs): " + profileTest1.getAgeYears() + "\n"
                            + "Weight (lbs): " + profileTest1.getWeightPounds();

        String tempToString2 = "Name: " + profileTest2.getName() + "\n"
                + "Gender: " + profileTest2.getGender() + "\n"
                + "Age (yrs): " + profileTest2.getAgeYears() + "\n"
                + "Weight (lbs): " + profileTest2.getWeightPounds();

        assertEquals(tempToString1, profileTest1.toString());
        assertEquals(tempToString2, profileTest2.toString());
    }

    @Test
    public void testSetName() {
        profileTest1.setName("Jeff");
        profileTest2.setName("A");

        assertEquals("Jeff", profileTest1.getName());
        assertEquals("A", profileTest2.getName());
    }

    @Test
    public void testSetGender() {
        profileTest1.setGender("Jeff");
        profileTest2.setGender("A");

        assertEquals("Jeff", profileTest1.getGender());
        assertEquals("A", profileTest2.getGender());
    }

    @Test
    public void testSetAge() {
        profileTest1.setAgeYears(5);
        profileTest2.setAgeYears(50);

        assertEquals(5, profileTest1.getAgeYears());
        assertEquals(50, profileTest2.getAgeYears());
    }

    @Test
    public void testSetWeight() {
        profileTest1.setWeightPounds(3);
        profileTest2.setWeightPounds(30);

        assertEquals(3, profileTest1.getWeightPounds());
        assertEquals(30, profileTest2.getWeightPounds());
    }

    @Test
    public void testSetId() {
        profileTest1.setId(3);

        assertEquals(3, profileTest1.getId());

        profileTest2.setId(5);

        assertEquals(5, profileTest2.getId());
    }

    @Test
    public void testSetExercisesEmptyExercises() {
        assertTrue(profileTest1.getExercises().isEmpty());

        ExercisesByName exercisesByNameTest1 = new ExercisesByName();

        profileTest1.setExercises(exercisesByNameTest1);

        assertTrue(profileTest1.getExercises().isEmpty());
    }

    @Test
    public void testSetExercisesMultipleExercisesFromEmpty() {
        assertTrue(profileTest1.getExercises().isEmpty());

        ExercisesByName exercisesByNameTest1 = new ExercisesByName();
        exercisesByNameTest1.addExercise(new CardioExercise("1", MuscleGroup.LEGS, 1,
                Difficulty.INTENSE, 1));
        exercisesByNameTest1.addExercise(new CardioExercise("2", MuscleGroup.BACK, 2,
                Difficulty.LIGHT, 2));

        profileTest1.setExercises(exercisesByNameTest1);

        assertEquals(2, profileTest1.getExercises().length());
        assertEquals("1", profileTest1.getExercises().getExercise("1").getName());
        assertEquals("2", profileTest1.getExercises().getExercise("2").getName());
    }

    @Test
    public void testSetExercisesEmptyExercisesFromMultiple() {
        ExercisesByName exercisesByNameTest1 = new ExercisesByName();
        exercisesByNameTest1.addExercise(new CardioExercise("1", MuscleGroup.LEGS, 1,
                Difficulty.INTENSE, 1));
        exercisesByNameTest1.addExercise(new CardioExercise("2", MuscleGroup.BACK, 2,
                Difficulty.LIGHT, 2));

        profileTest1.setExercises(exercisesByNameTest1);

        assertEquals(2, profileTest1.getExercises().length());
        assertEquals("1", profileTest1.getExercises().getExercise("1").getName());
        assertEquals("2", profileTest1.getExercises().getExercise("2").getName());

        profileTest1.setExercises(new ExercisesByName());
        assertTrue(profileTest1.getExercises().isEmpty());
    }

    @Test
    public void testSetWorkoutsEmptyWorkouts() {
        assertTrue(profileTest1.getWorkouts().isEmpty());

        WorkoutsByName workoutsByNameTest1 = new WorkoutsByName();

        profileTest1.setWorkouts(workoutsByNameTest1);

        assertTrue(profileTest1.getWorkouts().isEmpty());
    }

    @Test
    public void testSetWorkoutsMultipleWorkoutsFromEmpty() {
        assertTrue(profileTest1.getWorkouts().isEmpty());

        WorkoutsByName workoutsByNameTest1 = new WorkoutsByName();
        workoutsByNameTest1.addWorkout(new Workout("1", Difficulty.LIGHT));
        workoutsByNameTest1.addWorkout(new Workout("2", Difficulty.MODERATE));

        profileTest1.setWorkouts(workoutsByNameTest1);

        assertEquals(2, profileTest1.getWorkouts().length());
        assertEquals("1", profileTest1.getWorkouts().getWorkout("1").getName());
        assertEquals("2", profileTest1.getWorkouts().getWorkout("2").getName());
    }

    @Test
    public void testSetWorkoutsEmptyWorkoutsFromMultiple() {
        WorkoutsByName workoutsByNameTest1 = new WorkoutsByName();
        workoutsByNameTest1.addWorkout(new Workout("1", Difficulty.LIGHT));
        workoutsByNameTest1.addWorkout(new Workout("2", Difficulty.MODERATE));

        profileTest1.setWorkouts(workoutsByNameTest1);

        assertEquals(2, profileTest1.getWorkouts().length());
        assertEquals("1", profileTest1.getWorkouts().getWorkout("1").getName());
        assertEquals("2", profileTest1.getWorkouts().getWorkout("2").getName());

        profileTest1.setWorkouts(new WorkoutsByName());

        assertTrue(profileTest1.getWorkouts().isEmpty());

    }

    @Test
    public void testToJsonEmptyExercisesAndWorkouts() {
        JSONObject jsonObjectTest1 = profileTest1.toJson();

        assertEquals("Shagua", jsonObjectTest1.getString("name"));
        assertEquals("Female", jsonObjectTest1.getString("gender"));
        assertEquals(20, jsonObjectTest1.getInt("age"));
        assertEquals(160, jsonObjectTest1.getInt("weight"));
        assertTrue(jsonObjectTest1.getJSONObject("exercises").getJSONArray("exercises").isEmpty());
        assertTrue(jsonObjectTest1.getJSONObject("workouts").getJSONArray("workouts").isEmpty());
    }

    @Test
    public void testToJsonNonEmptyExercisesAndWorkouts() {
        profileTest2.getExercises().addExercise(new CardioExercise("1", MuscleGroup.LEGS,
                1, Difficulty.LIGHT, 1));
        profileTest2.getExercises().addExercise(new CardioExercise("2", MuscleGroup.LEGS,
                2, Difficulty.LIGHT, 2));
        profileTest2.getWorkouts().addWorkout(new Workout("1", Difficulty.MODERATE));
        profileTest2.getWorkouts().addWorkout(new Workout("2", Difficulty.MODERATE));

        JSONObject jsonObjectTest2 = profileTest2.toJson();

        assertEquals("Sukmai", jsonObjectTest2.getString("name"));
        assertEquals("None", jsonObjectTest2.getString("gender"));
        assertEquals(95, jsonObjectTest2.getInt("age"));
        assertEquals(30, jsonObjectTest2.getInt("weight"));

        JSONArray jsonExercises = jsonObjectTest2.getJSONObject("exercises").getJSONArray("exercises");
        JSONArray jsonWorkouts = jsonObjectTest2.getJSONObject("workouts").getJSONArray("workouts");

        assertEquals("1", jsonExercises.getJSONObject(0).getString("name"));
        assertEquals("2", jsonExercises.getJSONObject(1).getString("name"));

        assertEquals("1", jsonWorkouts.getJSONObject(0).getString("name"));
        assertEquals("2", jsonWorkouts.getJSONObject(1).getString("name"));
    }
}