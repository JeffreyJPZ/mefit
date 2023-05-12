package ui;

// Represents a UI component that can be notified
public interface UIObserver {

    // EFFECTS: updates the UI component
    <T> void update(T t, FitnessAppCommands key);
}
