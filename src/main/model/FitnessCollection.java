package model;

import persistence.JsonWritable;

// Represents a collection of objects for a fitness application
public interface FitnessCollection extends JsonWritable {

    // EFFECTS: returns a string representation of the given collection
    @Override
    String toString();

    // EFFECTS: filters the collection with the given name
    FitnessCollection filterName(String name);

    // REQUIRES: name is not empty
    // EFFECTS: returns true if collection contains an element with the same name, otherwise returns false
    boolean contains(String name);

    // EFFECTS: returns true if collection is empty, otherwise returns false
    boolean isEmpty();

    // EFFECTS: returns the number of elements in given collection
    int length();
}
