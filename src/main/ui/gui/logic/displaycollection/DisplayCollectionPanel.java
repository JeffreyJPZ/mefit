package ui.gui.logic.displaycollection;

import ui.gui.app.FitnessPanel;

import javax.swing.*;
import java.awt.*;

import static ui.gui.app.FitnessAppCommands.*;

// represents a panel for displaying elements of a fitness collection
public abstract class DisplayCollectionPanel extends FitnessPanel {
    protected static final int ID_POSITION = 0; // column index for identifiers of elements in the table

    protected boolean filterable;

    protected JTable dataTable;
    protected JScrollPane scrollableDataTable;

    protected JLabel splashText;
    protected JButton viewButton;
    private JButton addButton;
    private JButton removeButton;
    private JLabel filterLabel;
    protected JComboBox<String> filters;
    protected JTextField inputFilter;
    private JButton filterButton;
    private JButton resetFiltersButton;
    private JButton backButton;

    // EFFECTS: makes a panel for displaying a fitness collection
    //          if filterable, adds components for filtering
    public DisplayCollectionPanel(boolean filterable) {
        super();
        this.filterable = filterable;

        initializeFields();
        initializeActions();
    }

    // EFFECTS: initializes the components for displaying a fitness collection
    protected void initializeFields() {
        this.splashText = new JLabel();
        this.viewButton = new JButton(VIEW_COMMAND.getFitnessAppCommand());
        this.addButton = new JButton(ADD_COMMAND.getFitnessAppCommand());
        this.removeButton = new JButton(REMOVE_COMMAND.getFitnessAppCommand());

        if (filterable) {
            this.filterLabel = new JLabel("Filters");
            this.filters = new JComboBox<>();
            this.inputFilter = new JTextField("Enter the desired filter here");
            this.filterButton = new JButton(FILTER_COMMAND.getFitnessAppCommand());
            this.resetFiltersButton = new JButton(RESET_FILTERS_COMMAND.getFitnessAppCommand());
        }

        this.backButton = new JButton(BACK_COMMAND.getFitnessAppCommand());
    }

    // EFFECTS: sets the appropriate components to respond to the appropriate events
    @Override
    protected void initializeActions() {
        initializeAction(viewButton, VIEW_COMMAND.getFitnessAppCommand());
        initializeAction(addButton, ADD_COMMAND.getFitnessAppCommand());
        initializeAction(removeButton, REMOVE_COMMAND.getFitnessAppCommand());

        if (filterable) {
            initializeAction(filterButton, FILTER_COMMAND.getFitnessAppCommand());
            initializeAction(resetFiltersButton, RESET_FILTERS_COMMAND.getFitnessAppCommand());
        }

        initializeAction(backButton, BACK_COMMAND.getFitnessAppCommand());
    }

    // EFFECTS: sets the placement of the components for the panel
    @Override
    protected void initializePlacements() {
        super.initializePlacements();

        scrollableDataTable.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        scrollableDataTable.setVisible(true);

        if (filterable) {
            inputFilter.setMaximumSize(new Dimension(WIDTH, 10));
        }
    }

    // EFFECTS: adds each component to be displayed to components
    @Override
    protected void addDisplayComponents() {
        components.add(splashText);
        components.add(viewButton);
        components.add(addButton);
        components.add(removeButton);

        if (filterable) {
            components.add(filterLabel);
            components.add(filters);
            components.add(inputFilter);
            components.add(filterButton);
            components.add(resetFiltersButton);
        }

        components.add(backButton);
    }

    // EFFECTS: updates the display of the collection
    protected abstract void updateDisplayCollection();

    // EFFECTS: updates the text on the panel to the given text
    public void setText(String text) {
        splashText.setText(text);
    }
}
