package model;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

// Test class for Profile
class ProfileTest {
    private Profile profileTest1;
    private Profile profileTest2;
    private Profile profileTest3;

    @BeforeEach
    public void runBefore() {
        profileTest1 = new Profile("Shagua", "Shabi", "Female", 20, 160);
        profileTest2 = new Profile("Sukmai", "Mak", "None", 95, 30);
    }

    @Test
    public void testConstructorTypical() {
        assertEquals("Shagua", profileTest1.getFirstName());
        assertEquals("Shabi", profileTest1.getLastName());
        assertEquals("Female", profileTest1.getGender());
        assertEquals(20, profileTest1.getAge());
        assertEquals(160, profileTest1.getWeight());

        assertTrue(profileTest1.getExerciseList().isEmpty());
        assertTrue(profileTest1.getWorkoutList().isEmpty());

        assertEquals(1, profileTest1.getId());

        assertEquals("Sukmai", profileTest1.getFirstName());
        assertEquals("Mak", profileTest1.getLastName());
        assertEquals("None", profileTest1.getGender());
        assertEquals(95, profileTest1.getAge());
        assertEquals(30, profileTest1.getWeight());

        assertTrue(profileTest2.getExerciseList().isEmpty());
        assertTrue(profileTest2.getWorkoutList().isEmpty());

        assertEquals(2, profileTest2.getId());
    }

    @Test
    public void testConstructorBoundary() {
        profileTest3 = new Profile("Boundary", "Case", "Male", 1, 1);

        assertEquals("Boundary", profileTest3.getFirstName());
        assertEquals("Case", profileTest3.getLastName());
        assertEquals("Male", profileTest3.getGender());
        assertEquals(1, profileTest3.getAge());
        assertEquals(1, profileTest3.getWeight());

        assertTrue(profileTest3.getExerciseList().isEmpty());
        assertTrue(profileTest3.getWorkoutList().isEmpty());

        assertEquals(3, profileTest3.getId());
    }

    @Test
    public void testDisplaySummary() {
        String tempSummary1 = "First Name: " + profileTest1.getFirstName() + "\n"
                            + "Last Name: " + profileTest1.getLastName() + "\n"
                            + "Gender: " + profileTest1.getGender() + "\n"
                            + "Age: " + profileTest1.getAge() + "\n"
                            + "Weight: " + profileTest1.getWeight();

        String tempSummary2 = "First Name: " + profileTest2.getFirstName() + "\n"
                + "Last Name: " + profileTest2.getLastName() + "\n"
                + "Gender: " + profileTest2.getGender() + "\n"
                + "Age: " + profileTest2.getAge() + "\n"
                + "Weight: " + profileTest2.getWeight();

        assertEquals(tempSummary1, profileTest1.viewProfile());
        assertEquals(tempSummary2, profileTest2.viewProfile());
    }
}