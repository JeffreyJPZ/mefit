package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

// Represents a panel of the fitness application
public abstract class FitnessPanel extends View implements ActionListener {
    protected static final int WIDTH = 300;
    protected static final int HEIGHT = 300;
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
    // EFFECTS: sets the given button to respond to the appropriate event
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
    public <T> void update(T t, FitnessAppCommands key) {

    }
}
