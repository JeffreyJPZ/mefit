package ui;

import model.Difficulty;
import model.Exercise;
import model.ExercisesByName;
import model.MuscleGroup;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import static java.lang.Integer.parseInt;
import static ui.FitnessAppCommands.*;

// Represents the exercises panel for the fitness application
public class ExercisesPanel extends JPanel implements ActionListener {
    private static final int EXERCISES_WIDTH = 300;
    private static final int EXERCISES_HEIGHT = 300;
    private static final String EXERCISE_NAME = "Name";
    private static final String EXERCISE_MUSCLE_GROUP = "Muscle Group";
    private static final String EXERCISE_DIFFICULTY = "Difficulty";
    private static final String EXERCISE_TIME = "Time";
    private static final String EXERCISE_FAVOURITE = "Favourite?";

    private static final List<String> EXERCISE_INFO = List.of(EXERCISE_NAME, EXERCISE_MUSCLE_GROUP,
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
    private JButton deleteExerciseButton;
    private JLabel filterLabel;
    private JComboBox<String> exerciseFilters;
    private JTextField inputFilter;
    private JButton filterExercisesButton;
    private JButton resetExerciseFiltersButton;
    private JButton backButton;

    // EFFECTS: creates the exercises panel
    public ExercisesPanel(FitnessApp fitnessApp) {
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
        this.deleteExerciseButton = new JButton(DELETE_EXERCISE_COMMAND.getFitnessAppCommand());
        this.filterLabel = new JLabel("Filters");
        this.inputFilter = new JTextField("Enter the desired filter here");
        this.filterExercisesButton = new JButton(FILTER_EXERCISE_COMMAND.getFitnessAppCommand());
        this.resetExerciseFiltersButton = new JButton(RESET_EXERCISE_FILTERS_COMMAND.getFitnessAppCommand());
        this.backButton = new JButton(BACK_COMMAND.getFitnessAppCommand());
    }

    // MODIFIES: this
    // EFFECTS: sets the placements of the components for the exercises panel
    private void initializePlacements() {
        addExerciseButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        deleteExerciseButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        filterLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        exerciseFilters.setAlignmentX(Component.CENTER_ALIGNMENT);
        inputFilter.setAlignmentX(Component.CENTER_ALIGNMENT);
        filterExercisesButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        resetExerciseFiltersButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        exercisesScrollableTable.setPreferredSize(new Dimension(EXERCISES_WIDTH, EXERCISES_HEIGHT));
        exercisesScrollableTable.setVisible(true);

        inputFilter.setMaximumSize(new Dimension(EXERCISES_WIDTH, 10));

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    // MODIFIES: this
    // EFFECTS: sets the components to respond to appropriate events
    private void initializeActions() {
        addExerciseButton.setActionCommand(ADD_EXERCISE_COMMAND.getFitnessAppCommand());
        addExerciseButton.addActionListener(this);
        deleteExerciseButton.setActionCommand(DELETE_EXERCISE_COMMAND.getFitnessAppCommand());
        deleteExerciseButton.addActionListener(this);
        filterExercisesButton.setActionCommand(FILTER_EXERCISE_COMMAND.getFitnessAppCommand());
        filterExercisesButton.addActionListener(this);
        resetExerciseFiltersButton.setActionCommand(RESET_EXERCISE_FILTERS_COMMAND.getFitnessAppCommand());
        resetExerciseFiltersButton.addActionListener(this);
        backButton.setActionCommand(BACK_COMMAND.getFitnessAppCommand());
        backButton.addActionListener(this);
    }

    // MODIFIES: this
    // EFFECTS: adds components to the exercises panel
    private void addComponents() {
        add(Box.createVerticalGlue());
        add(exercisesScrollableTable);
        add(Box.createVerticalGlue());
        add(addExerciseButton);
        add(Box.createVerticalGlue());
        add(deleteExerciseButton);
        add(Box.createVerticalGlue());
        add(filterLabel);
        add(Box.createVerticalGlue());
        add(exerciseFilters);
        add(Box.createVerticalGlue());
        add(inputFilter);
        add(Box.createVerticalGlue());
        add(filterExercisesButton);
        add(Box.createVerticalGlue());
        add(resetExerciseFiltersButton);
        add(Box.createVerticalGlue());
        add(backButton);
        add(Box.createVerticalGlue());
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
        } else if (e.getActionCommand().equals(DELETE_EXERCISE_COMMAND.getFitnessAppCommand())) {
            deleteSelectedExercises();
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
    // EFFECTS: deletes the selected exercises from the display
    private void deleteSelectedExercises() {
        for (int i : exercisesDataTable.getSelectedRows()) {
            String exerciseName = (String) tableModel.getDataVector().elementAt(i).get(0);
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
