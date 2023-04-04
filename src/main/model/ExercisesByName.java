package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.JsonWritable;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

// Represents a mapping of exercises by name
public class ExercisesByName implements FitnessCollection, JsonWritable {
    public static final int DISPLAY_NUMBER_OF_EXERCISES = 10;
    public static final String ADDITIONAL_EXERCISE_MESSAGE = " additional exercises";

    private Map<String, Exercise> exercises;

    // EFFECTS: Makes a map of exercises with no exercises
    public ExercisesByName() {
        exercises = new LinkedHashMap<>();
    }

    // REQUIRES: exercises does not contain an exercise with the same name ignoring case
    // MODIFIES: this
    // EFFECTS: adds an exercise to the exercises
    public void addExercise(Exercise exercise) {
        exercises.put(exercise.getName(), exercise);
        EventLog.getInstance().logEvent(new Event(EventDescription.ADD_EXERCISE.getDescription()));
    }

    // REQUIRES: exercises is not empty and name matches an exercise
    // MODIFIES: this
    // EFFECTS: removes an exercise from the exercises with the given name
    public void removeExercise(String name) {
        exercises.remove(name);
        EventLog.getInstance().logEvent(new Event(EventDescription.REMOVE_EXERCISE.getDescription()));
    }

    @Override
    // EFFECTS: if number of exercises  <= DISPLAY_NUMBER_OF_EXERCISES,
    //          returns the exercise name, muscle group, difficulty, time and whether the exercise is favourited
    //          up to the first DISPLAY_NUMBER_OF_EXERCISES exercises,
    //          otherwise also returns the number of remaining exercises and ADDITIONAL_EXERCISE_MESSAGE
    public String toString() {
        StringBuilder exercisesString = new StringBuilder();
        exercisesString.append("Name\tMuscle Group\tDifficulty\tTime (min)\t Favourite?\n");
        int count = 0;

        for (Exercise exercise: exercises.values()) {
            if (count == DISPLAY_NUMBER_OF_EXERCISES) {
                break;
            }
            exercisesString.append("[").append(exercise.getName()).append("]").append("\t");
            exercisesString.append(exercise.getMuscleGroup().getMuscleGroup()).append("\t");
            exercisesString.append(exercise.getDifficulty().getDifficulty()).append("\t");
            exercisesString.append(exercise.getTime()).append("\t");
            exercisesString.append(exercise.isFavourite()).append("\n");
            count++;
        }

        if (exercises.size() > DISPLAY_NUMBER_OF_EXERCISES) {
            exercisesString.append("... with ");
            exercisesString.append((exercises.size() - DISPLAY_NUMBER_OF_EXERCISES));
            exercisesString.append(ADDITIONAL_EXERCISE_MESSAGE);
        }

        return exercisesString.toString();
    }

    // EFFECTS: returns the exercises where string matches the beginning of the exercise name
    //          case insensitively
    public ExercisesByName filter(String name) {
        ExercisesByName exercisesByName = new ExercisesByName();

        Pattern pattern = Pattern.compile("^" + name + ".*", Pattern.CASE_INSENSITIVE);

        for (Exercise exercise : exercises.values()) {
            if (pattern.matcher(exercise.getName()).matches()) {
                exercisesByName.getExercises().put(exercise.getName(), exercise);
            }
        }

        EventLog.getInstance().logEvent(new Event(EventDescription.FILTER_EXERCISES.getDescription()));

        return exercisesByName;
    }

    // EFFECTS: returns the exercises with muscle group matching muscleGroup
    public ExercisesByName filterMuscleGroup(MuscleGroup muscleGroup) {
        ExercisesByName exercisesByName = new ExercisesByName();

        for (Exercise exercise : exercises.values()) {
            if (exercise.getMuscleGroup().equals(muscleGroup)) {
                exercisesByName.getExercises().put(exercise.getName(), exercise);
            }
        }

        EventLog.getInstance().logEvent(new Event(EventDescription.FILTER_EXERCISES.getDescription()));

        return exercisesByName;
    }

    // EFFECTS: returns the exercises with difficulty matching given difficulty
    public ExercisesByName filterDifficulty(Difficulty difficulty) {
        ExercisesByName exercisesByName = new ExercisesByName();

        for (Exercise exercise : exercises.values()) {
            if (exercise.getDifficulty().getDifficulty() == difficulty.getDifficulty()) {
                exercisesByName.getExercises().put(exercise.getName(), exercise);
            }
        }

        EventLog.getInstance().logEvent(new Event(EventDescription.FILTER_EXERCISES.getDescription()));

        return exercisesByName;
    }

    // EFFECTS: returns the exercises with their time <= time
    public ExercisesByName filterTime(int time) {
        ExercisesByName exercisesByName = new ExercisesByName();

        for (Exercise exercise : exercises.values()) {
            if (exercise.getTime() <= time) {
                exercisesByName.getExercises().put(exercise.getName(), exercise);
            }
        }

        EventLog.getInstance().logEvent(new Event(EventDescription.FILTER_EXERCISES.getDescription()));

        return exercisesByName;
    }

    // EFFECTS: returns the exercises that are favourited
    public ExercisesByName filterFavourite() {
        ExercisesByName exercisesByName = new ExercisesByName();

        for (Exercise exercise : exercises.values()) {
            if (exercise.isFavourite()) {
                exercisesByName.getExercises().put(exercise.getName(), exercise);
            }
        }

        EventLog.getInstance().logEvent(new Event(EventDescription.FILTER_EXERCISES.getDescription()));

        return exercisesByName;
    }

    // EFFECTS: returns true if exercise with same name in the exercises, otherwise returns false
    public boolean contains(String name) {
        return exercises.containsKey(name);
    }

    // EFFECTS: returns true if exercises has no exercises, otherwise returns false
    public boolean isEmpty() {
        return exercises.isEmpty();
    }

    // EFFECTS: returns the number of exercises
    public int length() {
        return exercises.size();
    }

    // REQUIRES: exercises is not empty and name matches an exercise
    // EFFECTS: returns the exercise with the given name in exercises
    public Exercise getExercise(String name) {
        return exercises.get(name);
    }

    public Map<String, Exercise> getExercises() {
        return exercises;
    }

    // EFFECTS: Returns a json object with exercises
    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("exercises", exercisesToJson());

        return jsonObject;
    }

    // EFFECTS: Returns a json array with exercises
    private JSONArray exercisesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Exercise exercise : exercises.values()) {
            jsonArray.put(exercise.toJson());
        }

        return jsonArray;
    }
}
