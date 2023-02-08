package model;

import java.util.List;

public interface GymList {

    // REQUIRES: this is not empty
    // MODIFIES: this
    // EFFECTS: returns a string representation of the given list
    @Override
    String toString();

    // REQUIRES: name is not empty
    // MODIFIES: this
    // EFFECTS: returns a list of elements satisfying the given name
    GymList filter(String name);

    // REQUIRES: name is not empty
    // MODIFIES: this
    // EFFECTS: returns true if list contains an element with the same name, otherwise returns false
    boolean contains(String name);

    // MODIFIES: this
    // EFFECTS: returns true if list is empty, otherwise returns false
    boolean isEmpty();

    // MODIFIES: this
    // EFFECTS: returns the number of elements in given list
    int getCount();
}
