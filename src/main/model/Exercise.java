package model;

import static java.lang.Boolean.*;

// Represents an exercise with basic information
public abstract class Exercise {
    protected String name;
    protected MuscleGroup muscleGroup;
    protected Difficulty difficulty;
    protected int time;
    protected Boolean favourite;

    // EFFECTS: Makes a new exercise with a name, muscle group worked, difficulty, and time (min)-
    public Exercise(String name, MuscleGroup muscleGroup, Difficulty difficulty, int time) {
        this.name = name;
        this.muscleGroup = muscleGroup;
        this.difficulty = difficulty;
        this.time = time;
        this.favourite = FALSE;
    }

    // REQUIRES: exercises contains at least one exercise
    // MODIFIES: this
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

}
