package persistence;

import model.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTestHelpers {
    public void checkProfile(String name, String gender, int age, int weight, int id,
                             int exercises, int workouts, Profile profile) {
        assertEquals(name, profile.getName());
        assertEquals(gender, profile.getGender());
        assertEquals(age, profile.getAge());
        assertEquals(weight, profile.getWeight());
        assertEquals(id, profile.getId());

        assertEquals(exercises, profile.getExercises().length());
        assertEquals(workouts, profile.getWorkouts().length());
    }
}
