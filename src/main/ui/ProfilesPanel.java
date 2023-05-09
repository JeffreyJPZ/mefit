package ui;

import exceptions.InvalidExerciseException;
import model.Profile;
import model.ProfilesById;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import static ui.FitnessAppCommands.*;

// Represents a panel with profiles for the fitness application
public class ProfilesPanel extends FitnessPanel {
    private static final String PROFILE_ID = "ID";
    private static final String PROFILE_NAME = "Name";
    private static final int PROFILE_ID_POSITION = 0; // column for all profile ids

    private static final List<String> PROFILES_COLUMN_NAMES = Arrays.asList(PROFILE_ID, PROFILE_NAME);
    private static final Vector<String> PROFILE_COLUMN_NAMES_VECTOR = new Vector<>(PROFILES_COLUMN_NAMES);

    private static final String SAVE_PROFILES = "Save Profiles";
    private static final String LOAD_PROFILES = "Load Profiles";
    private static final String PATH = "./data/fitnessapp.json";
    private static final String WELCOME_TEXT = "Welcome to the application!";

    private ProfilePanel profilePanel;

    private ProfilesById profilesById;
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;

    private JTable profilesDataTable;
    private JScrollPane profilesScrollableTable;
    private Vector<Vector<Object>> profilesData;
    private DefaultTableModel tableModel;

    private JLabel splashText;
    private JButton viewProfileButton;
    private JButton addProfileButton;
    private JButton removeProfileButton;
    private JButton saveButton;
    private JButton loadButton;
    private JButton backButton;

    // EFFECTS: creates a profiles panel
    public ProfilesPanel(ProfilePanel profilePanel) {
        super();
        initializeFields(profilePanel);
        initializePlacements();
        initializeActions();
        addComponents();
    }

    // MODIFIES: this
    // EFFECTS: initializes the components for the profiles panel
    private void initializeFields(ProfilePanel profilePanel) {
        this.profilePanel = profilePanel;

        this.profilesById = new ProfilesById();
        this.jsonReader = new JsonReader(PATH);
        this.jsonWriter = new JsonWriter(PATH);

        this.profilesData = new Vector<>();

        extractProfilesData();

        this.tableModel = new DefaultTableModel(profilesData, PROFILE_COLUMN_NAMES_VECTOR);
        this.profilesDataTable = new JTable(tableModel);

        this.profilesScrollableTable = new JScrollPane(profilesDataTable,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        this.splashText = new JLabel(WELCOME_TEXT);
        this.viewProfileButton = new JButton(PROFILE_COMMAND.getFitnessAppCommand());
        this.addProfileButton = new JButton(ADD_PROFILE_COMMAND.getFitnessAppCommand());
        this.removeProfileButton = new JButton(REMOVE_PROFILE_COMMAND.getFitnessAppCommand());
        this.saveButton = new JButton(SAVE_PROFILES);
        this.loadButton = new JButton(LOAD_PROFILES);
        this.backButton = new JButton(BACK_COMMAND.getFitnessAppCommand());

        addDisplayComponents();
    }

    @Override
    // MODIFIES: this
    // EFFECTS: adds each component to be displayed to components
    protected void addDisplayComponents() {
        components.add(profilesScrollableTable);
        components.add(splashText);
        components.add(viewProfileButton);
        components.add(addProfileButton);
        components.add(removeProfileButton);
        components.add(saveButton);
        components.add(loadButton);
        components.add(backButton);
    }

    // MODIFIES: this
    // EFFECTS: extracts profile information from each profile in profiles
    private void extractProfilesData() {
        for (Profile profile : profilesById.getProfiles().values()) {
            Vector<Object> profileData = new Vector<>();

            profileData.add(profile.getId());
            profileData.add(profile.getName());

            profilesData.add(profileData);
        }
    }

    @Override
    // MODIFIES: this
    // EFFECTS: sets the appropriate components to respond to appropriate events
    protected void initializeActions() {
        initializeAction(viewProfileButton, PROFILE_COMMAND.getFitnessAppCommand());
        initializeAction(addProfileButton, ADD_PROFILE_COMMAND.getFitnessAppCommand());
        initializeAction(removeProfileButton, REMOVE_PROFILE_COMMAND.getFitnessAppCommand());
        initializeAction(saveButton, SAVE_PROFILES);
        initializeAction(loadButton, LOAD_PROFILES);
        initializeAction(backButton, BACK_COMMAND.getFitnessAppCommand());
    }

    // MODIFIES: this, fitnessApp, profilePanel
    // EFFECTS: handles the appropriate event for each component
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(PROFILE_COMMAND.getFitnessAppCommand())) {
            profilePanel();
        } else if (e.getActionCommand().equals(ADD_PROFILE_COMMAND.getFitnessAppCommand())) {
            addProfilePanel();
        } else if (e.getActionCommand().equals(REMOVE_PROFILE_COMMAND.getFitnessAppCommand())) {
            removeSelectedProfile();
        } else if (e.getActionCommand().equals(SAVE_PROFILES)) {
            saveProfiles();
        } else if (e.getActionCommand().equals(LOAD_PROFILES)) {
            loadProfiles();
        } else if (e.getActionCommand().equals(BACK_COMMAND.getFitnessAppCommand())) {
            previousPanel();
        }
    }

    // REQUIRES: selected profile is not null
    // MODIFIES: this, profilesPanel, fitnessApp
    // EFFECTS: switches to the profile panel for the selected profile, if more than one profile is selected
    //          indicates only one selection should be made
    private void profilePanel() {
        int id = getIdFromSelectedProfile();

        if (profilesDataTable.getSelectedRowCount() > 1) {
            splashText.setText("Please select one profile only.");
            return;
        }

        profilePanel.setProfile(profilesById.getProfile(id));
        profilePanel.updateTable();

        splashText.setText(WELCOME_TEXT);

        FitnessApp.getInstance().switchPanel(PROFILE_COMMAND.getFitnessAppCommand());
    }

    // REQUIRES: selected profile is not null
    // EFFECTS: returns the id associated with the selected profile
    private int getIdFromSelectedProfile() {
        int selectedProfile = profilesDataTable.getSelectedRow();

        return (int) profilesDataTable.getValueAt(selectedProfile, PROFILE_ID_POSITION);
    }

    // MODIFIES: fitnessApp
    // EFFECTS: switches to the panel for adding a profile
    private void addProfilePanel() {
        FitnessApp.getInstance().switchPanel(ADD_PROFILE_COMMAND.getFitnessAppCommand());
    }

    // REQUIRES: selected profile is not null
    // MODIFIES: this
    // EFFECTS: removes the selected profile from the display
    private void removeSelectedProfile() {
        int id = getIdFromSelectedProfile();

        profilesById.removeProfile(id);
        updateProfiles();
    }

    // MODIFIES: this
    // EFFECTS: saves profiles to file
    //          indicates success if saving was successful, otherwise indicates failure
    private void saveProfiles() {
        try {
            saveProfilesToFile();
            splashText.setText("Successfully saved to: " + PATH + "\n");
        } catch (FileNotFoundException exception) {
            splashText.setText("Could not load from: " + PATH + "\n");
        }
    }

    // MODIFIES: this
    // EFFECTS: loads profiles from file and updates the display of profiles
    //          indicates success if loading was successful, otherwise indicates failure
    private void loadProfiles() {
        try {
            loadProfilesFromFile();
            splashText.setText("Successfully loaded from: " + PATH + "\n");
        } catch (InvalidExerciseException exception) {
            splashText.setText("Invalid exercise type encountered in: " + PATH + "\n");
        } catch (IOException exception) {
            splashText.setText("Could not load from: " + PATH + "\n");
        }

        updateProfiles();
    }

    // MODIFIES: this
    // EFFECTS: updates the display of profiles
    public void updateProfiles() {
        profilesData.clear();
        extractProfilesData();
        tableModel.setDataVector(profilesData, PROFILE_COLUMN_NAMES_VECTOR);
        profilesDataTable.setModel(tableModel);
        profilesScrollableTable.setViewportView(profilesDataTable);
    }

    // MODIFIES: this, fitnessApp
    // EFFECTS: returns to the previous panel to the profiles panel
    private void previousPanel() {
        splashText.setText(WELCOME_TEXT);
        FitnessApp.getInstance().switchPanel(HOME_COMMAND.getFitnessAppCommand());
    }

    // MODIFIES: this
    // EFFECTS: writes profiles to file with given path,
    //          throws FileNotFoundException if file path does not exist or cannot be accessed
    public void saveProfilesToFile() throws FileNotFoundException {
        jsonWriter.open();
        jsonWriter.write(profilesById);
        jsonWriter.close();
    }

    // MODIFIES: this
    // EFFECTS: reads profiles from file and indicates success if successfully read,
    //          throws InvalidExerciseException if an exercise type is invalid,
    //          throws IOException if an error occurs in input or output
    public void loadProfilesFromFile() throws InvalidExerciseException, IOException {
        profilesById = jsonReader.read();
    }

    // MODIFIES: this
    // EFFECTS: adds the given profile to the profiles
    public void addProfile(Profile profile) {
        profilesById.addProfile(profile);
    }
}
