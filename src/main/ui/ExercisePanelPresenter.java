package ui;

import model.*;
import org.json.JSONObject;

import static java.lang.Integer.parseInt;
import static ui.FitnessAppCommands.EXERCISES_COMMAND;
import static ui.FitnessAppCommands.EXERCISE_COMMAND;

public class ExercisePanelPresenter extends Presenter {
    private ExercisePanel exercisePanel;

    private Exercise exercise;

    // EFFECTS: makes a presenter for the exercise panel
    public ExercisePanelPresenter(ExercisePanel exercisePanel) {
        this.exercisePanel = exercisePanel;
    }

    @Override
    // MODIFIES: this, exercisePanel, fitnessApp
    // EFFECTS: updates the model appropriately with t according to the given key
    public <T> void update(T t, FitnessAppCommands key) {
        if (key.getFitnessAppCommand().equals(FitnessAppCommands.EDIT_COMMAND.getFitnessAppCommand())) {
            editExercise(t);
        } else if (key.getFitnessAppCommand().equals(FitnessAppCommands.EXERCISE_COMMAND.getFitnessAppCommand())) {
            setExercise(t);
        } else if (key.getFitnessAppCommand().equals(FitnessAppCommands.BACK_COMMAND.getFitnessAppCommand())) {
            back();
        }
    }

    // MODIFIES: this, exercisePanel
    // EFFECTS: parses the exercise info from t and updates the exercise with the exercise info
    private <T> void editExercise(T t) {
        try {
            JSONObject jsonObject = (JSONObject) t;
            JSONObject data = jsonObject.getJSONObject(JsonKeys.DATA.getKey());

            JSONObject textFields = data.getJSONObject(JsonKeys.FIELDS.getKey());
            JSONObject boxes = data.getJSONObject(JsonKeys.BOXES.getKey());
            String exerciseTypeName = data.getString(JsonKeys.EXERCISE_TYPE.getKey());

            editExercise(textFields, boxes, exerciseTypeName);
        } catch (ClassCastException e) {
            throw new RuntimeException("Invalid data type was passed to the model");
        }
    }

    // MODIFIES: this, exercisePanel
    // EFFECTS: updates the exercise with the exercise info
    private void editExercise(JSONObject textFields, JSONObject boxes, String exerciseTypeName) {
        ExerciseType exerciseType = FitnessMetricParser.getInstance().getExerciseTypeByName(exerciseTypeName);

        editBasicInfo(textFields, boxes);
        editAdvancedInfo(textFields, boxes, exerciseType);

        exercisePanel.updateInputs(exercise, exerciseType);

        notifyAll(exercise, EXERCISE_COMMAND);
    }

    // MODIFIES: this
    // EFFECTS: updates the exercise with basic exercise info
    private void editBasicInfo(JSONObject textFields, JSONObject boxes) {
        String name = textFields.getString("name");
        String muscleGroup = boxes.getString("selectMuscleGroup");
        String difficulty = boxes.getString("selectDifficulty");
        String favourite = boxes.getString("selectFavourite");
        String time = textFields.getString("time");

        exercise.setName(name);
        exercise.setMuscleGroup(FitnessMetricParser.getInstance().getMuscleGroupByName(muscleGroup));
        exercise.setDifficulty(FitnessMetricParser.getInstance().getDifficultyByLevel(parseInt(difficulty)));
        exercise.setTimeMinutes(parseInt(time));
        exercise.setFavourite(Boolean.parseBoolean(favourite));
    }

    // MODIFIES: this
    // EFFECTS: updates the exercise with advanced exercise info
    private void editAdvancedInfo(JSONObject textFields, JSONObject boxes, ExerciseType exerciseType) {
        switch (exerciseType) {
            case WEIGHTS_EXERCISE:
                WeightsExercise weightsExercise = (WeightsExercise) exercise;

                String weight = textFields.getString("weight");
                String sets = textFields.getString("sets");
                String reps = textFields.getString("reps");

                weightsExercise.setWeightPounds(parseInt(weight));
                weightsExercise.setSets(parseInt(sets));
                weightsExercise.setReps(parseInt(reps));

                break;
            case BODYWEIGHTS_EXERCISE:
                BodyWeightsExercise bodyWeightsExercise = (BodyWeightsExercise) exercise;

                sets = textFields.getString("sets");
                reps = textFields.getString("reps");

                bodyWeightsExercise.setSets(parseInt(sets));
                bodyWeightsExercise.setReps(parseInt(reps));

                break;
            case CARDIO_EXERCISE:
                CardioExercise cardioExercise = (CardioExercise) exercise;

                String distance = textFields.getString("distance");

                cardioExercise.setDistanceMetres(parseInt(distance));

                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: parses an exercise from t and sets the exercise for the current panel as the given exercise
    private <T> void setExercise(T t) {
        Exercise exercise = (Exercise) t;

        setExercise(exercise);
    }

    // MODIFIES: this, exercisePanel
    // EFFECTS: updates the
    private void setExercise(Exercise exercise) {
        this.exercise = exercise;

        ExerciseType exerciseType = FitnessMetricParser.getInstance()
                .getExerciseTypeByName(exercise.getClass().getSimpleName());

        exercisePanel.setExerciseType(exerciseType);
        exercisePanel.setInputVisibility(exerciseType);
        exercisePanel.updateInputs(exercise, exerciseType);
    }

    // MODIFIES: fitnessApp
    // EFFECTS: switches to the exercises panel
    private void back() {
        exercisePanel.resetInputVisibility();

        FitnessApp.getInstance().switchPanel(EXERCISES_COMMAND.getFitnessAppCommand());
    }

    @Override
    // EFFECTS: does nothing
    protected void updatePresenter() {

    }
}
