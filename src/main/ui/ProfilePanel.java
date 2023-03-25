package ui;

import model.Profile;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.List;
import java.util.Vector;

import static ui.FitnessAppCommands.*;

// Represents the profile panel for the fitness application
public class ProfilePanel extends JPanel implements ActionListener {
    private static final List<String> PROFILE_INFO_COLUMN_NAMES = List.of("ID", "Name", "Gender", "Age", "Weight");
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

    // EFFECTS: creates the profile panel
    public ProfilePanel(FitnessApp fitnessApp, ExercisesPanel exercisesPanel) {
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
    }

    // MODIFIES: this
    // EFFECTS: sets the placement of the components for the profile panel
    private void initializePlacements() {
        exercisesButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    // MODIFIES: this
    // EFFECTS: sets the components to respond to appropriate events
    private void initializeActions() {
        exercisesButton.setActionCommand(EXERCISES_COMMAND.getFitnessAppCommand());
        exercisesButton.addActionListener(this);

        backButton.setActionCommand(BACK_COMMAND.getFitnessAppCommand());
        backButton.addActionListener(this);
    }

    // MODIFIES: this
    // EFFECTS: adds the components to the profile panel
    private void addComponents() {
        add(Box.createVerticalGlue());
        add(profileInfoHeader);
        add(profileInfoTable);
        add(Box.createVerticalGlue());
        add(exercisesButton);
        add(Box.createVerticalGlue());
        add(backButton);
        add(Box.createVerticalGlue());
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
        profileInfoDataVector.add(profile.getGender());

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
