package ui.gui.app.exercises.addexercise;


import model.exercises.exercise.*;
import model.metrics.Difficulty;
import model.metrics.ExerciseType;
import model.metrics.FitnessMetricParser;
import model.metrics.MuscleGroup;
import org.json.JSONObject;
import ui.gui.json.JsonKeys;
import ui.gui.logic.addtocollection.AddToCollectionPresenter;
import ui.gui.app.FitnessApp;
import ui.gui.app.FitnessAppCommands;

import static java.lang.Integer.parseInt;
import static ui.gui.app.FitnessAppCommands.*;

// Represents the data and actions for a panel for adding an exercise
public class AddExercisePanelPresenter extends AddToCollectionPresenter {
    private AddExercisePanel addExercisePanel;

    // EFFECTS: makes a new presenter for adding an exercise{
    public AddExercisePanelPresenter(AddExercisePanel addExercisePanel) {
        this.addExercisePanel = addExercisePanel;
    }

    // MODIFIES: exercisesPanelPresenter, fitnessApp
    // EFFECTS: updates the model appropriately with t according to the given key
    @Override
    public <T> void update(T t, FitnessAppCommands key) {
        if (key.getFitnessAppCommand().equals(ADD_EXERCISE_COMMAND.getFitnessAppCommand())) {
            addExercise(t);
        } else if (key.getFitnessAppCommand().equals(BACK_COMMAND.getFitnessAppCommand())) {
            back();
        }
    }

    // MODIFIES: exercisesPanelPresenter, fitnessApp
    // EFFECTS: parses the exercise data from t and makes an exercise with the data
    private <T> void addExercise(T t) {
        JSONObject jsonObject = (JSONObject) t;
        JSONObject data = jsonObject.getJSONObject(JsonKeys.DATA.getKey());

        JSONObject textFields = data.getJSONObject(JsonKeys.FIELDS.getKey());
        JSONObject boxes = data.getJSONObject(JsonKeys.BOXES.getKey());

        addExercise(textFields, boxes);
    }

    // MODIFIES: exercisesPanelPresenter, fitnessApp
    // EFFECTS: adds an exercise to the profile with the given data
    //          if exercise data cannot be parsed, notifies the user
    private void addExercise(JSONObject textFields, JSONObject boxes) {
        Exercise exercise = null;

        try {
            exercise = makeExercise(textFields, boxes);
        } catch (IllegalArgumentException e) {
            addExercisePanel.setText(INVALID_INPUT_TEXT);
        }

        boolean favourite = Boolean.parseBoolean(boxes.getString(JsonKeys.EXERCISE_FAVOURITE.getKey()));

        setFavourite(exercise, favourite);

        notifyAll(exercise, ADD_EXERCISE_COMMAND);

        back();
    }

    // REQUIRES: exerciseType matches a valid exercise type
    // EFFECTS: returns the appropriate exercise given exercise type
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private Exercise makeExercise(JSONObject textFields, JSONObject boxes) {
        String exerciseName = textFields.getString(JsonKeys.EXERCISE_NAME.getKey());

        String inputExerciseType = boxes.getString(JsonKeys.EXERCISE_TYPE.getKey());
        ExerciseType exerciseType = FitnessMetricParser.getInstance().getExerciseTypeByName(inputExerciseType);

        String inputMuscleGroup = boxes.getString(JsonKeys.EXERCISE_MUSCLE_GROUP.getKey());
        MuscleGroup muscleGroup = FitnessMetricParser.getInstance().getMuscleGroupByName(inputMuscleGroup);

        String inputDifficulty = boxes.getString(JsonKeys.EXERCISE_DIFFICULTY.getKey());
        Difficulty difficultyLevel = FitnessMetricParser.getInstance().getDifficultyByLevel(parseInt(inputDifficulty));

        String exerciseTime = textFields.getString(JsonKeys.EXERCISE_TIME.getKey());

        switch (exerciseType) {
            case WEIGHTS_EXERCISE:
                return new WeightsExercise(exerciseName,
                        muscleGroup,
                        parseInt(textFields.getString(JsonKeys.EXERCISE_WEIGHT.getKey())),
                        parseInt(textFields.getString(JsonKeys.EXERCISE_SETS.getKey())),
                        parseInt(textFields.getString(JsonKeys.EXERCISE_REPS.getKey())),
                        difficultyLevel,
                        parseInt(exerciseTime));
            case BODYWEIGHTS_EXERCISE:
                return new BodyWeightsExercise(exerciseName,
                        muscleGroup,
                        parseInt(textFields.getString(JsonKeys.EXERCISE_SETS.getKey())),
                        parseInt(textFields.getString(JsonKeys.EXERCISE_REPS.getKey())),
                        difficultyLevel,
                        parseInt(exerciseTime));
            case CARDIO_EXERCISE:
                return new CardioExercise(exerciseName,
                        muscleGroup,
                        parseInt(textFields.getString(JsonKeys.EXERCISE_DISTANCE.getKey())),
                        difficultyLevel,
                        parseInt(exerciseTime));
        }
        return null;
    }

    // MODIFIES: exercise
    // EFFECTS: sets the exercise to be either favourite or not favourite
    private void setFavourite(Exercise exercise, boolean favourite) {
        exercise.setFavourite(favourite);
    }

    // MODIFIES: fitnessApp
    // EFFECTS: switches to the exercises panel
    private void back() {
        FitnessApp.getInstance().switchPanel(FitnessAppCommands.EXERCISES_COMMAND.getFitnessAppCommand());
    }

    // EFFECTS: does nothing
    @Override
    protected void updatePresenter() {

    }
}
