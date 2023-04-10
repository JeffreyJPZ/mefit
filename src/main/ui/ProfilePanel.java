package ui;

import model.Profile;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.event.ActionEvent;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import static ui.FitnessAppCommands.*;

// Represents a profile panel for the fitness application
public class ProfilePanel extends FitnessPanel {
    private static final String PROFILE_ID = "ID";
    private static final String PROFILE_NAME = "Name";
    private static final String PROFILE_GENDER = "Gender";
    private static final String PROFILE_AGE = "Age (yrs)";
    private static final String PROFILE_WEIGHT = "Weight (lbs)";
    private static final List<String> PROFILE_INFO_COLUMN_NAMES = Arrays.asList(PROFILE_ID, PROFILE_NAME,
            PROFILE_GENDER, PROFILE_AGE, PROFILE_WEIGHT);
    private static final Vector<String> PROFILE_INFO_COLUMN_NAMES_VECTOR = new Vector<>(PROFILE_INFO_COLUMN_NAMES);

    private ExercisesPanel exercisesPanel;
    private FitnessApp fitnessApp;

    private Profile profile;

    private Vector<Vector<Object>> profileInfoData;
    private DefaultTableModel tableModel;
    private JTableHeader profileInfoHeader;
    private JTable profileInfoTable;

    private JButton exercisesButton;
    private JButton backButton;

    // EFFECTS: creates a profile panel
    public ProfilePanel(FitnessApp fitnessApp, ExercisesPanel exercisesPanel) {
        super();
        initializeFields(fitnessApp, exercisesPanel);
        initializePlacements();
        initializeActions();
        addComponents();
    }

    // MODIFIES: this
    // EFFECTS: initializes the components for the profile panel
    private void initializeFields(FitnessApp fitnessApp, ExercisesPanel exercisesPanel) {
        this.fitnessApp = fitnessApp;
        this.exercisesPanel = exercisesPanel;

        this.profile = new Profile("Test", "Test", 0, 0); // initialize sample profile
        this.profileInfoData = new Vector<>();

        extractProfileInfo();

        this.tableModel = new DefaultTableModel(profileInfoData, PROFILE_INFO_COLUMN_NAMES_VECTOR);
        this.profileInfoTable = new JTable(tableModel);
        this.profileInfoHeader = profileInfoTable.getTableHeader();

        this.exercisesButton = new JButton(EXERCISES_COMMAND.getFitnessAppCommand());
        this.backButton = new JButton(BACK_COMMAND.getFitnessAppCommand());

        addDisplayComponents();
    }

    @Override
    // MODIFIES: this
    // EFFECTS: adds each component to be displayed to components
    protected void addDisplayComponents() {
        components.add(profileInfoTable);
        components.add(exercisesButton);
        components.add(backButton);
    }

    @Override
    // MODIFIES: this
    // EFFECTS: adds each component to be displayed to the profile panel
    protected void addComponents() {
        add(Box.createVerticalGlue());
        add(profileInfoHeader);
        super.addComponents();
    }

    @Override
    // MODIFIES: this
    // EFFECTS: sets the appropriate components to respond to appropriate events
    protected void initializeActions() {
        initializeAction(exercisesButton, EXERCISES_COMMAND.getFitnessAppCommand());
        initializeAction(backButton, BACK_COMMAND.getFitnessAppCommand());
    }

    // MODIFIES: exercisesPanel, fitnessApp
    // EFFECTS: handles the appropriate event for each component
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(EXERCISES_COMMAND.getFitnessAppCommand())) {
            exercisesPanel();
        } else if (e.getActionCommand().equals(BACK_COMMAND.getFitnessAppCommand())) {
            profilesPanel();
        }
    }

    // MODIFIES: exercisesPanel, fitnessApp
    // EFFECTS: updates and switches to the exercises panel for the current profile
    private void exercisesPanel() {
        exercisesPanel.setExercises(profile.getExercises());
        exercisesPanel.updateTable();
        fitnessApp.switchPanel(EXERCISES_COMMAND.getFitnessAppCommand());
    }

    // MODIFIES: fitnessApp
    // EFFECTS: switches to the profiles panel
    private void profilesPanel() {
        fitnessApp.switchPanel(PROFILES_COMMAND.getFitnessAppCommand());
    }

    // MODIFIES: this
    // EFFECTS: extracts the profile info from a profile
    private void extractProfileInfo() {
        Vector<Object> profileInfoDataVector = new Vector<>();

        profileInfoDataVector.add(profile.getId());
        profileInfoDataVector.add(profile.getName());
        profileInfoDataVector.add(profile.getGender());
        profileInfoDataVector.add(profile.getAge());
        profileInfoDataVector.add(profile.getWeight());

        profileInfoData.add(profileInfoDataVector);
    }

    // MODIFIES: this
    // EFFECTS: sets the profile for the profile panel to the given profile
    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    // MODIFIES: this
    // EFFECTS: updates the display of the current profile
    public void updateTable() {
        profileInfoData.clear();
        extractProfileInfo();
        tableModel.setDataVector(profileInfoData, PROFILE_INFO_COLUMN_NAMES_VECTOR);
        profileInfoTable.setModel(tableModel);
    }
}
