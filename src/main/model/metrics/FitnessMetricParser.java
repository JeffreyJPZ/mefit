package model.metrics;

// Represents a tool for retrieving fitness metric types by values
public class FitnessMetricParser {

    private static FitnessMetricParser fitnessMetricParser;

    // EFFECTS: creates a new FitnessMetricParser
    private FitnessMetricParser() {}


    public static FitnessMetricParser getInstance() {
        if (fitnessMetricParser == null) {
            fitnessMetricParser = new FitnessMetricParser();
        }
        return fitnessMetricParser;
    }

    // REQUIRES: muscleGroupName matches a muscle group
    // EFFECTS: returns the muscle group associated with the given name
    public MuscleGroup getMuscleGroupByName(String muscleGroupName) {
        MuscleGroup muscleGroup = null;

        for (MuscleGroup m : MuscleGroup.values()) {
            if (m.getMuscleGroupAsString().equals(muscleGroupName)) {
                muscleGroup = m;
                break;
            }
        }
        return muscleGroup;
    }

    // REQUIRES: difficultyLevel matches a difficulty
    // EFFECTS: returns the difficulty associated with the given difficulty level
    public Difficulty getDifficultyByLevel(int difficultyLevel) {
        Difficulty difficulty = null;

        for (Difficulty d : Difficulty.values()) {
            if (d.getDifficultyAsInt() == difficultyLevel) {
                difficulty = d;
                break;
            }
        }
        return difficulty;
    }

    // REQUIRES: exerciseTypeName matches an exercise type
    // EFFECTS: returns the exercise type associated with the given name
    public ExerciseType getExerciseTypeByName(String exerciseTypeName) {
        ExerciseType exerciseType = null;

        for (ExerciseType e : ExerciseType.values()) {
            if (e.getType().equals(exerciseTypeName)) {
                exerciseType = e;
                break;
            }
        }
        return exerciseType;
    }
}
