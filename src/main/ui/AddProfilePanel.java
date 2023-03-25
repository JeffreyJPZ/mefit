package ui;

import model.Profile;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.lang.Integer.parseInt;
import static ui.FitnessAppCommands.*;

// Represents the panel for adding profiles for the fitness application
public class AddProfilePanel extends JPanel implements ActionListener {
    private static final String NAME_COMMAND = "Name";
    private static final String GENDER_COMMAND = "Gender";
    private static final String AGE_COMMAND = "Age (yrs)";
    private static final String WEIGHT_COMMAND = "Weight (lbs)";

    private FitnessApp fitnessApp;
    private ProfilesPanel profilesPanel;

    private JTextField name;
    private JTextField age;
    private JTextField gender;
    private JTextField weight;
    private JButton addProfileButton;
    private JButton backButton;

    public AddProfilePanel(FitnessApp fitnessApp, ProfilesPanel profilesPanel) {
        initializeFields(fitnessApp, profilesPanel);
        initializePlacements();
        initializeActions();
        addComponents();
    }

    // MODIFIES: this
    // EFFECTS: initializes the components of the add profile panel
    private void initializeFields(FitnessApp fitnessApp, ProfilesPanel profilesPanel) {
        this.fitnessApp = fitnessApp;
        this.profilesPanel = profilesPanel;

        this.name = new JTextField("John");
        this.gender = new JTextField("Example");
        this.age = new JTextField("30");
        this.weight = new JTextField("225");
        this.addProfileButton = new JButton(ADD_PROFILE_COMMAND.getFitnessAppCommand());
        this.backButton = new JButton(BACK_COMMAND.getFitnessAppCommand());
    }

    // MODIFIES: this
    // EFFECTS: sets the placement of components for the add profile panel
    private void initializePlacements() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    // MODIFIES: this
    // EFFECTS: sets the components to respond to appropriate events
    private void initializeActions() {
        addProfileButton.setActionCommand(ADD_PROFILE_COMMAND.getFitnessAppCommand());
        addProfileButton.addActionListener(this);

        backButton.setActionCommand(BACK_COMMAND.getFitnessAppCommand());
        backButton.addActionListener(this);
    }

    // MODIFIES: this
    // EFFECTS: adds the components to the profile panel
    private void addComponents() {
        add(Box.createVerticalGlue());
        add(new JLabel(NAME_COMMAND));
        add(Box.createVerticalGlue());
        add(name);
        add(Box.createVerticalGlue());
        add(new JLabel(GENDER_COMMAND));
        add(Box.createVerticalGlue());
        add(gender);
        add(Box.createVerticalGlue());
        add(new JLabel(AGE_COMMAND));
        add(Box.createVerticalGlue());
        add(age);
        add(Box.createVerticalGlue());
        add(new JLabel(WEIGHT_COMMAND));
        add(Box.createVerticalGlue());
        add(weight);
        add(Box.createVerticalGlue());
        add(addProfileButton);
        add(Box.createVerticalGlue());
        add(backButton);
        add(Box.createVerticalGlue());
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

        fitnessApp.switchPanel(PROFILES_COMMAND.getFitnessAppCommand());
    }

    // MODIFIES: fitnessApp
    // EFFECTS: switches to the profiles panel
    private void profilesPanel() {
        fitnessApp.switchPanel(PROFILES_COMMAND.getFitnessAppCommand());
    }
}
