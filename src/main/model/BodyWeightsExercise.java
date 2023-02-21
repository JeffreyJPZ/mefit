package model;

// Represents a bodyweight exercise with its description
public class BodyWeightsExercise extends Exercise {
    private int sets;
    private int reps;

    // REQUIRES: name, muscleGroup are not empty; sets, reps, difficulty, time > 0
    // EFFECTS: makes a bodyweight exercise with a name, muscle group, # of sets, # of reps, difficulty, and time (min),
    //          and unfavourited
    public BodyWeightsExercise(String name, MuscleGroup muscleGroup, int sets, int reps,
                               Difficulty difficulty, int time) {
        super(name, muscleGroup, difficulty, time);
        this.sets = sets;
        this.reps = reps;
    }

    // MODIFIES: this
    // EFFECTS: returns a string representation with the exercise's name, muscle group,
    //          sets, reps, difficulty, and time
    @Override
    public String toString() {
        return super.toString() + "\n"
                + "Sets: " + sets + "\n"
                + "Reps: " + reps;
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
