package ui;

import javax.swing.*;
import javax.swing.table.JTableHeader;

import java.util.HashMap;
import java.util.Map;

import static ui.FitnessAppCommands.*;

// Represents a panel for displaying an element of a fitness collection
public abstract class DisplayElementPanel extends FitnessPanel {
    protected Map<String, JTextField> inputTextFields;

    protected JButton editButton;
    protected JButton backButton;

    // EFFECTS: makes a display element panel
    public DisplayElementPanel() {
        super();
        initializeFields();
    }

    // EFFECTS: initializes the components for the panel
    protected void initializeFields() {
        this.inputTextFields = new HashMap<>();

        this.editButton = new JButton(EDIT_COMMAND.getFitnessAppCommand());
        this.backButton = new JButton(BACK_COMMAND.getFitnessAppCommand());
    }

    // EFFECTS: sets the appropriate components to respond to the appropriate events
    @Override
    protected void initializeActions() {
        initializeAction(editButton, EDIT_COMMAND.getFitnessAppCommand());
        initializeAction(backButton, BACK_COMMAND.getFitnessAppCommand());
    }

    // EFFECTS: initializes the input components and adds them to the appropriate collection
    protected abstract void initializeInputs();
}
