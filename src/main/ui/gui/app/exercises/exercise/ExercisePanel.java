package ui.gui.app.exercises.exercise;

import model.exercises.exercise.*;
import model.metrics.Difficulty;
import model.metrics.ExerciseType;
import model.metrics.MuscleGroup;
import org.json.JSONObject;
import ui.gui.logic.displayelement.DisplayElementPanel;
import ui.gui.logic.Presenter;
import ui.gui.app.FitnessAppCommands;
import ui.gui.json.JsonKeys;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

import static ui.gui.app.FitnessAppCommands.*;

// Represents a panel for displaying an exercise
public class ExercisePanel extends DisplayElementPanel {
    private static final String EXERCISE_NAME_LABEL = "Name";
    private static final String EXERCISE_MUSCLE_GROUP_LABEL = "Muscle Group";
    private static final String EXERCISE_DIFFICULTY_LABEL = "Difficulty";
    private static final String EXERCISE_TIME_LABEL = "Time (min)";
    private static final String EXERCISE_FAVOURITE_LABEL = "Favourite?";
    private static final String EXERCISE_WEIGHT_LABEL = "Weight (lbs)";
    private static final String EXERCISE_SETS_LABEL = "Sets";
    private static final String EXERCISE_REPS_LABEL = "Reps";
    private static final String EXERCISE_DISTANCE_LABEL = "Distance (m)";

    private ExercisePanelPresenter exercisePanelPresenter;

    private Map<String, JComboBox<String>> inputBoxes;

    private JLabel name;
    private JLabel exerciseType;
    private JComboBox<String> selectDifficulty;
    private JComboBox<String> selectMuscleGroup;
    private JComboBox<String> selectFavourite;
    private JTextField timeMinutes;
    private JTextField weightPounds;
    private JTextField sets;
    private JTextField reps;
    private JTextField distance;

    // EFFECTS: makes a new exercise panel
    public ExercisePanel() {
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

        this.exercisePanelPresenter = new ExercisePanelPresenter(this);

        this.inputBoxes = new HashMap<>();

        this.name = new JLabel("Example Name");
        this.exerciseType = new JLabel("Sample Type");

        initializeInputs();
    }

    // MODIFIES: this
    // EFFECTS: initializes the input areas for the user for the exercise panel
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
        this.timeMinutes = new JTextField("0");

        this.weightPounds = new JTextField("Weight: Edit only for weights exercises");
        this.sets = new JTextField("Sets: Edit only for weights and bodyweights exercises");
        this.reps = new JTextField("Reps: Edit only for weights and bodyweights exercises");
        this.distance = new JTextField("Distance: Edit only for cardio exercises");
    }

    // MODIFIES: this
    // EFFECTS: collects the text fields for convenient parsing
    private void collectTextFields() {
        inputTextFields.put(JsonKeys.EXERCISE_TIME.getKey(), timeMinutes);
        inputTextFields.put(JsonKeys.EXERCISE_WEIGHT.getKey(), weightPounds);
        inputTextFields.put(JsonKeys.EXERCISE_SETS.getKey(), sets);
        inputTextFields.put(JsonKeys.EXERCISE_REPS.getKey(), reps);
        inputTextFields.put(JsonKeys.EXERCISE_DISTANCE.getKey(), distance);
    }

    // MODIFIES: this
    // EFFECTS: initializes the input boxes
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
    // EFFECTS: collects the input boxes for convenient parsing
    private void collectBoxes() {
        inputBoxes.put(JsonKeys.EXERCISE_DIFFICULTY.getKey(), selectDifficulty);
        inputBoxes.put(JsonKeys.EXERCISE_MUSCLE_GROUP.getKey(), selectMuscleGroup);
        inputBoxes.put(JsonKeys.EXERCISE_FAVOURITE.getKey(), selectFavourite);
    }

    @Override
    // MODIFIES: this
    // EFFECTS: stores the components of the exercise panel for convenient access
    protected void addDisplayComponents() {
        components.add(exerciseType);
        components.add(new JLabel(EXERCISE_NAME_LABEL));
        components.add(name);
        components.add(new JLabel(EXERCISE_MUSCLE_GROUP_LABEL));
        components.add(selectMuscleGroup);
        components.add(new JLabel(EXERCISE_DIFFICULTY_LABEL));
        components.add(selectDifficulty);
        components.add(new JLabel(EXERCISE_FAVOURITE_LABEL));
        components.add(selectFavourite);
        components.add(new JLabel(EXERCISE_TIME_LABEL));
        components.add(timeMinutes);
        components.add(new JLabel(EXERCISE_WEIGHT_LABEL));
        components.add(weightPounds);
        components.add(new JLabel(EXERCISE_SETS_LABEL));
        components.add(sets);
        components.add(new JLabel(EXERCISE_REPS_LABEL));
        components.add(reps);
        components.add(new JLabel(EXERCISE_DISTANCE_LABEL));
        components.add(distance);
        components.add(splashText);
        components.add(editButton);
        components.add(backButton);
    }

    @Override
    // MODIFIES: this, exercisePanelPresenter, fitnessApp
    // EFFECTS: handles the appropriate event for appropriate components
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(FitnessAppCommands.EDIT_COMMAND.getFitnessAppCommand())) {
            editExercise();
        } else if (e.getActionCommand().equals(FitnessAppCommands.BACK_COMMAND.getFitnessAppCommand())) {
            back();
        }
    }

    // MODIFIES: exercisePanelPresenter
    // EFFECTS: updates exercise info in the exercise associated with the panel
    private void editExercise() {
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

        inputs.put(JsonKeys.EXERCISE_TYPE.getKey(), exerciseType.getText());
        inputs.put(JsonKeys.FIELDS.getKey(), inputTextFieldsJson);
        inputs.put(JsonKeys.BOXES.getKey(), inputBoxesJson);

        data.put(JsonKeys.DATA.getKey(), inputs);

        exercisePanelPresenter.update(data, EDIT_COMMAND);
    }

    // MODIFIES: fitnessApp
    // EFFECTS: switches to the exercises panel
    private void back() {
        exercisePanelPresenter.update(null, BACK_COMMAND);
    }

    // MODIFIES: this
    // EFFECTS: sets the exercise type shown for the exercise
    public void setExerciseType(ExerciseType exerciseType) {
        this.exerciseType.setText(exerciseType.getType());
    }

    // MODIFIES: this
    // EFFECTS: sets the visibility of inputs based on the given exercise type
    void setInputVisibility(ExerciseType exerciseType) {
        resetInputVisibility();

        switch (exerciseType) {
            case WEIGHTS_EXERCISE:
                inputTextFields.get(JsonKeys.EXERCISE_DISTANCE.getKey()).setVisible(false);

                break;
            case BODYWEIGHTS_EXERCISE:
                inputTextFields.get(JsonKeys.EXERCISE_WEIGHT.getKey()).setVisible(false);
                inputTextFields.get(JsonKeys.EXERCISE_DISTANCE.getKey()).setVisible(false);


                break;
            case CARDIO_EXERCISE:
                inputTextFields.get(JsonKeys.EXERCISE_WEIGHT.getKey()).setVisible(false);
                inputTextFields.get(JsonKeys.EXERCISE_SETS.getKey()).setVisible(false);
                inputTextFields.get(JsonKeys.EXERCISE_REPS.getKey()).setVisible(false);

                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: sets all inputs to visible
    void resetInputVisibility() {
        for (String name : inputTextFields.keySet()) {
            JTextField field = inputTextFields.get(name);
            field.setVisible(true);
        }

        for (String name : inputBoxes.keySet()) {
            JComboBox<String> box = inputBoxes.get(name);
            box.setVisible(true);
        }
    }

    // MODIFIES: this
    // EFFECTS: updates the appropriate inputs according to the given exercise type with the given exercise info
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    void updateInputs(Exercise exercise, ExerciseType exerciseType) {
        name.setText(exercise.getName());
        inputBoxes.get(JsonKeys.EXERCISE_DIFFICULTY.getKey())
                .setSelectedItem(Integer.toString(exercise.getDifficulty().getDifficultyAsInt()));
        inputBoxes.get(JsonKeys.EXERCISE_MUSCLE_GROUP.getKey())
                .setSelectedItem(exercise.getMuscleGroup().getMuscleGroupAsString());
        inputBoxes.get(JsonKeys.EXERCISE_FAVOURITE.getKey())
                .setSelectedItem(Boolean.toString(exercise.isFavourite()));
        inputTextFields.get(JsonKeys.EXERCISE_TIME.getKey()).setText(Integer.toString(exercise.getTimeMinutes()));

        switch (exerciseType) {
            case WEIGHTS_EXERCISE:
                WeightsExercise weightsExercise = (WeightsExercise) exercise;

                inputTextFields.get(JsonKeys.EXERCISE_WEIGHT.getKey())
                        .setText(Integer.toString(weightsExercise.getWeightPounds()));
                inputTextFields.get(JsonKeys.EXERCISE_SETS.getKey())
                        .setText(Integer.toString(weightsExercise.getSets()));
                inputTextFields.get(JsonKeys.EXERCISE_REPS.getKey())
                        .setText(Integer.toString(weightsExercise.getReps()));

                break;
            case BODYWEIGHTS_EXERCISE:
                BodyWeightsExercise bodyWeightsExercise = (BodyWeightsExercise) exercise;

                inputTextFields.get(JsonKeys.EXERCISE_SETS.getKey())
                        .setText(Integer.toString(bodyWeightsExercise.getSets()));
                inputTextFields.get(JsonKeys.EXERCISE_REPS.getKey())
                        .setText(Integer.toString(bodyWeightsExercise.getReps()));

                break;
            case CARDIO_EXERCISE:
                CardioExercise cardioExercise = (CardioExercise) exercise;

                inputTextFields.get(JsonKeys.EXERCISE_DISTANCE.getKey())
                        .setText(Integer.toString(cardioExercise.getDistanceMetres()));

                break;
        }
    }

    @Override
    // EFFECTS: returns the presenter associated with this panel
    public Presenter getPresenter() {
        return exercisePanelPresenter;
    }
}
