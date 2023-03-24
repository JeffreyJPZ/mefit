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

    public ExercisesPanel(FitnessApp fitnessApp) {
        initializeFields(fitnessApp);
        initializePlacements();
        initializeActions();
        addComponents();
    }

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

    private void initializePlacements() {
        exercisesScrollableTable.setPreferredSize(new Dimension(EXERCISES_WIDTH, EXERCISES_HEIGHT));

        inputFilter.setMaximumSize(new Dimension(EXERCISES_WIDTH, 10));

        addExerciseButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        deleteExerciseButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        filterLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        exerciseFilters.setAlignmentX(Component.CENTER_ALIGNMENT);
        inputFilter.setAlignmentX(Component.CENTER_ALIGNMENT);
        filterExercisesButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        resetExerciseFiltersButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    private void initializeFields(FitnessApp fitnessApp) {
        this.fitnessApp = fitnessApp;

        exercisesByName = new ExercisesByName(); // initializes sample exercises
        exercisesByNameMaster = exercisesByName;

        exercisesData = new Vector<>();

        addExercisesToTable();

        exerciseFilters = new JComboBox<>();

        addFilters();

        tableModel = new DefaultTableModel(exercisesData, EXERCISE_INFO_VECTOR);
        exercisesDataTable = new JTable(tableModel);

        exercisesScrollableTable = new JScrollPane(exercisesDataTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        exercisesScrollableTable.setVisible(true);

        addExerciseButton = new JButton(ADD_EXERCISE_COMMAND.getFitnessAppCommand());
        deleteExerciseButton = new JButton(DELETE_EXERCISE_COMMAND.getFitnessAppCommand());
        filterLabel = new JLabel("Filters");
        inputFilter = new JTextField("Enter the desired filter here");
        filterExercisesButton = new JButton(FILTER_EXERCISE_COMMAND.getFitnessAppCommand());
        resetExerciseFiltersButton = new JButton(RESET_EXERCISE_FILTERS_COMMAND.getFitnessAppCommand());
        backButton = new JButton(BACK_COMMAND.getFitnessAppCommand());
    }

    private void addExercisesToTable() {
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

    public void setExercises(ExercisesByName exercisesByName) {
        this.exercisesByName = exercisesByName;
        exercisesByNameMaster = this.exercisesByName;
    }

    public void addExercise(Exercise exercise) {
        exercisesByName.addExercise(exercise);
    }

    private void deleteExercises() {
        for (int i : exercisesDataTable.getSelectedRows()) {
            String exerciseName = (String) tableModel.getDataVector().elementAt(i).get(0);
            exercisesByName.removeExercise(exerciseName);
        }

        updateTable();
    }

    public void updateTable() {
        exercisesData.clear();
        addExercisesToTable();
        tableModel.setDataVector(exercisesData, EXERCISE_INFO_VECTOR);
        exercisesDataTable.setModel(tableModel);
        exercisesScrollableTable.setViewportView(exercisesDataTable);
    }

    private void addFilters() {
        for (String filter : EXERCISE_INFO) {
            exerciseFilters.addItem(filter);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(ADD_EXERCISE_COMMAND.getFitnessAppCommand())) {
            exercisesByName = exercisesByNameMaster;
            fitnessApp.switchPanel(ADD_EXERCISE_COMMAND.getFitnessAppCommand());
        } else if (e.getActionCommand().equals(DELETE_EXERCISE_COMMAND.getFitnessAppCommand())) {
            deleteExercises();
        } else if (e.getActionCommand().equals(FILTER_EXERCISE_COMMAND.getFitnessAppCommand())) {
            String selectedFilter = (String) exerciseFilters.getSelectedItem();
            String input = inputFilter.getText();

            filterExercisesBySelectedFilter(selectedFilter, input);

            updateTable();
        } else if (e.getActionCommand().equals(RESET_EXERCISE_FILTERS_COMMAND.getFitnessAppCommand())) {
            exercisesByName = exercisesByNameMaster;
            updateTable();
        } else if (e.getActionCommand().equals(BACK_COMMAND.getFitnessAppCommand())) {
            exercisesByName = exercisesByNameMaster;
            fitnessApp.switchPanel(PROFILE_COMMAND.getFitnessAppCommand());
        }
    }

    private void filterExercisesBySelectedFilter(String filter, String input) {
        switch (filter) {
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
