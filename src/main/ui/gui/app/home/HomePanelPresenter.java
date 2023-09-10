package ui.gui.app.home;

import ui.gui.logic.Presenter;
import ui.gui.app.FitnessApp;
import ui.gui.app.FitnessAppCommands;

import static ui.gui.app.FitnessAppCommands.PROFILES_COMMAND;

// Represents the data and actions of a home panel
public class HomePanelPresenter extends Presenter {

    // MODIFIES: fitnessApp
    // EFFECTS: updates the model appropriately with t according to the given key
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
