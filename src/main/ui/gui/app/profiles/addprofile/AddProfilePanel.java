package ui.gui.app.profiles.addprofile;

import org.json.JSONObject;
import ui.gui.json.JsonKeys;
import ui.gui.logic.Presenter;
import ui.gui.logic.addtocollection.AddToCollectionPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;

import static ui.gui.app.FitnessAppCommands.*;

// Represents a panel for adding profiles for the fitness application
public class AddProfilePanel extends AddToCollectionPanel {
    private static final String PROFILE_NAME = "Profile Name";
    private static final String PROFILE_GENDER = "Gender";
    private static final String PROFILE_AGE = "Age (yrs)";
    private static final String PROFILE_WEIGHT = "Weight (lbs)";

    private AddProfilePanelPresenter addProfilePanelPresenter;

    private JTextField name;
    private JTextField age;
    private JTextField gender;
    private JTextField weight;

    // EFFECTS: makes a panel for adding a new profile
    public AddProfilePanel() {
        super();
        initializeFields();
        addDisplayComponents();
        initializePlacements();
        initializeActions();
        addComponents();
    }

    // MODIFIES: this
    // EFFECTS: initializes the components of the add profile panel
    protected void initializeFields() {
        super.initializeFields();

        this.addProfilePanelPresenter = new AddProfilePanelPresenter(this);

        initializeInputs();
    }

    // MODIFIES: this
    // EFFECTS: initializes the text fields and adds them to the collection
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
        this.age = new JTextField("30");
        this.weight = new JTextField("225");
    }

    // MODIFIES: this
    // EFFECTS: collects the text fields for convenient parsing
    private void collectTextFields() {
        inputTextFields.put(JsonKeys.PROFILE_NAME.getKey(), name);
        inputTextFields.put(JsonKeys.PROFILE_GENDER.getKey(), gender);
        inputTextFields.put(JsonKeys.PROFILE_AGE.getKey(), age);
        inputTextFields.put(JsonKeys.PROFILE_WEIGHT.getKey(), weight);
    }

    @Override
    // MODIFIES: this
    // EFFECTS: adds each component to be displayed to components
    protected void addDisplayComponents() {
        components.add(new JLabel("Add New Profile"));
        components.add(new JLabel(PROFILE_NAME));
        components.add(name);
        components.add(new JLabel(PROFILE_GENDER));
        components.add(gender);
        components.add(new JLabel(PROFILE_AGE));
        components.add(age);
        components.add(new JLabel(PROFILE_WEIGHT));
        components.add(weight);
        components.add(addButton);
        components.add(backButton);
    }

    // MODIFIES: profilesPanelPresenter, fitnessApp
    // EFFECTS: handles the appropriate event for appropriate components
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(ADD_COMMAND.getFitnessAppCommand())) {
            addProfile();
        } else if (e.getActionCommand().equals(BACK_COMMAND.getFitnessAppCommand())) {
            back();
        }
    }

    // REQUIRES: all inputs have valid input types
    // MODIFIES: profilesPanelPresenter, fitnessApp
    // EFFECTS: adds a profile with given inputs to the profiles and switches to the profiles panel
    private void addProfile() {
        JSONObject data = new JSONObject();
        JSONObject inputs = new JSONObject();
        JSONObject inputTextFieldsJson = new JSONObject();

        for (String name : inputTextFields.keySet()) {
            JTextField field = inputTextFields.get(name);
            inputTextFieldsJson.put(name, field.getText());
        }

        inputs.put(JsonKeys.FIELDS.getKey(), inputTextFieldsJson);
        data.put(JsonKeys.DATA.getKey(), inputs);

        addProfilePanelPresenter.update(data, ADD_PROFILE_COMMAND);

        back();
    }

    // MODIFIES: fitnessApp
    // EFFECTS: switches to the profiles panel
    private void back() {
        addProfilePanelPresenter.update(null, BACK_COMMAND);
    }

    @Override
    public Presenter getPresenter() {
        return addProfilePanelPresenter;
    }
}
