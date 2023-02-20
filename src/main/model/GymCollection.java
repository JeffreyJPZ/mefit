package model;

import javax.imageio.plugins.tiff.GeoTIFFTagSet;
import java.util.Map;

// Represents a collection of objects for a gym application
public interface GymCollection {

    // REQUIRES: this is not empty
    // MODIFIES: this
    // EFFECTS: returns a string representation of the given collection
    @Override
    String toString();

    // REQUIRES: name is not empty
    // MODIFIES: this
    // EFFECTS: returns a collection of elements satisfying the given string
    GymCollection filter(String string);

    // REQUIRES: name is not empty
    // MODIFIES: this
    // EFFECTS: returns true if collection contains an element with the same name, otherwise returns false
    boolean contains(String name);

    // MODIFIES: this
    // EFFECTS: returns true if collection is empty, otherwise returns false
    boolean isEmpty();

    // MODIFIES: this
    // EFFECTS: returns the number of elements in given collection
    int length();
}
