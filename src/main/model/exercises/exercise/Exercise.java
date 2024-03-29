package model.exercises.exercise;

import model.json.JsonKeys;
import model.metrics.MuscleGroup;
import model.metrics.Difficulty;
import org.json.JSONObject;
import persistence.JsonWritable;

// Represents an exercise with basic information of name, muscle group, difficulty, and time (min)
public abstract class Exercise implements JsonWritable {
    private String name;
    private MuscleGroup muscleGroup;
    private Difficulty difficulty;
    private int timeMinutes;
    private Boolean favourite;

    // REQUIRES: name is not empty; time > 0
    // EFFECTS: Makes a new exercise with a name, muscle group worked, difficulty, and time (min)
    public Exercise(String name, MuscleGroup muscleGroup, Difficulty difficulty, int timeMinutes) {
        this.name = name;
        this.muscleGroup = muscleGroup;
        this.difficulty = difficulty;
        this.timeMinutes = timeMinutes;
        this.favourite = false;
    }

    @Override
    // EFFECTS: returns a string representation of the exercise description
    public String toString() {
        return "Exercise Name: " + getName() + "\n"
                + "Muscle Group: " + muscleGroup.getMuscleGroupAsString() + "\n"
                + "Difficulty: " + difficulty.getDifficultyAsInt() + "\n"
                + "Time (min): " + timeMinutes + "\n"
                + "Favourite?: " + favourite;
    }

    public String getName() {
        return name;
    }

    public MuscleGroup getMuscleGroup() {
        return muscleGroup;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public int getTimeMinutes() {
        return timeMinutes;
    }

    public Boolean isFavourite() {
        return favourite;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMuscleGroup(MuscleGroup muscleGroup) {
        this.muscleGroup = muscleGroup;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public void setTimeMinutes(int timeMinutes) {
        this.timeMinutes = timeMinutes;
    }

    public void setFavourite(Boolean favourite) {
        this.favourite = favourite;
    }

    // EFFECTS: creates a json object with exercise name, muscle group, difficulty, time (min), and favourited status
    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put(JsonKeys.EXERCISE_NAME.getKey(), name);
        jsonObject.put(JsonKeys.EXERCISE_MUSCLE_GROUP.getKey(), muscleGroup.getMuscleGroupAsString());
        jsonObject.put(JsonKeys.EXERCISE_DIFFICULTY.getKey(), difficulty.getDifficultyAsInt());
        jsonObject.put(JsonKeys.EXERCISE_TIME.getKey(), timeMinutes);
        jsonObject.put(JsonKeys.EXERCISE_FAVOURITE.getKey(), favourite);

        return jsonObject;
    }
}
