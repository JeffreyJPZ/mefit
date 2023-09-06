package model;

// Represents the types of exercise in the fitness app
public enum ExerciseType {
    WEIGHTS_EXERCISE("WeightsExercise"),
    BODYWEIGHTS_EXERCISE("BodyWeightsExercise"),
    CARDIO_EXERCISE("CardioExercise");

    private final String exerciseType;

    // REQUIRES: type is not empty
    // EFFECTS: makes an exercise type with the given name
    ExerciseType(String type) {
        this.exerciseType = type;
    }

    public String getType() {
        return exerciseType;
    }
}
