package model;

import org.json.JSONObject;
import persistence.JsonWritable;

import static java.lang.Boolean.*;

// Represents an exercise with basic information of name, muscle group, difficulty, and time (min)
public abstract class Exercise implements JsonWritable {
    protected String name;
    protected MuscleGroup muscleGroup;
    protected Difficulty difficulty;
    protected int time;
    protected Boolean favourite;

    // REQUIRES: name is not empty; time > 0
    // EFFECTS: Makes a new exercise with a name, muscle group worked, difficulty, and time (min)
    public Exercise(String name, MuscleGroup muscleGroup, Difficulty difficulty, int time) {
        this.name = name;
        this.muscleGroup = muscleGroup;
        this.difficulty = difficulty;
        this.time = time;
        this.favourite = FALSE;
    }

    // EFFECTS: returns a string representation of the exercise description
    public String toString() {
        return "Exercise Name: " + name + "\n"
                + "Muscle Group: " + muscleGroup.getMuscleGroup() + "\n"
                + "Difficulty: " + difficulty.getDifficulty() + "\n"
                + "Time (min): " + time + "\n"
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

    public int getTime() {
        return time;
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
        jsonObject.put("name", name);
        jsonObject.put("muscleGroup", muscleGroup.getMuscleGroup());
        jsonObject.put("difficulty", difficulty.getDifficulty());
        jsonObject.put("time", time);
        jsonObject.put("favourite", favourite);

        return jsonObject;
    }
}
