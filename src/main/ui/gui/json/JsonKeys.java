package ui.gui.json;

// Represents the keys used in loading, saving, and sending data in the GUI
public enum JsonKeys {
    DATA("data"),
    PROFILE("profile"),
    EXERCISE("exercise"),
    WORKOUT("workout"),
    PROFILES("profiles"),
    PROFILE_ID("id"),
    PROFILE_NAME("name"),
    PROFILE_AGE("age"),
    PROFILE_GENDER("gender"),
    PROFILE_WEIGHT("weight"),
    EXERCISES("exercises"),
    EXERCISE_NAME("name"),
    EXERCISE_TYPE("exerciseType"),
    EXERCISE_MUSCLE_GROUP("muscleGroup"),
    EXERCISE_DIFFICULTY("difficulty"),
    EXERCISE_TIME("time"),
    EXERCISE_FAVOURITE("favourite"),
    EXERCISE_WEIGHT("weight"),
    EXERCISE_SETS("sets"),
    EXERCISE_REPS("reps"),
    EXERCISE_DISTANCE("distance"),
    WORKOUTS("workouts"),
    WORKOUT_NAME("name"),
    WORKOUT_DIFFICULTY("difficulty"),
    WORKOUT_FAVOURITE("favourite"),
    WORKOUT_EXERCISES("exercises"),
    RANDOM_WORKOUT_MUSCLE_GROUP_FOCUS("muscleGroup"),
    RANDOM_WORKOUT_DIFFICULTY_MODE("difficulty"),
    RANDOM_WORKOUT_MAX_TIME("time"),
    RANDOM_WORKOUT_SIZE("size"),
    RANDOM_WORKOUT_SAMPLE_SIZE("sampleSize"),
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
