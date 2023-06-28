package ui;

import exceptions.InvalidExerciseException;
import model.Profile;
import model.ProfilesById;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

import static ui.FitnessAppCommands.*;

// Represents a panel with profiles for the fitness application
public class ProfilesPanel extends DisplayCollectionPanel implements UIObserver {
    private static final String PROFILE_ID = "ID";
    private static final String PROFILE_NAME = "Name";

    private static final String SAVE_PROFILES = "Save Profiles";
    private static final String LOAD_PROFILES = "Load Profiles";
    private static final String PATH = "./data/fitnessapp.json";
    private static final String WELCOME_TEXT = "Welcome to the application!";

    private ProfilesById profilesById;
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;

    private JLabel splashText;
    private JButton saveButton;
    private JButton loadButton;

    // EFFECTS: creates a profiles panel
    public ProfilesPanel() {
        super();
        initializeFields();
        initializeActions();
        addDisplayComponents();
        addComponents();
        initializePlacements();
    }

    // MODIFIES: this
    // EFFECTS: initializes the components for the profiles panel
    protected void initializeFields() {
        super.initializeFields();

        this.info = List.of(PROFILE_ID, PROFILE_NAME);
        this.infoHeader = new Vector<>(info);
        this.filterable = List.of(PROFILE_NAME);

        this.profilesById = new ProfilesById();
        this.jsonReader = new JsonReader(PATH);
        this.jsonWriter = new JsonWriter(PATH);

        this.data = new Vector<>();

        extractProfilesData();

        this.tableModel = new DefaultTableModel(data, infoHeader);
        this.dataTable = new JTable(tableModel);

        this.scrollableDataTable = new JScrollPane(dataTable,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        this.splashText = new JLabel(WELCOME_TEXT);
        this.saveButton = new JButton(SAVE_PROFILES);
        this.loadButton = new JButton(LOAD_PROFILES);
    }

    @Override
    protected void initializePlacements() {
        super.initializePlacements();

        scrollableDataTable.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        scrollableDataTable.setVisible(true);

        inputFilter.setMaximumSize(new Dimension(WIDTH, 10));
    }

    @Override
    // MODIFIES: this
    // EFFECTS: adds each component to be displayed to components
    protected void addDisplayComponents() {
        super.addDisplayComponents();

        components.add(0, splashText);
        components.add(0, scrollableDataTable);
        components.add(components.size() - 1, saveButton);
        components.add(components.size() - 1, loadButton);
    }

    // MODIFIES: this
    // EFFECTS: extracts profile information from each profile in profiles
    private void extractProfilesData() {
        for (Profile profile : profilesById.getProfiles().values()) {
            Vector<Object> profileData = new Vector<>();

            profileData.add(profile.getId());
            profileData.add(profile.getName());

            data.add(profileData);
        }
    }

    @Override
    // MODIFIES: this
    // EFFECTS: sets the appropriate components to respond to appropriate events
    protected void initializeActions() {
        super.initializeActions();

        initializeAction(viewButton, VIEW_COMMAND.getFitnessAppCommand());
        initializeAction(saveButton, SAVE_PROFILES);
        initializeAction(loadButton, LOAD_PROFILES);
    }

    // MODIFIES: this, fitnessApp, profilePanel
    // EFFECTS: handles the appropriate event for each component
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(VIEW_COMMAND.getFitnessAppCommand())) {
            profilePanel();
        } else if (e.getActionCommand().equals(ADD_COMMAND.getFitnessAppCommand())) {
            addProfilePanel();
        } else if (e.getActionCommand().equals(REMOVE_COMMAND.getFitnessAppCommand())) {
            removeSelectedProfile();
        } else if (e.getActionCommand().equals(SAVE_PROFILES)) {
            saveProfiles();
        } else if (e.getActionCommand().equals(LOAD_PROFILES)) {
            loadProfiles();
        } else if (e.getActionCommand().equals(BACK_COMMAND.getFitnessAppCommand())) {
            back();
        }
    }

    // REQUIRES: selected profile is not null
    // MODIFIES: this, profilesPanel, fitnessApp
    // EFFECTS: switches to the profile panel for the selected profile, if more than one profile is selected
    //          indicates only one selection should be made
    private void profilePanel() {
        int id = getIdFromSelectedProfile();

        if (dataTable.getSelectedRowCount() > 1) {
            splashText.setText("Please select one profile only.");
            return;
        }

        notifyAll(profilesById.getProfile(id), PROFILES_COMMAND);

        splashText.setText(WELCOME_TEXT);

        FitnessApp.getInstance().switchPanel(PROFILE_COMMAND.getFitnessAppCommand());
    }

    // REQUIRES: selected profile is not null
    // EFFECTS: returns the id associated with the selected profile
    private int getIdFromSelectedProfile() {
        int selectedProfile = dataTable.getSelectedRow();

        return (int) dataTable.getValueAt(selectedProfile, ID_POSITION);
    }

    // MODIFIES: this
    // EFFECTS: adds the given profile to profiles
    private void addProfile(Profile profile) {
        profilesById.addProfile(profile);
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
        updateDisplayCollection();
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

        updateDisplayCollection();
    }

    // MODIFIES: this, fitnessApp
    // EFFECTS: returns to the previous panel to the profiles panel
    private void back() {
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

    @Override
    // MODIFIES: this
    // EFFECTS: updates the exercises display
    protected void updateDisplayCollection() {
        data.clear();
        extractProfilesData();
        tableModel.setDataVector(data, infoHeader);
        dataTable.setModel(tableModel);
        scrollableDataTable.setViewportView(dataTable);
    }

    @Override
    // MODIFIES: this
    // EFFECTS: updates profiles panel with t if key is a match
    public <T> void update(T t, FitnessAppCommands key) {
        if (key.getFitnessAppCommand().equals(ADD_COMMAND.getFitnessAppCommand())) {
            Profile profile = (Profile) t;
            addProfile(profile);
        }
        updateDisplayCollection();
    }
}
