package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;

import static ui.FitnessAppCommands.*;

// Represents a home panel for the fitness application
public class HomePanel extends FitnessPanel {
    private static final String CREATOR_PICTURE_PATH = "./data/creatorpicture.jpg";

    private HomePanelPresenter homePanelPresenter;

    private JLabel applicationName;
    private JLabel attributeText;
    private JLabel creatorPicture;
    private JButton profilesButton;

    // EFFECTS: creates a home panel
    public HomePanel() {
        super();
        initializeFields();
        addDisplayComponents();
        initializePlacements();
        initializeActions();
        addComponents();
    }

    // MODIFIES: this
    // EFFECTS: initializes the components for the home panel
    public void initializeFields() {
        this.homePanelPresenter = new HomePanelPresenter();

        this.applicationName = new JLabel("MeFit - a fitness app");
        this.attributeText = new JLabel("Created by Jeffrey Zhang");
        this.creatorPicture = new JLabel(new ImageIcon(CREATOR_PICTURE_PATH));
        this.profilesButton = new JButton(PROFILES_COMMAND.getFitnessAppCommand());
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

    // EFFECTS: returns the presenter for this home panel
    @Override
    public Presenter getPresenter() {
        return homePanelPresenter;
    }

    // MODIFIES: fitnessApp
    // EFFECTS: handles the appropriate event for appropriate components
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(PROFILES_COMMAND.getFitnessAppCommand())) {
            profilesPanel();
        }
    }

    // MODIFIES: fitnessApp
    // EFFECTS: switches to the profiles panel
    private void profilesPanel() {
        homePanelPresenter.update(null, PROFILES_COMMAND);
    }
}
