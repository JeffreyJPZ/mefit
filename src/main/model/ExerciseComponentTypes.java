package model;

// Represents the different exercise component types
public enum ExerciseComponentTypes {
    EXERCISE("Exercise"),
    WORKOUT("Workout"),
    FOLDER("Folder");

    private final String type;

    // REQUIRES: type is not empty
    // EFFECTS: makes an exercise component type with the given name
    ExerciseComponentTypes(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
