package persistence;

import exceptions.InvalidExerciseException;
import model.profiles.profile.Profile;
import model.profiles.ProfilesById;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

// Test class for JsonReader
// implementation attributed to JsonSerializationDemo from CPSC 210
public class JsonReaderTest extends JsonTestHelpers {
    private JsonReader jsonReader;

    @BeforeEach
    public void runBefore() {
        Profile.setNextId(1); // for consistency
    }

    @Test
    public void testJsonReaderNoPathIOExceptionExpected() {
        jsonReader = new JsonReader("./data/emptyfile.json");
        try {
            ProfilesById profilesById = jsonReader.read();
            fail("Expected IOException, no exception thrown");
        } catch (InvalidExerciseException e) {
            fail("Expected IOException, caught InvalidExerciseException");
        } catch (IOException e) {
            // passes
        }
    }

    @Test
    public void testJsonReaderEmptyProfiles() {
        jsonReader = new JsonReader("./data/testJsonReaderEmptyProfiles.json");
        try {
            ProfilesById profilesById = jsonReader.read();
            assertTrue(profilesById.isEmpty());
            assertEquals(1, Profile.getNextId());
        } catch (InvalidExerciseException e) {
            fail("Expected none, caught InvalidExerciseException");
        } catch (IOException e) {
            fail("Expected none, caught IOException");
        }
    }

    @Test
    public void testJsonReaderNormalProfiles() {
        jsonReader = new JsonReader("./data/testJsonReaderNormalProfiles.json");
        try {
            ProfilesById profilesById = jsonReader.read();
            assertEquals(3, profilesById.length());
            assertEquals(1, profilesById.getProfile(1).getId());
            assertEquals(2, profilesById.getProfile(2).getId());
            assertEquals(3, profilesById.getProfile(3).getId());
            assertEquals(4, Profile.getNextId());
        } catch (InvalidExerciseException e) {
            fail("Expected none, caught InvalidExerciseException");
        } catch (IOException e) {
            fail("Expected none, caught IOException");
        }
    }

    @Test
    public void testProfilesJsonToProfiles() {
        jsonReader = new JsonReader("./data/testProfilesJsonToProfiles.json");
        try {
            ProfilesById profilesById = jsonReader.read();
            assertEquals(3, profilesById.length());
            checkProfile("A", "1", 1, 1, 1, 6, 2,
                    profilesById.getProfile(1));
            checkProfile("B", "Other", 2, 2, 2, 1, 1,
                    profilesById.getProfile(2));
            checkProfile("C", "Bozo", 3, 3, 3, 0, 0,
                    profilesById.getProfile(3));
            assertEquals(4, Profile.getNextId());
        } catch (InvalidExerciseException e) {
            fail("Expected none, caught InvalidExerciseException");
        } catch (IOException e) {
            fail("Expected none, caught IOException");
        }
    }

    @Test
    public void testProfilesJsonToProfilesInvalidExerciseTypeExceptionInExercises() {
        jsonReader = new JsonReader(
                "./data/testProfilesJsonToProfilesInvalidExerciseTypeExceptionInExercises.json");
        try {
            ProfilesById profilesById = jsonReader.read();
            fail("Expected InvalidExerciseException, caught none");
        } catch (InvalidExerciseException e) {
            // passes
        } catch (IOException e) {
            fail("Expected InvalidExerciseException, caught IOException");
        }
    }

    @Test
    public void testProfilesJsonToProfilesInvalidExerciseTypeExceptionInWorkout() {
        jsonReader = new JsonReader(
                "./data/testProfilesJsonToProfilesInvalidExerciseTypeExceptionInWorkout.json");
        try {
            ProfilesById profilesById = jsonReader.read();
            fail("Expected InvalidExerciseException, caught none");
        } catch (InvalidExerciseException e) {
            // passes
        } catch (IOException e) {
            fail("Expected InvalidExerciseException, caught IOException");
        }
    }
}
