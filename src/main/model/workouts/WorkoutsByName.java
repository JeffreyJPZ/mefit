package model.workouts;

import model.json.JsonKeys;
import model.metrics.Difficulty;
import model.FitnessCollection;
import model.formatting.StringFormat;
import model.workouts.workout.Workout;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

// Represents a mapping of workouts by name
public class WorkoutsByName implements FitnessCollection {
    public static final int DISPLAY_NUMBER_OF_WORKOUTS = 10;
    public static final String ADDITIONAL_WORKOUT_MESSAGE = " additional workouts";

    private Map<String, Workout> workouts;

    // EFFECTS: Makes a map of workouts with no workouts
    public WorkoutsByName() {
        workouts = new LinkedHashMap<>();
    }

    // TODO: throw exception if workout with same name is already contained
    // REQUIRES: workouts does not contain a workout with the same name ignoring case
    // MODIFIES: this
    // EFFECTS: adds a workout to the workouts
    public void addWorkout(Workout workout) {
        workouts.put(workout.getName(), workout);
    }

    // REQUIRES: workouts is not empty and name matches a workout
    // MODIFIES: this
    // EFFECTS: removes the workout from the workouts with the given name
    public void removeWorkout(String name) {
        workouts.remove(name);
    }

    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    @Override
    // EFFECTS: if the number of workouts <= DISPLAY_NUMBER_OF_WORKOUTS, returns a string representation
    //          of the workout name, difficulty, time, # of exercises, and whether it is favourited up to the first
    //          DISPLAY_NUMBER_OF_WORKOUTS workouts
    //          Otherwise, also returns the number of remaining workouts and ADDITIONAL_WORKOUT_MESSAGE
    public String toString() {
        StringBuilder workoutsString = new StringBuilder();
        workoutsString.append("Name").append(StringFormat.SEPARATOR.getFormat())
                .append("Difficulty")
                .append(StringFormat.SEPARATOR.getFormat())
                .append("Time (min)")
                .append(StringFormat.SEPARATOR.getFormat())
                .append("# of Exercises")
                .append(StringFormat.SEPARATOR.getFormat())
                .append("Favourite?")
                .append(StringFormat.LINE_BREAK.getFormat());
        int count = 0;

        for (Workout workout: workouts.values()) {
            if (count == DISPLAY_NUMBER_OF_WORKOUTS) {
                break;
            }
            workoutsString.append(StringFormat.LEFT_BRACKET.getFormat())
                    .append(workout.getName())
                    .append(StringFormat.RIGHT_BRACKET.getFormat())
                    .append(StringFormat.SEPARATOR.getFormat());
            workoutsString.append(workout.getDifficulty().getDifficultyAsInt())
                    .append(StringFormat.SEPARATOR.getFormat());
            workoutsString.append(workout.getTimeMinutes())
                    .append(StringFormat.SEPARATOR.getFormat());
            workoutsString.append(workout.length())
                    .append(StringFormat.SEPARATOR.getFormat());
            workoutsString.append(workout.isFavourite())
                    .append(StringFormat.LINE_BREAK.getFormat());
            count++;
        }

        if (workouts.size() > DISPLAY_NUMBER_OF_WORKOUTS) {
            workoutsString.append(StringFormat.CUTOFF.getFormat());
            workoutsString.append((workouts.size() - DISPLAY_NUMBER_OF_WORKOUTS));
            workoutsString.append(ADDITIONAL_WORKOUT_MESSAGE);
        }

        return workoutsString.toString();
    }

    @Override
    // EFFECTS: returns the workouts with their name matching given name case insensitively
    public WorkoutsByName filterName(String name) {
        WorkoutsByName workoutsByName = new WorkoutsByName();

        Pattern pattern = Pattern.compile("^" + name + ".*", Pattern.CASE_INSENSITIVE);

        for (Workout workout : workouts.values()) {
            filterPredicate(workoutsByName, workout, pattern.matcher(workout.getName()).matches());
        }

        return workoutsByName;
    }

    // EFFECTS: returns the workouts with difficulty matching given difficulty
    public WorkoutsByName filterDifficulty(Difficulty difficulty) {
        WorkoutsByName workoutsByName = new WorkoutsByName();

        for (Workout workout : workouts.values()) {
            filterPredicate(workoutsByName, workout, workout.getDifficulty().equals(difficulty));
        }

        return workoutsByName;
    }

    // EFFECTS: returns the workouts with their time <= time
    public WorkoutsByName filterTime(int time) {
        WorkoutsByName workoutsByName = new WorkoutsByName();

        for (Workout workout : workouts.values()) {
            filterPredicate(workoutsByName, workout, workout.getTimeMinutes() <= time);
        }

        return workoutsByName;
    }

    // EFFECTS: returns the workouts that are favourited
    public WorkoutsByName filterFavourite() {
        WorkoutsByName workoutsByName = new WorkoutsByName();

        for (Workout workout : workouts.values()) {
            filterPredicate(workoutsByName, workout, workout.isFavourite());
        }

        return workoutsByName;
    }

    // EFFECTS: returns the workouts with their number of exercises <= given number
    public WorkoutsByName filterSize(int numberOfExercises) {
        WorkoutsByName workoutsByName = new WorkoutsByName();

        for (Workout workout : workouts.values()) {
            filterPredicate(workoutsByName, workout, workout.length() <= numberOfExercises);
        }

        return workoutsByName;
    }

    // EFFECTS: adds the given workout to the workouts if predicate matches
    //          otherwise does nothing
    private void filterPredicate(WorkoutsByName workoutsByName, Workout workout, boolean predicate) {
        if (predicate) {
            workoutsByName.addWorkout(workout);
        }
    }

    // EFFECTS: returns true if workouts contains a workout with the given name, otherwise returns false
    @Override
    public boolean contains(String name) {
        return workouts.containsKey(name);
    }

    @Override
    // EFFECTS: returns true if workouts has no workouts, otherwise returns false
    public boolean isEmpty() {
        return workouts.isEmpty();
    }

    @Override
    // EFFECTS: returns the number of workouts
    public int length() {
        return workouts.size();
    }

    // REQUIRES: workouts is not empty and name matches a workout
    // EFFECTS: returns the workout with the given name
    public Workout getWorkout(String name) {
        return workouts.get(name);
    }

    public Map<String, Workout> getWorkouts() {
        return workouts;
    }

    @Override
    // EFFECTS: Returns a json object with workouts
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put(JsonKeys.WORKOUTS.getKey(), workoutsToJson());

        return jsonObject;
    }

    // EFFECTS: Returns a json array with workouts
    private JSONArray workoutsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Workout workout : workouts.values()) {
            jsonArray.put(workout.toJson());
        }

        return jsonArray;
    }
}
