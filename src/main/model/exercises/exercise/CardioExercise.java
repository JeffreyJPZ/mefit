package model.exercises.exercise;

import model.metrics.Difficulty;
import model.metrics.ExerciseType;
import model.metrics.MuscleGroup;
import org.json.JSONObject;

// Represents a cardiovascular exercise with a name, muscle group, distance (m), difficulty, time (min),
// and unfavourited
public class CardioExercise extends Exercise {
    private int distanceMetres;

    // REQUIRES: name is not empty; distance, time > 0
    // EFFECTS: makes a cardio exercise with a name, muscle group, distance (m), difficulty, time (min),
    //          and unfavourited
    public CardioExercise(String name, MuscleGroup muscleGroup, int distanceMetres, Difficulty difficulty, int time) {
        super(name, muscleGroup, difficulty, time);
        this.distanceMetres = distanceMetres;
    }

    // EFFECTS: returns a string representation with the exercise's name, muscle group,
    //          distance, difficulty, and time
    @Override
    public String toString() {
        return super.toString() + "\n"
                + "Distance (m): " + distanceMetres;
    }

    public int getDistanceMetres() {
        return distanceMetres;
    }

    public void setDistanceMetres(int distanceMetres) {
        this.distanceMetres = distanceMetres;
    }

    // EFFECTS: creates a json object with exercise name, muscle group, difficulty, time (min), and favourited status,
    //          and distance (m)
    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = super.toJson();

        jsonObject.put("exerciseType", ExerciseType.CARDIO_EXERCISE.getType());
        jsonObject.put("distance", distanceMetres);

        return jsonObject;
    }
}
