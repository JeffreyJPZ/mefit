package model;

import static java.lang.Boolean.*;

// Represents an exercise using weighted equipment with its description
public class WeightsExercise extends Exercise {
    private int weight;
    private int sets;
    private int reps;

    // REQUIRES: name, muscleGroup are not empty, weight, sets, reps, difficulty, time > 0
    // EFFECTS: makes a new exercise with a name, muscle group, weight in lbs, # of sets, # of reps,
    //          difficulty, time (min), and unfavourited
    public WeightsExercise(String name, String muscleGroup, int weight, int sets, int reps, int difficulty, int time) {
        super(name, muscleGroup, difficulty, time);
        this.weight = weight;
        this.sets = sets;
        this.reps = reps;
    }

    // MODIFIES: this
    // EFFECTS: returns a string representation with the exercise's name, muscle group,
    //          weight, sets, reps, difficulty, and time
    @Override
    public String toString() {
        return super.toString() + "\n"
                + "Weight: " + weight + "\n"
                + "Sets: " + sets + "\n"
                + "Reps: " + reps;
    }

    public int getWeight() {
        return weight; // stub
    }

    public int getSets() {
        return sets; // stub
    }

    public int getReps() {
        return reps; // stub
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }
}
