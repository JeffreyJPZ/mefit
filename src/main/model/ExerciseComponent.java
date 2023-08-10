package model;

import persistence.JsonWritable;

public abstract class ExerciseComponent implements JsonWritable {
    private String name;
    private ExerciseComponentTypes type;

    // EFFECTS: makes a new exercise component
    public ExerciseComponent(String name, ExerciseComponentTypes type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public ExerciseComponentTypes getType() {
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }

    // EFFECTS: returns the time of the exercise component
    public abstract int getTime();

    // EFFECTS: returns the number of items in the exercise component
    public abstract int getSize();
}
