package ui.gui.app.workouts.workout;

import model.metrics.Difficulty;
import model.exercises.exercise.Exercise;
import model.metrics.FitnessMetricParser;
import model.workouts.workout.Workout;
import org.json.JSONObject;
import ui.gui.logic.displayelement.DisplayElementPresenter;
import ui.gui.json.JsonKeys;
import ui.gui.app.FitnessApp;
import ui.gui.app.FitnessAppCommands;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.util.List;
import java.util.Vector;

import static java.lang.Integer.parseInt;
import static ui.gui.app.FitnessAppCommands.*;

// Represents the data and actions for a panel for displaying a workout
public class WorkoutPanelPresenter extends DisplayElementPresenter {
    private static final String EXERCISE_POSITION = "Position";
    private static final String EXERCISE_NAME = "Name";
    private static final String EXERCISE_MUSCLE_GROUP = "Muscle Group";
    private static final String EXERCISE_DIFFICULTY = "Difficulty";
    private static final String EXERCISE_TIME = "Time (min)";
    private static final String EXERCISE_FAVOURITE = "Favourite?";

    private List<String> info;
    private Vector<String> infoHeader;
    private Vector<Vector<Object>> data;
    private DefaultTableModel tableModel;

    private WorkoutPanel workoutPanel;

    private Workout workout;

    // EFFECTS: makes a new presenter for the workout panel
    public WorkoutPanelPresenter(WorkoutPanel workoutPanel) {
        this.workoutPanel = workoutPanel;

        // initialize sample workout
        this.workout = new Workout("Example Name", Difficulty.LIGHT);

        this.info = List.of(EXERCISE_POSITION, EXERCISE_NAME, EXERCISE_MUSCLE_GROUP,
                EXERCISE_DIFFICULTY, EXERCISE_TIME, EXERCISE_FAVOURITE);
        this.infoHeader = new Vector<>(info);

        this.data = new Vector<>();

        extractData();

        this.tableModel = new DefaultTableModel(data, infoHeader);
    }

    // MODIFIES: this
    // EFFECTS: extracts each exercise's information from the exercise collection
    protected void extractData() {
        for (int i = 0; i < workout.length(); i++) {
            Vector<Object> exerciseData = new Vector<>();

            Exercise exercise = workout.getExercise(i + 1);

            exerciseData.add(i + 1); // exercise position
            exerciseData.add(exercise.getName());
            exerciseData.add(exercise.getMuscleGroup().getMuscleGroupAsString());
            exerciseData.add(exercise.getDifficulty().getDifficultyAsInt());
            exerciseData.add(exercise.getTimeMinutes());
            exerciseData.add(exercise.isFavourite());

            data.add(exerciseData);
        }
    }

    @Override
    // MODIFIES: this, workoutPanel, fitnessApp
    // EFFECTS: updates the model appropriately with t according to the given key
    public <T> void update(T t, FitnessAppCommands key) {
        if (key.getFitnessAppCommand().equals(FitnessAppCommands.EDIT_COMMAND.getFitnessAppCommand())) {
            editWorkout(t);
        } else if (key.getFitnessAppCommand().equals(FitnessAppCommands.BACK_COMMAND.getFitnessAppCommand())) {
            back();
        } else if (key.getFitnessAppCommand().equals(FitnessAppCommands.WORKOUT_COMMAND.getFitnessAppCommand())) {
            setWorkout(t);
        }
    }

    // MODIFIES: this, exercisePanel
    // EFFECTS: parses the workout data from t and updates the workout with the workout data
    private <T> void editWorkout(T t) {
        try {
            JSONObject jsonObject = (JSONObject) t;
            JSONObject data = jsonObject.getJSONObject(JsonKeys.DATA.getKey());

            JSONObject boxes = data.getJSONObject(JsonKeys.BOXES.getKey());

            editWorkout(boxes);
        } catch (ClassCastException e) {
            throw new RuntimeException("Invalid data type was passed to the model");
        }
    }

    // MODIFIES: this, workoutPanel
    // EFFECTS: updates the workout with the workout data
    private void editWorkout(JSONObject boxes) {
        JSONObject data = new JSONObject();
        JSONObject workoutData = new JSONObject();

        workoutData.put(JsonKeys.WORKOUT_NAME.getKey(), workout.getName());

        editBasicInfo(boxes);

        workoutPanel.updateInputs(workout);

        workoutData.put(JsonKeys.WORKOUT.getKey(), workout);

        data.put(JsonKeys.DATA.getKey(), workoutData);

        notifyAll(data, SAVE_WORKOUT_TO_WORKOUTS);
    }

    // MODIFIES: this, workoutPanel
    // EFFECTS: edits the basic information of the workout
    private void editBasicInfo(JSONObject boxes) {
        String difficulty = boxes.getString("selectDifficulty");
        String favourite = boxes.getString("selectFavourite");

        try {
            workout.setDifficulty(FitnessMetricParser.getInstance().getDifficultyByLevel(parseInt(difficulty)));
            workout.setFavourite(Boolean.parseBoolean(favourite));
        } catch (IllegalArgumentException e) {
            workoutPanel.setText(INVALID_INPUT_TEXT);
        }
    }

    // MODIFIES: fitnessApp
    // EFFECTS: switches to the workouts panel
    private void back() {
        FitnessApp.getInstance().switchPanel(WORKOUTS_COMMAND.getFitnessAppCommand());
    }

    // MODIFIES: this
    // EFFECTS: parses a workout from t and sets the workout for the current panel as the given workout
    private <T> void setWorkout(T t) {
        Workout workout = (Workout) t;

        setWorkout(workout);
    }

    // MODIFIES: this, workoutPanel
    // EFFECTS: updates the
    private void setWorkout(Workout workout) {
        this.workout = workout;

        updatePresenter();
        workoutPanel.updateInputs(workout);
        workoutPanel.updateDisplayCollection();
    }

    @Override
    protected void updatePresenter() {
        data.clear();
        extractData();
        tableModel.setDataVector(data, infoHeader);
    }

    public TableModel getTableModel() {
        return tableModel;
    }
}
