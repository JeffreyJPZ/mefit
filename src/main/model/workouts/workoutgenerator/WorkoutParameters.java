package model.workouts.workoutgenerator;

import model.metrics.Difficulty;
import model.metrics.MuscleGroup;

// Represents a number of parameters for workout generation
public class WorkoutParameters {
    private final MuscleGroup muscleGroup;
    private final Difficulty difficulty;
    private final int time;
    private final int size;
    private final int sampleSize;

    private WorkoutParameters(WorkoutParametersBuilder parametersBuilder) {
        this.muscleGroup = parametersBuilder.muscleGroup;
        this.difficulty = parametersBuilder.difficulty;
        this.time = parametersBuilder.time;
        this.size = parametersBuilder.size;
        this.sampleSize = parametersBuilder.sampleSize;
    }

    public MuscleGroup getMuscleGroup() {
        return muscleGroup;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public int getTime() {
        return time;
    }

    public int getSize() {
        return size;
    }

    public int getSampleSize() {
        return sampleSize;
    }

    public static class WorkoutParametersBuilder {
        private final MuscleGroup muscleGroup;
        private final Difficulty difficulty;
        private final int time;
        private final int size;
        private final int sampleSize;

        public WorkoutParametersBuilder(MuscleGroup muscleGroup, Difficulty difficulty, int time, int size,
                                        int sampleSize) {
            this.muscleGroup = muscleGroup;
            this.difficulty = difficulty;
            this.time = time;
            this.size = size;
            this.sampleSize = sampleSize;
        }

        // MODIFIES: this
        // EFFECTS: returns the given workout parameters
        public WorkoutParameters build() {
            return new WorkoutParameters(this);
        }
    }
}
