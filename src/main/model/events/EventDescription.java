package model.events;

// Represents descriptions for events taken in the fitness application
public enum EventDescription {
    ADD_EXERCISE("Added an exercise to a profile's exercises"),
    REMOVE_EXERCISE("Removed an exercise from a profile's exercises"),
    FILTER_EXERCISES("Filtered a profile's exercises");

    private final String description;

    // REQUIRES: description is not empty
    // EFFECTS: Makes an event description with the given description
    EventDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
