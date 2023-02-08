package model;

import java.util.ArrayList;

public class ProfileList implements GymList {
    private static final int DISPLAY_NUMBER_OF_ROWS = 10;

    private ArrayList<Profile> profiles;
    private int count; // Count of elements in profiles

    // EFFECTS: Makes a new profile list with no profiles
    public ProfileList() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: returns the profile id and name of the first DISPLAY_NUMBER_OF_ROWS profiles in list,
    //          and number of remaining rows in list
    public String toString() {
        return ""; // stub
    }

    // MODIFIES: this
    // EFFECTS: returns a list of profiles with the given name
    public ProfileList filter(String name) {
        return null; // stub
    }

    // MODIFIES: this
    // EFFECTS: returns true if element with same name is in list
    public boolean contains(String name) {
        return false; // stub
    }

    // MODIFIES: this
    // EFFECTS: returns true if list is empty, otherwise returns false
    public boolean isEmpty() {
        return false;// stub
    }

    // MODIFIES: this
    // EFFECTS: returns the profile with the given id
    public Profile getProfile(int id) {
        return null; // stub
    }

    public ArrayList<Profile> getProfiles() {
        return null; // stub
    }

    public int getCount() {
        return 0; // stub
    }
}
