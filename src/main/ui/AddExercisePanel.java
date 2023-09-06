package ui;

import model.*;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

import static ui.FitnessAppCommands.*;

// Represents a panel for adding exercises for the fitness application
public class AddExercisePanel extends AddToCollectionPanel {
    private static final ExerciseType[] exerciseTypes = ExerciseType.values();

    private AddExercisePanelPresenter addExercisePanelPresenter;

    private Map<String, JComboBox<String>> inputBoxes;

    private JTextField name;
    private JComboBox<String> selectType;
    private JComboBox<String> selectDifficulty;
    private JComboBox<String> selectMuscleGroup;
    private JTextField time;
    private JTextField weight;
    private JTextField sets;
    private JTextField reps;
    private JTextField distance;

    // EFFECTS: creates a panel for adding exercises
    public AddExercisePanel() {
        super();
        initializeFields();
        initializePlacements();
        initializeActions();
        addDisplayComponents();
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

    // EFFECTS: initializes the input components and adds them to the appropriate collection
    @Override
    protected void initializeInputs() {
        initializeTextFields();
        initializeBoxes();
    }

    // MODIFIES: this
    // EFFECTS: initializes the text fields and adds them to the collection
    private void initializeTextFields() {
        this.name = new JTextField("Example Name");

        this.time = new JTextField("0");

        this.weight = new JTextField("Weight: Edit only for weights exercises");
        this.sets = new JTextField("Sets: Edit only for weights and bodyweights exercises");
        this.reps = new JTextField("Reps: Edit only for weights and bodyweights exercises");
        this.distance = new JTextField("Distance: Edit only for cardio exercises");

        inputTextFields.put("name", name);
        inputTextFields.put("time", time);
        inputTextFields.put("weight", weight);
        inputTextFields.put("sets", sets);
        inputTextFields.put("reps", reps);
        inputTextFields.put("distance", distance);
    }

    // MODIFIES: this
    // EFFECTS: initializes the boxes and adds them to the collection
    private void initializeBoxes() {
        this.selectType = new JComboBox<>();
        this.selectDifficulty = new JComboBox<>();
        this.selectMuscleGroup = new JComboBox<>();

        for (Difficulty difficulty : Difficulty.values()) {
            this.selectDifficulty.addItem(Integer.toString(difficulty.getDifficulty()));
        }

        for (MuscleGroup muscleGroup : MuscleGroup.values()) {
            this.selectMuscleGroup.addItem(muscleGroup.getMuscleGroup());
        }

        for (ExerciseType exerciseType : exerciseTypes) {
            this.selectType.addItem(exerciseType.getType());
        }

        inputBoxes.put("selectType", selectType);
        inputBoxes.put("selectDifficulty", selectDifficulty);
        inputBoxes.put("selectMuscleGroup", selectMuscleGroup);
    }

    @Override
    // MODIFIES: this
    // EFFECTS: adds each component to be displayed to components
    protected void addDisplayComponents() {
        components.add(new JLabel("Name"));
        components.add(name);
        components.add(new JLabel("Exercise Type"));
        components.add(selectType);
        components.add(new JLabel("Difficulty"));
        components.add(selectDifficulty);
        components.add(new JLabel("Muscle Group"));
        components.add(selectMuscleGroup);
        components.add(new JLabel("Time (min)"));
        components.add(time);
        components.add(new JLabel("Weight (lbs)"));
        components.add(weight);
        components.add(new JLabel("Sets"));
        components.add(sets);
        components.add(new JLabel("Reps"));
        components.add(reps);
        components.add(new JLabel("Distance (m)"));
        components.add(distance);
        components.add(addButton);
        components.add(backButton);
    }

    // MODIFIES: exercisesPanel, fitnessApp
    // EFFECTS: handles the appropriate event for each component
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(ADD_COMMAND.getFitnessAppCommand())) {
            addExercise();
        } else if (e.getActionCommand().equals(BACK_COMMAND.getFitnessAppCommand())) {
            back();
        }
    }

    // REQUIRES: selected exercise type, muscle group, and difficulty are not null
    // MODIFIES: addExerciseModel, fitnessApp
    // EFFECTS: adds an exercise with given inputs to exercises, updates and switches to exercise panel
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

        // sends data from input components to model
        addExercisePanelPresenter.update(data, ADD_EXERCISE_COMMAND);

        back();
    }

    // MODIFIES: fitnessApp
    // EFFECTS: switches to the exercises panel
    private void back() {
        addExercisePanelPresenter.update(null, BACK_COMMAND);
    }

    // EFFECTS: returns the model for this panel
    @Override
    public Presenter getPresenter() {
        return addExercisePanelPresenter;
    }
}
