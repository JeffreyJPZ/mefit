package ui;

import model.Event;
import model.EventLog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import static ui.FitnessAppCommands.*;

// Represents a graphical interface for the exercise application
public class FitnessApp extends JFrame implements WindowListener {
    private static FitnessApp fitnessApp = new FitnessApp();
    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;

    private JPanel panels;
    private CardLayout layout;

    // EFFECTS: runs the application and creates a new window
    private FitnessApp() {
        super("Fitness Application");
        initializeFields();
        initializeWindow();
        initializePanels();

        add(panels);
        addWindowListener(this);

        pack();
    }

    // EFFECTS: returns the instance of the fitness application
    public static FitnessApp getInstance() {
        return fitnessApp;
    }

    // MODIFIES: fitnessApp
    // EFFECTS: initializes the appropriate components for the window
    private void initializeFields() {
        this.panels = new JPanel();
        this.layout = new CardLayout();
    }

    // MODIFIES: fitnessApp
    // EFFECTS: displays the window with the appropriate default size and layout
    private void initializeWindow() {
        panels.setLayout(layout);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    // MODIFIES: fitnessApp
    // EFFECTS: creates the panels for the application
    private void initializePanels() {
        View homePanel = new HomePanel();
        View profilesPanel = new ProfilesPanel();
        View profilePanel = new ProfilePanel();
        View addProfilePanel = new AddProfilePanel();
        View exercisesPanel = new ExercisesPanel();
        View exercisePanel = new ExercisePanel();
        View addExercisePanel = new AddExercisePanel();

        profilesPanel.getPresenter().addObserver(profilePanel.getPresenter());
        profilePanel.getPresenter().addObserver(exercisesPanel.getPresenter());
        addProfilePanel.getPresenter().addObserver(profilesPanel.getPresenter());
        exercisesPanel.getPresenter().addObserver(exercisePanel.getPresenter());
        exercisePanel.getPresenter().addObserver(exercisesPanel.getPresenter());
        addExercisePanel.getPresenter().addObserver(exercisesPanel.getPresenter());

        panels.add(homePanel, HOME_COMMAND.getFitnessAppCommand());
        panels.add(profilesPanel, PROFILES_COMMAND.getFitnessAppCommand());
        panels.add(profilePanel, PROFILE_COMMAND.getFitnessAppCommand());
        panels.add(addProfilePanel, ADD_PROFILE_COMMAND.getFitnessAppCommand());
        panels.add(exercisesPanel, EXERCISES_COMMAND.getFitnessAppCommand());
        panels.add(exercisePanel, EXERCISE_COMMAND.getFitnessAppCommand());
        panels.add(addExercisePanel, ADD_EXERCISE_COMMAND.getFitnessAppCommand());
    }

    // REQUIRES: name matches a valid command
    // MODIFIES: this
    // EFFECTS: switches the current panel to the panel with the given name
    public void switchPanel(String name) {
        layout.show(panels, name);
    }

    // EFFECTS: does nothing when window is opened
    @Override
    public void windowOpened(WindowEvent e) {

    }

    // EFFECTS: does nothing when window is closing
    @Override
    public void windowClosing(WindowEvent e) {

    }

    // EFFECTS: prints all logged events to console
    @Override
    public void windowClosed(WindowEvent e) {
        for (Event event : EventLog.getInstance()) {
            System.out.println(event.toString());
        }
    }

    // EFFECTS: does nothing when window is minimized
    @Override
    public void windowIconified(WindowEvent e) {

    }

    // EFFECTS: does nothing when window is maximized
    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    // EFFECTS: does nothing when window gains focus
    @Override
    public void windowActivated(WindowEvent e) {

    }

    // EFFECTS: does nothing when window loses focus
    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
