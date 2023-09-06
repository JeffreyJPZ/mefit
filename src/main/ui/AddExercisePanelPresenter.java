package ui;


import model.*;
import org.json.JSONObject;

import static java.lang.Integer.parseInt;
import static ui.FitnessAppCommands.*;

public class AddExercisePanelPresenter extends AddToCollectionPresenter {

    // MODIFIES: exercisesPanelModel, fitnessApp
    // EFFECTS: updates the add exercise model appropriately with t according to the given key
    @Override
    public <T> void update(T t, FitnessAppCommands key) {
        if (key.getFitnessAppCommand().equals(ADD_EXERCISE_COMMAND.getFitnessAppCommand())) {
            addExercise(t);
        } else if (key.getFitnessAppCommand().equals(BACK_COMMAND.getFitnessAppCommand())) {
            back();
        }
    }

    // EFFECTS: does nothing
    @Override
    protected void updatePresenter() {

    }

    // MODIFIES: exercisesPanelModel, fitnessApp
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

    // MODIFIES: exercisesPanelModel, fitnessApp
    // EFFECTS: adds an exercise to the profile with the given information
    private void addExercise(JSONObject textFields, JSONObject boxes) {
        Exercise exercise = makeExercise(textFields, boxes);

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

    // MODIFIES: fitnessApp
    // EFFECTS: switches to the exercises panel
    private void back() {
        FitnessApp.getInstance().switchPanel(FitnessAppCommands.EXERCISES_COMMAND.getFitnessAppCommand());
    }
}
