package model;

// Represents a bodyweight exercise with its description
public class BodyWeightsExercise extends Exercise {
    private int sets;
    private int reps;

    // REQUIRES: name, muscleGroup are not empty; sets, reps, difficulty, time > 0
    // EFFECTS: makes a bodyweight exercise with a name, muscle group, # of sets, # of reps, difficulty, and time (min),
    //          and unfavourited
    public BodyWeightsExercise(String name, String muscleGroup, int sets, int reps, int difficulty, int time) {
        super(name, muscleGroup, difficulty, time);
        this.sets = sets;
        this.reps = reps;
    }

    public int getSets() {
        return sets; // stub
    }

    public int getReps() {
        return reps; // stub
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }
}
