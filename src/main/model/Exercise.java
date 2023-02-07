package model;

public abstract class Exercise {
    protected String name;
    protected String muscleGroup;
    protected int difficulty;
    protected int time;

    protected Boolean favourite;

    public String getName() {
        return ""; // stub
    }

    public String getMuscleGroup() {
        return ""; // stub
    }

    public abstract int getMetric();

    public int getDifficulty() {
        return 0; // stub
    }

    public int getTime() {
        return 0; // stub
    }

    public Boolean isFavourite() {
        return false;
    }

    public void setName(String name) {
        // stub
    }

    public void setMuscleGroup(String muscleGroup) {
        // stub
    }

    public abstract void setMetric(int metric);

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
