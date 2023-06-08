package ui;

import model.Difficulty;
import model.MuscleGroup;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

// Represents a panel of the fitness application
public abstract class FitnessPanel extends UIObservablePanel implements UIObserver, ActionListener {
    protected List<JComponent> components;

    // EFFECTS: creates a fitness panel with components
    protected FitnessPanel() {
        this.components = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds each component to be displayed to components
    protected abstract void addDisplayComponents();

    // MODIFIES: this
    // EFFECTS: sets the appropriate components to respond to appropriate events
    protected abstract void initializeActions();

    // MODIFIES: this
    // EFFECTS: sets the given component to respond to the appropriate event
    protected void initializeAction(JButton b, String command) {
        b.setActionCommand(command);
        b.addActionListener(this);
    }

    // MODIFIES: this
    // EFFECTS: sets the placement of the components for the panel
    protected void initializePlacements() {
        for (JComponent c : components) {
            c.setAlignmentX(Component.CENTER_ALIGNMENT);
        }
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    // MODIFIES: this
    // EFFECTS: adds the components to be displayed to the panel
    protected void addComponents() {
        for (JComponent c : components) {
            add(c);
            add(Box.createVerticalGlue());
        }
    }

    // EFFECTS: does nothing if key is a match
    @Override
    public <T> void update(T t, FitnessAppCommands key) {}

    // REQUIRES: muscleGroupName matches a muscle group
    // EFFECTS: returns the muscle group associated with the given name
    protected MuscleGroup getMuscleGroupByName(String muscleGroupName) {
        MuscleGroup muscleGroup = null;

        for (MuscleGroup m : MuscleGroup.values()) {
            if (m.getMuscleGroup().equals(muscleGroupName)) {
                muscleGroup = m;
                break;
            }
        }
        return muscleGroup;
    }

    // REQUIRES: difficultyLevel matches a difficulty
    // EFFECTS: returns the difficulty associated with the given difficulty level
    protected Difficulty getDifficultyByLevel(int difficultyLevel) {
        Difficulty difficulty = null;

        for (Difficulty d : Difficulty.values()) {
            if (d.getDifficulty() == difficultyLevel) {
                difficulty = d;
                break;
            }
        }
        return difficulty;
    }
}
