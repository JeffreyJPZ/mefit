package model;

import org.json.JSONObject;
import persistence.JsonWritable;

// Represents an exercise with basic information of name, muscle group, difficulty, and time (min)
public abstract class Exercise extends ExerciseComponent implements JsonWritable {
    private MuscleGroup muscleGroup;
    private Difficulty difficulty;
    private int time;
    private Boolean favourite;

    // REQUIRES: name is not empty; time > 0
    // EFFECTS: Makes a new exercise with a name, muscle group worked, difficulty, and time (min)
    public Exercise(String name, MuscleGroup muscleGroup, Difficulty difficulty, int time) {
        super(name, ExerciseComponentTypes.EXERCISE);
        this.muscleGroup = muscleGroup;
        this.difficulty = difficulty;
        this.time = time;
        this.favourite = false;
    }

    @Override
    // EFFECTS: returns a string representation of the exercise description
    public String toString() {
        return "Exercise Name: " + getName() + "\n"
                + "Muscle Group: " + muscleGroup.getMuscleGroup() + "\n"
                + "Difficulty: " + difficulty.getDifficulty() + "\n"
                + "Time (min): " + time + "\n"
                + "Favourite?: " + favourite;
    }

    public MuscleGroup getMuscleGroup() {
        return muscleGroup;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    @Override
    public int getTime() {
        return time;
    }

    @Override
    public int getSize() {
        return 1;
    }

    public Boolean isFavourite() {
        return favourite;
    }

    public void setMuscleGroup(MuscleGroup muscleGroup) {
        this.muscleGroup = muscleGroup;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setFavourite(Boolean favourite) {
        this.favourite = favourite;
    }

    // EFFECTS: creates a json object with exercise name, muscle group, difficulty, time (min), and favourited status
    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("type", getClass().getSimpleName());
        jsonObject.put("name", getName());
        jsonObject.put("muscleGroup", muscleGroup.getMuscleGroup());
        jsonObject.put("difficulty", difficulty.getDifficulty());
        jsonObject.put("time", time);
        jsonObject.put("favourite", favourite);

        return jsonObject;
    }
}
