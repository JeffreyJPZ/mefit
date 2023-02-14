package model;

// Represents a user profile with a name, age (in years), gender, weight (lbs), exercises, and workouts
public class Profile {
    private static int nextId = 1; // account number (attributed from TellerApp)

    private int id;
    private String name;
    private String gender;
    private int age;
    private int weight;
    private ExercisesByName exercises;
    private WorkoutsByName workouts;


    // REQUIRES: name, gender are not empty and age, weight are both > 0
    // EFFECTS: makes a profile with a name, gender, age in years, weight in lbs,
    //          empty exercises, empty workouts, and a unique id
    public Profile(String name, String gender, int age, int weight) {
        this.id = nextId;
        nextId++;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.weight = weight;
    }

    public String getName() {
        return name;
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
        return "Name" + name + "\n"
                +
                "Gender: " + gender + "\n"
                +
                "Age: " + age + "\n"
                +
                "Weight: " + weight;
    }
}
