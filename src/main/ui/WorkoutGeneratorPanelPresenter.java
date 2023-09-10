package ui;

import exceptions.NoValidWorkoutException;
import model.*;
import org.json.JSONObject;

import java.util.Random;

import static java.lang.Integer.parseInt;
import static ui.FitnessAppCommands.*;

// Represents the data and actions for a random workout generator panel
public class WorkoutGeneratorPanelPresenter extends AddToCollectionPresenter {
    private static final String INVALID_WORKOUT_TEXT = "No possible workout - Please choose another set of parameters.";

    private WorkoutGeneratorPanel workoutGeneratorPanel;

    private ExercisesByName exercisesByName;

    // EFFECTS: makes a new presenter for the workout generator panel
    public WorkoutGeneratorPanelPresenter(WorkoutGeneratorPanel workoutGeneratorPanel) {
        this.workoutGeneratorPanel = workoutGeneratorPanel;
        this.exercisesByName = new ExercisesByName();
    }

    // MODIFIES: workoutsPanelPresenter, fitnessApp
    // EFFECTS: updates the model appropriately with t according to the given key
    @Override
    public <T> void update(T t, FitnessAppCommands key) {
        if (key.getFitnessAppCommand().equals(ADD_WORKOUT_COMMAND.getFitnessAppCommand())) {
            addWorkout(t);
        } else if (key.getFitnessAppCommand().equals(SEND_EXERCISES_COMMAND.getFitnessAppCommand())) {
            setExercises(t);
        } else if (key.getFitnessAppCommand().equals(BACK_COMMAND.getFitnessAppCommand())) {
            back();
        }
    }

    // MODIFIES: workoutsPanelPresenter, fitnessApp
    // EFFECTS: parses the workout data from t and makes an exercise with the data
    private <T> void addWorkout(T t) {
        try {
            JSONObject jsonObject = (JSONObject) t;
            JSONObject data = jsonObject.getJSONObject(JsonKeys.DATA.getKey());

            JSONObject textFields = data.getJSONObject(JsonKeys.FIELDS.getKey());
            JSONObject boxes = data.getJSONObject(JsonKeys.BOXES.getKey());

            addWorkout(textFields, boxes);
        } catch (ClassCastException e) {
            throw new RuntimeException("Invalid data type was passed to the model");
        }
    }

    // MODIFIES: workoutsPanelPresenter, fitnessApp
    // EFFECTS: adds a workout to the workout collection with the given data
    //          if no valid workout can be made
    private void addWorkout(JSONObject textFields, JSONObject boxes) {
        Workout workout = null;

        try {
            workout = generateWorkout(textFields, boxes);
        } catch (NoValidWorkoutException e) {
            workoutGeneratorPanel.setText(INVALID_WORKOUT_TEXT);
        }

        boolean favourite = Boolean.parseBoolean(boxes.getString("selectFavourite"));
        setFavourite(workout, favourite);

        notifyAll(workout, ADD_WORKOUT_COMMAND);

        back();
    }

    // EFFECTS: returns a workout matching the given data
    //          if no valid workout matching the given data can be made, throws NoValidWorkoutException
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private Workout generateWorkout(JSONObject textFields, JSONObject boxes) throws NoValidWorkoutException {
        notifyAll(null, GET_EXERCISES_COMMAND);

        String name = textFields.getString("name");
        String timeMinutes = textFields.getString("time");
        String size = textFields.getString("size");
        String sampleSize = textFields.getString("sampleSize");

        String inputMuscleGroup = boxes.getString("selectMuscleGroup");
        MuscleGroup muscleGroup = FitnessMetricParser.getInstance().getMuscleGroupByName(inputMuscleGroup);

        String inputDifficulty = boxes.getString("selectDifficulty");
        Difficulty difficultyLevel = FitnessMetricParser.getInstance().getDifficultyByLevel(parseInt(inputDifficulty));

        WorkoutParameters workoutParameters = new WorkoutParameters
                .WorkoutParametersBuilder(muscleGroup, difficultyLevel,
                parseInt(timeMinutes), parseInt(size), parseInt(sampleSize))
                .build();

        return WorkoutGenerator.getInstance()
                .generateWorkout(name, exercisesByName, workoutParameters, new Random());
    }

    // MODIFIES: workout
    // EFFECTS: sets the workout to be either favourite or not favourite
    private void setFavourite(Workout workout, boolean favourite) {
        workout.setFavourite(favourite);
    }

    // MODIFIES: this
    // EFFECTS: parses an exercise collection from t and sets the currently stored exercises to the given exercises
    private <T> void setExercises(T t) {
        ExercisesByName exercises = (ExercisesByName) t;

        setExercises(exercises);
    }

    // MODIFIES: this
    // EFFECTS: sets the currently stored exercises to the given exercises
    private void setExercises(ExercisesByName exercises) {
        this.exercisesByName = exercises;
    }

    // MODIFIES: fitnessApp
    // EFFECTS: switches to the workouts panel
    private void back() {
        workoutGeneratorPanel.setText("");
        FitnessApp.getInstance().switchPanel(WORKOUTS_COMMAND.getFitnessAppCommand());
    }

    // EFFECTS: does nothing
    @Override
    protected void updatePresenter() {

    }
}
