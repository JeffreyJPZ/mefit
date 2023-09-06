package model;

import exceptions.InvalidPositionException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.JsonWritable;

import java.util.ArrayList;
import java.util.List;

// Represents a number of exercises organized into a workout
public class Workout implements JsonWritable {
    private String name;
    private Difficulty difficulty;
    private boolean favourite;
    private List<Exercise> exercises;

    // REQUIRES: name is not empty, difficulty > 0
    // EFFECTS: Makes a new workout with a name, difficulty, unfavourited and with no exercises
    public Workout(String name, Difficulty difficulty) {
        this.name = name;
        this.difficulty = difficulty;
        this.favourite = false;
        this.exercises = new ArrayList<>();
    }

    // REQUIRES: exercises does not contain an exercise with the same name
    // MODIFIES: this
    // EFFECTS: adds an exercise to the end of the workout
    public void addExercise(Exercise exercise) {
        exercises.add(exercise);
    }

    // REQUIRES: exercises does not contain an exercise with the same name
    // MODIFIES: this
    // EFFECTS: adds an exercise to the given position in the workout
    public void insertExercise(Exercise exercise, int position) throws InvalidPositionException {
        if (position < 1 || position > (this.length() + 1)) {
            throw new InvalidPositionException();
        }

        exercises.add(position - 1, exercise);
    }

    // REQUIRES: exercises does not contain an exercise with the same name
    // MODIFIES: this
    // EFFECTS: replaces the exercise at the given position in the workout
    public void setExercise(Exercise exercise, int position) throws InvalidPositionException {
        if (position < 1 || position > this.length()) {
            throw new InvalidPositionException();
        }

        exercises.set(position - 1, exercise);
    }

    // MODIFIES: this
    // EFFECTS: removes the exercise from the given position in the workout
    public void removeExercise(int position) throws InvalidPositionException {
        if (position < 1 || position > this.length()) {
            throw new InvalidPositionException();
        }
        exercises.remove(position - 1);
    }

    // REQUIRES: string is not empty and matches an exercise in workout
    // MODIFIES: this
    // EFFECTS: removes the exercise with the given name from the workout and
    //          decreases the workout time by exercise time
    public void removeExercise(String name) {
        for (Exercise exercise : exercises) {
            if (exercise.getName().equalsIgnoreCase(name)) {
                exercises.remove(exercise);
                break;
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: removes all exercises from the workout
    public void clear() {
        exercises.clear();
    }

    // EFFECTS: returns true if exercise with the same name ignoring case is in list, otherwise returns false
    public boolean contains(String name) {
        for (Exercise exercise : exercises) {
            if (exercise.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }


    @Override
    // EFFECTS: returns a string representation of the workout name, difficulty, time (min), number of exercises,
    //          whether it is favourited and exercises
    public String toString() {
        StringBuilder workoutString = new StringBuilder();

        workoutString.append("Name\tDifficulty\tTime (min)\t # of Exercises\tFavourite?\n");
        workoutString.append("[").append(getName()).append("]\t");
        workoutString.append(difficulty.getDifficulty()).append("\t");
        workoutString.append(getTimeMinutes()).append("\t");
        workoutString.append(length()).append("\t");
        workoutString.append(isFavourite());
        workoutString.append("\n\n");
        workoutString.append("Exercises\n");
        workoutString.append("Name\tMuscle Group\tDifficulty\tTime (min)\tFavourite?\n");

        for (Exercise exercise : exercises) {
            workoutString.append("[").append(exercise.getName()).append("]\t");
            workoutString.append(exercise.getMuscleGroup().getMuscleGroup()).append("\t");
            workoutString.append(exercise.getDifficulty().getDifficulty()).append("\t");
            workoutString.append(exercise.getTimeMinutes()).append("\t");
            workoutString.append(exercise.isFavourite()).append("\n");
        }

        return workoutString.toString();
    }

    // MODIFIES: this
    // EFFECTS: returns the number of exercises in workout
    public int length() {
        return exercises.size();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    public String getName() {
        return name;
    }

    // REQUIRES: 1 <= position <= exercises.size()
    // EFFECTS: returns the exercise at the given position in the workout
    public Exercise getExercise(int position) {
        return exercises.get(position - 1);
    }

    // REQUIRES: string is not empty
    // EFFECTS: returns the exercise with the given name in the workout case insensitively if found,
    //          otherwise does nothing
    //
    public Exercise getExercise(String name) {
        for (Exercise exercise : exercises) {
            if (exercise.getName().equalsIgnoreCase(name)) {
                return exercise;
            }
        }
        return null;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    // EFFECTS: returns the total time of exercises in the workout
    public int getTimeMinutes() {
        int time = 0;

        for (Exercise exercise : exercises) {
            time += exercise.getTimeMinutes();
        }

        return time;
    }

    public boolean isFavourite() {
        return favourite;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    // EFFECTS: Makes a json object with workout name, difficulty, favourite status, and exercises
    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("name", getName());
        jsonObject.put("difficulty", difficulty.getDifficulty());
        jsonObject.put("favourite", favourite);
        jsonObject.put("exercises", exercisesToJson());

        return jsonObject;
    }

    // EFFECTS: Makes a json array with workout exercises
    public JSONArray exercisesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Exercise exercise : exercises) {
            jsonArray.put(exercise.toJson());
        }

        return jsonArray;
    }
}
