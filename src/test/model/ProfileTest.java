package model;

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
        assertEquals(20, profileTest1.getAge());
        assertEquals(160, profileTest1.getWeight());

        assertTrue(profileTest1.getExercises().isEmpty());
        assertTrue(profileTest1.getWorkouts().isEmpty());

        assertEquals(1, profileTest1.getId());

        assertEquals("Sukmai", profileTest2.getName());
        assertEquals("None", profileTest2.getGender());
        assertEquals(95, profileTest2.getAge());
        assertEquals(30, profileTest2.getWeight());

        assertTrue(profileTest2.getExercises().isEmpty());
        assertTrue(profileTest2.getWorkouts().isEmpty());

        assertEquals(2, profileTest2.getId());
    }

    @Test
    public void testConstructorBoundary() {
        Profile profileTest3 = new Profile("Boundary", "Male", 1, 1);

        assertEquals("Boundary", profileTest3.getName());
        assertEquals("Male", profileTest3.getGender());
        assertEquals(1, profileTest3.getAge());
        assertEquals(1, profileTest3.getWeight());

        assertTrue(profileTest3.getExercises().isEmpty());
        assertTrue(profileTest3.getWorkouts().isEmpty());

        assertEquals(3, profileTest3.getId());
    }

    @Test
    public void testToString() {
        String tempToString1 = "Name: " + profileTest1.getName() + "\n"
                            + "Gender: " + profileTest1.getGender() + "\n"
                            + "Age: " + profileTest1.getAge() + "\n"
                            + "Weight: " + profileTest1.getWeight();

        String tempToString2 = "Name: " + profileTest2.getName() + "\n"
                + "Gender: " + profileTest2.getGender() + "\n"
                + "Age: " + profileTest2.getAge() + "\n"
                + "Weight: " + profileTest2.getWeight();

        assertEquals(tempToString1, profileTest1.toString());
        assertEquals(tempToString2, profileTest2.toString());
    }
}