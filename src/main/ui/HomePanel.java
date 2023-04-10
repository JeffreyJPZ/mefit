package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;

import static ui.FitnessAppCommands.*;

// Represents a home panel for the fitness application
public class HomePanel extends FitnessPanel {
    private static final String CREATOR_PICTURE_PATH = "./data/creatorpicture.jpg";

    private FitnessApp fitnessApp;

    private JLabel applicationName;
    private JLabel attributeText;
    private JLabel creatorPicture;
    private JButton profilesButton;

    // EFFECTS: creates a home panel
    public HomePanel(FitnessApp fitnessApp) {
        super();
        initializeFields(fitnessApp);
        initializePlacements();
        initializeActions();
        addComponents();
    }

    // MODIFIES: this
    // EFFECTS: initializes the components for the home panel
    public void initializeFields(FitnessApp fitnessApp) {
        this.fitnessApp = fitnessApp;

        this.applicationName = new JLabel("Fitness App");
        this.attributeText = new JLabel("Created by Jeffrey Zhang");
        this.creatorPicture = new JLabel(new ImageIcon(CREATOR_PICTURE_PATH));
        this.profilesButton = new JButton(PROFILES_COMMAND.getFitnessAppCommand());

        addDisplayComponents();
    }

    @Override
    // MODIFIES: this
    // EFFECTS: adds each component to be displayed to the components
    protected void addDisplayComponents() {
        components.add(applicationName);
        components.add(attributeText);
        components.add(creatorPicture);
        components.add(profilesButton);
    }

    @Override
    // MODIFIES: this
    // EFFECTS: sets the appropriate components to respond to appropriate events
    protected void initializeActions() {
        initializeAction(profilesButton, PROFILES_COMMAND.getFitnessAppCommand());
    }

    // MODIFIES: fitnessApp
    // EFFECTS: handles the appropriate event for each component
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(PROFILES_COMMAND.getFitnessAppCommand())) {
            fitnessApp.switchPanel(PROFILES_COMMAND.getFitnessAppCommand());
        }
    }
}
