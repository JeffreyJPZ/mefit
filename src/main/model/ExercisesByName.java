package model;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

// Represents a mapping of exercises by name
public class ExercisesByName implements FitnessCollection {
    public static final int DISPLAY_NUMBER_OF_EXERCISES = 10;
    public static final String ADDITIONAL_EXERCISE_MESSAGE = " additional exercises";

    private Map<String, Exercise> exercises;

    // EFFECTS: Makes an empty map of exercises
    public ExercisesByName() {
        exercises = new LinkedHashMap<>();
    }

    // REQUIRES: exercise map does not contain an exercise with the same name ignoring case
    // MODIFIES: this
    // EFFECTS: adds an exercise to the exercise map
    public void addExercise(Exercise exercise) {
        exercises.put(exercise.getName(), exercise);
    }

    // REQUIRES: exercise map is not empty and name matches an exercise
    // MODIFIES: this
    // EFFECTS: removes the exercise from the exercise map with the given name
    public void removeExercise(String name) {
        exercises.remove(name);
    }

    @Override
    // MODIFIES: this
    // EFFECTS: if number of exercises in map <= DISPLAY_NUMBER_OF_EXERCISES,
    //          returns the exercise name, muscle group, difficulty, time and whether the exercise is favourited
    //          up to the first DISPLAY_NUMBER_OF_EXERCISES exercises in map,
    //          otherwise also returns the number of remaining exercises in map and ADDITIONAL_EXERCISE_MESSAGE
    public String toString() {
        String retString = "Name\tMuscle Group\tDifficulty\tTime (min)\t Favourite?" + "\n";
        int count = 0;

        for (Exercise exercise: exercises.values()) {
            if (count == DISPLAY_NUMBER_OF_EXERCISES) {
                break;
            }
            retString = retString + "[" + exercise.getName() + "]" + "\t"
                                + exercise.getMuscleGroup().getMuscleGroup() + "\t"
                                + exercise.getDifficulty().getDifficulty() + "\t"
                                + exercise.getTime() + "\t"
                                + exercise.isFavourite() + "\n";
            count++;
        }

        if (exercises.size() <= DISPLAY_NUMBER_OF_EXERCISES) {
            return retString;
        } else {
            return retString + "... with "
                    + (exercises.size() - DISPLAY_NUMBER_OF_EXERCISES)
                    + ADDITIONAL_EXERCISE_MESSAGE;
        }
    }

    // REQUIRES: exercise map is not empty and string matches at least one element in exercise map
    // MODIFIES: this
    // EFFECTS: returns a mapping of exercises where string matches the beginning of the exercise name
    //          case insensitively
    public ExercisesByName filter(String name) {
        ExercisesByName exercisesByName = new ExercisesByName();

        Pattern pattern = Pattern.compile("^" + name + ".*", Pattern.CASE_INSENSITIVE);

        for (Exercise exercise : exercises.values()) {
            if (pattern.matcher(exercise.getName()).matches()) {
                exercisesByName.getExercises().put(exercise.getName(), exercise);
            }
        }

        return exercisesByName;
    }

    // REQUIRES: exercise map is not empty and muscleGroup matches at least one element in exercise map
    // MODIFIES: this
    // EFFECTS: returns a mapping of exercises with muscle group matching muscleGroup
    public ExercisesByName filterMuscleGroup(MuscleGroup muscleGroup) {
        ExercisesByName exercisesByName = new ExercisesByName();

        for (Exercise exercise : exercises.values()) {
            if (exercise.getMuscleGroup().equals(muscleGroup)) {
                exercisesByName.getExercises().put(exercise.getName(), exercise);
            }
        }

        return exercisesByName;
    }

    // REQUIRES: exercise map is not empty and difficulty matches at least one element in exercise map
    // MODIFIES: this
    // EFFECTS: returns a mapping of exercises with difficulty matching given difficulty
    public ExercisesByName filterDifficulty(Difficulty difficulty) {
        ExercisesByName exercisesByName = new ExercisesByName();

        for (Exercise exercise : exercises.values()) {
            if (exercise.getDifficulty().getDifficulty() == difficulty.getDifficulty()) {
                exercisesByName.getExercises().put(exercise.getName(), exercise);
            }
        }

        return exercisesByName;
    }

    // REQUIRES: exercise map is not empty and at least one element in exercise map <= time
    // MODIFIES: this
    // EFFECTS: returns a mapping of exercises with their time <= time
    public ExercisesByName filterTime(int time) {
        ExercisesByName exercisesByName = new ExercisesByName();

        for (Exercise exercise : exercises.values()) {
            if (exercise.getTime() <= time) {
                exercisesByName.getExercises().put(exercise.getName(), exercise);
            }
        }
        return exercisesByName;
    }

    // REQUIRES: exercise map is not empty and at least one element in exercise map is favourited
    // MODIFIES: this
    // EFFECTS: returns a mapping of exercises that are favourited
    public ExercisesByName filterFavourite() {
        ExercisesByName exercisesByName = new ExercisesByName();

        for (Exercise exercise : exercises.values()) {
            if (exercise.isFavourite()) {
                exercisesByName.getExercises().put(exercise.getName(), exercise);
            }
        }
        return exercisesByName;
    }

    // MODIFIES: this
    // EFFECTS: returns true if exercise with same name is in map, otherwise returns false
    public boolean contains(String name) {
        return exercises.containsKey(name);
    }

    // MODIFIES: this
    // EFFECTS: returns true if map is empty, otherwise returns false
    public boolean isEmpty() {
        return exercises.isEmpty();
    }

    // MODIFIES: this
    // EFFECTS: returns the length of the exercise map
    public int length() {
        return exercises.size();
    }

    // REQUIRES: exercise map is not empty and name matches an exercise
    // MODIFIES: this
    // EFFECTS: returns the exercise with the given name in exercise map
    public Exercise getExercise(String name) {
        return exercises.get(name);
    }

    public Map<String, Exercise> getExercises() {
        return exercises;
    }
}
