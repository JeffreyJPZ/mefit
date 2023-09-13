package ui.gui.logic.addtocollection;

import ui.gui.app.FitnessPanel;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

import static ui.gui.app.FitnessAppCommands.*;
import static ui.gui.app.FitnessAppCommands.BACK_COMMAND;

// Represents a panel for adding elements to a fitness collection
public abstract class AddToCollectionPanel extends FitnessPanel {

    protected Map<String, JTextField> inputTextFields;

    protected JLabel splashText;
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

        this.splashText = new JLabel("");
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

    // EFFECTS: sets the splash text to display the given string
    public void setText(String text) {
        splashText.setText(text);
    }
}
