package ui;

import model.*;
import org.json.JSONObject;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.util.List;
import java.util.Vector;

import static java.lang.Integer.parseInt;
import static ui.FitnessAppCommands.*;
import static ui.FitnessAppCommands.PROFILE_COMMAND;

// Represents the data and actions of a panel for displaying workouts
public class WorkoutsPanelPresenter extends DisplayCollectionPresenter {
    private static final String WORKOUT_NAME = "Name";
    private static final String WORKOUT_SIZE = "# of Exercises";
    private static final String WORKOUT_DIFFICULTY = "Difficulty";
    private static final String WORKOUT_TIME = "Time (min)";
    private static final String WORKOUT_FAVOURITE = "Favourite?";

    private WorkoutsPanel workoutsPanel;

    private WorkoutsByName workoutsByName;
    private WorkoutsByName workoutsByNameMaster;

    // EFFECTS: makes a presenter for the workouts panel
    public WorkoutsPanelPresenter(WorkoutsPanel workoutsPanel) {
        this.workoutsPanel = workoutsPanel;

        this.workoutsByName = new WorkoutsByName(); // initializes sample workouts
        this.workoutsByNameMaster = workoutsByName;

        this.info = List.of(WORKOUT_NAME, WORKOUT_SIZE, WORKOUT_DIFFICULTY, WORKOUT_TIME, WORKOUT_FAVOURITE);
        this.infoHeader = new Vector<>(info);

        this.filterable = info;
        this.data = new Vector<>();

        extractData();

        this.tableModel = new DefaultTableModel(data, infoHeader);
    }

    // MODIFIES: this
    // EFFECTS: extracts each workout's information from the workout collection
    @Override
    protected void extractData() {
        for (Workout workout : workoutsByName.getWorkouts().values()) {
            Vector<Object> workoutData = new Vector<>();

            workoutData.add(workout.getName());
            workoutData.add(workout.length());
            workoutData.add(workout.getDifficulty().getDifficultyAsInt());
            workoutData.add(workout.getTimeMinutes());
            workoutData.add(workout.isFavourite());

            data.add(workoutData);
        }
    }

    // MODIFIES: this, workoutsPanel, fitnessApp
    // EFFECTS: updates the model appropriately with t according to the given key
    @Override
    public <T> void update(T t, FitnessAppCommands key) {
        if (key.getFitnessAppCommand().equals(VIEW_COMMAND.getFitnessAppCommand())) {
            workoutPanel(t);
        } else if (key.getFitnessAppCommand().equals(ADD_COMMAND.getFitnessAppCommand())) {
            workoutGeneratorPanel();
        } else if (key.getFitnessAppCommand().equals(REMOVE_COMMAND.getFitnessAppCommand())) {
            removeWorkout(t);
        } else if (key.getFitnessAppCommand().equals(FILTER_COMMAND.getFitnessAppCommand())) {
            filterWorkouts(t);
        } else if (key.getFitnessAppCommand().equals(RESET_FILTERS_COMMAND.getFitnessAppCommand())) {
            resetFilters();
        } else if (key.getFitnessAppCommand().equals(BACK_COMMAND.getFitnessAppCommand())) {
            back();
        } else if (key.getFitnessAppCommand().equals(SAVE_WORKOUT_TO_WORKOUTS.getFitnessAppCommand())) {
            updateWorkouts(t);
        } else if (key.getFitnessAppCommand().equals(ADD_WORKOUT_COMMAND.getFitnessAppCommand())) {
            addWorkout(t);
        } else if (key.getFitnessAppCommand().equals(WORKOUTS_COMMAND.getFitnessAppCommand())) {
            setWorkouts(t);
        }
    }

    // MODIFIES: this, workoutPanel, workoutPanelPresenter, fitnessApp
    // EFFECTS: parses the selected workout name from t and switches to the workout panel with the selected workout
    private <T> void workoutPanel(T t) {
        JSONObject jsonObject = (JSONObject) t;
        JSONObject data = jsonObject.getJSONObject(JsonKeys.DATA.getKey());

        workoutPanel(data);
    }

    // MODIFIES: this, workoutPanel, workoutPanelPresenter, fitnessApp
    // EFFECTS: switches to the workout panel with the selected workout
    private void workoutPanel(JSONObject data) {
        String name = data.getString(JsonKeys.WORKOUT_NAME.getKey());

        resetFilters();

        workoutsPanel.setText("");

        notifyAll(workoutsByName.getWorkout(name), WORKOUT_COMMAND);

        FitnessApp.getInstance().switchPanel(WORKOUT_COMMAND.getFitnessAppCommand());
    }

    // MODIFIES: this, workoutsPanel, fitnessApp
    // EFFECTS: switches to the panel for adding workouts
    private void workoutGeneratorPanel() {
        resetFilters();
        workoutsPanel.setText("");
        FitnessApp.getInstance().switchPanel(ADD_WORKOUT_COMMAND.getFitnessAppCommand());
    }

    // MODIFIES: this, workoutsPanel
    // EFFECTS: parses a workout from t and adds the workout
    private <T> void addWorkout(T t) {
        Workout workout = (Workout) t;
        addWorkout(workout);
    }

    // MODIFIES: this, workoutsPanel
    // EFFECTS: adds an exercise to the exercise collection
    private void addWorkout(Workout workout) {
        workoutsByName.addWorkout(workout);

        updatePresenter();
        workoutsPanel.updateDisplayCollection();
    }

    // MODIFIES: this, workoutsPanel
    // EFFECTS: parses a name from t and removes the workout with the given name
    private <T> void removeWorkout(T t) {
        JSONObject jsonObject = (JSONObject) t;
        JSONObject data = jsonObject.getJSONObject(JsonKeys.DATA.getKey());

        String name = data.getString(JsonKeys.WORKOUT_NAME.getKey());

        removeWorkout(name);
    }

    // MODIFIES: this, workoutsPanel
    // EFFECTS: removes a workout with the given name from the workout collection
    private void removeWorkout(String name) {
        workoutsByName.removeWorkout(name);

        updatePresenter();
        workoutsPanel.updateDisplayCollection();
    }

    // MODIFIES: this, exercisesPanel
    // EFFECTS: parses a filter type and user input from t and filters the workout collection
    private <T> void filterWorkouts(T t) {
        JSONObject jsonObject = (JSONObject) t;
        JSONObject data = jsonObject.getJSONObject(JsonKeys.DATA.getKey());

        String filterType = data.getString(JsonKeys.FILTER_TYPE.getKey());
        String input = data.getString(JsonKeys.INPUT.getKey());

        filterWorkouts(filterType, input);
    }

    // REQUIRES: selected filter and user input are not null
    // MODIFIES: this, workoutsPanel
    // EFFECTS: filters workouts given appropriate filter and input
    private void filterWorkouts(String selectedFilter, String input) {
        switch (selectedFilter) {
            case WORKOUT_NAME:
                workoutsByName = workoutsByName.filterName(input);
                break;
            case WORKOUT_SIZE:
                workoutsByName = workoutsByName
                        .filterSize(parseInt(input));
                break;
            case WORKOUT_DIFFICULTY:
                workoutsByName = workoutsByName
                        .filterDifficulty(FitnessMetricParser.getInstance().getDifficultyByLevel(parseInt(input)));
                break;
            case WORKOUT_TIME:
                workoutsByName = workoutsByName.filterTime(parseInt(input));
                break;
            case WORKOUT_FAVOURITE:
                workoutsByName = workoutsByName.filterFavourite();
                break;
        }
        updatePresenter();
        workoutsPanel.updateDisplayCollection();
    }

    // MODIFIES: this, workoutsPanel
    // EFFECTS: removes filters and resets the workouts
    private void resetFilters() {
        workoutsByName = workoutsByNameMaster;
        updatePresenter();
        workoutsPanel.updateDisplayCollection();
    }

    // MODIFIES: this, workoutsPanel, fitnessApp
    // EFFECTS: switches to the profile panel
    public void back() {
        resetFilters();
        workoutsPanel.setText("");
        FitnessApp.getInstance().switchPanel(PROFILE_COMMAND.getFitnessAppCommand());
    }

    // MODIFIES: this
    // EFFECTS: parses the workout data from t and updates the corresponding workout with the data
    private <T> void updateWorkouts(T t) {
        JSONObject jsonObject = (JSONObject) t;
        JSONObject data = jsonObject.getJSONObject(JsonKeys.DATA.getKey());

        updateWorkouts(data);
    }

    // MODIFIES: this
    // EFFECTS: updates the corresponding workout with the data
    private void updateWorkouts(JSONObject data) {
        String name = data.getString(JsonKeys.WORKOUT_NAME.getKey());
        Workout updatedWorkout = (Workout) data.get(JsonKeys.WORKOUT.getKey());

        Workout workout = workoutsByName.getWorkout(name);

        workout.setDifficulty(updatedWorkout.getDifficulty());
        workout.setFavourite(updatedWorkout.isFavourite());

        updatePresenter();
        workoutsPanel.updateDisplayCollection();
    }

    // MODIFIES: this, workoutsPanel
    // EFFECTS: parses the workouts from t and sets the current workouts to the given workouts
    private <T> void setWorkouts(T t) {
        WorkoutsByName workouts = (WorkoutsByName) t;

        setWorkouts(workouts);
    }

    // MODIFIES: this, workoutsPanel
    // EFFECTS: sets the current workouts to the given workouts
    private void setWorkouts(WorkoutsByName workouts) {
        this.workoutsByName = workouts;
        workoutsByNameMaster = this.workoutsByName;

        updatePresenter();
        workoutsPanel.updateDisplayCollection();
    }

    // MODIFIES: this
    // EFFECTS: updates the workouts collection
    @Override
    protected void updatePresenter() {
        data.clear();
        extractData();
        tableModel.setDataVector(data, infoHeader);
    }

    @Override
    public TableModel getTableModel() {
        return tableModel;
    }

    @Override
    public List<String> getFilters() {
        return filterable;
    }
}
