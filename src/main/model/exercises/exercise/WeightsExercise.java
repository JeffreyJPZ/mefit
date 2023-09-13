package model.exercises.exercise;

import model.json.JsonKeys;
import model.metrics.ExerciseType;
import model.metrics.Difficulty;
import model.metrics.MuscleGroup;
import org.json.JSONObject;

// Represents an exercise using weighted equipment with its description
public class WeightsExercise extends Exercise {
    private int weightPounds;
    private int sets;
    private int reps;

    // REQUIRES: name is not empty; weight, sets, reps, time > 0
    // EFFECTS: makes a new exercise with a name, muscle group, weight in lbs, # of sets, # of reps,
    //          difficulty, time (min), and unfavourited
    public WeightsExercise(String name, MuscleGroup muscleGroup, int weightPounds, int sets, int reps,
                           Difficulty difficulty, int time) {
        super(name, muscleGroup, difficulty, time);
        this.weightPounds = weightPounds;
        this.sets = sets;
        this.reps = reps;
    }

    // EFFECTS: returns a string representation with the exercise's name, muscle group,
    //          weight in lbs, sets, reps, difficulty, and time
    @Override
    public String toString() {
        return super.toString() + "\n"
                + "Weight (lbs): " + weightPounds + "\n"
                + "Sets: " + sets + "\n"
                + "Reps: " + reps;
    }

    public int getWeightPounds() {
        return weightPounds; // stub
    }

    public int getSets() {
        return sets; // stub
    }

    public int getReps() {
        return reps; // stub
    }

    public void setWeightPounds(int weightPounds) {
        this.weightPounds = weightPounds;
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

        jsonObject.put(JsonKeys.EXERCISE_TYPE.getKey(), ExerciseType.WEIGHTS_EXERCISE.getType());
        jsonObject.put(JsonKeys.EXERCISE_WEIGHT.getKey(), weightPounds);
        jsonObject.put(JsonKeys.EXERCISE_SETS.getKey(), sets);
        jsonObject.put(JsonKeys.EXERCISE_REPS.getKey(), reps);

        return jsonObject;
    }
}
