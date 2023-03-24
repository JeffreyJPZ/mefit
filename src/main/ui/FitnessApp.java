package ui;

import javax.swing.*;
import java.awt.*;

import static ui.FitnessAppCommands.*;

// Represents a graphical interface for exercise application
// Credit to SimpleDrawingPlayer and https://stackoverflow.com/questions/6175899/how-to-change-card-layout-panels-from-another-panel
public class FitnessApp extends JFrame {
    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;

    private JPanel panels;
    private CardLayout layout;

    // EFFECTS: runs the application and creates a new window
    public FitnessApp() {
        super("Fitness Application");
        initializeFields();
        initializeWindow();
        initializePanels();

        add(panels);

        pack();
    }

    // MODIFIES: this
    // EFFECTS: initializes the appropriate components for the window
    private void initializeFields() {
        this.panels = new JPanel();
        this.layout = new CardLayout();
    }

    // MODIFIES: this
    // EFFECTS: displays the window with the appropriate default size and layout
    private void initializeWindow() {
        panels.setLayout(layout);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: creates the panels for the application
    private void initializePanels() {
        HomePanel homePanel = new HomePanel(this);
        ExercisesPanel exercisesPanel = new ExercisesPanel(this);
        AddExercisePanel addExercisePanel = new AddExercisePanel(this, exercisesPanel);
        ProfilePanel profilePanel = new ProfilePanel(this, exercisesPanel);
        ProfilesPanel profilesPanel = new ProfilesPanel(this, profilePanel);
        AddProfilePanel addProfilePanel = new AddProfilePanel(this, profilesPanel);

        panels.add(homePanel, HOME_COMMAND.getFitnessAppCommand());
        panels.add(profilesPanel, PROFILES_COMMAND.getFitnessAppCommand());
        panels.add(addProfilePanel, ADD_PROFILE_COMMAND.getFitnessAppCommand());
        panels.add(profilePanel, PROFILE_COMMAND.getFitnessAppCommand());
        panels.add(exercisesPanel, EXERCISES_COMMAND.getFitnessAppCommand());
        panels.add(addExercisePanel, ADD_EXERCISE_COMMAND.getFitnessAppCommand());
    }

    // REQUIRES: name matches a valid command
    // MODIFIES: this
    // EFFECTS: switches the current panel to the panel with the given name
    public void switchPanel(String name) {
        layout.show(panels, name);
    }
}
