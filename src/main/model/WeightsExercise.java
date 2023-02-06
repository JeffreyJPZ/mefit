package model;

import model.Exercise;

public class WeightsExercise implements Exercise {
    private final String name;
    private int metric; // metric is weight in kg
    private int sets;
    private int reps;
    private String muscleGroup;
    private int difficulty;
    private int time;

    // REQUIRES: name is not empty, sets and reps > 0, muscleGroup is one of:
    // EFFECTS: makes a new exercise with a name, metric as weight in kg, # of sets, # of reps, muscle group,
    //          personal difficulty, and time taken
    public WeightsExercise(String name, int weight, int sets, int reps, String muscleGroup, int difficulty, int time) {
        // stub
    }

    public String getName() {
        return ""; // stub
    }

    public int getMetric() {
        return 0; // stub
    }

    public String getMuscleGroup() {
        return ""; // stub
    }

    public int getDifficulty() {
        return 0; // stub
    }

    public int getTime() {
        return 0; // stub
    }

    public void setMetric(int metric) {
        // stub
    }

    public void setMuscleGroup(String muscleGroup) {
        // stub
    }

    public void setDifficulty(int difficulty) {
        // stub
    }

    public void setTime(int time) {
        // stub
    }
}
