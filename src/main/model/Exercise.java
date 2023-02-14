package model;

import static java.lang.Boolean.*;

// Represents an exercise with basic information
public abstract class Exercise {
    protected String name;
    protected String muscleGroup;
    protected int difficulty;
    protected int time;
    protected Boolean favourite;

    public Exercise(String name, String muscleGroup, int difficulty, int time) {
        this.name = name;
        this.muscleGroup = muscleGroup;
        this.difficulty = difficulty;
        this.time = time;
        this.favourite = FALSE;
    }

    public String getName() {
        return name;
    }

    public String getMuscleGroup() {
        return muscleGroup;
    }

    public int getDifficulty() {
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

    public void setMuscleGroup(String muscleGroup) {
        this.muscleGroup = muscleGroup;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setFavourite(Boolean favourite) {
        this.favourite = favourite;
    }

}
