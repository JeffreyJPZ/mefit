package ui.gui.app.profiles.profile;

import model.profiles.profile.Profile;
import org.json.JSONObject;
import ui.gui.logic.displayelement.DisplayElementPresenter;
import ui.gui.json.JsonKeys;
import ui.gui.app.FitnessApp;
import ui.gui.app.FitnessAppCommands;

import static java.lang.Integer.parseInt;
import static ui.gui.app.FitnessAppCommands.*;

// Represents the data and actions of a profile panel
public class ProfilePanelPresenter extends DisplayElementPresenter {
    private ProfilePanel profilePanel;

    private Profile profile;

    // EFFECTS: makes a profile panel presenter
    public ProfilePanelPresenter(ProfilePanel profilePanel) {
        this.profilePanel = profilePanel;

        // initialize sample profile
        this.profile = new Profile("Example Name", "Example Gender", 0, 0);
    }

    // MODIFIES: this, profilePanel, fitnessApp
    // EFFECTS: updates the model appropriately with t according to the given key
    @Override
    public <T> void update(T t, FitnessAppCommands key) {
        if (key.getFitnessAppCommand().equals(EDIT_COMMAND.getFitnessAppCommand())) {
            editProfile(t);
        }  else if (key.getFitnessAppCommand().equals(EXERCISES_COMMAND.getFitnessAppCommand())) {
            exercisesPanel();
        }  else if (key.getFitnessAppCommand().equals(WORKOUTS_COMMAND.getFitnessAppCommand())) {
            workoutsPanel();
        } else if (key.getFitnessAppCommand().equals(BACK_COMMAND.getFitnessAppCommand())) {
            back();
        } else if (key.getFitnessAppCommand().equals(PROFILE_COMMAND.getFitnessAppCommand())) {
            setProfile(t);
        } else if (key.getFitnessAppCommand().equals(GET_EXERCISES_COMMAND.getFitnessAppCommand())) {
            sendExercises();
        }
    }

    // MODIFIES: this, profilePanel
    // EFFECTS: parses the profile data from t and updates the profile with the data
    private <T> void editProfile(T t) {
        JSONObject jsonObject = (JSONObject) t;
        JSONObject data = jsonObject.getJSONObject(JsonKeys.DATA.getKey());
        JSONObject inputs = data.getJSONObject(JsonKeys.INPUT.getKey());

        editProfile(inputs);
    }

    // MODIFIES: this, profilePanel
    // EFFECTS: updates the profile with the profile data
    private void editProfile(JSONObject inputs) {
        editBasicInfo(inputs);
        profilePanel.updateInputs(profile);
        updateProfiles();
    }

    // MODIFIES: this, profilePanel
    // EFFECTS: edits profile with the given basic profile data
    private void editBasicInfo(JSONObject inputs) {
        String name = inputs.getString("name");
        String gender = inputs.getString("gender");
        String age = inputs.getString("age");
        String weight = inputs.getString("weight");

        try {
            profile.setName(name);
            profile.setGender(gender);
            profile.setAgeYears(parseInt(age));
            profile.setWeightPounds(parseInt(weight));
        } catch (IllegalArgumentException e) {
            profilePanel.setText(INVALID_INPUT_TEXT);
        }
    }

    // MODIFIES: exercisesPanelPresenter, fitnessApp
    // EFFECTS: switches to the exercises panel with the given exercises
    private void exercisesPanel() {
        notifyAll(profile.getExercises(), EXERCISES_COMMAND);

        profilePanel.setText("");

        FitnessApp.getInstance().switchPanel(EXERCISES_COMMAND.getFitnessAppCommand());
    }

    // MODIFIES: workoutsPanelPresenter, fitnessApp
    // EFFECTS: switches to the workouts panel with the given workouts
    private void workoutsPanel() {
        notifyAll(profile.getWorkouts(), WORKOUTS_COMMAND);

        profilePanel.setText("");

        FitnessApp.getInstance().switchPanel(WORKOUTS_COMMAND.getFitnessAppCommand());
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
        profilePanel.updateInputs(profile);
    }

    // MODIFIES: fitnessApp
    // EFFECTS: switches to the profiles panel
    private void back() {
        profilePanel.setText("");

        FitnessApp.getInstance().switchPanel(PROFILES_COMMAND.getFitnessAppCommand());
    }

    // MODIFIES: profilesPanelPresenter
    // EFFECTS: updates profile collection with updated profile
    private void updateProfiles() {
        JSONObject data = new JSONObject();
        JSONObject exerciseData = new JSONObject();

        exerciseData.put(JsonKeys.PROFILE_ID.getKey(), profile.getId());
        exerciseData.put(JsonKeys.PROFILE.getKey(), profile);

        data.put(JsonKeys.DATA.getKey(), exerciseData);

        notifyAll(data, SAVE_PROFILE_TO_PROFILES);
    }

    // MODIFIES: workoutGeneratorPanelPresenter
    // EFFECTS: sends the exercises for the profile to interested observers
    private void sendExercises() {
        notifyAll(profile.getExercises(), SEND_EXERCISES_COMMAND);
    }

    // EFFECTS: returns the profile for the profile panel
    public Profile getProfile() {
        return profile;
    }

    @Override
    // EFFECTS: does nothing
    protected void updatePresenter() {

    }
}
