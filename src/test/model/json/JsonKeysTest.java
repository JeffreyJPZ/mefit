package model.json;

import org.junit.jupiter.api.Test;

import static model.json.JsonKeys.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

// Test class for JsonKeys
public class JsonKeysTest {

    @Test
    public void testValues() {
        assertEquals("profiles", PROFILES.getKey());
        assertEquals("id", PROFILE_ID.getKey());
        assertEquals("name", PROFILE_NAME.getKey());
        assertEquals("age", PROFILE_AGE.getKey());
        assertEquals("gender", PROFILE_GENDER.getKey());
        assertEquals("weight", PROFILE_WEIGHT.getKey());
        assertEquals("exercises", EXERCISES.getKey());
        assertEquals("name", EXERCISE_NAME.getKey());
        assertEquals("exerciseType", EXERCISE_TYPE.getKey());
        assertEquals("muscleGroup", EXERCISE_MUSCLE_GROUP.getKey());
        assertEquals("difficulty", EXERCISE_DIFFICULTY.getKey());
        assertEquals("time", EXERCISE_TIME.getKey());
        assertEquals("favourite", EXERCISE_FAVOURITE.getKey());
        assertEquals("weight", EXERCISE_WEIGHT.getKey());
        assertEquals("sets", EXERCISE_SETS.getKey());
        assertEquals("reps", EXERCISE_REPS.getKey());
        assertEquals("distance", EXERCISE_DISTANCE.getKey());
        assertEquals("workouts", WORKOUTS.getKey());
        assertEquals("name", WORKOUT_NAME.getKey());
        assertEquals("difficulty", WORKOUT_DIFFICULTY.getKey());
        assertEquals("favourite", WORKOUT_FAVOURITE.getKey());
        assertEquals("exercises", WORKOUT_EXERCISES.getKey());
    }
}
