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

    // EFFECTS: initializes the fields for this
    // MODIFIES: this
    // EFFECTS: updates the model with t according to the given key
    @Override
    public <T> void update(T t, FitnessAppCommands key) {
        if (key.getFitnessAppCommand().equals(VIEW_COMMAND.getFitnessAppCommand())) {
            String name = (String) t; // TODO: make exercise panel and model
        } else if (key.getFitnessAppCommand().equals(ADD_COMMAND.getFitnessAppCommand())) {
            addExercisePanel();
        } else if (key.getFitnessAppCommand().equals(ADD_EXERCISE_COMMAND.getFitnessAppCommand())) {
            addExercise(t);
        } else if (key.getFitnessAppCommand().equals(EXERCISES_COMMAND.getFitnessAppCommand())) {
            setExercises(t);
        } else if (key.getFitnessAppCommand().equals(REMOVE_COMMAND.getFitnessAppCommand())) {
            removeExercise(t);
        } else if (key.getFitnessAppCommand().equals(FILTER_COMMAND.getFitnessAppCommand())) {
            filterExercises(t);
        } else if (key.getFitnessAppCommand().equals(RESET_FILTERS_COMMAND.getFitnessAppCommand())) {
            resetFilters();
        } else if (key.getFitnessAppCommand().equals(BACK_COMMAND.getFitnessAppCommand())) {
            back();
        }
    }

    // MODIFIES: this
    // EFFECTS: parses an exercise from t and adds the exercise
    private <T> void addExercise(T t) {
        Exercise exercise = (Exercise) t;
        addExercise(exercise);
    }

    // MODIFIES: this, exercisesByName
    // EFFECTS: adds an exercise to the exercise collection
    private void addExercise(Exercise exercise) {
        exercisesByName.addExercise(exercise);
        updatePresenter();
        exercisesPanel.updateDisplayCollection();
    }

    // MODIFIES: this
    // EFFECTS: parses a name from t and removes the exercise with the given name
    private <T> void removeExercise(T t) {
        JSONObject jsonObject = (JSONObject) t;
        JSONObject data = jsonObject.getJSONObject(JsonKeys.DATA.getKey());

        String name = data.getString(JsonKeys.EXERCISE_NAME.getKey());

        removeExercise(name);
    }

    // EFFECTS
    // MODIFIES: this, exercisesByName
    // EFFECTS: removes an exercise with the given name from the exercise collection
    private void removeExercise(String name) {
        exercisesByName.removeExercise(name);
        updatePresenter();
        exercisesPanel.updateDisplayCollection();
    }

    // MODIFIES: this
    // EFFECTS: parses a filter type and user input from t and filters exercises
    private <T> void filterExercises(T t) {
        JSONObject jsonObject = (JSONObject) t;
        JSONObject data = jsonObject.getJSONObject(JsonKeys.DATA.getKey());

        String filterType = data.getString(JsonKeys.FILTER_TYPE.getKey());
        String input = data.getString(JsonKeys.INPUT.getKey());

        filterExercises(filterType, input);
    }

    // REQUIRES: selected filter and user input are not null
    // MODIFIES: this
    // EFFECTS: filters exercises given appropriate filter and input
    private void filterExercises(String selectedFilter, String input) {
        switch (selectedFilter) {
            case EXERCISE_NAME:
                exercisesByName = exercisesByName.filter(input);
                break;
            case EXERCISE_MUSCLE_GROUP:
                exercisesByName = exercisesByName
                        .filter(FitnessMetricParser.getInstance().getMuscleGroupByName(input));
                break;
            case EXERCISE_DIFFICULTY:
                exercisesByName = exercisesByName
                        .filter(FitnessMetricParser.getInstance().getDifficultyByLevel(parseInt(input)));
                break;
            case EXERCISE_TIME:
                exercisesByName = exercisesByName.filter(parseInt(input));
                break;
            case EXERCISE_FAVOURITE:
                exercisesByName = exercisesByName.filter();
        }
        updatePresenter();
        exercisesPanel.updateDisplayCollection();
    }

    // EFFECTS: parses the exercises from t and sets the current exercises to the given exercises
    private <T> void setExercises(T t) {
        ExercisesByName exercisesByName = (ExercisesByName) t;

        setExercises(exercisesByName);
    }

    // MODIFIES: this, exercisesByName, exercisesByNameMaster
    // EFFECTS: sets the current exercises to the given exercises
    private void setExercises(ExercisesByName exercisesByName) {
        this.exercisesByName = exercisesByName;
        exercisesByNameMaster = this.exercisesByName;

        updatePresenter();
        exercisesPanel.updateDisplayCollection();
    }

    // MODIFIES: this, fitnessApp
    // EFFECTS: switches to the panel for adding exercises
    private void addExercisePanel() {
        resetFilters();
        FitnessApp.getInstance().switchPanel(ADD_EXERCISE_COMMAND.getFitnessAppCommand());
    }

    // MODIFIES: this
    // EFFECTS: extracts each exercise's information from exercises
    @Override
    protected void extractData() {
        for (Exercise exercise : exercisesByName.getExercises().values()) {
            Vector<Object> exerciseData = new Vector<>();

            exerciseData.add(exercise.getName());
            exerciseData.add(exercise.getMuscleGroup().getMuscleGroup());
            exerciseData.add(exercise.getDifficulty().getDifficulty());
            exerciseData.add(exercise.getTimeMinutes());
            exerciseData.add(exercise.isFavourite());

            data.add(exerciseData);
        }
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

    // MODIFIES: this
    // EFFECTS: removes filters and resets the exercises
    private void resetFilters() {
        exercisesByName = exercisesByNameMaster;
        updatePresenter();
        exercisesPanel.updateDisplayCollection();
    }

    // MODIFIES: this, fitnessApp
    // EFFECTS: switches to the profile panel
    public void back() {
        resetFilters();
        FitnessApp.getInstance().switchPanel(PROFILE_COMMAND.getFitnessAppCommand());
    }

}
