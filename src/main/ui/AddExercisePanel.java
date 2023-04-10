package ui;

import model.*;

import javax.swing.*;
import java.awt.event.ActionEvent;

import static java.lang.Integer.parseInt;
import static ui.FitnessAppCommands.*;

// Represents a panel for adding exercises for the fitness application
public class AddExercisePanel extends FitnessPanel {
    private static final String WEIGHTS_EXERCISE = "Weights";
    private static final String BODYWEIGHTS_EXERCISE = "Bodyweights";
    private static final String CARDIO_EXERCISE = "Cardio";

    private static final String[] exerciseTypes = {WEIGHTS_EXERCISE, BODYWEIGHTS_EXERCISE, CARDIO_EXERCISE};

    private ExercisesPanel exercisesPanel;
    private FitnessApp fitnessApp;

    private JTextField name;
    private JComboBox<String> selectType;
    private JComboBox<Integer> selectDifficulty;
    private JComboBox<String> selectMuscleGroup;
    private JTextField time;
    private JTextField weight;
    private JTextField sets;
    private JTextField reps;
    private JTextField distance;
    private JButton addExerciseButton;
    private JButton backButton;

    // EFFECTS: creates a panel for adding exercises
    public AddExercisePanel(FitnessApp fitnessApp, ExercisesPanel exercisesPanel) {
        super();
        initializeFields(fitnessApp, exercisesPanel);
        initializePlacements();
        initializeActions();
        addComponents();
    }

    // MODIFIES: this
    // EFFECTS: initializes the components of the add exercise panel
    private void initializeFields(FitnessApp fitnessApp, ExercisesPanel exercisesPanel) {
        this.fitnessApp = fitnessApp;
        this.exercisesPanel = exercisesPanel;

        this.name = new JTextField("Example Name");
        this.selectType = new JComboBox<>();
        this.selectDifficulty = new JComboBox<>();
        this.selectMuscleGroup = new JComboBox<>();

        for (Difficulty difficulty : Difficulty.values()) {
            this.selectDifficulty.addItem(difficulty.getDifficulty());
        }

        for (MuscleGroup muscleGroup : MuscleGroup.values()) {
            this.selectMuscleGroup.addItem(muscleGroup.getMuscleGroup());
        }

        for (String exerciseType : exerciseTypes) {
            this.selectType.addItem(exerciseType);
        }

        this.time = new JTextField("0");

        this.weight = new JTextField("Weight: Edit only for weights exercises");
        this.sets = new JTextField("Sets: Edit only for weights and bodyweights exercises");
        this.reps = new JTextField("Reps: Edit only for weights and bodyweights exercises");
        this.distance = new JTextField("Distance: Edit only for cardio exercises");

        this.addExerciseButton = new JButton(ADD_EXERCISE_COMMAND.getFitnessAppCommand());
        this.backButton = new JButton(BACK_COMMAND.getFitnessAppCommand());

        addDisplayComponents();
    }

    @Override
    // MODIFIES: this
    // EFFECTS: adds each component to be displayed to components
    protected void addDisplayComponents() {
        components.add(new JLabel("Name"));
        components.add(name);
        components.add(new JLabel("Exercise Type"));
        components.add(selectType);
        components.add(new JLabel("Difficulty"));
        components.add(selectDifficulty);
        components.add(new JLabel("Muscle Group"));
        components.add(selectMuscleGroup);
        components.add(new JLabel("Time (min)"));
        components.add(time);
        components.add(new JLabel("Weight (lbs)"));
        components.add(weight);
        components.add(new JLabel("Sets"));
        components.add(sets);
        components.add(new JLabel("Reps"));
        components.add(reps);
        components.add(new JLabel("Distance (m)"));
        components.add(distance);
        components.add(addExerciseButton);
        components.add(backButton);
    }

    @Override
    // MODIFIES: this
    // EFFECTS: sets the appropriate components to respond to appropriate events
    protected void initializeActions() {
        initializeAction(addExerciseButton, ADD_EXERCISE_COMMAND.getFitnessAppCommand());
        initializeAction(backButton, BACK_COMMAND.getFitnessAppCommand());
    }

    // MODIFIES: exercisesPanel, fitnessApp
    // EFFECTS: handles the appropriate event for each component
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(ADD_EXERCISE_COMMAND.getFitnessAppCommand())) {
            addExercise();
        } else if (e.getActionCommand().equals(BACK_COMMAND.getFitnessAppCommand())) {
            exercisesPanel();
        }
    }

    // REQUIRES: selected exercise type, muscle group, and difficulty are not null
    // MODIFIES: exercisesPanel, fitnessApp
    // EFFECTS: adds an exercise with given inputs to exercises, updates and switches to exercise panel
    private void addExercise() {
        String exerciseType = (String) selectType.getSelectedItem();
        String muscleGroupName = (String) selectMuscleGroup.getSelectedItem();
        int difficultyLevel = (int) selectDifficulty.getSelectedItem();

        Exercise exercise = getExerciseFromType(exerciseType, muscleGroupName, difficultyLevel);

        exercisesPanel.addExercise(exercise);
        exercisesPanel.updateTable();

        fitnessApp.switchPanel(EXERCISES_COMMAND.getFitnessAppCommand());
    }

    // REQUIRES: exerciseType matches a valid exercise type
    // EFFECTS: returns the appropriate exercise given exercise type
    private Exercise getExerciseFromType(String exerciseType, String muscleGroupName, int difficultyLevel) {
        switch (exerciseType) {
            case WEIGHTS_EXERCISE:
                return new WeightsExercise(name.getText(), getMuscleGroupByName(muscleGroupName),
                        parseInt(weight.getText()), parseInt(sets.getText()), parseInt(reps.getText()),
                        getDifficultyByLevel(difficultyLevel), parseInt(time.getText()));
            case BODYWEIGHTS_EXERCISE:
                return new BodyWeightsExercise(name.getText(), getMuscleGroupByName(muscleGroupName),
                        parseInt(sets.getText()), parseInt(reps.getText()), getDifficultyByLevel(difficultyLevel),
                        parseInt(time.getText()));
            case CARDIO_EXERCISE:
                return new CardioExercise(name.getText(), getMuscleGroupByName(muscleGroupName),
                        parseInt(distance.getText()), getDifficultyByLevel(difficultyLevel),
                        parseInt(time.getText()));
        }
        return null;
    }

    // MODIFIES: fitnessApp
    // EFFECTS: switches to the exercises panel
    private void exercisesPanel() {
        fitnessApp.switchPanel(EXERCISES_COMMAND.getFitnessAppCommand());
    }

    // REQUIRES: muscleGroupName matches a value in MuscleGroup
    // EFFECTS: returns the muscle group associated with the given muscle group name
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

    // REQUIRES: difficultyLevel matches a value in Difficulty
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
