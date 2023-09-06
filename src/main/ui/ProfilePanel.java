package ui;

import model.Profile;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.event.ActionEvent;

import static ui.FitnessAppCommands.*;

// Represents a profile panel for the fitness application
public class ProfilePanel extends DisplayElementPanel {
    private static final String PROFILE_NAME = "Name";
    private static final String PROFILE_GENDER = "Gender";
    private static final String PROFILE_AGE = "Age (yrs)";
    private static final String PROFILE_WEIGHT = "Weight (lbs)";

    private ProfilePanelPresenter profilePanelPresenter;

    private JTextField name;
    private JTextField age;
    private JTextField gender;
    private JTextField weight;
    private JButton exercisesButton;

    // EFFECTS: creates a profile panel
    public ProfilePanel() {
        super();
        initializeFields();
        initializePlacements();
        initializeActions();
        addDisplayComponents();
        addComponents();
    }

    // MODIFIES: this
    // EFFECTS: initializes the components for the profile panel
    protected void initializeFields() {
        super.initializeFields();

        this.profilePanelPresenter = new ProfilePanelPresenter(this);

        initializeInputs();

        this.exercisesButton = new JButton(EXERCISES_COMMAND.getFitnessAppCommand());
    }

    // MODIFIES: this
    // EFFECTS: initializes the text fields and adds them to the collection
    private void initializeTextFields() {
        this.name = new JTextField("John");
        this.gender = new JTextField("Example");
        this.age = new JTextField("30");
        this.weight = new JTextField("225");

        inputTextFields.put("name", name);
        inputTextFields.put("gender", gender);
        inputTextFields.put("age", age);
        inputTextFields.put("weight", weight);
    }

    @Override
    // MODIFIES: this
    // EFFECTS: adds each component to be displayed to components
    protected void addDisplayComponents() {
        components.add(new JLabel(PROFILE_NAME));
        components.add(name);
        components.add(new JLabel(PROFILE_GENDER));
        components.add(gender);
        components.add(new JLabel(PROFILE_AGE));
        components.add(age);
        components.add(new JLabel(PROFILE_WEIGHT));
        components.add(weight);
        components.add(editButton);
        components.add(exercisesButton);
        components.add(backButton);
    }

    @Override
    // MODIFIES: this
    // EFFECTS: sets the appropriate components to respond to appropriate events
    protected void initializeActions() {
        super.initializeActions();
        initializeAction(exercisesButton, EXERCISES_COMMAND.getFitnessAppCommand());
    }

    // EFFECTS: initializes the inputs for the profile panel
    @Override
    protected void initializeInputs() {
        initializeTextFields();
    }

    // MODIFIES: exercisesPanel, fitnessApp
    // EFFECTS: handles the appropriate event for each component
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(EDIT_COMMAND.getFitnessAppCommand())) {
            editProfile();
        } else if (e.getActionCommand().equals(EXERCISES_COMMAND.getFitnessAppCommand())) {
            exercisesPanel();
        } else if (e.getActionCommand().equals(BACK_COMMAND.getFitnessAppCommand())) {
            back();
        }
    }

    // MODIFIES: exercisesPanel, fitnessApp
    // EFFECTS: updates and switches to the exercises panel for the current profile
    private void exercisesPanel() {
        profilePanelPresenter.update(null, EXERCISES_COMMAND);
    }

    // EFFECTS: switches to the profiles panel
    private void back() {
        profilePanelPresenter.update(null, BACK_COMMAND);
    }

    // MODIFIES: this, profilePanelPresenter
    // EFFECTS: updates the fields with the profile info associated with the panel
    private void editProfile() {
        updateProfile();
        updateFields();
    }

    // MODIFIES: this
    // EFFECTS: updates the display of the current profile
    private void updateProfile() {
        JSONObject data = new JSONObject();
        JSONObject profileInfo = new JSONObject();

        for (String name : inputTextFields.keySet()) {
            JTextField field = inputTextFields.get(name);
            profileInfo.put(name, field.getText());
        }

        data.put(JsonKeys.DATA.getKey(), profileInfo);

        profilePanelPresenter.update(data, EDIT_COMMAND);
    }

    // MODIFIES: this
    // EFFECTS: updates the fields with the profile info associated with the panel
    public void updateFields() {
        Profile profile = profilePanelPresenter.getProfile();

        name.setText(profile.getName());
        gender.setText(profile.getGender());
        age.setText(Integer.toString(profile.getAge()));
        weight.setText(Integer.toString(profile.getWeight()));
    }

    // EFFECTS: returns the presenter for this profile panel
    @Override
    public Presenter getPresenter() {
        return profilePanelPresenter;
    }
}
