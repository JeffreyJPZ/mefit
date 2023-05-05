package ui;

import model.Profile;

import javax.swing.*;
import java.awt.event.ActionEvent;

import static java.lang.Integer.parseInt;
import static ui.FitnessAppCommands.*;

// Represents a panel for adding profiles for the fitness application
public class AddProfilePanel extends FitnessPanel {
    private static final String NAME_COMMAND = "Name";
    private static final String GENDER_COMMAND = "Gender";
    private static final String AGE_COMMAND = "Age (yrs)";
    private static final String WEIGHT_COMMAND = "Weight (lbs)";

    private ProfilesPanel profilesPanel;

    private JTextField name;
    private JTextField age;
    private JTextField gender;
    private JTextField weight;
    private JButton addProfileButton;
    private JButton backButton;

    // EFFECTS: makes a panel for adding a new profile
    public AddProfilePanel(ProfilesPanel profilesPanel) {
        super();
        initializeFields(profilesPanel);
        initializePlacements();
        initializeActions();
        addComponents();
    }

    // MODIFIES: this
    // EFFECTS: initializes the components of the add profile panel
    private void initializeFields(ProfilesPanel profilesPanel) {
        this.profilesPanel = profilesPanel;

        this.name = new JTextField("John");
        this.gender = new JTextField("Example");
        this.age = new JTextField("30");
        this.weight = new JTextField("225");
        this.addProfileButton = new JButton(ADD_PROFILE_COMMAND.getFitnessAppCommand());
        this.backButton = new JButton(BACK_COMMAND.getFitnessAppCommand());

        addDisplayComponents();
    }

    @Override
    // MODIFIES: this
    // EFFECTS: adds each component to be displayed to components
    protected void addDisplayComponents() {
        components.add(new JLabel(NAME_COMMAND));
        components.add(name);
        components.add(new JLabel(GENDER_COMMAND));
        components.add(gender);
        components.add(new JLabel(AGE_COMMAND));
        components.add(age);
        components.add(new JLabel(WEIGHT_COMMAND));
        components.add(weight);
        components.add(addProfileButton);
        components.add(backButton);
    }

    @Override
    // MODIFIES: this
    // EFFECTS: sets the appropriate components to respond to appropriate events
    protected void initializeActions() {
        initializeAction(addProfileButton, ADD_PROFILE_COMMAND.getFitnessAppCommand());
        initializeAction(backButton, BACK_COMMAND.getFitnessAppCommand());
    }

    // MODIFIES: profilesPanel, fitnessApp
    // EFFECTS: handles the appropriate event for each component
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(ADD_PROFILE_COMMAND.getFitnessAppCommand())) {
            addProfile();
        } else if (e.getActionCommand().equals(BACK_COMMAND.getFitnessAppCommand())) {
            profilesPanel();
        }
    }

    // MODIFIES: profilesPanel, fitnessApp
    // EFFECTS: adds a profile with given inputs to the profiles and switches to the profiles panel
    private void addProfile() {
        Profile profile = new Profile(name.getText(), gender.getText(),
                parseInt(age.getText()), parseInt(weight.getText()));

        profilesPanel.addProfile(profile);
        profilesPanel.updateProfiles();

        FitnessApp.getInstance().switchPanel(PROFILES_COMMAND.getFitnessAppCommand());
    }

    // MODIFIES: fitnessApp
    // EFFECTS: switches to the profiles panel
    private void profilesPanel() {
        FitnessApp.getInstance().switchPanel(PROFILES_COMMAND.getFitnessAppCommand());
    }
}
