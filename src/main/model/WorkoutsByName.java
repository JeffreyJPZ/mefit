package model;

import java.util.LinkedHashMap;
import java.util.Map;

// Represents a mapping of workouts by name
public class WorkoutsByName implements GymCollection {
    private Map<String, Workout> workouts;

    // EFFECTS: Makes an empty map of workouts
    public WorkoutsByName() {
        workouts = new LinkedHashMap<>();
    }

    @Override
    public String toString() {
        return ""; // stub
    }

    public WorkoutsByName filter(String name) {
        return null; // stub
    }

    public boolean contains(String name) {
        return false; // stub
    }

    public boolean isEmpty() {
        return false; // stub
    }

    public int length() {
        return 0; // stub
    }
}
