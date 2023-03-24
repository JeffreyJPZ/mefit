package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static ui.FitnessAppCommands.*;

// Represents the home panel for the fitness application
public class HomePanel extends JPanel implements ActionListener {
    private FitnessApp fitnessApp;

    private JLabel applicationName;
    private JButton profilesButton;

    // EFFECTS: creates the home panel for the application
    public HomePanel(FitnessApp fitnessApp) {
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
        this.profilesButton = new JButton(PROFILES_COMMAND.getFitnessAppCommand());
    }

    // MODIFIES: this
    // EFFECTS: sets the placement for the home panel components
    private void initializePlacements() {
        applicationName.setAlignmentX(Component.CENTER_ALIGNMENT);
        profilesButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    // MODIFIES: this
    // EFFECTS: sets the components to respond to appropriate events
    private void initializeActions() {
        profilesButton.setActionCommand(PROFILES_COMMAND.getFitnessAppCommand());
        profilesButton.addActionListener(this);
    }

    // MODIFIES: this
    // EFFECTS: adds the appropriate components to the home panel
    private void addComponents() {
        add(Box.createVerticalGlue());
        add(applicationName);
        add(Box.createVerticalGlue());
        add(profilesButton);
        add(Box.createVerticalGlue());
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
