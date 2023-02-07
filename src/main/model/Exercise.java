package model;

public class Exercise {
    protected final String name;

    protected int metric;
    protected MuscleGroup muscleGroup;
    protected int difficulty;
    protected int time;

    protected Boolean favourite;

    // REQUIRES: name, muscleGroup are not empty; metric, difficulty, time > 0
    // EFFECTS: Makes an exercise with a name, muscle group, exercise metric, difficulty, time taken, and unfavourited
    public Exercise(String name, String muscleGroup, int metric, int difficulty, int time) {
        // stub
    }

    public String getName() {
        return ""; // stub
    }

    public String getMuscleGroup() {
        return ""; // stub
    }

    public int getMetric() {
        return 0; // stub
    }

    public int getDifficulty() {
        return 0; // stub
    }

    public int getTime() {
        return 0; // stub
    }

    public Boolean isFavourite() {
        return false;
    }

    public void setMuscleGroup(String muscleGroup) {
        // stub
    }

    public void setMetric(int metric) {
        // stub
    }

    public void setDifficulty(int difficulty) {
        // stub
    }

    public void setTime(int time) {
        // stub
    }

    public void setFavourite(Boolean favourite) {
        // stub
    }

}
