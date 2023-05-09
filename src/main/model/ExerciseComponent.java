package model;

public abstract class ExerciseComponent {
    private String name;

    // EFFECTS: makes a new exercise component
    public ExerciseComponent(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // EFFECTS: returns the time of the exercise component
    public abstract int getTime();

    // EFFECTS: returns the number of items in the exercise component
    public abstract int getSize();
}
