package model;

import org.json.JSONObject;

import static java.lang.Boolean.*;

// Represents an exercise using weighted equipment with its description
public class WeightsExercise extends Exercise {
    private int weight;
    private int sets;
    private int reps;

    // REQUIRES: name is not empty; weight, sets, reps, time > 0
    // EFFECTS: makes a new exercise with a name, muscle group, weight in lbs, # of sets, # of reps,
    //          difficulty, time (min), and unfavourited
    public WeightsExercise(String name, MuscleGroup muscleGroup, int weight, int sets, int reps,
                           Difficulty difficulty, int time) {
        super(name, muscleGroup, difficulty, time);
        this.weight = weight;
        this.sets = sets;
        this.reps = reps;
    }

    // EFFECTS: returns a string representation with the exercise's name, muscle group,
    //          weight in lbs, sets, reps, difficulty, and time
    @Override
    public String toString() {
        return super.toString() + "\n"
                + "Weight (lbs): " + weight + "\n"
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

    // EFFECTS: creates a json object with exercise name, muscle group, difficulty, time (min), and favourited status,
    //          weight (kg), # of sets, and # of reps
    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = super.toJson();

        jsonObject.put("exerciseType", ExerciseType.WEIGHTS_EXERCISE.getType());
        jsonObject.put("weight", weight);
        jsonObject.put("sets", sets);
        jsonObject.put("reps", reps);

        return jsonObject;
    }
}
