package persistence;

import exceptions.InvalidExerciseException;
import model.exercises.*;
import model.exercises.exercise.*;
import model.json.JsonKeys;
import model.metrics.ExerciseType;
import model.profiles.profile.Profile;
import model.profiles.ProfilesById;
import model.workouts.workout.Workout;
import model.workouts.WorkoutsByName;
import org.json.JSONArray;
import org.json.JSONObject;
import model.metrics.FitnessMetricParser;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static model.json.JsonKeys.*;

// A reader that reads JSON files for profiles from a given path
// attributed to JsonSerializationDemo from CPSC 210
public class JsonReader {
    private String path;
    private ExercisesByName exercisesByName; // stores the current exercises worked on

    // EFFECTS: creates a json reader to read json file with a given path
    public JsonReader(String path) {
        this.path = path;
        this.exercisesByName = new ExercisesByName();
    }

    // EFFECTS: returns the profiles stored in json file, throws IOException if
    //          error occurs in input or output, throws InvalidExerciseException if exercise
    //          type is invalid
    public ProfilesById read() throws IOException, InvalidExerciseException {
        String profilesJsonData = readFile(path);
        JSONObject profilesJson = new JSONObject(profilesJsonData);

        return profilesJsonToProfiles(profilesJson);
    }

    // EFFECTS: returns the json file with the given path as a string, throws IOException if
    //          error occurs in input or output
    private String readFile(String path) throws IOException {
        StringBuilder profilesJsonData = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(path), StandardCharsets.UTF_8)) {
            stream.forEach(profilesJsonData::append);
        }

        return profilesJsonData.toString();
    }

    // EFFECTS: converts the json data for profiles and returns profiles, throws InvalidExerciseException if exercise
    //          type is invalid
    public ProfilesById profilesJsonToProfiles(JSONObject profilesJson) throws InvalidExerciseException {
        JSONArray profiles = profilesJson.getJSONArray(PROFILES.getKey());
        ProfilesById profilesById = new ProfilesById();
        int largestId = 0;

        for (int i = 0; i < profiles.length(); i++) {
            JSONObject profileJson = profiles.getJSONObject(i);

            Profile profile = new Profile(profileJson.getString(PROFILE_NAME.getKey()),
                    profileJson.getString(PROFILE_GENDER.getKey()),
                    profileJson.getInt(PROFILE_AGE.getKey()),
                    profileJson.getInt(PROFILE_WEIGHT.getKey()));

            exercisesByName = exercisesJsonToExercises(profileJson.getJSONObject(EXERCISES.getKey()));
            WorkoutsByName workoutsByName = workoutsJsonToWorkouts(profileJson.getJSONObject(WORKOUTS.getKey()));

            profile.setExercises(exercisesByName);
            profile.setWorkouts(workoutsByName);

            int id = profileJson.getInt(PROFILE_ID.getKey());
            profile.setId(id);

            if (id > largestId) {
                largestId = id;
            }

            profilesById.addProfile(profile);
        }

        Profile.setNextId(largestId += 1);

        return profilesById;
    }

    // EFFECTS: converts json data for exercises to exercises, throws InvalidExerciseException if exercise
    //          type is invalid
    private ExercisesByName exercisesJsonToExercises(JSONObject exercisesJson) throws InvalidExerciseException {
        JSONArray exercises = exercisesJson.getJSONArray(EXERCISES.getKey());
        ExercisesByName exercisesByName = new ExercisesByName();

        for (int i = 0; i < exercises.length(); i++) {
            JSONObject exerciseJson = exercises.getJSONObject(i);
            exercisesByName.addExercise(exerciseJsonToExercise(exerciseJson));
        }
        return exercisesByName;
    }

    // EFFECTS: converts json data for exercise to an exercise with given type,
    // throws InvalidExerciseException if exercise type is invalid
    private Exercise exerciseJsonToExercise(JSONObject exerciseJson) throws InvalidExerciseException {
        Exercise exercise = null;

        String name = exerciseJson.getString(EXERCISE_TYPE.getKey());
        ExerciseType exerciseType = FitnessMetricParser.getInstance().getExerciseTypeByName(name);

        if (exerciseType == null) {
            throw new InvalidExerciseException();
        }

        switch (exerciseType) {
            case WEIGHTS_EXERCISE:
                exercise = exerciseJsonToWeightsExercise(exerciseJson);
                break;
            case BODYWEIGHTS_EXERCISE:
                exercise = exerciseJsonToBodyWeightsExercise(exerciseJson);
                break;
            case CARDIO_EXERCISE:
                exercise = exerciseJsonToCardioExercise(exerciseJson);
                break;
            default:
                throw new InvalidExerciseException();
        }

        return exercise;
    }

    // EFFECTS: returns a weights exercise with given json data
    private Exercise exerciseJsonToWeightsExercise(JSONObject exerciseJson) {
        Exercise exercise = new WeightsExercise(exerciseJson.getString(EXERCISE_NAME.getKey()),
                FitnessMetricParser.getInstance()
                        .getMuscleGroupByName(exerciseJson.getString(EXERCISE_MUSCLE_GROUP.getKey())),
                exerciseJson.getInt(EXERCISE_WEIGHT.getKey()),
                exerciseJson.getInt(EXERCISE_SETS.getKey()),
                exerciseJson.getInt(EXERCISE_REPS.getKey()),
                FitnessMetricParser.getInstance()
                        .getDifficultyByLevel(exerciseJson.getInt(EXERCISE_DIFFICULTY.getKey())),
                exerciseJson.getInt(EXERCISE_TIME.getKey()));

        exercise.setFavourite(exerciseJson.getBoolean(EXERCISE_FAVOURITE.getKey()));

        return exercise;
    }

    // EFFECTS: returns a bodyweights exercise with given json data
    private Exercise exerciseJsonToBodyWeightsExercise(JSONObject exerciseJson) {
        Exercise exercise = new BodyWeightsExercise(exerciseJson.getString(EXERCISE_NAME.getKey()),
                FitnessMetricParser.getInstance()
                        .getMuscleGroupByName(exerciseJson.getString(EXERCISE_MUSCLE_GROUP.getKey())),
                exerciseJson.getInt(EXERCISE_SETS.getKey()),
                exerciseJson.getInt(EXERCISE_REPS.getKey()),
                FitnessMetricParser.getInstance()
                        .getDifficultyByLevel(exerciseJson.getInt(EXERCISE_DIFFICULTY.getKey())),
                exerciseJson.getInt(EXERCISE_TIME.getKey()));

        exercise.setFavourite(exerciseJson.getBoolean(EXERCISE_FAVOURITE.getKey()));

        return exercise;
    }

    // EFFECTS: returns a cardio exercise with given json data
    private Exercise exerciseJsonToCardioExercise(JSONObject exerciseJson) {
        Exercise exercise = new CardioExercise(exerciseJson.getString(EXERCISE_NAME.getKey()),
                FitnessMetricParser.getInstance()
                        .getMuscleGroupByName(exerciseJson.getString(EXERCISE_MUSCLE_GROUP.getKey())),
                exerciseJson.getInt(EXERCISE_DISTANCE.getKey()),
                FitnessMetricParser.getInstance()
                        .getDifficultyByLevel(exerciseJson.getInt(EXERCISE_DIFFICULTY.getKey())),
                exerciseJson.getInt(EXERCISE_TIME.getKey()));

        exercise.setFavourite(exerciseJson.getBoolean(EXERCISE_FAVOURITE.getKey()));

        return exercise;
    }

    // EFFECTS: converts json data for workouts to workouts, throws InvalidExerciseException if
    //          exercise type is invalid

    private WorkoutsByName workoutsJsonToWorkouts(JSONObject workoutsJson) throws InvalidExerciseException {
        JSONArray workouts = workoutsJson.getJSONArray(WORKOUTS.getKey());
        WorkoutsByName workoutsByName = new WorkoutsByName();

        for (int i = 0; i < workouts.length(); i++) {
            JSONObject workoutJson = workouts.getJSONObject(i);
            workoutsByName.addWorkout(workoutJsonToWorkout(workoutJson));
        }
        return workoutsByName;
    }

    // EFFECTS: converts json data for workout to workout, throws InvalidExerciseException if
    //          exercise type is invalid
    private Workout workoutJsonToWorkout(JSONObject workoutJson) throws InvalidExerciseException {
        JSONArray exercisesJson = workoutJson.getJSONArray(WORKOUT_EXERCISES.getKey());
        JSONObject exerciseJson;
        List<Exercise> exercises = new ArrayList<>();

        for (int i = 0; i < exercisesJson.length(); i++) {
            exerciseJson = exercisesJson.getJSONObject(i);

            String exerciseName = exerciseJson.getString(EXERCISE_NAME.getKey());
            String exerciseTypeName = exerciseJson.getString(EXERCISE_TYPE.getKey());
            ExerciseType exerciseType = FitnessMetricParser.getInstance().getExerciseTypeByName(exerciseTypeName);

            if (exerciseType == null) {
                throw new InvalidExerciseException();
            }

            // adds the corresponding exercise in exercise collection
            exercises.add(exercisesByName.getExercise(exerciseName));
        }

        Workout workout = new Workout(workoutJson.getString(WORKOUT_NAME.getKey()),
                FitnessMetricParser.getInstance()
                        .getDifficultyByLevel(workoutJson.getInt(WORKOUT_DIFFICULTY.getKey())));

        workout.setFavourite(workoutJson.getBoolean(WORKOUT_FAVOURITE.getKey()));
        workout.setExercises(exercises);

        return workout;
    }

}
