package ui.gui.app.workouts;

import org.json.JSONObject;
import ui.gui.logic.displaycollection.DisplayCollectionPanel;
import ui.gui.json.JsonKeys;
import ui.gui.logic.Presenter;

import javax.swing.*;
import java.awt.event.ActionEvent;

import static ui.gui.app.FitnessAppCommands.*;
import static ui.gui.app.FitnessAppCommands.BACK_COMMAND;

// Represents a panel with workouts for the fitness application
public class WorkoutsPanel extends DisplayCollectionPanel {
    private WorkoutsPanelPresenter workoutsPanelPresenter;

    // EFFECTS: makes a new workouts panel
    public WorkoutsPanel() {
        super(true);
        initializeFields();
        addDisplayComponents();
        initializePlacements();
        initializeActions();
        addComponents();
    }

    @Override
    // MODIFIES: this
    // EFFECTS: initializes the components for the workouts panel
    protected void initializeFields() {
        super.initializeFields();

        this.workoutsPanelPresenter = new WorkoutsPanelPresenter(this);

        this.dataTable = new JTable(workoutsPanelPresenter.getTableModel());

        this.scrollableDataTable = new JScrollPane(dataTable,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        addFilters();
    }

    // MODIFIES: this
    // EFFECTS: adds each component to be displayed to components
    @Override
    protected void addDisplayComponents() {
        super.addDisplayComponents();

        components.add(0, new JLabel("Workouts"));
        components.add(1, scrollableDataTable);
    }

    // MODIFIES: this
    // EFFECTS: adds filter options to display
    private void addFilters() {
        for (String filter : workoutsPanelPresenter.getFilters()) {
            filters.addItem(filter);
        }
    }

    // MODIFIES: workoutsPanelPresenter, fitnessApp
    // EFFECTS: handles the appropriate event for appropriate components
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(VIEW_COMMAND.getFitnessAppCommand())) {
            workoutPanel();
        } else if (e.getActionCommand().equals(ADD_COMMAND.getFitnessAppCommand())) {
            addWorkoutPanel();
        } else if (e.getActionCommand().equals(REMOVE_COMMAND.getFitnessAppCommand())) {
            removeSelectedWorkouts();
        } else if (e.getActionCommand().equals(FILTER_COMMAND.getFitnessAppCommand())) {
            filterWorkouts();
        } else if (e.getActionCommand().equals(RESET_FILTERS_COMMAND.getFitnessAppCommand())) {
            resetFilters();
        } else if (e.getActionCommand().equals(BACK_COMMAND.getFitnessAppCommand())) {
            back();
        }
    }

    // MODIFIES: this, workoutPanel, workoutPanelPresenter, fitnessApp
    // EFFECTS: switches to the workout panel
    //          if more than one profile is selected
    private void workoutPanel() {
        if (dataTable.getSelectedRowCount() > 1) {
            setText("Please select one workout only.");
            return;
        }

        String name = getNameFromSelectedWorkout();

        if (name == null) {
            setText("Please select one workout.");
            return;
        }

        setText("");

        JSONObject data = new JSONObject();
        JSONObject workoutName = new JSONObject();

        workoutName.put(JsonKeys.WORKOUT_NAME.getKey(), name);
        data.put(JsonKeys.DATA.getKey(), workoutName);

        workoutsPanelPresenter.update(data, VIEW_COMMAND);
    }

    // EFFECTS: returns the name from the currently selected workout
    //          if no workout is selected, returns null
    private String getNameFromSelectedWorkout() {
        int selectedWorkoutRowIndex = dataTable.getSelectedRow();

        if (selectedWorkoutRowIndex == -1) {
            return null;
        } else {
            return (String) dataTable.getValueAt(selectedWorkoutRowIndex, ID_POSITION);
        }
    }

    // MODIFIES: workoutsPanelPresenter, fitnessApp
    // EFFECTS: switches to the panel for adding a workout
    private void addWorkoutPanel() {
        workoutsPanelPresenter.update(null, ADD_COMMAND);
    }

    // MODIFIES: workoutsPanelPresenter, this
    // EFFECTS: removes the selected workouts from the display
    private void removeSelectedWorkouts() {
        for (int i : dataTable.getSelectedRows()) {
            String name = (String) dataTable.getValueAt(i, ID_POSITION);

            JSONObject data = new JSONObject();
            JSONObject workoutName = new JSONObject();

            workoutName.put(JsonKeys.WORKOUT_NAME.getKey(), name);
            data.put(JsonKeys.DATA.getKey(), workoutName);

            workoutsPanelPresenter.update(data, REMOVE_COMMAND);
        }
    }

    // REQUIRES: selected filter and user input are not null
    // MODIFIES: workoutsPanelPresenter, this
    // EFFECTS: filters the workouts on the display
    private void filterWorkouts() {
        String filterType = (String) filters.getSelectedItem();
        String input = inputFilter.getText();

        JSONObject data = new JSONObject();
        JSONObject filterData = new JSONObject();

        filterData.put(JsonKeys.FILTER_TYPE.getKey(), filterType);
        filterData.put(JsonKeys.INPUT.getKey(), input);

        data.put(JsonKeys.DATA.getKey(), filterData);

        workoutsPanelPresenter.update(data, FILTER_COMMAND);
    }

    // MODIFIES: this, workoutsPanelPresenter
    // EFFECTS: resets the workout filters on the display
    private void resetFilters() {
        workoutsPanelPresenter.update(null, RESET_FILTERS_COMMAND);
    }


    // MODIFIES: fitnessApp
    // EFFECTS: switches to the profile panel
    private void back() {
        workoutsPanelPresenter.update(null, BACK_COMMAND);
    }

    // MODIFIES: this
    // EFFECTS: updates the workouts display
    @Override
    protected void updateDisplayCollection() {
        dataTable.setModel(workoutsPanelPresenter.getTableModel());
        scrollableDataTable.setViewportView(dataTable);
    }

    @Override
    public Presenter getPresenter() {
        return workoutsPanelPresenter;
    }
}
