package ui;

// Represents the keys used in sending data between viewmodels and views
public enum JsonKeys {
    DATA("data"),
    PROFILE_ID("profileID"),
    EXERCISE_NAME("exerciseName"),
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
