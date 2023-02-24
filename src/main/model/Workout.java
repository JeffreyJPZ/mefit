package model;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Boolean.*;

// Represents a number of exercises organized into a workout
public class Workout {
    private String name;
    private Difficulty difficulty;
    private int time;
    private boolean favourite;
    private List<Exercise> exercises;

    // REQUIRES: name is not empty, difficulty > 0
    // EFFECTS: Makes a new workout with a name, difficulty, time (min) equal to 0,
    //          unfavourited and with no exercises
    public Workout(String name, Difficulty difficulty) {
        this.name = name;
        this.difficulty = difficulty;
        this.time = 0;
        this.favourite = FALSE;
        this.exercises = new ArrayList<>();
    }

    // REQUIRES: exercises does not contain an exercise with the same name
    // MODIFIES: this
    // EFFECTS: adds an exercise to the end of the workout, increases the workout time by exercise time
    public void addExercise(Exercise exercise) {
        exercises.add(exercise);
        time += exercise.getTime();
    }

    // REQUIRES: exercises does not contain an exercise with the same name and 1 <= position <= exercises.size()
    // MODIFIES: this
    // EFFECTS: adds an exercise to the given position in the workout and increases the workout time by exercise time
    public void insertExercise(Exercise exercise, int position) {
        exercises.add(position - 1, exercise);
        time += exercise.getTime();
    }

    // REQUIRES: exercises does not contain an exercise with the same name and 1 <= position <= exercises.size()
    // MODIFIES: this
    // EFFECTS: replaces the exercise at the given position in the workout
    //          and if given exercise time is greater or equal to the replaced exercise time,
    //          increases workout time by their difference,
    //          otherwise decreases workout time by their difference
    public void setExercise(Exercise exercise, int position) {
        int prevExerciseTime = exercises.get(position - 1).getTime();

        exercises.set(position - 1, exercise);

        if (prevExerciseTime <= exercise.getTime()) {
            time += exercise.getTime() - prevExerciseTime;
        } else {
            time -= prevExerciseTime - exercise.getTime();
        }
    }

    // REQUIRES: 1 <= position <= exercises.size()
    // MODIFIES: this
    // EFFECTS: removes the exercise from the given position in the workout and
    //          decreases the workout time by exercise time
    public void removeExercise(int position) {
        int removedExerciseTime = exercises.get(position - 1).getTime();

        exercises.remove(position - 1);
        time -= removedExerciseTime;
    }

    // REQUIRES: string is not empty and matches an exercise in workout
    // MODIFIES: this
    // EFFECTS: removes the exercise with the given name from the workout and
    //          decreases the workout time by exercise time
    public void removeExercise(String name) {
        for (Exercise exercise : exercises) {
            if (exercise.getName().equalsIgnoreCase(name)) {
                int removedExerciseTime = exercise.getTime();

                exercises.remove(exercise);
                time -= removedExerciseTime;

                break;
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: removes all exercises from the workout and sets the workout time to zero
    public void clear() {
        exercises.clear();
        time = 0;
    }

    // MODIFIES: this
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
    // REQUIRES: exercises is not empty
    // MODIFIES: this
    // EFFECTS: returns a string representation of the workout name, difficulty, time (min), number of exercises,
    //          whether it is favourited and exercises
    public String toString() {
        String retString = "Name\tDifficulty\tTime (min)\t # of Exercises\tFavourite?" + "\n"
                        + "[" + name + "]" + "\t"
                        + difficulty.getDifficulty() + "\t"
                        + time + "\t"
                        + length() + "\t"
                        + isFavourite() + "\t"
                        + "\n"
                        + "Exercises" + "\n"
                        + "Name\tMuscle Group\tDifficulty\tTime (min)\tFavourite?" + "\n";

        for (Exercise exercise : exercises) {
            retString += "[" + exercise.getName() + "]" + "\t"
                    + exercise.getMuscleGroup().getMuscleGroup() + "\t"
                    + exercise.getDifficulty().getDifficulty() + "\t"
                    + exercise.getTime() + "\t"
                    + exercise.isFavourite() + "\n";
        }

        return retString;
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

    public void setTime(int time) {
        this.time = time;
    }

    // REQUIRES: 1 <= position <= exercises.size()
    // MODIFIES: this
    // EFFECTS: returns the exercise at the given position in the workout
    public Exercise getExercise(int position) {
        return exercises.get(position - 1);
    }

    // REQUIRES: string is not empty
    // MODIFIES: this
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

    public String getName() {
        return name;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public int getTime() {
        return time;
    }

    public boolean isFavourite() {
        return favourite;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }
}
