package model;

// Represents a user profile with a name, age (in years), gender, weight (lbs), list of exercises, list of workouts,
// and list of exercise schedules
public class Profile {
    private static int nextId = 1; // account number (attributed from TellerApp)

    private int id;
    private String firstName;
    private String lastName;
    private String gender;
    private int age;
    private int weight;
    private ExerciseList exerciseList;
    private WorkoutList workoutList;


    // REQUIRES: firstName, lastName, gender are not empty and age, weight are both > 0
    // EFFECTS: makes a profile with a first name, last name, gender, age in years, weight in lbs,
    //          empty list of exercises, empty list of schedules, and a unique id
    public Profile(String firstName, String lastName, String gender, int age, int weight) {
        this.id = nextId;
        nextId++;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.age = age;
        this.weight = weight;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    public int getWeight() {
        return weight;
    }

    public int getId() {
        return id;
    }

    public ExerciseList getExerciseList() {
        return exerciseList;
    }

    public WorkoutList getWorkoutList() {
        return workoutList;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    // MODIFIES: this
    // EFFECTS: returns a string representation of profile with
    //          first and last name, gender, age in years, and weight in lbs
    @Override
    public String toString() {
        return "First Name: " + firstName + "\n"
                +
                "Last Name: " + lastName + "\n"
                +
                "Gender: " + gender + "\n"
                +
                "Age: " + age + "\n"
                +
                "Weight: " + lastName;
    }
}
