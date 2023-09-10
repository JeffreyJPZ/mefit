package ui;

import model.Exercise;
import model.ExercisesByName;
import model.FitnessMetricParser;
import org.json.JSONObject;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.util.List;
import java.util.Vector;

import static java.lang.Integer.parseInt;
import static ui.FitnessAppCommands.*;

public class ExercisesPanelPresenter extends DisplayCollectionPresenter {
    private static final String EXERCISE_NAME = "Name";
    private static final String EXERCISE_MUSCLE_GROUP = "Muscle Group";
    private static final String EXERCISE_DIFFICULTY = "Difficulty";
    private static final String EXERCISE_TIME = "Time (min)";
    private static final String EXERCISE_FAVOURITE = "Favourite?";

    private ExercisesPanel exercisesPanel;

    private ExercisesByName exercisesByName;
    private ExercisesByName exercisesByNameMaster;

    // EFFECTS: makes a model for exercises panel
    public ExercisesPanelPresenter(ExercisesPanel exercisesPanel) {
        this.exercisesPanel = exercisesPanel;

        this.exercisesByName = new ExercisesByName(); // initializes sample exercises
        this.exercisesByNameMaster = exercisesByName;

        this.info = List.of(EXERCISE_NAME, EXERCISE_MUSCLE_GROUP,
                EXERCISE_DIFFICULTY, EXERCISE_TIME, EXERCISE_FAVOURITE);
        this.infoHeader = new Vector<>(info);

        this.filterable = info;
        this.data = new Vector<>();

        extractData();

        this.tableModel = new DefaultTableModel(data, infoHeader);
    }

    // MODIFIES: this
    // EFFECTS: extracts each exercise's information from the exercise collection
    @Override
    protected void extractData() {
        for (Exercise exercise : exercisesByName.getExercises().values()) {
            Vector<Object> exerciseData = new Vector<>();

            exerciseData.add(exercise.getName());
            exerciseData.add(exercise.getMuscleGroup().getMuscleGroupAsString());
            exerciseData.add(exercise.getDifficulty().getDifficultyAsInt());
            exerciseData.add(exercise.getTimeMinutes());
            exerciseData.add(exercise.isFavourite());

            data.add(exerciseData);
        }
    }

    // MODIFIES: this, exercisesPanel, fitnessApp
    // EFFECTS: updates the model appropriately with t according to the given key
    @Override
    public <T> void update(T t, FitnessAppCommands key) {
        if (key.getFitnessAppCommand().equals(VIEW_COMMAND.getFitnessAppCommand())) {
            exercisePanel(t);
        } else if (key.getFitnessAppCommand().equals(ADD_COMMAND.getFitnessAppCommand())) {
            addExercisePanel();
        } else if (key.getFitnessAppCommand().equals(REMOVE_COMMAND.getFitnessAppCommand())) {
            removeExercise(t);
        } else if (key.getFitnessAppCommand().equals(FILTER_COMMAND.getFitnessAppCommand())) {
            filterExercises(t);
        } else if (key.getFitnessAppCommand().equals(RESET_FILTERS_COMMAND.getFitnessAppCommand())) {
            resetFilters();
        } else if (key.getFitnessAppCommand().equals(BACK_COMMAND.getFitnessAppCommand())) {
            back();
        } else if (key.getFitnessAppCommand().equals(SAVE_EXERCISE_TO_EXERCISES.getFitnessAppCommand())) {
            updateExercises(t);
        } else if (key.getFitnessAppCommand().equals(ADD_EXERCISE_COMMAND.getFitnessAppCommand())) {
            addExercise(t);
        } else if (key.getFitnessAppCommand().equals(EXERCISES_COMMAND.getFitnessAppCommand())) {
            setExercises(t);
        }
    }

    // MODIFIES: this, exercisePanel, exercisePanelPresenter, fitnessApp
    // EFFECTS: parses the selected exercise name from t and switches to the exercise panel with the selected exercise
    private <T> void exercisePanel(T t) {
        JSONObject jsonObject = (JSONObject) t;
        JSONObject data = jsonObject.getJSONObject(JsonKeys.DATA.getKey());

        exercisePanel(data);
    }

    // MODIFIES: this, exercisePanel, exercisePanelPresenter, fitnessApp
    // EFFECTS: switches to the exercise panel with the selected exercise
    private void exercisePanel(JSONObject data) {
        String name = data.getString(JsonKeys.EXERCISE_NAME.getKey());

        resetFilters();

        exercisesPanel.setText("");

        notifyAll(exercisesByName.getExercise(name), EXERCISE_COMMAND);

        FitnessApp.getInstance().switchPanel(EXERCISE_COMMAND.getFitnessAppCommand());
    }

    // MODIFIES: this, exercisesPanel, fitnessApp
    // EFFECTS: switches to the panel for adding exercises
    private void addExercisePanel() {
        resetFilters();
        FitnessApp.getInstance().switchPanel(ADD_EXERCISE_COMMAND.getFitnessAppCommand());
    }

    // MODIFIES: this, exercisesPanel
    // EFFECTS: parses a name from t and removes the exercise with the given name
    private <T> void removeExercise(T t) {
        JSONObject jsonObject = (JSONObject) t;
        JSONObject data = jsonObject.getJSONObject(JsonKeys.DATA.getKey());

        String name = data.getString(JsonKeys.EXERCISE_NAME.getKey());

        removeExercise(name);
    }

    // MODIFIES: this, exercisesPanel
    // EFFECTS: removes an exercise with the given name from the exercise collection
    private void removeExercise(String name) {
        exercisesByName.removeExercise(name);
        updatePresenter();
        exercisesPanel.updateDisplayCollection();
    }

    // MODIFIES: this, exercisesPanel
    // EFFECTS: parses a filter type and user input from t and filters the exercise collection
    private <T> void filterExercises(T t) {
        JSONObject jsonObject = (JSONObject) t;
        JSONObject data = jsonObject.getJSONObject(JsonKeys.DATA.getKey());

        String filterType = data.getString(JsonKeys.FILTER_TYPE.getKey());
        String input = data.getString(JsonKeys.INPUT.getKey());

        filterExercises(filterType, input);
    }

    // REQUIRES: selected filter and user input are not null
    // MODIFIES: this, exercisesPanel
    // EFFECTS: filters exercises given appropriate filter and input
    private void filterExercises(String selectedFilter, String input) {
        switch (selectedFilter) {
            case EXERCISE_NAME:
                exercisesByName = exercisesByName.filterName(input);
                break;
            case EXERCISE_MUSCLE_GROUP:
                exercisesByName = exercisesByName
                        .filterMuscleGroup(FitnessMetricParser.getInstance().getMuscleGroupByName(input));
                break;
            case EXERCISE_DIFFICULTY:
                exercisesByName = exercisesByName
                        .filterDifficulty(FitnessMetricParser.getInstance().getDifficultyByLevel(parseInt(input)));
                break;
            case EXERCISE_TIME:
                exercisesByName = exercisesByName.filterTime(parseInt(input));
                break;
            case EXERCISE_FAVOURITE:
                exercisesByName = exercisesByName.filterFavourite();
        }
        updatePresenter();
        exercisesPanel.updateDisplayCollection();
    }

    // MODIFIES: this, exercisesPanel
    // EFFECTS: removes filters and resets the exercises
    private void resetFilters() {
        exercisesByName = exercisesByNameMaster;
        updatePresenter();
        exercisesPanel.updateDisplayCollection();
    }

    // MODIFIES: this, exercisesPanel, fitnessApp
    // EFFECTS: switches to the profile panel
    public void back() {
        resetFilters();
        exercisesPanel.setText("");
        FitnessApp.getInstance().switchPanel(PROFILE_COMMAND.getFitnessAppCommand());
    }

    // MODIFIES: this
    // EFFECTS: parses the exercise data from t and updates the corresponding exercise with the data
    private <T> void updateExercises(T t) {
        JSONObject jsonObject = (JSONObject) t;
        JSONObject data = jsonObject.getJSONObject(JsonKeys.DATA.getKey());

        updateExercises(data);
    }

    // MODIFIES: this
    // EFFECTS: updates the corresponding exercise with the data
    private void updateExercises(JSONObject data) {
        String name = data.getString(JsonKeys.EXERCISE_NAME.getKey());
        Exercise updatedExercise = (Exercise) data.get(JsonKeys.EXERCISE.getKey());

        Exercise exercise = exercisesByName.getExercise(name);

        exercise.setName(updatedExercise.getName());
        exercise.setMuscleGroup(updatedExercise.getMuscleGroup());
        exercise.setDifficulty(updatedExercise.getDifficulty());
        exercise.setTimeMinutes(updatedExercise.getTimeMinutes());
        exercise.setFavourite(updatedExercise.isFavourite());

        updatePresenter();
        exercisesPanel.updateDisplayCollection();
    }

    // MODIFIES: this, exercisesPanel
    // EFFECTS: parses an exercise from t and adds the exercise
    private <T> void addExercise(T t) {
        Exercise exercise = (Exercise) t;
        addExercise(exercise);
    }

    // MODIFIES: this, exercisesPanel, exercisesByName
    // EFFECTS: adds an exercise to the exercise collection
    private void addExercise(Exercise exercise) {
        exercisesByName.addExercise(exercise);
        updatePresenter();
        exercisesPanel.updateDisplayCollection();
    }

    // MODIFIES: this, exercisesPanel
    // EFFECTS: parses the exercises from t and sets the current exercises to the given exercises
    private <T> void setExercises(T t) {
        ExercisesByName exercises = (ExercisesByName) t;

        setExercises(exercises);
    }

    // MODIFIES: this, exercisesPanel
    // EFFECTS: sets the current exercises to the given exercises
    private void setExercises(ExercisesByName exercisesByName) {
        this.exercisesByName = exercisesByName;
        exercisesByNameMaster = this.exercisesByName;

        updatePresenter();
        exercisesPanel.updateDisplayCollection();
    }

    // MODIFIES: this
    // EFFECTS: updates the exercises collection
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
