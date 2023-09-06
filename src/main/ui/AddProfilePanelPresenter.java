package ui;

import model.Profile;
import org.json.JSONObject;

import static java.lang.Integer.parseInt;
import static ui.FitnessAppCommands.*;

// Represents the data and actions for adding a profile to a collection
public class AddProfilePanelPresenter extends AddToCollectionPresenter {

    // EFFECTS: updates the add profile model appropriately with t according to the given key
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

    // EFFECTS: parses the profile data from t and makes a new profile
    private <T> void addProfile(T t) {
        try {
            JSONObject jsonObject = (JSONObject) t;
            JSONObject data = jsonObject.getJSONObject(JsonKeys.DATA.getKey());
            JSONObject textFields = data.getJSONObject(JsonKeys.FIELDS.getKey());

            addProfile(textFields);
        } catch (ClassCastException e) {
            throw new RuntimeException("Invalid data type was passed to the model");
        }
    }

    // MODIFIES: profilesPanel, fitnessApp
    // EFFECTS: adds a profile to the profiles with the given profile data
    private void addProfile(JSONObject textFields) {
        Profile profile = makeProfile(textFields);

        notifyAll(profile, ADD_PROFILE_COMMAND);

        back();
    }

    // EFFECTS: returns a profile with the given profile data
    private Profile makeProfile(JSONObject textFields) {
        String name = textFields.getString("name");
        String gender = textFields.getString("gender");
        String age = textFields.getString("age");
        String weight = textFields.getString("weight");

        return new Profile(name, gender, parseInt(age), parseInt(weight));
    }

    // MODIFIES: fitnessApp
    // EFFECTS: switches to the profiles panel
    private void back() {
        FitnessApp.getInstance().switchPanel(PROFILES_COMMAND.getFitnessAppCommand());
    }
}
