package ui;

import org.json.JSONObject;

import javax.swing.*;
import java.awt.event.ActionEvent;

import static java.lang.Integer.parseInt;
import static ui.FitnessAppCommands.*;

// Represents a panel with exercises for the fitness application
public class ExercisesPanel extends DisplayCollectionPanel {
    private ExercisesPanelPresenter exercisesPanelPresenter;

    // EFFECTS: creates an exercises panel
    public ExercisesPanel() {
        super(true);
        initializeFields();
        addDisplayComponents();
        initializePlacements();
        initializeActions();
        addComponents();
    }

    @Override
    // MODIFIES: this
    // EFFECTS: initializes the components for the exercises panel
    protected void initializeFields() {
        super.initializeFields();

        this.exercisesPanelPresenter = new ExercisesPanelPresenter(this);

        this.dataTable = new JTable(exercisesPanelPresenter.getTableModel());

        this.scrollableDataTable = new JScrollPane(dataTable,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        addFilters();
    }

    // MODIFIES: this
    // EFFECTS: adds each component to be displayed to components
    @Override
    protected void addDisplayComponents() {
        super.addDisplayComponents();
        components.add(0, scrollableDataTable);
    }

    // MODIFIES: this
    // EFFECTS: adds filter options to display
    private void addFilters() {
        for (String filter : exercisesPanelPresenter.getFilters()) {
            filters.addItem(filter);
        }
    }

    // MODIFIES: exercisesPanelPresenter, fitnessApp
    // EFFECTS: handles the appropriate event for appropriate components
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(VIEW_COMMAND.getFitnessAppCommand())) {
            exercisePanel();
        } else if (e.getActionCommand().equals(ADD_COMMAND.getFitnessAppCommand())) {
            addExercisePanel();
        } else if (e.getActionCommand().equals(REMOVE_COMMAND.getFitnessAppCommand())) {
            removeSelectedExercises();
        } else if (e.getActionCommand().equals(FILTER_COMMAND.getFitnessAppCommand())) {
            filterExercises();
        } else if (e.getActionCommand().equals(RESET_FILTERS_COMMAND.getFitnessAppCommand())) {
            resetFilters();
        } else if (e.getActionCommand().equals(BACK_COMMAND.getFitnessAppCommand())) {
            back();
        }
    }

    // MODIFIES: this, exercisePanel, exercisePanelPresenter, fitnessApp
    // EFFECTS: switches to the exercise panel
    //          if more than one profile is selected
    private void exercisePanel() {
        if (dataTable.getSelectedRowCount() > 1) {
            setText("Please select one exercise only.");
            return;
        }

        String name = getNameFromSelectedExercise();

        if (name == null) {
            setText("Please select one exercise.");
            return;
        }

        setText("");

        JSONObject data = new JSONObject();
        JSONObject exerciseName = new JSONObject();

        exerciseName.put(JsonKeys.EXERCISE_NAME.getKey(), name);
        data.put(JsonKeys.DATA.getKey(), exerciseName);

        exercisesPanelPresenter.update(data, VIEW_COMMAND);
    }

    // EFFECTS: returns the name from the currently selected exercise
    //          if no exercise is selected, returns null
    private String getNameFromSelectedExercise() {
        int selectedExerciseRowIndex = dataTable.getSelectedRow();

        if (selectedExerciseRowIndex == -1) {
            return null;
        } else {
            return (String) dataTable.getValueAt(selectedExerciseRowIndex, ID_POSITION);
        }
    }


    // MODIFIES: exercisesPanelPresenter, fitnessApp
    // EFFECTS: switches to the panel for adding an exercise
    private void addExercisePanel() {
        exercisesPanelPresenter.update(null, ADD_COMMAND);
    }

    // MODIFIES: exercisesPanelPresenter, this
    // EFFECTS: removes the selected exercises from the display
    private void removeSelectedExercises() {
        for (int i : dataTable.getSelectedRows()) {
            String name = (String) dataTable.getValueAt(i, ID_POSITION);

            JSONObject data = new JSONObject();
            JSONObject exerciseName = new JSONObject();

            exerciseName.put(JsonKeys.EXERCISE_NAME.getKey(), name);
            data.put(JsonKeys.DATA.getKey(), exerciseName);

            exercisesPanelPresenter.update(data, REMOVE_COMMAND);
        }
    }

    // REQUIRES: selected filter and user input are not null
    // MODIFIES: exercisesPanelPresenter, this
    // EFFECTS: filters the exercises on the display
    private void filterExercises() {
        String filterType = (String) filters.getSelectedItem();
        String input = inputFilter.getText();

        JSONObject data = new JSONObject();
        JSONObject filterData = new JSONObject();

        filterData.put(JsonKeys.FILTER_TYPE.getKey(), filterType);
        filterData.put(JsonKeys.INPUT.getKey(), input);

        data.put(JsonKeys.DATA.getKey(), filterData);

        exercisesPanelPresenter.update(data, FILTER_COMMAND);
    }


    // MODIFIES: this, exercisesPanelPresenter
    // EFFECTS: resets the exercise filters on the display
    private void resetFilters() {
        exercisesPanelPresenter.update(null, RESET_FILTERS_COMMAND);
    }


    // MODIFIES: fitnessApp
    // EFFECTS: switches to the profile panel
    private void back() {
        exercisesPanelPresenter.update(null, BACK_COMMAND);
    }

    // MODIFIES: this
    // EFFECTS: updates the exercises display
    @Override
    protected void updateDisplayCollection() {
        dataTable.setModel(exercisesPanelPresenter.getTableModel());
        scrollableDataTable.setViewportView(dataTable);
    }

    @Override
    public Presenter getPresenter() {
        return exercisesPanelPresenter;
    }
}
