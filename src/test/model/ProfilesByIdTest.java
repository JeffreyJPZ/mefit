package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static model.ProfilesById.NO_PROFILES_MESSAGE;
import static org.junit.jupiter.api.Assertions.*;

// Test class for ProfilesByIdTest
public class ProfilesByIdTest {
    private ProfilesById profilesByIdTest1;

    @BeforeEach
    public void runBefore() {
        profilesByIdTest1 = new ProfilesById();
    }

    @Test
    public void testConstructorEmptyList() {
        assertTrue(profilesByIdTest1.isEmpty());
    }

    @Test
    public void testAddProfileOnceEmptyMap() {
        addProfileHelper(profilesByIdTest1, 1);

        assertEquals(1, profilesByIdTest1.length());

        assertEquals("1", profilesByIdTest1.getProfile(1).getName());
        assertEquals("1", profilesByIdTest1.getProfile(1).getGender());
        assertEquals(1, profilesByIdTest1.getProfile(1).getAge());
        assertEquals(1, profilesByIdTest1.getProfile(1).getWeight());
    }

    @Test
    public void testAddProfileOnceNonEmptyMap() {
        addProfileHelper(profilesByIdTest1, 2);

        assertEquals(2, profilesByIdTest1.length());

        profilesByIdTest1.addProfile(new Profile("3", "3", 3, 3));

        assertEquals(3, profilesByIdTest1.length());

        assertEquals("3", profilesByIdTest1.getProfile(3).getName());
        assertEquals("3", profilesByIdTest1.getProfile(3).getGender());
        assertEquals(3, profilesByIdTest1.getProfile(3).getAge());
        assertEquals(3, profilesByIdTest1.getProfile(3).getWeight());
    }

    @Test
    public void testAddProfileMultipleEmptyMap() {
        profilesByIdTest1.addProfile(new Profile("1", "1", 1, 1));
        profilesByIdTest1.addProfile(new Profile("2", "2", 2, 2));

        assertEquals(2, profilesByIdTest1.length());

        assertEquals("1", profilesByIdTest1.getProfile(1).getName());
        assertEquals("1", profilesByIdTest1.getProfile(1).getGender());
        assertEquals(1, profilesByIdTest1.getProfile(1).getAge());
        assertEquals(1, profilesByIdTest1.getProfile(1).getWeight());

        assertEquals("2", profilesByIdTest1.getProfile(2).getName());
        assertEquals("2", profilesByIdTest1.getProfile(2).getGender());
        assertEquals(2, profilesByIdTest1.getProfile(2).getAge());
        assertEquals(2, profilesByIdTest1.getProfile(2).getWeight());
    }

    @Test
    public void testAddProfileMultipleNonEmptyMap() {
        addProfileHelper(profilesByIdTest1, 2);

        assertEquals(2, profilesByIdTest1.length());

        profilesByIdTest1.addProfile(new Profile("3", "3", 3, 3));
        profilesByIdTest1.addProfile(new Profile("4", "4", 4, 4));

        assertEquals("3", profilesByIdTest1.getProfile(3).getName());
        assertEquals("3", profilesByIdTest1.getProfile(3).getGender());
        assertEquals(3, profilesByIdTest1.getProfile(3).getAge());
        assertEquals(3, profilesByIdTest1.getProfile(3).getWeight());

        assertEquals("4", profilesByIdTest1.getProfile(4).getName());
        assertEquals("4", profilesByIdTest1.getProfile(4).getGender());
        assertEquals(4, profilesByIdTest1.getProfile(4).getAge());
        assertEquals(4, profilesByIdTest1.getProfile(4).getWeight());
    }

    @Test
    public void testRemoveProfileOnceSingleElementMap() {
        addProfileHelper(profilesByIdTest1, 1);

        assertEquals(1, profilesByIdTest1.length());

        profilesByIdTest1.removeProfile(1);

        assertEquals(0, profilesByIdTest1.length());
    }

    @Test
    public void testRemoveProfileOnceMultipleElementMap() {
        addProfileHelper(profilesByIdTest1, 2);

        assertEquals(2, profilesByIdTest1.length());

        profilesByIdTest1.removeProfile(1);

        assertEquals(1, profilesByIdTest1.length());
        assertEquals(2, profilesByIdTest1.getProfile(2).getId());
    }

    @Test
    public void testRemoveProfileMultipleElementMap() {
        addProfileHelper(profilesByIdTest1, 5);

        assertEquals(5, profilesByIdTest1.length());

        profilesByIdTest1.removeProfile(1);
        profilesByIdTest1.removeProfile(3);
        profilesByIdTest1.removeProfile(5);

        assertEquals(2, profilesByIdTest1.length());
        assertEquals(2, profilesByIdTest1.getProfile(2).getId());
        assertEquals(4, profilesByIdTest1.getProfile(4).getId());
    }

    @Test
    public void testToStringEmptyMap() {
        assertEquals(NO_PROFILES_MESSAGE, profilesByIdTest1.toString());
    }

    @Test
    public void testToStringOneProfileInMap() {
        addProfileHelper(profilesByIdTest1, 1);
        assertEquals("[1] 1", profilesByIdTest1.toString());
    }

    @Test
    public void testToStringMultipleProfileInMap() {
        addProfileHelper(profilesByIdTest1, 2);

        assertEquals("[1] 1" + "\n" + "[2] 2", profilesByIdTest1.toString());
    }

    @Test
    public void testContainsEmptyMap() {
        assertFalse(profilesByIdTest1.contains("1"));
    }

    @Test
    public void testContainsSingleProfileInMapAndDoesContain() {
        addProfileHelper(profilesByIdTest1, 1);

        assertTrue(profilesByIdTest1.contains("1"));
    }

    @Test
    public void testContainsSingleProfileInMapAndDoesNotContain() {
        addProfileHelper(profilesByIdTest1, 1);

        assertFalse(profilesByIdTest1.contains("2"));
    }

    @Test
    public void testContainsMultipleProfileInMapAndDoesContain() {
        addProfileHelper(profilesByIdTest1, 5);

        assertTrue(profilesByIdTest1.contains("1"));
        assertTrue(profilesByIdTest1.contains("3"));
    }

    @Test
    public void testContainsMultipleProfileInMapAndDoesNotContain() {
        addProfileHelper(profilesByIdTest1, 5);

        assertFalse(profilesByIdTest1.contains("6"));
        assertFalse(profilesByIdTest1.contains("7"));
    }


    @Test
    public void isEmptyEmptyMap() {
        assertTrue(profilesByIdTest1.isEmpty());
    }

    @Test
    public void isEmptySingleProfileInMap() {
        addProfileHelper(profilesByIdTest1, 1);

        assertFalse(profilesByIdTest1.isEmpty());
    }

    @Test
    public void isEmptyMultipleProfileInMap() {
        addProfileHelper(profilesByIdTest1, 3);

        assertFalse(profilesByIdTest1.isEmpty());
    }

    @Test
    public void lengthEmptyMap() {
        assertEquals(0, profilesByIdTest1.length());
    }

    @Test
    public void lengthSingleProfileInMap() {
        addProfileHelper(profilesByIdTest1, 1);

        assertEquals(1, profilesByIdTest1.length());
    }

    @Test
    public void lengthMultipleProfileInMap() {
        addProfileHelper(profilesByIdTest1, 2);

        assertEquals(2, profilesByIdTest1.length());
    }

    @Test
    public void getProfileOneProfileInMap() {
        addProfileHelper(profilesByIdTest1, 1);

        assertEquals(1, profilesByIdTest1.getProfile(1).getId());
    }

    @Test
    public void getProfileMultipleProfileInMap() {
        addProfileHelper(profilesByIdTest1, 2);

        assertEquals(1, profilesByIdTest1.getProfile(1).getId());
        assertEquals(2, profilesByIdTest1.getProfile(2).getId());
    }

    private void addProfileHelper(ProfilesById profilesById, int repeats) {
        for (int i = 0; i < repeats; i++) {
            profilesById.addProfile(new Profile(Integer.toString(i + 1), Integer.toString(i + 1),
                    i + 1, i + 1));
        }
    }
}
