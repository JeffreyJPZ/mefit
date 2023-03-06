package persistence;

import exceptions.InvalidExerciseException;
import model.Profile;
import model.ProfilesById;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest extends JsonTestHelpers {
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    @BeforeEach
    public void runBefore() {
        Profile.setNextId(1); // for consistency
    }

    @Test
    public void testJsonWriterFileNotFoundException() {
        jsonWriter = new JsonWriter("./data/\trandomfile//.json");

        try {
            ProfilesById profilesById = new ProfilesById();
            jsonWriter.open();
            fail("Expected FileNotFoundException, caught none");
        } catch (FileNotFoundException e) {
            // passes
        }
    }

    @Test
    public void testJsonWriterEmptyProfiles() {
        jsonWriter = new JsonWriter("./data/testJsonWriterEmptyProfiles.json");
        jsonReader = new JsonReader("./data/testJsonWriterEmptyProfiles.json");

        try {
            ProfilesById profilesByIdTest1 = new ProfilesById();
            jsonWriter.open();
            jsonWriter.write(profilesByIdTest1);
            jsonWriter.close();

            ProfilesById profilesByIdTest2 = jsonReader.read();

            assertTrue(profilesByIdTest2.isEmpty());

        } catch (InvalidExerciseException e) {
            fail("Expected none, caught InvalidExerciseException");
        } catch (IOException e) {
            fail("Expected none, caught IOException");
        }
    }

    @Test
    public void testJsonWriterNormalProfiles() {
        jsonWriter = new JsonWriter("./data/testJsonWriterNormalProfiles.json");
        jsonReader = new JsonReader("./data/testJsonWriterNormalProfiles.json");

        try {
            ProfilesById profilesByIdTest1 = new ProfilesById();
            profilesByIdTest1.addProfile(new Profile("1", "1", 1, 1));
            profilesByIdTest1.addProfile(new Profile("2", "2", 2, 2));

            jsonWriter.open();
            jsonWriter.write(profilesByIdTest1);
            jsonWriter.close();

            ProfilesById profilesByIdTest2 = jsonReader.read();

            assertEquals(2, profilesByIdTest2.length());
            checkProfile("1", "1", 1, 1, 1, 0, 0,
                    profilesByIdTest2.getProfile(1));
            checkProfile("2", "2", 2, 2, 2, 0, 0,
                    profilesByIdTest2.getProfile(2));

        } catch (InvalidExerciseException e) {
            fail("Expected none, caught InvalidExerciseException");
        } catch (IOException e) {
            fail("Expected none, caught IOException");
        }
    }
}
