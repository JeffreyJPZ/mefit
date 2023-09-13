package model.exercises.exercise;

import model.json.JsonKeys;
import model.metrics.Difficulty;
import model.metrics.ExerciseType;
import model.metrics.MuscleGroup;
import org.json.JSONObject;

// Represents a bodyweight exercise with a name, muscle group, # of sets, # of reps, difficulty, and time (min),
// and unfavourited
public class BodyWeightsExercise extends Exercise {
    private int sets;
    private int reps;

    // REQUIRES: name is not empty; sets, reps, time > 0
    // EFFECTS: makes a bodyweight exercise with a name, muscle group, # of sets, # of reps, difficulty, and time (min),
    //          and unfavourited
    public BodyWeightsExercise(String name, MuscleGroup muscleGroup, int sets, int reps,
                               Difficulty difficulty, int time) {
        super(name, muscleGroup, difficulty, time);
        this.sets = sets;
        this.reps = reps;
    }

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

    // EFFECTS: creates a json object with exercise name, muscle group, difficulty, time (min), and favourited status,
    //          # of sets, and # of reps
    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = super.toJson();

        jsonObject.put(JsonKeys.EXERCISE_TYPE.getKey(), ExerciseType.BODYWEIGHTS_EXERCISE.getType());
        jsonObject.put(JsonKeys.EXERCISE_SETS.getKey(), sets);
        jsonObject.put(JsonKeys.EXERCISE_REPS.getKey(), reps);

        return jsonObject;
    }
}
