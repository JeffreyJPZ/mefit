package ui.gui.app.profiles.addprofile;

import model.profiles.profile.Profile;
import org.json.JSONObject;
import ui.gui.json.JsonKeys;
import ui.gui.logic.addtocollection.AddToCollectionPresenter;
import ui.gui.app.FitnessApp;
import ui.gui.app.FitnessAppCommands;

import static java.lang.Integer.parseInt;
import static ui.gui.app.FitnessAppCommands.*;

// Represents the data and actions for a panel for adding a profile
public class AddProfilePanelPresenter extends AddToCollectionPresenter {
    private AddProfilePanel addProfilePanel;

    // EFFECTS: makes a new presenter for adding an exercise{
    public AddProfilePanelPresenter(AddProfilePanel addProfilePanel) {
        this.addProfilePanel = addProfilePanel;
    }

    // MODIFIES: profilesPanelPresenter, fitnessApp
    // EFFECTS: updates the model appropriately with t according to the given key
    @Override
    public <T> void update(T t, FitnessAppCommands key) {
        if (key.getFitnessAppCommand().equals(ADD_PROFILE_COMMAND.getFitnessAppCommand())) {
            addProfile(t);
        } else if (key.getFitnessAppCommand().equals(BACK_COMMAND.getFitnessAppCommand())) {
            back();
        }
    }

    // EFFECTS: does nothing
    @Override
    protected void updatePresenter() {

    }

    // MODIFIES: profilesPanelPresenter, fitnessApp
    // EFFECTS: parses the profile data from t and makes a new profile
    private <T> void addProfile(T t) {
        JSONObject jsonObject = (JSONObject) t;
        JSONObject data = jsonObject.getJSONObject(JsonKeys.DATA.getKey());
        JSONObject textFields = data.getJSONObject(JsonKeys.FIELDS.getKey());

        addProfile(textFields);
    }

    // MODIFIES: profilesPanelPresenter, fitnessApp
    // EFFECTS: makes a profile and adds the profile to the profile collection
    //          if profile data cannot be parsed, notifies the user
    private void addProfile(JSONObject textFields) {
        Profile profile = null;

        try {
            profile = makeProfile(textFields);
        } catch (IllegalArgumentException e) {
            addProfilePanel.setText(INVALID_INPUT_TEXT);
        }

        notifyAll(profile, ADD_PROFILE_COMMAND);

        back();
    }

    // EFFECTS: returns a profile with the given profile data
    private Profile makeProfile(JSONObject textFields) {
        String name = textFields.getString(JsonKeys.PROFILE_NAME.getKey());
        String gender = textFields.getString(JsonKeys.PROFILE_GENDER.getKey());
        String age = textFields.getString(JsonKeys.PROFILE_AGE.getKey());
        String weight = textFields.getString(JsonKeys.PROFILE_WEIGHT.getKey());

        return new Profile(name, gender, parseInt(age), parseInt(weight));
    }

    // MODIFIES: fitnessApp
    // EFFECTS: switches to the profiles panel
    private void back() {
        FitnessApp.getInstance().switchPanel(PROFILES_COMMAND.getFitnessAppCommand());
    }
}
