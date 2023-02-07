package model;

import java.util.List;

public interface GymList {

    // REQUIRES: this is not empty
    // MODIFIES: this
    // EFFECTS: returns a string representation of the given list
    @Override
    String toString();

    GymList filter(String name);

    boolean contains(String name);

    // MODIFIES: this
    // EFFECTS: returns true if list is empty, otherwise returns false
    boolean isEmpty();
}
