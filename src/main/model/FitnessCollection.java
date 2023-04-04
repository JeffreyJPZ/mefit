package model;

import persistence.JsonWritable;

// Represents a collection of objects for a fitness application
public interface FitnessCollection extends JsonWritable {

    // EFFECTS: returns a string representation of the given collection
    @Override
    String toString();

    // REQUIRES: name is not empty
    // EFFECTS: returns a collection of elements satisfying the given name
    FitnessCollection filter(String name);

    // REQUIRES: name is not empty
    // EFFECTS: returns true if collection contains an element with the same name, otherwise returns false
    boolean contains(String name);

    // EFFECTS: returns true if collection is empty, otherwise returns false
    boolean isEmpty();

    // EFFECTS: returns the number of elements in given collection
    int length();
}
