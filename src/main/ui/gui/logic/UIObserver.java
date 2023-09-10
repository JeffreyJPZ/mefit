package ui.gui.logic;

import ui.gui.app.FitnessAppCommands;

// Represents a UI component that can be notified of updates
public interface UIObserver {

    // EFFECTS: updates the UI component if key is a match
    <T> void update(T t, FitnessAppCommands key);
}
