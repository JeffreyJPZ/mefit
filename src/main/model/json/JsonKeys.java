package model.json;

// Represents the keys used in loading, saving, and sending data in the model
public enum JsonKeys {
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
    WORKOUT_EXERCISES("exercises");


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
