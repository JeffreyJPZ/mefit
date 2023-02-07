package model;

public class BodyWeightsExercise extends Exercise {
    private int sets;
    private int reps;

    // REQUIRES: name, muscleGroup are not empty; sets, reps, difficulty, time > 0
    // EFFECTS: makes a bodyweight exercise with a name, muscle group, metric as # of total reps,
    //          # of sets, # of reps, difficulty, and time (min)
    public BodyWeightsExercise(String name, String muscleGroup, int sets, int reps, int difficulty, int time) {
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
