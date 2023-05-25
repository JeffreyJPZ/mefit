package ui;

import model.Profile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import static ui.FitnessAppCommands.PROFILES_COMMAND;

// Represents a panel of the fitness application
public abstract class FitnessPanel extends UIObservablePanel implements UIObserver, ActionListener {
    protected List<JComponent> components; // TODO: make components a map with name

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
}
