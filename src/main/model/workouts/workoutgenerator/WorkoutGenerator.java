package model.workouts.workoutgenerator;

import exceptions.NoValidWorkoutException;
import model.metrics.Difficulty;
import model.metrics.MuscleGroup;
import model.exercises.exercise.Exercise;
import model.exercises.ExercisesByName;
import model.workouts.workout.Workout;

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

    // EFFECTS: generates and returns a random workout with a name and given parameters
    //          UNLESS: if there are no given exercises, throws NoValidWorkoutException
    //          otherwise, if no workout can be generated, throws NoValidWorkoutException
    public Workout generateWorkout(String name, ExercisesByName exercisesByName,
                                   WorkoutParameters parameters, Random random)
            throws NoValidWorkoutException {
        if (exercisesByName.isEmpty()) {
            throw new NoValidWorkoutException();
        }

        Workout workout = new Workout(name, parameters.getDifficulty());
        // All exercises that can be added
        List<Exercise> allExercises = new ArrayList<>(exercisesByName.getExercises().values());
        List<Exercise> exercisesAvailable = selectExercises(parameters.getSampleSize(), allExercises, random);

        Workout result = addNextExercise(name, workout, parameters, exercisesAvailable);

        if (result == null) {
            throw new NoValidWorkoutException();
        }

        return result;
    }

    // EFFECTS: returns sampleSize randomly selected exercises from the given exercises;
    private List<Exercise> selectExercises(int sampleSize, List<Exercise> allExercises, Random random) {
        List<Exercise> exercisesAvailable = new ArrayList<>();
        int i = 0;

        while (i < sampleSize) {
            int index = random.nextInt(allExercises.size());

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
    private Workout addNextExercise(String name, Workout workout,
                                    WorkoutParameters parameters, List<Exercise> exercisesAvailable) {
        // return workout if and only if workout.length() = exercisesNum, total time <= time,
        // mode of difficulties in workout is difficulty, mode of muscle groups is muscleGroup in workout

        if (isValid(workout, parameters)) {
            return workout;
        }

        // if workout.length < exercisesNum, total time <= time, and exercises available:
        // add first exercise from exercisesAvailable (new structure),
        // add the exercise to workout (new structure),
        // get new modes for difficulty and muscleGroup
        // recursively call with new params
        // repeat for all exercises until sol'n is found (while loop)
        if (workout.length() < parameters.getSize()
                && workout.getTimeMinutes() <= parameters.getTime()
                && !exercisesAvailable.isEmpty()) {
            int index = 0;

            while (index < exercisesAvailable.size()) {
                Workout workoutWithAddedExercise = addNextExerciseFromWorkout(name, workout, parameters.getDifficulty(),
                        exercisesAvailable, index);

                List<Exercise> reducedExercisesAvailable = removeExerciseFromAvailableExercises(exercisesAvailable,
                        index);

                Workout result = addNextExercise(name, workoutWithAddedExercise, parameters, reducedExercisesAvailable);
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
    private boolean isValid(Workout workout, WorkoutParameters parameters) {
        return workout.length() == parameters.getSize()
                && workout.getTimeMinutes() <= parameters.getTime()
                && matchesParameter(workout, parameters.getDifficulty(), "DIFFICULTY")
                && matchesParameter(workout, parameters.getMuscleGroup(), "MUSCLE_GROUP");
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
