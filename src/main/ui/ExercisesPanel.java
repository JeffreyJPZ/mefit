package ui;

import model.Difficulty;
import model.Exercise;
import model.ExercisesByName;
import model.MuscleGroup;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import static java.lang.Integer.parseInt;
import static ui.FitnessAppCommands.*;

// Represents a panel with exercises for the fitness application
public class ExercisesPanel extends FitnessPanel {
    private static final int EXERCISES_WIDTH = 300;
    private static final int EXERCISES_HEIGHT = 300;
    private static final String EXERCISE_NAME = "Name";
    private static final String EXERCISE_MUSCLE_GROUP = "Muscle Group";
    private static final String EXERCISE_DIFFICULTY = "Difficulty";
    private static final String EXERCISE_TIME = "Time (min)";
    private static final String EXERCISE_FAVOURITE = "Favourite?";
    private static final int EXERCISE_NAME_POSITION = 0; // column for all exercise names

    private static final List<String> EXERCISE_INFO = Arrays.asList(EXERCISE_NAME, EXERCISE_MUSCLE_GROUP,
            EXERCISE_DIFFICULTY, EXERCISE_TIME, EXERCISE_FAVOURITE);
    private static final Vector<String> EXERCISE_INFO_VECTOR = new Vector<>(EXERCISE_INFO);

    private ExercisesByName exercisesByName;
    private ExercisesByName exercisesByNameMaster;

    private FitnessApp fitnessApp;

    private Vector<Vector<Object>> exercisesData;
    private DefaultTableModel tableModel;

    private JTable exercisesDataTable;
    private JScrollPane exercisesScrollableTable;
    private JButton addExerciseButton;
    private JButton removeExerciseButton;
    private JLabel filterLabel;
    private JComboBox<String> exerciseFilters;
    private JTextField inputFilter;
    private JButton filterExercisesButton;
    private JButton resetExerciseFiltersButton;
    private JButton backButton;

    // EFFECTS: creates an exercises panel
    public ExercisesPanel(FitnessApp fitnessApp) {
        super();
        initializeFields(fitnessApp);
        initializePlacements();
        initializeActions();
        addComponents();
    }

    // MODIFIES: this
    // EFFECTS: initializes the components for the exercises panel
    private void initializeFields(FitnessApp fitnessApp) {
        this.fitnessApp = fitnessApp;

        this.exercisesByName = new ExercisesByName(); // initializes sample exercises
        this.exercisesByNameMaster = exercisesByName;

        this.exercisesData = new Vector<>();

        extractExercisesData();

        this.exerciseFilters = new JComboBox<>();

        addFilters();

        this.tableModel = new DefaultTableModel(exercisesData, EXERCISE_INFO_VECTOR);
        this.exercisesDataTable = new JTable(tableModel);

        this.exercisesScrollableTable = new JScrollPane(exercisesDataTable,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        this.addExerciseButton = new JButton(ADD_EXERCISE_COMMAND.getFitnessAppCommand());
        this.removeExerciseButton = new JButton(REMOVE_EXERCISE_COMMAND.getFitnessAppCommand());
        this.filterLabel = new JLabel("Filters");
        this.inputFilter = new JTextField("Enter the desired filter here");
        this.filterExercisesButton = new JButton(FILTER_EXERCISE_COMMAND.getFitnessAppCommand());
        this.resetExerciseFiltersButton = new JButton(RESET_EXERCISE_FILTERS_COMMAND.getFitnessAppCommand());
        this.backButton = new JButton(BACK_COMMAND.getFitnessAppCommand());

        addDisplayComponents();
    }

    @Override
    // MODIFIES: this
    // EFFECTS: sets the placements of the components for the exercises panel
    protected void initializePlacements() {
        super.initializePlacements();

        exercisesScrollableTable.setPreferredSize(new Dimension(EXERCISES_WIDTH, EXERCISES_HEIGHT));
        exercisesScrollableTable.setVisible(true);

        inputFilter.setMaximumSize(new Dimension(EXERCISES_WIDTH, 10));
    }

    // MODIFIES: this
    // EFFECTS: adds each component to be displayed to components
    @Override
    protected void addDisplayComponents() {
        components.add(exercisesScrollableTable);
        components.add(addExerciseButton);
        components.add(removeExerciseButton);
        components.add(filterLabel);
        components.add(exerciseFilters);
        components.add(inputFilter);
        components.add(filterExercisesButton);
        components.add(resetExerciseFiltersButton);
        components.add(backButton);
    }

    @Override
    // MODIFIES: this
    // EFFECTS: sets the appropriate components to respond to appropriate events
    protected void initializeActions() {
        initializeAction(addExerciseButton, ADD_EXERCISE_COMMAND.getFitnessAppCommand());
        initializeAction(removeExerciseButton, REMOVE_EXERCISE_COMMAND.getFitnessAppCommand());
        initializeAction(filterExercisesButton, FILTER_EXERCISE_COMMAND.getFitnessAppCommand());
        initializeAction(resetExerciseFiltersButton, RESET_EXERCISE_FILTERS_COMMAND.getFitnessAppCommand());
        initializeAction(backButton, BACK_COMMAND.getFitnessAppCommand());
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

            exercisesData.add(exerciseData);
        }
    }

    // MODIFIES: this
    // EFFECTS: sets the current exercises to the given exercises
    public void setExercises(ExercisesByName exercisesByName) {
        this.exercisesByName = exercisesByName;
        exercisesByNameMaster = this.exercisesByName;
    }

    // MODIFIES: this
    // EFFECTS: adds an exercise to the exercises
    public void addExercise(Exercise exercise) {
        exercisesByName.addExercise(exercise);
    }

    // MODIFIES: this
    // EFFECTS: adds filter options to display
    private void addFilters() {
        for (String filter : EXERCISE_INFO) {
            exerciseFilters.addItem(filter);
        }
    }

    // MODIFIES: this, fitnessApp
    // EFFECTS: handles the appropriate event for each component
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(ADD_EXERCISE_COMMAND.getFitnessAppCommand())) {
            addExercisePanel();
        } else if (e.getActionCommand().equals(REMOVE_EXERCISE_COMMAND.getFitnessAppCommand())) {
            removeSelectedExercises();
        } else if (e.getActionCommand().equals(FILTER_EXERCISE_COMMAND.getFitnessAppCommand())) {
            filterExercises();
        } else if (e.getActionCommand().equals(RESET_EXERCISE_FILTERS_COMMAND.getFitnessAppCommand())) {
            resetExercises();
            updateTable();
        } else if (e.getActionCommand().equals(BACK_COMMAND.getFitnessAppCommand())) {
            profilePanel();
        }
    }

    // MODIFIES: this, fitnessApp
    // EFFECTS: switches to the panel for adding an exercise
    private void addExercisePanel() {
        resetExercises();
        fitnessApp.switchPanel(ADD_EXERCISE_COMMAND.getFitnessAppCommand());
    }

    // MODIFIES: this
    // EFFECTS: removes the selected exercises from the display
    private void removeSelectedExercises() {
        for (int i : exercisesDataTable.getSelectedRows()) {
            String exerciseName = (String) exercisesDataTable.getValueAt(i, EXERCISE_NAME_POSITION);
            exercisesByName.removeExercise(exerciseName);
        }

        updateTable();
    }

    // REQUIRES: selected filter and user input are not null
    // MODIFIES: this
    // EFFECTS: filters the exercises on the display
    private void filterExercises() {
        String selectedFilter = (String) exerciseFilters.getSelectedItem();
        String input = inputFilter.getText();

        filterExercisesBySelectedFilter(selectedFilter, input);

        updateTable();
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
    private void profilePanel() {
        resetExercises();
        fitnessApp.switchPanel(PROFILE_COMMAND.getFitnessAppCommand());
    }

    // MODIFIES: this
    // EFFECTS: updates the exercises display
    public void updateTable() {
        exercisesData.clear();
        extractExercisesData();
        tableModel.setDataVector(exercisesData, EXERCISE_INFO_VECTOR);
        exercisesDataTable.setModel(tableModel);
        exercisesScrollableTable.setViewportView(exercisesDataTable);
    }

    // MODIFIES: this
    // EFFECTS: resets the exercise display to unfiltered state
    private void resetExercises() {
        exercisesByName = exercisesByNameMaster;
    }

    // REQUIRES: muscleGroupName matches a muscle group
    // EFFECTS: returns the muscle group associated with the given name
    private MuscleGroup getMuscleGroupByName(String muscleGroupName) {
        MuscleGroup muscleGroup = null;

        for (MuscleGroup m : MuscleGroup.values()) {
            if (m.getMuscleGroup().equals(muscleGroupName)) {
                muscleGroup = m;
                break;
            }
        }
        return muscleGroup;
    }

    // REQUIRES: difficultyLevel matches a difficulty
    // EFFECTS: returns the difficulty associated with the given difficulty level
    private Difficulty getDifficultyByLevel(int difficultyLevel) {
        Difficulty difficulty = null;

        for (Difficulty d : Difficulty.values()) {
            if (d.getDifficulty() == difficultyLevel) {
                difficulty = d;
                break;
            }
        }
        return difficulty;
    }
}
