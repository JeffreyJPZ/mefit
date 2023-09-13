package model.profiles.profile;

import model.json.JsonKeys;
import model.workouts.WorkoutsByName;
import model.exercises.ExercisesByName;
import org.json.JSONObject;
import persistence.JsonWritable;

// Represents a user profile with a name, age (in years), gender, weight (lbs), exercises, and workouts
public class Profile implements JsonWritable {
    private static int nextId = 1; // account number of the next profile (adapted from TellerApp)

    private int id;
    private String name;
    private String gender;
    private int ageYears;
    private int weightPounds;
    private ExercisesByName exercises;
    private WorkoutsByName workouts;


    // REQUIRES: name, gender are not empty and age, weight are both > 0
    // EFFECTS: makes a profile with a name, gender, age in years, weight in lbs,
    //          empty map of exercises, empty map of workouts, and a unique id
    public Profile(String name, String gender, int ageYears, int weightPounds) {
        this.id = nextId;
        nextId++;
        this.name = name;
        this.gender = gender;
        this.ageYears = ageYears;
        this.weightPounds = weightPounds;
        this.exercises = new ExercisesByName();
        this.workouts = new WorkoutsByName();
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public int getAgeYears() {
        return ageYears;
    }

    public int getWeightPounds() {
        return weightPounds;
    }

    public int getId() {
        return id;
    }

    public static int getNextId() {
        return nextId;
    }

    public ExercisesByName getExercises() {
        return exercises;
    }

    public WorkoutsByName getWorkouts() {
        return workouts;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAgeYears(int ageYears) {
        this.ageYears = ageYears;
    }

    public void setWeightPounds(int weightPounds) {
        this.weightPounds = weightPounds;
    }

    public void setExercises(ExercisesByName exercises) {
        this.exercises = exercises;
    }

    public void setWorkouts(WorkoutsByName workouts) {
        this.workouts = workouts;
    }

    // REQUIRES: id and nextId do not match an existing profile id
    // MODIFIES: this
    // EFFECTS: assigns given id to profile
    public void setId(int id) {
        this.id = id;
    }

    public static void setNextId(int id) {
        nextId = id;
    }

    // EFFECTS: returns a string representation of profile with
    //          profile name, gender, age in years, and weight in lbs
    @Override
    public String toString() {
        return "Name: " + name + "\n"
                +
                "Gender: " + gender + "\n"
                +
                "Age (yrs): " + ageYears + "\n"
                +
                "Weight (lbs): " + weightPounds;
    }

    // EFFECTS: returns a json object with id, profile name, gender, age in years, weight in lbs,
    //          exercises and workouts
    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put(JsonKeys.PROFILE_ID.getKey(), id);
        jsonObject.put(JsonKeys.PROFILE_NAME.getKey(), name);
        jsonObject.put(JsonKeys.PROFILE_GENDER.getKey(), gender);
        jsonObject.put(JsonKeys.PROFILE_AGE.getKey(), ageYears);
        jsonObject.put(JsonKeys.PROFILE_WEIGHT.getKey(), weightPounds);
        jsonObject.put(JsonKeys.EXERCISES.getKey(), exercises.toJson());
        jsonObject.put(JsonKeys.WORKOUTS.getKey(), workouts.toJson());

        return jsonObject;
    }
}
