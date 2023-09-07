package ui;

import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import static ui.FitnessAppCommands.*;

// Represents a panel with profiles for the fitness application
public class ProfilesPanel extends DisplayCollectionPanel implements UIObserver {
    private static final String WELCOME_TEXT = "Welcome to the application!";

    private ProfilesPanelPresenter profilesPanelPresenter;

    private JButton saveButton;
    private JButton loadButton;

    // EFFECTS: creates a profiles panel
    public ProfilesPanel() {
        super(true);
        initializeFields();
        addDisplayComponents();
        initializePlacements();
        initializeActions();
        addComponents();
    }

    // MODIFIES: this
    // EFFECTS: initializes the components for the profiles panel
    protected void initializeFields() {
        super.initializeFields();

        this.profilesPanelPresenter = new ProfilesPanelPresenter(this);

        this.dataTable = new JTable(profilesPanelPresenter.getTableModel());

        this.scrollableDataTable = new JScrollPane(dataTable,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        this.splashText = new JLabel(WELCOME_TEXT);
        addFilters();
        this.saveButton = new JButton(SAVE_COMMAND.getFitnessAppCommand());
        this.loadButton = new JButton(LOAD_COMMAND.getFitnessAppCommand());
    }

    // MODIFIES: this
    // EFFECTS: initializes the placements for the profiles panel
    @Override
    protected void initializePlacements() {
        super.initializePlacements();

        scrollableDataTable.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        scrollableDataTable.setVisible(true);

        inputFilter.setMaximumSize(new Dimension(WIDTH, 10));
    }

    // EFFECTS: returns the model associated with this profiles panel
    @Override
    public Presenter getPresenter() {
        return profilesPanelPresenter;
    }

    @Override
    // MODIFIES: this
    // EFFECTS: adds each component to be displayed to components
    protected void addDisplayComponents() {
        super.addDisplayComponents();

        components.add(0, splashText);
        components.add(0, scrollableDataTable);
        components.add(components.size() - 1, saveButton);
        components.add(components.size() - 1, loadButton);
    }

    @Override
    // MODIFIES: this
    // EFFECTS: sets the appropriate components to respond to appropriate events
    protected void initializeActions() {
        super.initializeActions();

        initializeAction(saveButton, SAVE_COMMAND.getFitnessAppCommand());
        initializeAction(loadButton, LOAD_COMMAND.getFitnessAppCommand());
    }

    @Override
    // MODIFIES: this, profilesPanelPresenter, fitnessApp
    // EFFECTS: handles the appropriate event for appropriate components
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(VIEW_COMMAND.getFitnessAppCommand())) {
            profilePanel();
        } else if (e.getActionCommand().equals(ADD_COMMAND.getFitnessAppCommand())) {
            addProfilePanel();
        } else if (e.getActionCommand().equals(REMOVE_COMMAND.getFitnessAppCommand())) {
            removeSelectedProfile();
        } else if (e.getActionCommand().equals(FILTER_COMMAND.getFitnessAppCommand())) {
            filterProfiles();
        } else if (e.getActionCommand().equals(RESET_FILTERS_COMMAND.getFitnessAppCommand())) {
            resetFilters();
        } else if (e.getActionCommand().equals(SAVE_COMMAND.getFitnessAppCommand())) {
            saveProfiles();
        } else if (e.getActionCommand().equals(LOAD_COMMAND.getFitnessAppCommand())) {
            loadProfiles();
        } else if (e.getActionCommand().equals(BACK_COMMAND.getFitnessAppCommand())) {
            back();
        }
    }

    // MODIFIES: this, profilePanel, profilePanelPresenter, fitnessApp
    // EFFECTS: switches to the profile panel for the selected profile,
    //          if more than one profile is selected, indicates only one selection should be made
    //          if no profile is selected, indicates that a selection should be made
    private void profilePanel() {
        if (dataTable.getSelectedRowCount() > 1) {
            setText("Please select one profile only.");
            return;
        }

        int id = getIdFromSelectedProfile();

        if (id == -1) {
            setText("Please select one profile.");
            return;
        }

        setText(WELCOME_TEXT);

        JSONObject data = new JSONObject();
        JSONObject profileID = new JSONObject();

        profileID.put(JsonKeys.PROFILE_ID.getKey(), id);
        data.put(JsonKeys.DATA.getKey(), profileID);

        profilesPanelPresenter.update(data, VIEW_COMMAND);
    }

    // MODIFIES: this
    // EFFECTS: adds filter options to display
    private void addFilters() {
        for (String filter : profilesPanelPresenter.getFilters()) {
            filters.addItem(filter);
        }
    }

    // REQUIRES: selected filter and user input are not null
    // MODIFIES: this, profilesPanelPresenter
    // EFFECTS: filters the profiles on the display
    private void filterProfiles() {
        String filterType = (String) filters.getSelectedItem();
        String input = inputFilter.getText();

        JSONObject data = new JSONObject();
        JSONObject filterData = new JSONObject();

        filterData.put(JsonKeys.FILTER_TYPE.getKey(), filterType);
        filterData.put(JsonKeys.INPUT.getKey(), input);

        data.put(JsonKeys.DATA.getKey(), filterData);

        profilesPanelPresenter.update(data, FILTER_COMMAND);
    }

    // MODIFIES: this, profilesPanelPresenter
    // EFFECTS: reset the filters on the display
    private void resetFilters() {
        profilesPanelPresenter.update(null, RESET_FILTERS_COMMAND);
    }

    // REQUIRES: selected profile is not null
    // EFFECTS: returns the id associated with the selected profile
    //          if no row is selected, returns -1
    private int getIdFromSelectedProfile() {
        int selectedProfileRowIndex = dataTable.getSelectedRow();

        if (selectedProfileRowIndex == -1) {
            return selectedProfileRowIndex;
        } else {
            return (int) dataTable.getValueAt(selectedProfileRowIndex, ID_POSITION);
        }
    }

    // MODIFIES: profilesPanelPresenter, fitnessApp
    // EFFECTS: switches to the panel for adding a profile
    private void addProfilePanel() {
        profilesPanelPresenter.update(null, ADD_COMMAND);
    }

    // REQUIRES: selected profile is not null
    // MODIFIES: this, profilesPanelPresenter
    // EFFECTS: removes the selected profile from the display
    private void removeSelectedProfile() {
        JSONObject data = new JSONObject();
        JSONObject profileID = new JSONObject();

        profileID.put(JsonKeys.PROFILE_ID.getKey(), getIdFromSelectedProfile());
        data.put(JsonKeys.DATA.getKey(), profileID);

        profilesPanelPresenter.update(data, REMOVE_COMMAND);
    }

    // MODIFIES: profilesPanelPresenter
    // EFFECTS: saves profiles to file
    private void saveProfiles() {
        profilesPanelPresenter.update(null, SAVE_COMMAND);
    }

    // MODIFIES: profilesPanelPresenter
    // EFFECTS: loads profiles from file and updates the display of profiles
    private void loadProfiles() {
        profilesPanelPresenter.update(null, LOAD_COMMAND);
    }

    // MODIFIES: this, fitnessApp
    // EFFECTS: returns to the previous panel to the profiles panel
    private void back() {
        setText(WELCOME_TEXT);
        profilesPanelPresenter.update(null, BACK_COMMAND);
    }

    @Override
    // MODIFIES: this
    // EFFECTS: updates the profiles display
    protected void updateDisplayCollection() {
        dataTable.setModel(profilesPanelPresenter.getTableModel());
        scrollableDataTable.setViewportView(dataTable);
    }
}
