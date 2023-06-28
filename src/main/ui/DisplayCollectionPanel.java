package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Vector;

import static ui.FitnessAppCommands.*;

// EFFECTS: represents a panel for displaying elements of a fitness collection
public abstract class DisplayCollectionPanel extends FitnessPanel {
    protected static final int WIDTH = 300;
    protected static final int HEIGHT = 300;
    protected static final int ID_POSITION = 0; // column index for identifiers of elements in the table

    protected List<String> info;
    protected Vector<String> infoHeader;
    protected List<String> filterable;

    protected Vector<Vector<Object>> data;
    protected DefaultTableModel tableModel;
    protected JTable dataTable;
    protected JScrollPane scrollableDataTable;

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
    public DisplayCollectionPanel() {
        super();
        initializeFields();
        initializeActions();
    }

    // MODIFIES: this
    // EFFECTS: initializes the components for displaying a fitness collection
    protected void initializeFields() {
        this.viewButton = new JButton(VIEW_COMMAND.getFitnessAppCommand());
        this.addButton = new JButton(ADD_COMMAND.getFitnessAppCommand());
        this.removeButton = new JButton(REMOVE_COMMAND.getFitnessAppCommand());
        this.filterLabel = new JLabel("Filters");
        this.filters = new JComboBox<>();
        this.inputFilter = new JTextField("Enter the desired filter here");
        this.filterButton = new JButton(FILTER_COMMAND.getFitnessAppCommand());
        this.resetFiltersButton = new JButton(RESET_FILTERS_COMMAND.getFitnessAppCommand());
        this.backButton = new JButton(BACK_COMMAND.getFitnessAppCommand());
    }

    // MODIFIES: this
    // EFFECTS: sets the appropriate components to respond to the appropriate events
    @Override
    protected void initializeActions() {
        initializeAction(addButton, ADD_COMMAND.getFitnessAppCommand());
        initializeAction(removeButton, REMOVE_COMMAND.getFitnessAppCommand());
        initializeAction(filterButton, FILTER_COMMAND.getFitnessAppCommand());
        initializeAction(resetFiltersButton, RESET_FILTERS_COMMAND.getFitnessAppCommand());
        initializeAction(backButton, BACK_COMMAND.getFitnessAppCommand());
    }

    // MODIFIES: this
    // EFFECTS: sets the placement of the components for the panel
    @Override
    protected void initializePlacements() {
        super.initializePlacements();

        scrollableDataTable.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        scrollableDataTable.setVisible(true);

        inputFilter.setMaximumSize(new Dimension(WIDTH, 10));
    }

    // MODIFIES: this
    // EFFECTS: adds each component to be displayed to components
    @Override
    protected void addDisplayComponents() {
        components.add(viewButton);
        components.add(addButton);
        components.add(removeButton);
        components.add(filterLabel);
        components.add(filters);
        components.add(inputFilter);
        components.add(filterButton);
        components.add(resetFiltersButton);
        components.add(backButton);
    }

    // MODIFIES: this
    // EFFECTS: updates the display of the collection
    protected abstract void updateDisplayCollection();
}
