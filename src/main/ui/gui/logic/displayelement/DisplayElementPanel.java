package ui.gui.logic.displayelement;

import ui.gui.app.FitnessPanel;

import javax.swing.*;

import java.util.HashMap;
import java.util.Map;

import static ui.gui.app.FitnessAppCommands.*;

// Represents a panel for displaying an element of a fitness collection
public abstract class DisplayElementPanel extends FitnessPanel {
    protected Map<String, JTextField> inputTextFields;

    protected JLabel splashText;
    protected JButton editButton;
    protected JButton backButton;

    // EFFECTS: makes a display element panel
    public DisplayElementPanel() {
        super();
        initializeFields();
    }

    // MODIFIES: this
    // EFFECTS: initializes the components for the panel
    protected void initializeFields() {
        this.inputTextFields = new HashMap<>();

        this.splashText = new JLabel("");
        this.editButton = new JButton(EDIT_COMMAND.getFitnessAppCommand());
        this.backButton = new JButton(BACK_COMMAND.getFitnessAppCommand());
    }

    // MODIFIES: this
    // EFFECTS: sets the appropriate components to respond to the appropriate events
    @Override
    protected void initializeActions() {
        initializeAction(editButton, EDIT_COMMAND.getFitnessAppCommand());
        initializeAction(backButton, BACK_COMMAND.getFitnessAppCommand());
    }

    // EFFECTS: initializes the input components and adds them to the appropriate collection
    protected abstract void initializeInputs();

    // MODIFIES: this
    // EFFECTS: sets the splash text displayed on the panel to be the given text
    public void setText(String text) {
        splashText.setText(text);
    }
}
