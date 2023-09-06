package ui;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

import static ui.FitnessAppCommands.*;
import static ui.FitnessAppCommands.BACK_COMMAND;

// Represents a panel for adding elements to a fitness collection
public abstract class AddToCollectionPanel extends FitnessPanel {

    protected Map<String, JTextField> inputTextFields;

    protected JButton addButton;
    protected JButton backButton;

    // EFFECTS: makes a panel for adding elements
    public AddToCollectionPanel() {
        super();
        initializeFields();
        initializeActions();
    }

    // EFFECTS: initializes the components for the panel
    protected void initializeFields() {
        this.inputTextFields = new HashMap<>();

        this.addButton = new JButton(ADD_COMMAND.getFitnessAppCommand());
        this.backButton = new JButton(BACK_COMMAND.getFitnessAppCommand());
    }

    // EFFECTS: sets the appropriate components to respond to the appropriate events
    @Override
    protected void initializeActions() {
        initializeAction(addButton, ADD_COMMAND.getFitnessAppCommand());
        initializeAction(backButton, BACK_COMMAND.getFitnessAppCommand());
    }

    // EFFECTS: initializes the input components and adds them to the appropriate collection
    protected abstract void initializeInputs();
}
