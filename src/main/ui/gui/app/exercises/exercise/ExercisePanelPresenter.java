package ui.gui.app.exercises.exercise;

import model.exercises.exercise.*;
import model.metrics.Difficulty;
import model.metrics.ExerciseType;
import model.metrics.FitnessMetricParser;
import model.metrics.MuscleGroup;
import org.json.JSONObject;
import ui.gui.logic.displayelement.DisplayElementPresenter;
import ui.gui.json.JsonKeys;
import ui.gui.app.FitnessApp;
import ui.gui.app.FitnessAppCommands;

import static java.lang.Integer.parseInt;
import static ui.gui.app.FitnessAppCommands.*;

// Represents the data and actions of a panel for displaying an exercise
public class ExercisePanelPresenter extends DisplayElementPresenter {
    private ExercisePanel exercisePanel;

    private Exercise exercise;

    // EFFECTS: makes a presenter for the exercise panel
    public ExercisePanelPresenter(ExercisePanel exercisePanel) {
        this.exercisePanel = exercisePanel;

        // initialize sample exercise
        this.exercise = new CardioExercise("Example Name", MuscleGroup.BACK,
                0, Difficulty.LIGHT, 0);
    }

    @Override
    // MODIFIES: this, exercisePanel, fitnessApp
    // EFFECTS: updates the model appropriately with t according to the given key
    public <T> void update(T t, FitnessAppCommands key) {
        if (key.getFitnessAppCommand().equals(FitnessAppCommands.EDIT_COMMAND.getFitnessAppCommand())) {
            editExercise(t);
        } else if (key.getFitnessAppCommand().equals(FitnessAppCommands.BACK_COMMAND.getFitnessAppCommand())) {
            back();
        } else if (key.getFitnessAppCommand().equals(FitnessAppCommands.EXERCISE_COMMAND.getFitnessAppCommand())) {
            setExercise(t);
        }
    }

    // MODIFIES: this, exercisePanel
    // EFFECTS: parses the exercise info from t and updates the exercise with the exercise info
    private <T> void editExercise(T t) {
        JSONObject jsonObject = (JSONObject) t;
        JSONObject data = jsonObject.getJSONObject(JsonKeys.DATA.getKey());

        JSONObject textFields = data.getJSONObject(JsonKeys.FIELDS.getKey());
        JSONObject boxes = data.getJSONObject(JsonKeys.BOXES.getKey());
        String exerciseTypeName = data.getString(JsonKeys.EXERCISE_TYPE.getKey());

        editExercise(textFields, boxes, exerciseTypeName);
    }

    // MODIFIES: this, exercisePanel
    // EFFECTS: updates the exercise with the exercise data
    //          if exercise data cannot be parsed, notifies the user
    private void editExercise(JSONObject textFields, JSONObject boxes, String exerciseTypeName) {
        ExerciseType exerciseType = FitnessMetricParser.getInstance().getExerciseTypeByName(exerciseTypeName);

        try {
            editBasicInfo(textFields, boxes);
            editAdvancedInfo(textFields, exerciseType);
        } catch (ClassCastException e) {
            exercisePanel.setText(INVALID_INPUT_TEXT);
        }

        exercisePanel.updateInputs(exercise, exerciseType);

        updateExercises();
    }

    // MODIFIES: exercisesPanelPresenter
    // EFFECTS: updates the exercise collection with the exercise data
    private void updateExercises() {
        JSONObject data = new JSONObject();
        JSONObject exerciseData = new JSONObject();

        exerciseData.put(JsonKeys.EXERCISE_NAME.getKey(), exercise.getName());

        exerciseData.put(JsonKeys.EXERCISE.getKey(), exercise);

        data.put(JsonKeys.DATA.getKey(), exerciseData);

        notifyAll(data, SAVE_EXERCISE_TO_EXERCISES);
    }

    // MODIFIES: this, exercisePanel
    // EFFECTS: updates the exercise with basic exercise data
    private void editBasicInfo(JSONObject textFields, JSONObject boxes) {
        String muscleGroup = boxes.getString(JsonKeys.EXERCISE_MUSCLE_GROUP.getKey());
        String difficulty = boxes.getString(JsonKeys.EXERCISE_DIFFICULTY.getKey());
        String favourite = boxes.getString(JsonKeys.EXERCISE_FAVOURITE.getKey());
        String time = textFields.getString(JsonKeys.EXERCISE_TIME.getKey());

        try {
            exercise.setMuscleGroup(FitnessMetricParser.getInstance().getMuscleGroupByName(muscleGroup));
            exercise.setDifficulty(FitnessMetricParser.getInstance().getDifficultyByLevel(parseInt(difficulty)));
            exercise.setTimeMinutes(parseInt(time));
            exercise.setFavourite(Boolean.parseBoolean(favourite));
        } catch (IllegalArgumentException e) {
            exercisePanel.setText(INVALID_INPUT_TEXT);
        }
    }

    // MODIFIES: this, exercisePanel
    // EFFECTS: updates the exercise with advanced exercise data
    private void editAdvancedInfo(JSONObject textFields, ExerciseType exerciseType) {
        switch (exerciseType) {
            case WEIGHTS_EXERCISE:
                WeightsExercise weightsExercise = (WeightsExercise) exercise;

                String weight = textFields.getString(JsonKeys.EXERCISE_WEIGHT.getKey());
                String sets = textFields.getString(JsonKeys.EXERCISE_SETS.getKey());
                String reps = textFields.getString(JsonKeys.EXERCISE_REPS.getKey());

                weightsExercise.setWeightPounds(parseInt(weight));
                weightsExercise.setSets(parseInt(sets));
                weightsExercise.setReps(parseInt(reps));

                break;
            case BODYWEIGHTS_EXERCISE:
                BodyWeightsExercise bodyWeightsExercise = (BodyWeightsExercise) exercise;

                sets = textFields.getString(JsonKeys.EXERCISE_SETS.getKey());
                reps = textFields.getString(JsonKeys.EXERCISE_REPS.getKey());

                bodyWeightsExercise.setSets(parseInt(sets));
                bodyWeightsExercise.setReps(parseInt(reps));

                break;
            case CARDIO_EXERCISE:
                CardioExercise cardioExercise = (CardioExercise) exercise;

                String distance = textFields.getString(JsonKeys.EXERCISE_DISTANCE.getKey());

                cardioExercise.setDistanceMetres(parseInt(distance));

                break;
        }
    }

    // MODIFIES: fitnessApp
    // EFFECTS: switches to the exercises panel
    private void back() {
        exercisePanel.resetInputVisibility();

        exercisePanel.setText("");

        FitnessApp.getInstance().switchPanel(EXERCISES_COMMAND.getFitnessAppCommand());
    }

    // MODIFIES: this
    // EFFECTS: parses an exercise from t and sets the exercise for the current panel as the given exercise
    private <T> void setExercise(T t) {
        Exercise exercise = (Exercise) t;

        setExercise(exercise);
    }

    // MODIFIES: this, exercisePanel
    // EFFECTS: sets the exercise for the current panel as the given exercise
    private void setExercise(Exercise exercise) {
        this.exercise = exercise;

        ExerciseType exerciseType = FitnessMetricParser.getInstance()
                .getExerciseTypeByName(exercise.getClass().getSimpleName());

        exercisePanel.setExerciseType(exerciseType);
        exercisePanel.setInputVisibility(exerciseType);
        exercisePanel.updateInputs(exercise, exerciseType);
    }

    @Override
    // EFFECTS: does nothing
    protected void updatePresenter() {

    }
}
