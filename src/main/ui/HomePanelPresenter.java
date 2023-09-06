package ui;

import static ui.FitnessAppCommands.PROFILES_COMMAND;

// Represents the data and actions of a home panel
public class HomePanelPresenter extends Presenter {

    // MODIFIES: fitnessApp
    // EFFECTS: updates the model appropriately with t if key is a match
    @Override
    public <T> void update(T t, FitnessAppCommands key) {
        if (key.getFitnessAppCommand().equals(PROFILES_COMMAND.getFitnessAppCommand())) {
            profilesPanel();
        }
    }

    // EFFECTS: does nothing
    @Override
    protected void updatePresenter() {

    }

    // MODIFIES: fitnessApp
    // EFFECTS: switches to the profiles panel
    public void profilesPanel() {
        FitnessApp.getInstance().switchPanel(PROFILES_COMMAND.getFitnessAppCommand());
    }


}
