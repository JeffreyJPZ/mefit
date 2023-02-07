package model;

import java.util.ArrayList;

public class ProfileList implements GymList {
    private ArrayList<Profile> profiles;

    // EFFECTS: Makes a new profile list with no profiles
    public ProfileList() {
        // stub
    }

    // MODIFIES: this

    public String toString() {
        return ""; // stub
    }

    public GymList filter(String name) {
        return null; // stub
    }

    public boolean contains(String name) {
        return false; // stub
    }

    public boolean isEmpty() {
        return false;// stub
    }

    public Profile getProfileList(int id) {
        return null; // stub
    }
}
