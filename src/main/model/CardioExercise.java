package model;

import static java.lang.Boolean.*;

// Represents a cardiovascular exercise with its description
public class CardioExercise extends Exercise {
    private int distance;

    // REQUIRES: name, muscleGroup are not empty; distance, difficulty, time > 0
    // EFFECTS: makes a cardio exercise with a name, muscle group, distance (m), difficulty, time (min),
    //          and unfavourited
    public CardioExercise(String name, String muscleGroup, int distance, int difficulty, int time) {
        super(name, muscleGroup, difficulty, time);
        this.distance = distance;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance() {
        this.distance = distance;
    }
}
