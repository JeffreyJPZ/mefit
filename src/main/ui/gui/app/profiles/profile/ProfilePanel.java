package ui.gui.app.profiles.profile;

import model.profiles.profile.Profile;
import org.json.JSONObject;
import ui.gui.logic.displayelement.DisplayElementPanel;
import ui.gui.json.JsonKeys;
import ui.gui.logic.Presenter;

import javax.swing.*;
import java.awt.event.ActionEvent;

import static ui.gui.app.FitnessAppCommands.*;

// Represents a profile panel for the fitness application
public class ProfilePanel extends DisplayElementPanel {
    private static final String PROFILE_NAME_LABEL = "Name";
    private static final String PROFILE_GENDER_LABEL = "Gender";
    private static final String PROFILE_AGE_LABEL = "Age (yrs)";
    private static final String PROFILE_WEIGHT_LABEL = "Weight (lbs)";

    private ProfilePanelPresenter profilePanelPresenter;

    private JTextField name;
    private JTextField ageYears;
    private JTextField gender;
    private JTextField weightPounds;
    private JButton exercisesButton;
    private JButton workoutsButton;

    // EFFECTS: creates a profile panel
    public ProfilePanel() {
        super();
        initializeFields();
        addDisplayComponents();
        initializePlacements();
        initializeActions();
        addComponents();
    }

    // MODIFIES: this
    // EFFECTS: initializes the components for the profile panel
    protected void initializeFields() {
        super.initializeFields();

        this.profilePanelPresenter = new ProfilePanelPresenter(this);

        this.exercisesButton = new JButton(EXERCISES_COMMAND.getFitnessAppCommand());
        this.workoutsButton = new JButton(WORKOUTS_COMMAND.getFitnessAppCommand());

        initializeInputs();

    }

    // MODIFIES: this
    // EFFECTS: initializes the input areas for the user for the profile panel
    @Override
    protected void initializeInputs() {
        initializeTextFields();
        collectTextFields();
    }

    // MODIFIES: this
    // EFFECTS: initializes the text fields
    private void initializeTextFields() {
        this.name = new JTextField("John");
        this.gender = new JTextField("Example");
        this.ageYears = new JTextField("30");
        this.weightPounds = new JTextField("225");
    }

    // MODIFIES: this
    // EFFECTS: collects the text fields for convenient parsing
    private void collectTextFields() {
        inputTextFields.put(JsonKeys.PROFILE_NAME.getKey(), name);
        inputTextFields.put(JsonKeys.PROFILE_GENDER.getKey(), gender);
        inputTextFields.put(JsonKeys.PROFILE_AGE.getKey(), ageYears);
        inputTextFields.put(JsonKeys.PROFILE_WEIGHT.getKey(), weightPounds);
    }

    @Override
    // MODIFIES: this
    // EFFECTS: adds each component to be displayed to components
    protected void addDisplayComponents() {
        components.add(new JLabel("Profile"));
        components.add(new JLabel(PROFILE_NAME_LABEL));
        components.add(name);
        components.add(new JLabel(PROFILE_GENDER_LABEL));
        components.add(gender);
        components.add(new JLabel(PROFILE_AGE_LABEL));
        components.add(ageYears);
        components.add(new JLabel(PROFILE_WEIGHT_LABEL));
        components.add(weightPounds);
        components.add(splashText);
        components.add(editButton);
        components.add(exercisesButton);
        components.add(workoutsButton);
        components.add(backButton);
    }

    @Override
    // MODIFIES: this
    // EFFECTS: sets the appropriate components to respond to appropriate events
    protected void initializeActions() {
        super.initializeActions();

        initializeAction(exercisesButton, EXERCISES_COMMAND.getFitnessAppCommand());
        initializeAction(workoutsButton, WORKOUTS_COMMAND.getFitnessAppCommand());
    }

    @Override
    // MODIFIES: exercisesPanel, fitnessApp
    // EFFECTS: handles the appropriate event for appropriate components
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(EDIT_COMMAND.getFitnessAppCommand())) {
            editProfile();
        } else if (e.getActionCommand().equals(EXERCISES_COMMAND.getFitnessAppCommand())) {
            exercisesPanel();
        } else if (e.getActionCommand().equals(WORKOUTS_COMMAND.getFitnessAppCommand())) {
            workoutsPanel();
        } else if (e.getActionCommand().equals(BACK_COMMAND.getFitnessAppCommand())) {
            back();
        }
    }

    // MODIFIES: profilePanelPresenter
    // EFFECTS: updates the profile info of the profile associated with the panel
    private void editProfile() {
        JSONObject data = new JSONObject();
        JSONObject inputs = new JSONObject();
        JSONObject inputTextFieldsJson = new JSONObject();

        for (String name : inputTextFields.keySet()) {
            JTextField field = inputTextFields.get(name);
            inputTextFieldsJson.put(name, field.getText());
        }

        inputs.put(JsonKeys.INPUT.getKey(), inputTextFieldsJson);
        data.put(JsonKeys.DATA.getKey(), inputs);

        profilePanelPresenter.update(data, EDIT_COMMAND);
    }

    // MODIFIES: exercisesPanel, fitnessApp
    // EFFECTS: updates and switches to the exercises panel for the current profile
    private void exercisesPanel() {
        splashText.setText("");
        profilePanelPresenter.update(null, EXERCISES_COMMAND);
    }

    // MODIFIES: workoutsPanel, fitnessApp
    // EFFECTS: updates and switches to the workouts panel for the current profile
    private void workoutsPanel() {
        profilePanelPresenter.update(null, WORKOUTS_COMMAND);
    }

    // EFFECTS: switches to the profiles panel
    private void back() {
        profilePanelPresenter.update(null, BACK_COMMAND);
    }

    // MODIFIES: this
    // EFFECTS: updates the inputs with the given profile info
    void updateInputs(Profile profile) {
        inputTextFields.get(JsonKeys.PROFILE_NAME.getKey()).setText(profile.getName());
        inputTextFields.get(JsonKeys.PROFILE_GENDER.getKey()).setText(profile.getGender());
        inputTextFields.get(JsonKeys.PROFILE_AGE.getKey()).setText(Integer.toString(profile.getAgeYears()));
        inputTextFields.get(JsonKeys.PROFILE_WEIGHT.getKey()).setText(Integer.toString(profile.getWeightPounds()));
    }

    // EFFECTS: returns the presenter for this profile panel
    @Override
    public Presenter getPresenter() {
        return profilePanelPresenter;
    }
}
