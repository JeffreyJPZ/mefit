package ui.gui.app.workouts.addworkout;

import model.metrics.Difficulty;
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
import static ui.gui.app.FitnessAppCommands.BACK_COMMAND;

// Represents a panel for generating a workout with random exercises
public class WorkoutGeneratorPanel extends AddToCollectionPanel {
    private static final String WORKOUT_NAME_LABEL = "Name";
    private static final String WORKOUT_MUSCLE_GROUP_LABEL = "Muscle Group Focus";
    private static final String WORKOUT_DIFFICULTY_LABEL = "Difficulty Average";
    private static final String WORKOUT_TIME_LABEL = "Time (min)";
    private static final String WORKOUT_FAVOURITE_LABEL = "Favourite?";
    private static final String WORKOUT_SIZE_LABEL = "# of Exercises in Workout";
    private static final String WORKOUT_SAMPLE_SIZE_LABEL = "# of Exercises as Sample Size";

    private WorkoutGeneratorPanelPresenter workoutGeneratorPanelPresenter;

    private Map<String, JComboBox<String>> inputBoxes;

    private JTextField name;
    private JComboBox<String> selectDifficulty;
    private JComboBox<String> selectMuscleGroup;
    private JComboBox<String> selectFavourite;
    private JTextField timeMinutes;
    private JTextField size;
    private JTextField sampleSize;

    // EFFECTS: creates a new panel for generating workouts
    public WorkoutGeneratorPanel() {
        super();
        initializeFields();
        addDisplayComponents();
        initializePlacements();
        initializeActions();
        addComponents();
    }

    // MODIFIES: this
    // EFFECTS: initializes the components of the panel for generating a workout
    protected void initializeFields() {
        super.initializeFields();

        this.workoutGeneratorPanelPresenter = new WorkoutGeneratorPanelPresenter(this);

        this.inputBoxes = new HashMap<>();

        this.splashText = new JLabel("Enter your desired workout parameters");

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
        this.name = new JTextField("Example Name");;
        this.timeMinutes = new JTextField("0");
        this.size = new JTextField("0");
        this.sampleSize = new JTextField("0");
    }

    // MODIFIES: this
    // EFFECTS: initializes the dropdown boxes
    private void initializeBoxes() {
        this.selectDifficulty = new JComboBox<>();
        this.selectMuscleGroup = new JComboBox<>();
        this.selectFavourite = new JComboBox<>();

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
        inputTextFields.put(JsonKeys.WORKOUT_NAME.getKey(), name);
        inputTextFields.put(JsonKeys.RANDOM_WORKOUT_MAX_TIME.getKey(), timeMinutes);
        inputTextFields.put(JsonKeys.RANDOM_WORKOUT_SIZE.getKey(), size);
        inputTextFields.put(JsonKeys.RANDOM_WORKOUT_SAMPLE_SIZE.getKey(), sampleSize);
    }

    // MODIFIES: this
    // EFFECTS: collects the input boxes for convenient parsing
    private void collectBoxes() {
        inputBoxes.put(JsonKeys.RANDOM_WORKOUT_MUSCLE_GROUP_FOCUS.getKey(), selectMuscleGroup);
        inputBoxes.put(JsonKeys.RANDOM_WORKOUT_DIFFICULTY_MODE.getKey(), selectDifficulty);
        inputBoxes.put(JsonKeys.WORKOUT_FAVOURITE.getKey(), selectFavourite);
    }

    @Override
    // MODIFIES: this
    // EFFECTS: adds each component to be displayed to components
    protected void addDisplayComponents() {
        components.add(new JLabel("Random Workout Generator"));
        components.add(new JLabel(WORKOUT_NAME_LABEL));
        components.add(name);
        components.add(new JLabel(WORKOUT_MUSCLE_GROUP_LABEL));
        components.add(selectMuscleGroup);
        components.add(new JLabel(WORKOUT_DIFFICULTY_LABEL));
        components.add(selectDifficulty);
        components.add(new JLabel(WORKOUT_FAVOURITE_LABEL));
        components.add(selectFavourite);
        components.add(new JLabel(WORKOUT_TIME_LABEL));
        components.add(timeMinutes);
        components.add(new JLabel(WORKOUT_SIZE_LABEL));
        components.add(size);
        components.add(new JLabel(WORKOUT_SAMPLE_SIZE_LABEL));
        components.add(sampleSize);
        components.add(splashText);
        components.add(addButton);
        components.add(backButton);
    }

    // MODIFIES: workoutsPanelPresenter, fitnessApp
    // EFFECTS: handles the appropriate event for appropriate components
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(ADD_COMMAND.getFitnessAppCommand())) {
            addWorkout();
        } else if (e.getActionCommand().equals(BACK_COMMAND.getFitnessAppCommand())) {
            back();
        }
    }

    // REQUIRES: all inputs have valid input types
    // MODIFIES: workoutsPanelPresenter, fitnessApp
    // EFFECTS: adds a workout with given inputs to the workout collection, updates and switches to workout panel
    private void addWorkout() {
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

        workoutGeneratorPanelPresenter.update(data, ADD_WORKOUT_COMMAND);

        back();
    }

    // MODIFIES: fitnessApp
    // EFFECTS: switches to the workouts panel
    private void back() {
        workoutGeneratorPanelPresenter.update(null, BACK_COMMAND);
    }

    // EFFECTS: returns the presenter for this panel
    @Override
    public Presenter getPresenter() {
        return workoutGeneratorPanelPresenter;
    }
}
