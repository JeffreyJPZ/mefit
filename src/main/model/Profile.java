package model;

// Represents a user profile with a name, age (in years), gender, weight (lbs), list of exercises, list of workouts,
// and list of exercise schedules
public class Profile {
    private static int nextId = 1;
    private int id; // account number (idea sourced from Account.java)
    private String firstName;
    private String lastName;
    private String gender;
    private int age;
    private int weight;
    private ExerciseList exerciseList;
    private WorkoutList scheduleList;


    // REQUIRES: firstName, lastName, gender are not empty and age, weight are both > 0
    // EFFECTS: makes a profile with a first name, last name, gender, age in years, weight in lbs,
    //          empty list of exercises, empty list of schedules, and a unique id
    public Profile(String firstName, String lastName, String gender, int age, int weight) {
        // stub
    }

    public void setFirstName(String firstName) {
        // stub
    }

    public void setLastName(String lastName) {
        // stub
    }

    public void setGender(String gender) {
        // stub
    }

    public void setAge(int age) {
        // stub
    }

    public void setWeight(int weight) {
        // stub
    }

    public String getFirstName() {
        return "";
    }

    public String getLastName() {
        return "";
    }

    public String getGender() {
        return "";
    }

    public int getAge() {
        return 0;
    }

    public int getWeight() {
        return 0;
    }

    public int getId() {
        return 0;
    }

    public ExerciseList getExerciseList() {
        return null;
    }

    public WorkoutList getWorkoutList() {
        return null;
    }

    // EFFECTS: returns a summary of profile with first and last name, gender, age in years, and weight in lbs
    public String viewProfile() {
        return "";
    }
}
