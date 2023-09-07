package persistence;

import model.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Json test class with helper methods
// implementation attributed to JsonSerializationDemo from CPSC 210
public class JsonTestHelpers {
    public void checkProfile(String name, String gender, int age, int weight, int id,
                             int exercises, int workouts, Profile profile) {
        assertEquals(name, profile.getName());
        assertEquals(gender, profile.getGender());
        assertEquals(age, profile.getAgeYears());
        assertEquals(weight, profile.getWeightPounds());
        assertEquals(id, profile.getId());

        assertEquals(exercises, profile.getExercises().length());
        assertEquals(workouts, profile.getWorkouts().length());
    }
}
