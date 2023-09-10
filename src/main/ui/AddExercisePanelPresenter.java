package ui;


import model.*;
import org.json.JSONObject;

import static java.lang.Integer.parseInt;
import static ui.FitnessAppCommands.*;

// Represents the data and actions for a panel for adding an exercise
public class AddExercisePanelPresenter extends AddToCollectionPresenter {

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
        try {
            JSONObject jsonObject = (JSONObject) t;
            JSONObject data = jsonObject.getJSONObject(JsonKeys.DATA.getKey());

            JSONObject textFields = data.getJSONObject(JsonKeys.FIELDS.getKey());
            JSONObject boxes = data.getJSONObject(JsonKeys.BOXES.getKey());

            addExercise(textFields, boxes);
        } catch (ClassCastException e) {
            throw new RuntimeException("Invalid data type was passed to the model");
        }
    }

    // MODIFIES: exercisesPanelPresenter, fitnessApp
    // EFFECTS: adds an exercise to the profile with the given data
    private void addExercise(JSONObject textFields, JSONObject boxes) {
        Exercise exercise = makeExercise(textFields, boxes);

        boolean favourite = Boolean.parseBoolean(boxes.getString("selectFavourite"));

        setFavourite(exercise, favourite);

        notifyAll(exercise, ADD_EXERCISE_COMMAND);

        back();
    }

    // REQUIRES: exerciseType matches a valid exercise type
    // EFFECTS: returns the appropriate exercise given exercise type
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private Exercise makeExercise(JSONObject textFields, JSONObject boxes) {
        String inputExerciseType = boxes.getString("selectType");
        ExerciseType exerciseType = FitnessMetricParser.getInstance().getExerciseTypeByName(inputExerciseType);

        String inputDifficulty = boxes.getString("selectDifficulty");
        Difficulty difficultyLevel = FitnessMetricParser.getInstance().getDifficultyByLevel(parseInt(inputDifficulty));

        String inputMuscleGroup = boxes.getString("selectMuscleGroup");
        MuscleGroup muscleGroup = FitnessMetricParser.getInstance().getMuscleGroupByName(inputMuscleGroup);

        switch (exerciseType) {
            case WEIGHTS_EXERCISE:
                return new WeightsExercise(textFields.getString("name"),
                        muscleGroup,
                        parseInt(textFields.getString("weight")),
                        parseInt(textFields.getString("sets")),
                        parseInt(textFields.getString("reps")),
                        difficultyLevel,
                        parseInt(textFields.getString("time")));
            case BODYWEIGHTS_EXERCISE:
                return new BodyWeightsExercise(textFields.getString("name"),
                        muscleGroup,
                        parseInt(textFields.getString("sets")),
                        parseInt(textFields.getString("reps")),
                        difficultyLevel,
                        parseInt(textFields.getString("time")));
            case CARDIO_EXERCISE:
                return new CardioExercise(textFields.getString("name"),
                        muscleGroup,
                        parseInt(textFields.getString("distance")),
                        difficultyLevel,
                        parseInt(textFields.getString("time")));
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
