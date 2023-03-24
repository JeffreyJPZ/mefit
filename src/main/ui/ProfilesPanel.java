package ui;

import exceptions.InvalidExerciseException;
import model.Profile;
import model.ProfilesById;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import static ui.FitnessAppCommands.*;

// Represents the panel with profiles for the fitness application
public class ProfilesPanel extends JPanel implements ActionListener {
    private static final String SAVE_PROFILES = "Save Profiles";
    private static final String LOAD_PROFILES = "Load Profiles";
    private static final String PATH = "./data/fitnessapp.json";
    private static final String WELCOME_TEXT = "Welcome to the application!";

    private FitnessApp fitnessApp;
    private ProfilePanel profilePanel;

    private ProfilesById profilesById;
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;

    private JLabel splashText;
    private JButton viewProfileButton;
    private JButton addProfileButton;
    private JButton deleteProfileButton;
    private JButton saveButton;
    private JButton loadButton;
    private JButton backButton;
    private JComboBox<Integer> profiles;

    // EFFECTS: Creates the profiles panel
    public ProfilesPanel(FitnessApp fitnessApp, ProfilePanel profilePanel) {
        initializeFields(fitnessApp, profilePanel);
        initializePlacements();
        initializeActions();
        addComponents();
    }

    // MODIFIES: this
    // EFFECTS: initializes the components for the profiles panel
    private void initializeFields(FitnessApp fitnessApp, ProfilePanel profilePanel) {
        this.fitnessApp = fitnessApp;
        this.profilePanel = profilePanel;

        this.profilesById = new ProfilesById();
        this.jsonReader = new JsonReader(PATH);
        this.jsonWriter = new JsonWriter(PATH);

        this.splashText = new JLabel(WELCOME_TEXT);
        this.viewProfileButton = new JButton(PROFILE_COMMAND.getFitnessAppCommand());
        this.addProfileButton = new JButton(ADD_PROFILE_COMMAND.getFitnessAppCommand());
        this.deleteProfileButton = new JButton(DELETE_PROFILE_COMMAND.getFitnessAppCommand());
        this.saveButton = new JButton(SAVE_PROFILES);
        this.loadButton = new JButton(LOAD_PROFILES);
        this.backButton = new JButton(BACK_COMMAND.getFitnessAppCommand());
        this.profiles = new JComboBox<>();
    }

    // MODIFIES: this
    // EFFECTS: sets the placement of the components for the profiles panel
    private void initializePlacements() {
        profiles.setAlignmentX(Component.CENTER_ALIGNMENT);
        splashText.setAlignmentX(Component.CENTER_ALIGNMENT);
        viewProfileButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        addProfileButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        deleteProfileButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    // MODIFIES: this
    // EFFECTS: sets the components to respond to appropriate events
    private void initializeActions() {
        viewProfileButton.setActionCommand(PROFILE_COMMAND.getFitnessAppCommand());
        viewProfileButton.addActionListener(this);

        addProfileButton.setActionCommand(ADD_PROFILE_COMMAND.getFitnessAppCommand());
        addProfileButton.addActionListener(this);

        deleteProfileButton.setActionCommand(DELETE_PROFILE_COMMAND.getFitnessAppCommand());
        deleteProfileButton.addActionListener(this);

        saveButton.setActionCommand(SAVE_PROFILES);
        saveButton.addActionListener(this);

        loadButton.setActionCommand(LOAD_PROFILES);
        loadButton.addActionListener(this);

        backButton.setActionCommand(BACK_COMMAND.getFitnessAppCommand());
        backButton.addActionListener(this);
    }

    // MODIFIES: this
    // EFFECTS: adds the appropriate components to the profiles panel
    private void addComponents() {
        add(Box.createVerticalGlue());
        add(profiles);
        add(Box.createVerticalGlue());
        add(splashText);
        add(Box.createVerticalGlue());
        add(viewProfileButton);
        add(Box.createVerticalGlue());
        add(addProfileButton);
        add(Box.createVerticalGlue());
        add(deleteProfileButton);
        add(Box.createVerticalGlue());
        add(saveButton);
        add(Box.createVerticalGlue());
        add(loadButton);
        add(Box.createVerticalGlue());
        add(backButton);
        add(Box.createVerticalGlue());
    }

    // MODIFIES: this, fitnessApp, profilePanel
    // EFFECTS: handles the appropriate event for each component
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(PROFILE_COMMAND.getFitnessAppCommand())) {
            profilePanel();
        } else if (e.getActionCommand().equals(ADD_PROFILE_COMMAND.getFitnessAppCommand())) {
            addProfilePanel();
        } else if (e.getActionCommand().equals(DELETE_PROFILE_COMMAND.getFitnessAppCommand())) {
            deleteProfile();
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
    // EFFECTS: switches to the profile panel for the selected profile
    private void profilePanel() {
        int id = (int) profiles.getSelectedItem();

        profilePanel.setProfile(profilesById.getProfile(id));
        profilePanel.updateTable();

        splashText.setText(WELCOME_TEXT);

        fitnessApp.switchPanel(PROFILE_COMMAND.getFitnessAppCommand());
    }

    // MODIFIES: fitnessApp
    // EFFECTS: switches to the panel for adding a profile
    private void addProfilePanel() {
        fitnessApp.switchPanel(ADD_PROFILE_COMMAND.getFitnessAppCommand());
    }

    // REQUIRES: selected profile is not null
    // MODIFIES: this
    // EFFECTS: deletes the selected profile from the display
    private void deleteProfile() {
        int id = (int) profiles.getSelectedItem();
        profilesById.removeProfile(id);

        profiles.removeItem(id);
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
    private void updateProfiles() {
        profiles.removeAllItems();

        for (Integer i : profilesById.getProfiles().keySet()) {
            profiles.addItem(i);
        }
    }

    // MODIFIES: this, fitnessApp
    // EFFECTS: returns to the previous panel to the profiles panel
    private void previousPanel() {
        splashText.setText(WELCOME_TEXT);
        fitnessApp.switchPanel(HOME_COMMAND.getFitnessAppCommand());
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

    // MODIFIES: this
    // EFFECTS: adds the given profile to the display of profiles
    public void addProfileToMenu(Profile profile) {
        profiles.addItem(profile.getId());
    }

    public JComboBox<Integer> getProfiles() {
        return profiles;
    }
}
