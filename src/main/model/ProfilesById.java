package model;

import java.util.HashMap;

// Represents a mapping of profiles by their id
public class ProfilesById implements GymCollection {
    public static final int DISPLAY_NUMBER_OF_PROFILES = 10;
    public static final String NO_PROFILES_MESSAGE = "No profiles on record";
    public static final String NO_MATCHES_MESSAGE = "No matching profiles";

    private HashMap<Integer, Profile> profiles;

    // EFFECTS: Makes a new profile map with no profiles
    public ProfilesById() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: adds a profile to the profile map
    public void addProfile(Profile profile) {
        profiles.put(profile.getId(), profile);
    }

    // REQUIRES: profile map is not empty
    // MODIFIES: this
    // EFFECTS: removes the profile from the profile map with the given id
    public void removeProfile(int id) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: returns the profile id, name of the first DISPLAY_NUMBER_OF_PROFILES profiles in map,
    //          and number of remaining profiles in map; if map is empty returns NO_PROFILES_MESSAGE
    public String toString() {
        return ""; // stub
    }

    // MODIFIES: this
    // EFFECTS: returns a map of profiles with string matching the profile name;
    //          if no matches returns NO_MATCHES_MESSAGE
    public ProfilesById filter(String string) {
        return null; // stub
    }

    // MODIFIES: this
    // EFFECTS: returns true if element with same name is in map, otherwise returns false
    public boolean contains(String name) {
        return false; // stub
    }

    // MODIFIES: this
    // EFFECTS: returns true if map is empty, otherwise returns false
    public boolean isEmpty() {
        return false;// stub
    }

    // REQUIRES: profiles is not empty
    // MODIFIES: this
    // EFFECTS: returns the profile with the given id
    public Profile getProfile(int id) {
        return null; // stub
    }

    // MODIFIES: this
    // EFFECTS: returns the length of the profile map
    public int length() {
        return 0; // stub
    }
}
