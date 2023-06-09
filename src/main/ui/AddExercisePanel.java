package ui;

import model.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;

import static java.lang.Integer.parseInt;
import static ui.FitnessAppCommands.*;

// Represents a panel for adding exercises for the fitness application
public class AddExercisePanel extends FitnessPanel {
    private static final String WEIGHTS_EXERCISE = "Weights";
    private static final String BODYWEIGHTS_EXERCISE = "Bodyweights";
    private static final String CARDIO_EXERCISE = "Cardio";

    private static final List<String> exerciseTypes = List.of(WEIGHTS_EXERCISE, BODYWEIGHTS_EXERCISE, CARDIO_EXERCISE);

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
    public AddExercisePanel() {
        super();
        initializeFields();
        initializePlacements();
        initializeActions();
        addComponents();
    }

    // MODIFIES: this
    // EFFECTS: initializes the components of the panel for adding exercises
    private void initializeFields() {
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

        this.addExerciseButton = new JButton(ADD_COMMAND.getFitnessAppCommand());
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
        initializeAction(addExerciseButton, ADD_COMMAND.getFitnessAppCommand());
        initializeAction(backButton, BACK_COMMAND.getFitnessAppCommand());
        selectType.setActionCommand(SELECT_TYPE_COMMAND.getFitnessAppCommand());
        selectType.addActionListener(this);
    }

    // MODIFIES: exercisesPanel, fitnessApp
    // EFFECTS: handles the appropriate event for each component
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(ADD_COMMAND.getFitnessAppCommand())) {
            addExercise();
        } else if (e.getActionCommand().equals(BACK_COMMAND.getFitnessAppCommand())) {
            back();
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

        notifyAll(exercise, ADD_COMMAND);

        back();
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
    private void back() {
        FitnessApp.getInstance().switchPanel(EXERCISES_COMMAND.getFitnessAppCommand());
    }
}
