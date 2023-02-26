package model;

// Represents a cardiovascular exercise with a name, muscle group, distance (m), difficulty, time (min),
// and unfavourited
public class CardioExercise extends Exercise {
    private int distance;

    // REQUIRES: name is not empty; distance, time > 0
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
                + "Distance (m): " + distance;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}
