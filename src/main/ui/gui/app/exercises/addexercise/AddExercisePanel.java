package ui.gui.app.exercises.addexercise;

import model.metrics.Difficulty;
import model.metrics.ExerciseType;
import model.metrics.MuscleGroup;
import org.json.JSONObject;
import ui.gui.json.JsonKeys;
import ui.gui.logic.Presenter;
import ui.gui.logic.addtocollection.AddToCollectionPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

import static ui.gui.app.FitnessAppCommands.*;

// Represents a panel for adding exercises for the fitness application
public class AddExercisePanel extends AddToCollectionPanel {
    private AddExercisePanelPresenter addExercisePanelPresenter;

    private Map<String, JComboBox<String>> inputBoxes;

    private JTextField name;
    private JComboBox<String> selectType;
    private JComboBox<String> selectDifficulty;
    private JComboBox<String> selectMuscleGroup;
    private JComboBox<String> selectFavourite;
    private JTextField timeMinutes;
    private JTextField weightPounds;
    private JTextField sets;
    private JTextField reps;
    private JTextField distance;

    // EFFECTS: creates a panel for adding exercises
    public AddExercisePanel() {
        super();
        initializeFields();
        addDisplayComponents();
        initializePlacements();
        initializeActions();
        addComponents();
    }

    // MODIFIES: this
    // EFFECTS: initializes the components of the panel for adding exercises
    protected void initializeFields() {
        super.initializeFields();

        this.addExercisePanelPresenter = new AddExercisePanelPresenter();

        this.inputBoxes = new HashMap<>();

        initializeInputs();
    }

    // MODIFIES: this
    // EFFECTS: initializes the input components and adds them to the appropriate collection
    @Override
    protected void initializeInputs() {
        initializeTextFields();
        collectTextFields();
        initializeBoxes();
        collectBoxes();
    }

    // MODIFIES: this
    // EFFECTS: initializes the text fields
    private void initializeTextFields() {
        this.name = new JTextField("Example Name");

        this.timeMinutes = new JTextField("0");

        this.weightPounds = new JTextField("Weight: Edit only for weights exercises");
        this.sets = new JTextField("Sets: Edit only for weights and bodyweights exercises");
        this.reps = new JTextField("Reps: Edit only for weights and bodyweights exercises");
        this.distance = new JTextField("Distance: Edit only for cardio exercises");
    }

    // MODIFIES: this
    // EFFECTS: initializes the dropdown boxes
    private void initializeBoxes() {
        this.selectType = new JComboBox<>();
        this.selectDifficulty = new JComboBox<>();
        this.selectMuscleGroup = new JComboBox<>();
        this.selectFavourite = new JComboBox<>();

        for (ExerciseType exerciseType : ExerciseType.values()) {
            selectType.addItem(exerciseType.getType());
        }

        for (Difficulty difficulty : Difficulty.values()) {
            selectDifficulty.addItem(Integer.toString(difficulty.getDifficultyAsInt()));
        }

        for (MuscleGroup muscleGroup : MuscleGroup.values()) {
            selectMuscleGroup.addItem(muscleGroup.getMuscleGroupAsString());
        }

        selectFavourite.addItem(Boolean.toString(false));
        selectFavourite.addItem(Boolean.toString(true));
    }

    // MODIFIES: this
    // EFFECTS: collects the text fields for convenient parsing
    private void collectTextFields() {
        inputTextFields.put("name", name);
        inputTextFields.put("time", timeMinutes);
        inputTextFields.put("weight", weightPounds);
        inputTextFields.put("sets", sets);
        inputTextFields.put("reps", reps);
        inputTextFields.put("distance", distance);
    }

    // MODIFIES: this
    // EFFECTS: collects the input boxes for convenient parsing
    private void collectBoxes() {
        inputBoxes.put("selectType", selectType);
        inputBoxes.put("selectDifficulty", selectDifficulty);
        inputBoxes.put("selectMuscleGroup", selectMuscleGroup);
        inputBoxes.put("selectFavourite", selectFavourite);
    }

    @Override
    // MODIFIES: this
    // EFFECTS: adds each component to be displayed to components
    protected void addDisplayComponents() {
        components.add(new JLabel("Add New Exercise"));
        components.add(new JLabel("Exercise Name"));
        components.add(name);
        components.add(new JLabel("Exercise Type"));
        components.add(selectType);
        components.add(new JLabel("Difficulty"));
        components.add(selectDifficulty);
        components.add(new JLabel("Muscle Group"));
        components.add(selectMuscleGroup);
        components.add(new JLabel("Favourite?"));
        components.add(selectFavourite);
        components.add(new JLabel("Time (min)"));
        components.add(timeMinutes);
        components.add(new JLabel("Weight (lbs)"));
        components.add(weightPounds);
        components.add(new JLabel("Sets"));
        components.add(sets);
        components.add(new JLabel("Reps"));
        components.add(reps);
        components.add(new JLabel("Distance (m)"));
        components.add(distance);
        components.add(addButton);
        components.add(backButton);
    }

    // MODIFIES: exercisesPanelPresenter, fitnessApp
    // EFFECTS: handles the appropriate event for appropriate components
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(ADD_COMMAND.getFitnessAppCommand())) {
            addExercise();
        } else if (e.getActionCommand().equals(BACK_COMMAND.getFitnessAppCommand())) {
            back();
        }
    }

    // REQUIRES: all inputs have valid input types
    // MODIFIES: exercisesPanelPresenter, fitnessApp
    // EFFECTS: adds an exercise with given inputs to the exercise collection, updates and switches to exercise panel
    private void addExercise() {
        JSONObject data = new JSONObject();
        JSONObject inputs = new JSONObject();
        JSONObject inputTextFieldsJson = new JSONObject();
        JSONObject inputBoxesJson = new JSONObject();

        for (String name : inputTextFields.keySet()) {
            JTextField field = inputTextFields.get(name);
            inputTextFieldsJson.put(name, field.getText());
        }

        for (String name : inputBoxes.keySet()) {
            JComboBox<String> box = inputBoxes.get(name);
            inputBoxesJson.put(name, box.getSelectedItem());
        }

        inputs.put(JsonKeys.FIELDS.getKey(), inputTextFieldsJson);
        inputs.put(JsonKeys.BOXES.getKey(), inputBoxesJson);

        data.put(JsonKeys.DATA.getKey(), inputs);

        addExercisePanelPresenter.update(data, ADD_EXERCISE_COMMAND);

        back();
    }

    // MODIFIES: fitnessApp
    // EFFECTS: switches to the exercises panel
    private void back() {
        addExercisePanelPresenter.update(null, BACK_COMMAND);
    }

    // EFFECTS: returns the presenter for this panel
    @Override
    public Presenter getPresenter() {
        return addExercisePanelPresenter;
    }
}
