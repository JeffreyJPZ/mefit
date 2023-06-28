package ui;

import model.Exercise;
import model.ExercisesByName;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Vector;

import static java.lang.Integer.parseInt;
import static ui.FitnessAppCommands.*;

// Represents a panel with exercises for the fitness application
public class ExercisesPanel extends DisplayCollectionPanel implements UIObserver {
    private static final String EXERCISE_NAME = "Name";
    private static final String EXERCISE_MUSCLE_GROUP = "Muscle Group";
    private static final String EXERCISE_DIFFICULTY = "Difficulty";
    private static final String EXERCISE_TIME = "Time (min)";
    private static final String EXERCISE_FAVOURITE = "Favourite?";

    private ExercisesByName exercisesByName;
    private ExercisesByName exercisesByNameMaster;

    // EFFECTS: creates an exercises panel
    public ExercisesPanel() {
        super();
        initializeFields();
        initializeActions();
        addDisplayComponents();
        addComponents();
        initializePlacements();
    }

    @Override
    // MODIFIES: this
    // EFFECTS: initializes the components for the exercises panel
    protected void initializeFields() {
        super.initializeFields();

        this.info = List.of(EXERCISE_NAME, EXERCISE_MUSCLE_GROUP,
                EXERCISE_DIFFICULTY, EXERCISE_TIME, EXERCISE_FAVOURITE);
        this.infoHeader = new Vector<>(info);

        this.filterable = info;

        this.exercisesByName = new ExercisesByName(); // initializes sample exercises
        this.exercisesByNameMaster = exercisesByName;

        this.data = new Vector<>();

        extractExercisesData();
        addFilters();

        this.tableModel = new DefaultTableModel(data, infoHeader);
        this.dataTable = new JTable(tableModel);

        this.scrollableDataTable = new JScrollPane(dataTable,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
    }

    // MODIFIES: this
    // EFFECTS: adds each component to be displayed to components
    @Override
    protected void addDisplayComponents() {
        super.addDisplayComponents();
        components.add(0, scrollableDataTable);
    }

    // MODIFIES: this
    // EFFECTS: updates the exercises display
    @Override
    protected void updateDisplayCollection() {
        data.clear();
        extractExercisesData();
        tableModel.setDataVector(data, infoHeader);
        dataTable.setModel(tableModel);
        scrollableDataTable.setViewportView(dataTable);
    }

    // MODIFIES: this
    // EFFECTS: extracts each exercise's information from exercises
    private void extractExercisesData() {
        for (Exercise exercise : exercisesByName.getExercises().values()) {
            Vector<Object> exerciseData = new Vector<>();

            exerciseData.add(exercise.getName());
            exerciseData.add(exercise.getMuscleGroup().getMuscleGroup());
            exerciseData.add(exercise.getDifficulty().getDifficulty());
            exerciseData.add(exercise.getTime());
            exerciseData.add(exercise.isFavourite());

            data.add(exerciseData);
        }
    }

    // MODIFIES: this
    // EFFECTS: sets the current exercises to the given exercises
    private void setExercises(ExercisesByName exercisesByName) {
        this.exercisesByName = exercisesByName;
        exercisesByNameMaster = this.exercisesByName;
    }

    // MODIFIES: this
    // EFFECTS: adds an exercise to the exercises
    private void addExercise(Exercise exercise) {
        exercisesByName.addExercise(exercise);
    }

    // MODIFIES: this
    // EFFECTS: adds filter options to display
    private void addFilters() {
        for (String filter : filterable) {
            filters.addItem(filter);
        }
    }

    // MODIFIES: this, fitnessApp
    // EFFECTS: handles the appropriate event for each component
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(ADD_COMMAND.getFitnessAppCommand())) {
            addExercisePanel();
        } else if (e.getActionCommand().equals(REMOVE_COMMAND.getFitnessAppCommand())) {
            removeSelectedExercises();
        } else if (e.getActionCommand().equals(FILTER_COMMAND.getFitnessAppCommand())) {
            filterExercises();
        } else if (e.getActionCommand().equals(RESET_FILTERS_COMMAND.getFitnessAppCommand())) {
            resetExercises();
            updateDisplayCollection();
        } else if (e.getActionCommand().equals(BACK_COMMAND.getFitnessAppCommand())) {
            back();
        }
    }

    // MODIFIES: this, fitnessApp
    // EFFECTS: switches to the panel for adding an exercise
    private void addExercisePanel() {
        resetExercises();
        FitnessApp.getInstance().switchPanel(ADD_EXERCISE_COMMAND.getFitnessAppCommand());
    }

    // MODIFIES: this
    // EFFECTS: removes the selected exercises from the display
    private void removeSelectedExercises() {
        for (int i : dataTable.getSelectedRows()) {
            String exerciseName = (String) dataTable.getValueAt(i, ID_POSITION);
            exercisesByName.removeExercise(exerciseName);
        }

        updateDisplayCollection();
    }

    // REQUIRES: selected filter and user input are not null
    // MODIFIES: this
    // EFFECTS: filters the exercises on the display
    private void filterExercises() {
        String selectedFilter = (String) filters.getSelectedItem();
        String input = inputFilter.getText();

        filterExercisesBySelectedFilter(selectedFilter, input);

        updateDisplayCollection();
    }

    // REQUIRES: selected filter and user input are not null
    // MODIFIES: this
    // EFFECTS: filters exercises given appropriate filter and input
    private void filterExercisesBySelectedFilter(String selectedFilter, String input) {
        switch (selectedFilter) {
            case EXERCISE_NAME:
                exercisesByName = exercisesByName.filter(input);
                break;
            case EXERCISE_MUSCLE_GROUP:
                exercisesByName = exercisesByName.filterMuscleGroup(getMuscleGroupByName(input));
                break;
            case EXERCISE_DIFFICULTY:
                exercisesByName = exercisesByName.filterDifficulty(getDifficultyByLevel(parseInt(input)));
                break;
            case EXERCISE_TIME:
                exercisesByName = exercisesByName.filterTime(parseInt(input));
                break;
            case EXERCISE_FAVOURITE:
                exercisesByName = exercisesByName.filterFavourite();
        }
    }

    // MODIFIES: this, fitnessApp
    // EFFECTS: switches to the profile panel
    private void back() {
        resetExercises();
        FitnessApp.getInstance().switchPanel(PROFILE_COMMAND.getFitnessAppCommand());
    }

    // MODIFIES: this
    // EFFECTS: resets the exercise display to unfiltered state
    private void resetExercises() {
        exercisesByName = exercisesByNameMaster;
    }

    // MODIFIES: this
    // EFFECTS: updates the exercises panel with t if key is a match
    @Override
    public <T> void update(T t, FitnessAppCommands key) {
        if (key.getFitnessAppCommand().equals(ADD_COMMAND.getFitnessAppCommand())) {
            Exercise exercise = (Exercise) t;
            addExercise(exercise);
        } else if (key.getFitnessAppCommand().equals(PROFILE_COMMAND.getFitnessAppCommand())) {
            ExercisesByName exercises = (ExercisesByName) t;
            setExercises(exercises);
        }
        updateDisplayCollection();
    }
}
