package ui;

import org.json.JSONObject;

import javax.swing.*;
import java.awt.event.ActionEvent;

import static ui.FitnessAppCommands.*;

// Represents a panel for adding profiles for the fitness application
public class AddProfilePanel extends AddToCollectionPanel {
    private static final String NAME_COMMAND = "Name";
    private static final String GENDER_COMMAND = "Gender";
    private static final String AGE_COMMAND = "Age (yrs)";
    private static final String WEIGHT_COMMAND = "Weight (lbs)";

    private AddProfilePanelPresenter addProfilePanelPresenter;

    private JTextField name;
    private JTextField age;
    private JTextField gender;
    private JTextField weight;

    // EFFECTS: makes a panel for adding a new profile
    public AddProfilePanel() {
        super();
        initializeFields();
        initializePlacements();
        initializeActions();
        addDisplayComponents();
        addComponents();
    }

    // MODIFIES: this
    // EFFECTS: initializes the components of the add profile panel
    protected void initializeFields() {
        super.initializeFields();

        this.addProfilePanelPresenter = new AddProfilePanelPresenter();

        initializeInputs();
    }

    // MODIFIES: this
    // EFFECTS: initializes the text fields and adds them to the collection
    @Override
    protected void initializeInputs() {
        initializeTextFields();
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
        components.add(new JLabel(NAME_COMMAND));
        components.add(name);
        components.add(new JLabel(GENDER_COMMAND));
        components.add(gender);
        components.add(new JLabel(AGE_COMMAND));
        components.add(age);
        components.add(new JLabel(WEIGHT_COMMAND));
        components.add(weight);
        components.add(addButton);
        components.add(backButton);
    }

    // MODIFIES: profilesPanel, fitnessApp
    // EFFECTS: handles the appropriate event for each component
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(ADD_COMMAND.getFitnessAppCommand())) {
            addProfile();
        } else if (e.getActionCommand().equals(BACK_COMMAND.getFitnessAppCommand())) {
            back();
        }
    }

    // MODIFIES: profilesPanel, fitnessApp
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
