package model;

import model.Exercise;

public class WeightsExercise extends Exercise {
    private int sets;
    private int reps;

    // REQUIRES: name, muscleGroup are not empty, weight, sets, reps, difficulty, time > 0
    // EFFECTS: makes a new exercise with a name, muscle group, metric as weight in lbs, # of sets, # of reps,
    //          difficulty, and time (min)
    public WeightsExercise(String name, String muscleGroup, int weight, int sets, int reps, int difficulty, int time) {
        // stub
    }

    public int getSets() {
        return 0; // stub
    }

    public int getReps() {
        return 0; // stub
    }

    public void setSets(int sets) {
        // stub
    }

    public void setReps(int reps) {
        // stub
    }
}
