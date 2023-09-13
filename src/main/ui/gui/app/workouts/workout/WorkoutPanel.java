package ui.gui.app.workouts.workout;

import model.metrics.Difficulty;
import model.workouts.workout.Workout;
import org.json.JSONObject;
import ui.gui.logic.displayelement.DisplayElementPanel;
import ui.gui.json.JsonKeys;
import ui.gui.logic.Presenter;
import ui.gui.app.FitnessAppCommands;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

import static ui.gui.app.FitnessAppCommands.BACK_COMMAND;
import static ui.gui.app.FitnessAppCommands.EDIT_COMMAND;

// Represents a panel for displaying a workout
public class WorkoutPanel extends DisplayElementPanel {
    private static final String WORKOUT_NAME_LABEL = "Name";
    private static final String WORKOUT_DIFFICULTY_LABEL = "Difficulty";
    private static final String WORKOUT_TIME_LABEL = "Time (min)";
    private static final String WORKOUT_FAVOURITE_LABEL = "Favourite?";

    private WorkoutPanelPresenter workoutPanelPresenter;

    private Map<String, JComboBox<String>> inputBoxes;

    protected JTable dataTable;
    protected JScrollPane scrollableDataTable;

    private JLabel name;
    private JComboBox<String> selectDifficulty;
    private JComboBox<String> selectFavourite;
    private JLabel time;

    // EFFECTS: makes a new workout panel
    public WorkoutPanel() {
        super();
        initializeFields();
        addDisplayComponents();
        initializePlacements();
        initializeActions();
        addComponents();
    }

    // MODIFIES: this
    // EFFECTS: initializes the components for the profile panel
    protected void initializeFields() {
        super.initializeFields();

        this.workoutPanelPresenter = new WorkoutPanelPresenter(this);

        this.inputBoxes = new HashMap<>();

        this.dataTable = new JTable(workoutPanelPresenter.getTableModel());

        this.scrollableDataTable = new JScrollPane(dataTable,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        this.name = new JLabel("Example Name");
        this.time = new JLabel("0");

        initializeInputs();
    }

    // MODIFIES: this
    // EFFECTS: initializes the input areas for the user for the workout panel
    @Override
    protected void initializeInputs() {
        initializeBoxes();
        collectBoxes();
    }

    // MODIFIES: this
    // EFFECTS: initializes the input boxes
    private void initializeBoxes() {
        this.selectDifficulty = new JComboBox<>();
        this.selectFavourite = new JComboBox<>();

        for (Difficulty difficulty : Difficulty.values()) {
            selectDifficulty.addItem(Integer.toString(difficulty.getDifficultyAsInt()));
        }

        selectFavourite.addItem(Boolean.toString(false));
        selectFavourite.addItem(Boolean.toString(true));
    }

    // MODIFIES: this
    // EFFECTS: collects the input boxes for convenient parsing
    private void collectBoxes() {
        inputBoxes.put(JsonKeys.WORKOUT_DIFFICULTY.getKey(), selectDifficulty);
        inputBoxes.put(JsonKeys.WORKOUT_FAVOURITE.getKey(), selectFavourite);
    }

    @Override
    // MODIFIES: this
    // EFFECTS: stores the components of the workout panel for convenient access
    protected void addDisplayComponents() {
        components.add(new JLabel("Workout"));
        components.add(scrollableDataTable);
        components.add(new JLabel(WORKOUT_NAME_LABEL));
        components.add(name);
        components.add(new JLabel(WORKOUT_DIFFICULTY_LABEL));
        components.add(selectDifficulty);
        components.add(new JLabel(WORKOUT_TIME_LABEL));
        components.add(time);
        components.add(new JLabel(WORKOUT_FAVOURITE_LABEL));
        components.add(selectFavourite);
        components.add(splashText);
        components.add(editButton);
        components.add(backButton);
    }

    @Override
    // MODIFIES: this, workoutPanelPresenter, fitnessApp
    // EFFECTS: handles the appropriate event for appropriate components
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(FitnessAppCommands.EDIT_COMMAND.getFitnessAppCommand())) {
            editWorkout();
        } else if (e.getActionCommand().equals(FitnessAppCommands.BACK_COMMAND.getFitnessAppCommand())) {
            back();
        }
    }

    // MODIFIES: workoutPanelPresenter
    // EFFECTS: updates workout info in the workout associated with the panel
    private void editWorkout() {
        JSONObject data = new JSONObject();
        JSONObject inputs = new JSONObject();
        JSONObject inputBoxesJson = new JSONObject();

        for (String name : inputBoxes.keySet()) {
            JComboBox<String> box = inputBoxes.get(name);
            inputBoxesJson.put(name, box.getSelectedItem());
        }
        inputs.put(JsonKeys.BOXES.getKey(), inputBoxesJson);

        data.put(JsonKeys.DATA.getKey(), inputs);

        workoutPanelPresenter.update(data, EDIT_COMMAND);
    }

    // MODIFIES: fitnessApp
    // EFFECTS: switches to the workouts panel
    private void back() {
        splashText.setText("");
        workoutPanelPresenter.update(null, BACK_COMMAND);
    }

    // MODIFIES: this
    // EFFECTS: updates the appropriate inputs with the given workout info
    void updateInputs(Workout workout) {
        name.setText(workout.getName());
        inputBoxes.get(JsonKeys.WORKOUT_DIFFICULTY.getKey())
                .setSelectedItem(Integer.toString(workout.getDifficulty().getDifficultyAsInt()));
        inputBoxes.get(JsonKeys.WORKOUT_FAVOURITE.getKey()).setSelectedItem(Boolean.toString(workout.isFavourite()));
        time.setText(Integer.toString(workout.getTimeMinutes()));
    }

    // MODIFIES: this
    // EFFECTS: updates the workout display
    void updateDisplayCollection() {
        dataTable.setModel(workoutPanelPresenter.getTableModel());
        scrollableDataTable.setViewportView(dataTable);
    }

    @Override
    // EFFECTS: returns the presenter associated with this panel
    public Presenter getPresenter() {
        return workoutPanelPresenter;
    }
}
