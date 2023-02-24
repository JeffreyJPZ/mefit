package model;

import static java.lang.Boolean.*;

// Represents a cardiovascular exercise with its description
public class CardioExercise extends Exercise {
    private int distance;

    // REQUIRES: name, muscleGroup are not empty; distance, difficulty, time > 0
    // EFFECTS: makes a cardio exercise with a name, muscle group, distance (m), difficulty, time (min),
    //          and unfavourited
    public CardioExercise(String name, MuscleGroup muscleGroup, int distance, Difficulty difficulty, int time) {
        super(name, muscleGroup, difficulty, time);
        this.distance = distance;
    }

    // MODIFIES: this
    // EFFECTS: returns a string representation with the exercise's name, muscle group,
    //          distance, difficulty, and time
    @Override
    public String toString() {
        return super.toString() + "\n"
                + "Distance: " + distance;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}
