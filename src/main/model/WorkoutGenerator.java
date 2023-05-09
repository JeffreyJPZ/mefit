package model;

import exceptions.NoValidWorkoutException;

import java.lang.reflect.Type;
import java.util.*;

// Represents an algorithm for generating a random workout
public class WorkoutGenerator {
    private static WorkoutGenerator workoutGenerator;

    // EFFECTS: makes a new workout generator
    private WorkoutGenerator() {

    }

    // EFFECTS: if workoutGenerator does not exist, makes a new workout generator
    //          otherwise returns the workout generator
    public static WorkoutGenerator getInstance() {
        if (workoutGenerator == null) {
            workoutGenerator = new WorkoutGenerator();
        }
        return workoutGenerator;
    }

    // EFFECTS: generates and returns a random workout with given parameters
    //          UNLESS: if there are no given exercises, throws NoValidWorkoutException
    //          otherwise, if no workout can be generated, throws NoValidWorkoutException
    public Workout generateWorkout(String name, int exercisesNum, int time, Difficulty difficulty,
                                   MuscleGroup muscleGroup, ExercisesByName exercisesByName,
                                   int sampleSize) throws NoValidWorkoutException {
        if (exercisesByName.isEmpty()) {
            throw new NoValidWorkoutException();
        }

        Workout workout = new Workout(name, difficulty);
        // All exercises that can be added
        List<Exercise> allExercises = new ArrayList<>(exercisesByName.getExercises().values());
        List<Exercise> exercisesAvailable = selectExercises(sampleSize, allExercises);

        Workout result = addNextExercise(name, workout, exercisesNum, time, difficulty,
                muscleGroup, exercisesAvailable);

        if (result == null) {
            throw new NoValidWorkoutException();
        }

        return result;
    }

    // EFFECTS: returns sampleSize randomly selected exercises from the given exercises;
    private List<Exercise> selectExercises(int sampleSize, List<Exercise> allExercises) {
        // Choose sampleSize amount of exercises from profile for search space
        List<Exercise> exercisesAvailable = new ArrayList<>();
        Random random = new Random();
        int i = 0;

        while (i < sampleSize) {
            int index = random.nextInt(allExercises.size());
            // if exercise w/ given index is not contained, increment i
            Exercise exercise = allExercises.get(index);
            if (!exercisesAvailable.contains(exercise)) {
                exercisesAvailable.add(exercise);
                ++i;
            }
        }
        return exercisesAvailable;
    }

    // EFFECTS: adds the first exercise from available exercises to the workout until a valid workout is made
    //          if no valid workout can be made, returns null
    private Workout addNextExercise(String name, Workout workout, int exercisesNum, int time, Difficulty difficulty,
                                    MuscleGroup muscleGroup, List<Exercise> exercisesAvailable) {
        // return workout if and only if workout.length() = exercisesNum, total time <= time,
        // mode of difficulties in workout is difficulty, mode of muscle groups is muscleGroup in workout

        if (isValid(workout, exercisesNum, time, difficulty, muscleGroup)) {
            return workout;
        }

        // if workout.length < exercisesNum, total time <= time, and exercises available:
        // add first exercise from exercisesAvailable (new structure),
        // add the exercise to workout (new structure),
        // get new modes for difficulty and muscleGroup
        // recursively call with new params
        // repeat for all exercises until sol'n is found (while loop)
        if (workout.length() < exercisesNum && workout.getTime() <= time && !exercisesAvailable.isEmpty()) {
            int index = 0;

            while (index < exercisesAvailable.size()) {
                Workout workoutWithAddedExercise = addNextExerciseFromWorkout(name, workout, difficulty,
                        exercisesAvailable, index);

                List<Exercise> reducedExercisesAvailable = removeExerciseFromAvailableExercises(exercisesAvailable,
                        index);

                Workout result = addNextExercise(name, workoutWithAddedExercise, exercisesNum, time, difficulty,
                        muscleGroup, reducedExercisesAvailable);
                // if result is null, branch has failed so increment index, otherwise return result
                if (result == null) {
                    ++index;
                } else {
                    return result;
                }
            }
        }

        // return null otherwise i.e. workout.length() > exercisesNum, total time > time,
        // or no more exercises available (i.e. empty)

        return null;
    }

    // EFFECTS: returns a new workout with all exercises from given workout
    //          and the exercise with the given index from the available exercises
    private Workout addNextExerciseFromWorkout(String name, Workout workout, Difficulty difficulty,
                                               List<Exercise> exercisesAvailable, int index) {
        Workout workoutWithAddedExercise = new Workout(name, difficulty);

        for (Exercise exercise : workout.getExercises()) {
            workoutWithAddedExercise.addExercise(exercise);
        }
        workoutWithAddedExercise.addExercise(exercisesAvailable.get(index));

        return workoutWithAddedExercise;
    }

    // EFFECTS: removes exercise with the given index from the available exercises and
    //          returns the remaining available exercises
    private List<Exercise> removeExerciseFromAvailableExercises(List<Exercise> exercisesAvailable, int index) {
        List<Exercise> reducedExercisesAvailable = new ArrayList<>(exercisesAvailable);
        reducedExercisesAvailable.remove(index);

        return reducedExercisesAvailable;
    }


    // EFFECTS: returns true if the given workout has the same number of exercises as exercisesNum, total time less than
    //          given time, and matches the given difficulty and muscle group
    private boolean isValid(Workout workout, int exercisesNum, int time,
                            Difficulty difficulty, MuscleGroup muscleGroup) {
        return workout.length() == exercisesNum
                && workout.getTime() <= time
                && matchesParameter(workout, difficulty, "DIFFICULTY")
                && matchesParameter(workout, muscleGroup, "MUSCLE_GROUP");
    }

    // EFFECTS: returns true if the given parameter is the most common parameter in the workout
    private <T> boolean matchesParameter(Workout workout, T parameter, String key) {
        int count = 0;

        switch (key) {
            case "DIFFICULTY":
                Difficulty difficulty = (Difficulty) parameter;

                for (Exercise exercise : workout.getExercises()) {
                    if (exercise.getDifficulty().equals(difficulty)) {
                        ++count;
                    }
                }

                break;
            case "MUSCLE_GROUP":
                MuscleGroup muscleGroup = (MuscleGroup) parameter;

                for (Exercise exercise : workout.getExercises()) {
                    if (exercise.getMuscleGroup().equals(muscleGroup)) {
                        ++count;
                    }
                }
        }

        return count >= workout.length() / 2;
    }
}
