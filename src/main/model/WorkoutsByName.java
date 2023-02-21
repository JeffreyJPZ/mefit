package model;

import java.util.LinkedHashMap;
import java.util.Map;

// Represents a mapping of workouts by name
public class WorkoutsByName implements GymCollection {
    public static final int DISPLAY_NUMBER_OF_WORKOUTS = 10;
    public static final String ADDITIONAL_WORKOUT_MESSAGE = " additional workouts";

    private Map<String, Workout> workouts;

    // EFFECTS: Makes an empty map of workouts
    public WorkoutsByName() {
        workouts = new LinkedHashMap<>();
    }

    // REQUIRES: workouts does not contain a workout with the same name ignoring case
    // MODIFIES: this
    // EFFECTS: adds a workout to the workout map
    public void addWorkout(Workout workout) {

    }

    // REQUIRES: workouts is not empty and name matches a workout
    // MODIFIES: this
    // EFFECTS: removes the workout with the given name
    public void removeWorkout(String name) {

    }

    // REQUIRES: workouts is not empty
    // MODIFIES: this
    // EFFECTS: if the number of workouts in map <= DISPLAY_NUMBER_OF_WORKOUTS, returns a string representation
    //          of the workout name, difficulty, time, # of exercises, and whether it is favourited up to the first
    //          DISPLAY_NUMBER_OF_WORKOUTS workouts
    //          Otherwise, also returns the number of remaining workouts and ADDITIONAL_WORKOUT_MESSAGE
    @Override
    public String toString() {
        return ""; // stub
    }

    // REQUIRES: workouts map is not empty and name matches at least one element in workout map
    // MODIFIES: this
    // EFFECTS: returns a mapping of workouts with their name matching given name case insensitively
    @Override
    public WorkoutsByName filter(String name) {
        return null; // stub
    }

    // REQUIRES: workouts map is not empty and difficulty matches at least one element in workout map
    // MODIFIES: this
    // EFFECTS: returns a mapping of workouts with difficulty matching given difficulty
    public WorkoutsByName filterDifficulty(Difficulty difficulty) {
        return null; // stub
    }

    // REQUIRES: workout map is not empty and at least one element in workout map <= time
    // MODIFIES: this
    // EFFECTS: returns a mapping of workouts with their time <= time
    public WorkoutsByName filterTime(int time) {
        return null; // stub
    }

    // REQUIRES: workout map is not empty and at least one element in workout map <= numberOfExercises
    // MODIFIES: this
    // EFFECTS: returns a mapping of workouts with their number of exercises <= time
    public WorkoutsByName filterNumberOfExercises(int numberOfExercises) {
        return null; // stub
    }

    // REQUIRES: workout map is not empty and at least one element in workout map is favourited
    // MODIFIES: this
    // EFFECTS: returns a mapping of workouts that are favourited
    public WorkoutsByName filterFavourite() {
        return null; // stub
    }

    // MODIFIES: this
    // EFFECTS: returns true if workout map contains a workout with the given name, otherwise returns false
    @Override
    public boolean contains(String name) {
        return false; // stub
    }

    @Override
    // MODIFIES: this
    // EFFECTS: returns the length of the workout map
    public boolean isEmpty() {
        return false; // stub
    }

    @Override
    // MODIFIES: this
    // EFFECTS: returns the length of the workout map
    public int length() {
        return 0; // stub
    }

    // REQUIRES: workouts is not empty and name matches a workout
    // MODIFIES: this
    // EFFECTS: returns the workout with the given name
    public Workout getWorkout(String name) {
        return null;
    }

    public Map<String, Workout> getWorkouts() {
        return workouts;
    }
}
