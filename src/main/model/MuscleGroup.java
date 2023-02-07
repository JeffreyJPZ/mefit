package model;

// Represents main muscle group worked in Exercise
public enum MuscleGroup {
    CHEST("Chest"),
    BACK("Back"),
    ARMS("Arms"),
    SHOULDERS("Shoulders"),
    LEGS("Legs"),
    CORE("Core");

    private final String muscleGroup;

    // REQUIRES: muscleGroup is not empty
    // EFFECTS: Makes a muscle group with the name of the muscle group
    MuscleGroup(String muscleGroup) {
        this.muscleGroup = muscleGroup;
    }

    public String getMuscleGroup() {
        return muscleGroup;
    }
}
