package ui;

// Represents the keys used in loading and saving data, and sending data
public enum JsonKeys {
    DATA("data"),
    PROFILE("profile"),
    PROFILE_ID("profileID"),
    EXERCISE("exercise"),
    EXERCISE_TYPE("exerciseType"),
    EXERCISE_NAME("exerciseName"),
    WORKOUT("workout"),
    WORKOUT_NAME("workoutName"),
    FILTER_TYPE("filterType"),
    INPUT("input"),
    FIELDS("fields"),
    BOXES("boxes");

    private final String key;

    // REQUIRES: key is not empty
    // EFFECTS: makes a new key
    JsonKeys(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

}
