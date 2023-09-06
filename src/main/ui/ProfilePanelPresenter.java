package ui;

import model.Profile;
import org.json.JSONObject;

import static java.lang.Integer.parseInt;
import static ui.FitnessAppCommands.*;

// Represents the data and actions of a profile panel
public class ProfilePanelPresenter extends DisplayElementPresenter {
    private ProfilePanel profilePanel;

    private Profile profile;

    // EFFECTS: makes a profile panel presenter
    public ProfilePanelPresenter(ProfilePanel profilePanel) {
        this.profilePanel = profilePanel;
        this.profile = new Profile("Test", "Test", 0, 0); // initialize sample profile
    }

    @Override
    public <T> void update(T t, FitnessAppCommands key) {
        if (key.getFitnessAppCommand().equals(EDIT_COMMAND.getFitnessAppCommand())) {
            editProfile(t);
        } else if (key.getFitnessAppCommand().equals(PROFILE_COMMAND.getFitnessAppCommand())) {
            setProfile(t);
        } else if (key.getFitnessAppCommand().equals(EXERCISES_COMMAND.getFitnessAppCommand())) {
            exercisesPanel();
        } else if (key.getFitnessAppCommand().equals(BACK_COMMAND.getFitnessAppCommand())) {
            back();
        }
    }

    // EFFECTS: parses the profile data from t and updates the profile with the data
    private <T> void editProfile(T t) {
        JSONObject jsonObject = (JSONObject) t;
        JSONObject data = jsonObject.getJSONObject(JsonKeys.DATA.getKey());

        editProfile(data);
    }

    // EFFECTS: updates the profile with the profile data
    private void editProfile(JSONObject data) {
        String name = data.getString("name");
        String gender = data.getString("gender");
        String age = data.getString("age");
        String weight = data.getString("weight");

        profile.setName(name);
        profile.setGender(gender);
        profile.setAge(parseInt(age));
        profile.setWeight(parseInt(weight));
    }

    // MODIFIES: this
    // EFFECTS: parses the profile from t sets the profile for the profile panel to the given profile
    private <T> void setProfile(T t) {
        Profile profile = (Profile) t;

        setProfile(profile);
    }

    // MODIFIES: this
    // EFFECTS: sets the profile for the profile panel to the given profile
    private void setProfile(Profile profile) {
        this.profile = profile;
        profilePanel.updateFields();
    }

    // MODIFIES: exercisesPanelPresenter, fitnessApp
    // EFFECTS: switches to the exercises panel with the given exercise
    private void exercisesPanel() {
        notifyAll(profile.getExercises(), EXERCISES_COMMAND);

        FitnessApp.getInstance().switchPanel(EXERCISES_COMMAND.getFitnessAppCommand());
    }

    // MODIFIES: fitnessApp
    // EFFECTS: switches to the profiles panel
    private void back() {
        FitnessApp.getInstance().switchPanel(PROFILES_COMMAND.getFitnessAppCommand());
    }

    // EFFECTS: returns the profile for the profile panel
    public Profile getProfile() {
        return profile;
    }

    // EFFECTS: does nothing
    @Override
    protected void updatePresenter() {
    }
}
