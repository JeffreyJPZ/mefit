package model;

public class CardioExercise extends Exercise {
    private int targetTime;

    // REQUIRES: name, muscleGroup are not empty; distance, difficulty, time > 0
    // EFFECTS: makes a cardio exercise with a name, muscle group, metric as distance (m), difficulty, time (min),
    //          and target time equal to time (min), and unfavourited
    public CardioExercise(String name, String muscleGroup, int distance, int difficulty, int time) {
        // stub
    }

    public int getMetric() {
        return 0; // stub
    }

    public void setMetric(int metric) {
        // stub
    }
}
