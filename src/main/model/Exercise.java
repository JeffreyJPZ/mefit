package model;

public interface Exercise {

    public String getName();

    public int getMetric();

    public String getMuscleGroup();

    public int getDifficulty();

    public int getTime();

    public void setMetric(int metric);

    public void setMuscleGroup(String muscleGroup);

    public void setDifficulty(int difficulty);

    public void setTime(int time);

}
