package model;

// Represents a number of parameters for workout generation
public class WorkoutParameters {
    private final MuscleGroup muscleGroup;
    private final Difficulty difficulty;
    private final int exercisesNum;
    private final int time;
    private final int sampleSize;

    private WorkoutParameters(WorkoutParametersBuilder parametersBuilder) {
        this.muscleGroup = parametersBuilder.muscleGroup;
        this.difficulty = parametersBuilder.difficulty;
        this.exercisesNum = parametersBuilder.exercisesNum;
        this.time = parametersBuilder.time;
        this.sampleSize = parametersBuilder.sampleSize;
    }

    public MuscleGroup getMuscleGroup() {
        return muscleGroup;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public int getExercisesNum() {
        return exercisesNum;
    }

    public int getTime() {
        return time;
    }

    public int getSampleSize() {
        return sampleSize;
    }

    public static class WorkoutParametersBuilder {
        private final MuscleGroup muscleGroup;
        private final Difficulty difficulty;
        private final int exercisesNum;
        private final int time;
        private final int sampleSize;

        public WorkoutParametersBuilder(MuscleGroup muscleGroup, Difficulty difficulty, int exercisesNum, int time,
                                        int sampleSize) {
            this.muscleGroup = muscleGroup;
            this.difficulty = difficulty;
            this.exercisesNum = exercisesNum;
            this.time = time;
            this.sampleSize = sampleSize;
        }

        // MODIFIES: this
        // EFFECTS: returns the given workout parameters
        public WorkoutParameters build() {
            return new WorkoutParameters(this);
        }
    }
}
