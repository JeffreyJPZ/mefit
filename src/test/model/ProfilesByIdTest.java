package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static model.ProfilesById.ADDITIONAL_PROFILE_MESSAGE;
import static model.ProfilesById.DISPLAY_NUMBER_OF_PROFILES;
import static org.junit.jupiter.api.Assertions.*;

// Test class for ProfilesByIdTest
public class ProfilesByIdTest {
    private ProfilesById profilesByIdTest1;

    @BeforeEach
    public void runBefore() {
        profilesByIdTest1 = new ProfilesById();
        Profile.setNextId(1);
    }

    @Test
    public void testConstructorEmptyMap() {
        assertTrue(profilesByIdTest1.isEmpty());
    }

    @Test
    public void testAddProfileOnceEmptyMap() {
        addProfileHelper(profilesByIdTest1, 1);

        assertEquals(1, profilesByIdTest1.length());

        Profile profile1 = profilesByIdTest1.getProfile(1);

        assertEquals("1", profile1.getName());
        assertEquals("1", profile1.getGender());
        assertEquals(1, profile1.getAge());
        assertEquals(1, profile1.getWeight());
    }

    @Test
    public void testAddProfileOnceNonEmptyMap() {
        addProfileHelper(profilesByIdTest1, 2);

        assertEquals(2, profilesByIdTest1.length());

        profilesByIdTest1.addProfile(new Profile("3", "3", 3, 3));

        assertEquals(3, profilesByIdTest1.length());

        Profile profile2 = profilesByIdTest1.getProfile(3);

        assertEquals("3", profile2.getName());
        assertEquals("3", profile2.getGender());
        assertEquals(3, profile2.getAge());
        assertEquals(3, profile2.getWeight());
    }

    @Test
    public void testAddProfileMultipleEmptyMap() {
        profilesByIdTest1.addProfile(new Profile("1", "1", 1, 1));
        profilesByIdTest1.addProfile(new Profile("2", "2", 2, 2));

        assertEquals(2, profilesByIdTest1.length());

        Profile profile3 = profilesByIdTest1.getProfile(1);
        Profile profile4 = profilesByIdTest1.getProfile(2);

        assertEquals("1", profile3.getName());
        assertEquals("1", profile3.getGender());
        assertEquals(1, profile3.getAge());
        assertEquals(1, profile3.getWeight());

        assertEquals("2", profile4.getName());
        assertEquals("2", profile4.getGender());
        assertEquals(2, profile4.getAge());
        assertEquals(2, profile4.getWeight());
    }

    @Test
    public void testAddProfileMultipleNonEmptyMap() {
        addProfileHelper(profilesByIdTest1, 2);

        assertEquals(2, profilesByIdTest1.length());

        profilesByIdTest1.addProfile(new Profile("3", "3", 3, 3));
        profilesByIdTest1.addProfile(new Profile("4", "4", 4, 4));

        Profile profile5 = profilesByIdTest1.getProfile(3);
        Profile profile6 = profilesByIdTest1.getProfile(4);

        assertEquals("3", profile5.getName());
        assertEquals("3", profile5.getGender());
        assertEquals(3, profile5.getAge());
        assertEquals(3, profile5.getWeight());

        assertEquals("4", profile6.getName());
        assertEquals("4", profile6.getGender());
        assertEquals(4, profile6.getAge());
        assertEquals(4, profile6.getWeight());
    }

    @Test
    public void testRemoveProfileOnceSingleProfileMap() {
        addProfileHelper(profilesByIdTest1, 1);

        assertEquals(1, profilesByIdTest1.length());

        profilesByIdTest1.removeProfile(1);

        assertEquals(0, profilesByIdTest1.length());
    }

    @Test
    public void testRemoveProfileOnceMultipleProfileMap() {
        addProfileHelper(profilesByIdTest1, 2);

        assertEquals(2, profilesByIdTest1.length());

        profilesByIdTest1.removeProfile(1);

        assertEquals(1, profilesByIdTest1.length());
        assertEquals(2, profilesByIdTest1.getProfile(2).getId());
    }

    @Test
    public void testRemoveProfileMultipleProfileMap() {
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
        assertEquals("ID\tName" + "\n", profilesByIdTest1.toString());
    }

    @Test
    public void testToStringOneProfileInMap() {
        addProfileHelper(profilesByIdTest1, 1);
        assertEquals("ID\tName" + "\n"
                            + "[1]\t1" + "\n", profilesByIdTest1.toString());
    }

    @Test
    public void testToStringMultipleProfileInMap() {
        addProfileHelper(profilesByIdTest1, 2);

        assertEquals("ID\tName" + "\n"
                            + "[1]\t1" + "\n"
                            + "[2]\t2" + "\n", profilesByIdTest1.toString());
    }

    @Test
    public void testToStringMultipleProfileInMapBoundary() {
        addProfileHelper(profilesByIdTest1, DISPLAY_NUMBER_OF_PROFILES);

        assertEquals("ID\tName" + "\n"
                            + "[1]\t1" + "\n"
                            + "[2]\t2" + "\n"
                            + "[3]\t3" + "\n"
                            + "[4]\t4" + "\n"
                            + "[5]\t5" + "\n"
                            + "[6]\t6" + "\n"
                            + "[7]\t7" + "\n"
                            + "[8]\t8" + "\n"
                            + "[9]\t9" + "\n"
                            + "[10]\t10" + "\n", profilesByIdTest1.toString());
    }

    @Test
    public void testToStringMultipleProfileInMapGreaterThanBoundary() {
        addProfileHelper(profilesByIdTest1, DISPLAY_NUMBER_OF_PROFILES + 1);

        assertEquals("ID\tName" + "\n"
                            + "[1]\t1" + "\n"
                            + "[2]\t2" + "\n"
                            + "[3]\t3" + "\n"
                            + "[4]\t4" + "\n"
                            + "[5]\t5" + "\n"
                            + "[6]\t6" + "\n"
                            + "[7]\t7" + "\n"
                            + "[8]\t8" + "\n"
                            + "[9]\t9" + "\n"
                            + "[10]\t10" + "\n"
                            + "... with " + 1 + ADDITIONAL_PROFILE_MESSAGE, profilesByIdTest1.toString());
    }

    @Test
    public void testFilterEmptyMapNoMatch() {
        ProfilesById profilesById = profilesByIdTest1.filter("1");

        assertEquals(0, profilesById.getProfiles().size());
    }

    @Test
    public void testFilterSingleProfileInMapMatches() {
        addProfileHelper(profilesByIdTest1, 1);
        ProfilesById profilesById = profilesByIdTest1.filter("1");

        assertEquals(1, profilesById.getProfiles().size());
        assertEquals(1, profilesById.getProfiles().get(1).getId());
    }

    @Test
    public void testFilterMultipleProfileInMapMatches() {
        addProfileHelper(profilesByIdTest1, 11);
        ProfilesById profilesById = profilesByIdTest1.filter("1");

        assertEquals(3, profilesById.getProfiles().size());
        assertEquals(1, profilesById.getProfiles().get(1).getId());
        assertEquals(10, profilesById.getProfiles().get(10).getId());
        assertEquals(11, profilesById.getProfiles().get(11).getId());
    }

    @Test
    public void testFilterMultipleProfileInMapCaseInsensitive() {
        profilesByIdTest1.addProfile(new Profile("abc", "1", 1, 1));
        profilesByIdTest1.addProfile(new Profile("AB", "2", 2, 2));
        profilesByIdTest1.addProfile(new Profile("a", "3", 3, 3));
        profilesByIdTest1.addProfile(new Profile("Bc", "4", 4, 4));
        profilesByIdTest1.addProfile(new Profile("dac", "5", 5, 5));
        profilesByIdTest1.addProfile(new Profile("cAB", "6", 6, 6));

        ProfilesById profilesById  = profilesByIdTest1.filter("a");

        assertEquals(3, profilesById.getProfiles().size());
        assertEquals(1, profilesById.getProfiles().get(1).getId());
        assertEquals(2, profilesById.getProfiles().get(2).getId());
        assertEquals(3, profilesById.getProfiles().get(3).getId());
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

        assertFalse(profilesByIdTest1.contains("11"));
        assertFalse(profilesByIdTest1.contains("2"));
    }

    @Test
    public void testContainsMultipleProfileInMapAndDoesContain() {
        addProfileHelper(profilesByIdTest1, 5);

        assertTrue(profilesByIdTest1.contains("1"));
        assertTrue(profilesByIdTest1.contains("3"));
    }

    @Test
    public void testContainsMultipleProfileInMapAndDoesContainCaseInsensitive() {
        profilesByIdTest1.addProfile(new Profile("abc", "1", 1, 1));
        profilesByIdTest1.addProfile(new Profile("DEF", "2", 2, 2));

        assertFalse(profilesByIdTest1.contains("abcd"));
        assertTrue(profilesByIdTest1.contains("aBc"));
        assertTrue(profilesByIdTest1.contains("ABC"));
        assertTrue(profilesByIdTest1.contains("def"));
        assertTrue(profilesByIdTest1.contains("DEF"));
    }

    @Test
    public void testContainsMultipleProfileInMapAndDoesNotContain() {
        addProfileHelper(profilesByIdTest1, 5);

        assertFalse(profilesByIdTest1.contains("6"));
        assertFalse(profilesByIdTest1.contains("7"));
    }

    @Test
    public void testContainsIdSingleProfileInMapAndDoesContain() {
        addProfileHelper(profilesByIdTest1, 1);

        assertTrue(profilesByIdTest1.contains(1));
    }

    @Test
    public void testContainsIdSingleProfileInMapAndDoesNotContain() {
        addProfileHelper(profilesByIdTest1, 1);

        assertFalse(profilesByIdTest1.contains(2));
    }

    @Test
    public void testContainsMultipleProfileInMap() {
        addProfileHelper(profilesByIdTest1, 5);

        assertTrue(profilesByIdTest1.contains(1));
        assertTrue(profilesByIdTest1.contains(3));
        assertTrue(profilesByIdTest1.contains(5));
        assertFalse(profilesByIdTest1.contains(6));
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
        Profile profile1 = new Profile("1", "1", 1, 1);

        profilesByIdTest1.addProfile(profile1);

        assertEquals(profile1, profilesByIdTest1.getProfile(1));
    }

    @Test
    public void getProfileMultipleProfileInMap() {
        Profile profile1 = new Profile("1", "1", 1, 1);
        Profile profile2 = new Profile("2", "2", 2, 2);

        profilesByIdTest1.addProfile(profile1);
        profilesByIdTest1.addProfile(profile2);

        assertEquals(profile1, profilesByIdTest1.getProfile(1));
        assertEquals(profile2, profilesByIdTest1.getProfile(2));
    }

    private void addProfileHelper(ProfilesById profilesById, int repeats) {
        for (int i = 0; i < repeats; i++) {
            profilesById.addProfile(new Profile(Integer.toString(i + 1), Integer.toString(i + 1),
                    i + 1, i + 1));
        }
    }
}
