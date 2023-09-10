package ui;

import exceptions.InvalidExerciseException;
import model.Exercise;
import model.Profile;
import model.ProfilesById;
import org.json.JSONObject;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

import static java.lang.Integer.highestOneBit;
import static java.lang.Integer.parseInt;
import static ui.FitnessAppCommands.*;

// Represents the data and actions of a profiles panel
public class ProfilesPanelPresenter extends DisplayCollectionPresenter {
    private static final String PROFILE_ID = "ID";
    private static final String PROFILE_NAME = "Name";
    private static final String PATH = "./data/fitnessapp.json";

    private ProfilesPanel profilesPanel;

    private ProfilesById profilesById;
    private ProfilesById profilesByIdMaster;

    private JsonReader jsonReader;
    private JsonWriter jsonWriter;

    // EFFECTS: creates a new profiles panel model
    public ProfilesPanelPresenter(ProfilesPanel profilesPanel) {
        this.profilesPanel = profilesPanel;

        this.profilesById = new ProfilesById();
        this.profilesByIdMaster = profilesById;

        this.jsonReader = new JsonReader(PATH);
        this.jsonWriter = new JsonWriter(PATH);

        this.info = List.of(PROFILE_ID, PROFILE_NAME);
        this.infoHeader = new Vector<>(info);
        this.filterable = List.of(PROFILE_NAME);

        this.data = new Vector<>();

        extractData();

        this.tableModel = new DefaultTableModel(data, infoHeader);
    }

    // MODIFIES: this
    // EFFECTS: extracts profile information from the profile collection
    @Override
    protected void extractData() {
        for (Profile profile : profilesById.getProfiles().values()) {
            Vector<Object> profileData = new Vector<>();

            profileData.add(profile.getId());
            profileData.add(profile.getName());

            data.add(profileData);
        }
    }

    // MODIFIES: this, profilesPanel, fitnessApp
    // EFFECTS: updates the model appropriately with t according to the given key
    @Override
    public <T> void update(T t, FitnessAppCommands key) {
        if (key.getFitnessAppCommand().equals(VIEW_COMMAND.getFitnessAppCommand())) {
            profilePanel(t);
        } else if (key.getFitnessAppCommand().equals(ADD_COMMAND.getFitnessAppCommand())) {
            addProfilePanel();
        } else if (key.getFitnessAppCommand().equals(REMOVE_COMMAND.getFitnessAppCommand())) {
            removeProfile(t);
        } else if (key.getFitnessAppCommand().equals(FILTER_COMMAND.getFitnessAppCommand())) {
            filterProfiles(t);
        } else if (key.getFitnessAppCommand().equals(RESET_FILTERS_COMMAND.getFitnessAppCommand())) {
            resetFilters();
        } else if (key.getFitnessAppCommand().equals(SAVE_COMMAND.getFitnessAppCommand())) {
            saveProfiles();
        } else if (key.getFitnessAppCommand().equals(LOAD_COMMAND.getFitnessAppCommand())) {
            loadProfiles();
        } else if (key.getFitnessAppCommand().equals(BACK_COMMAND.getFitnessAppCommand())) {
            back();
        } else if (key.getFitnessAppCommand().equals(ADD_PROFILE_COMMAND.getFitnessAppCommand())) {
            addProfile(t);
        } else if (key.getFitnessAppCommand().equals(SAVE_PROFILE_TO_PROFILES.getFitnessAppCommand())) {
            updateProfiles(t);
        }
    }

    // EFFECTS: parses the selected profile id from t and switches to the profile panel for the selected profile
    private <T> void profilePanel(T t) {
        JSONObject jsonObject = (JSONObject) t;
        JSONObject data = jsonObject.getJSONObject(JsonKeys.DATA.getKey());

        profilePanel(data);
    }

    // MODIFIES: this, fitnessApp
    // EFFECTS: switches to the profile panel for the profile with given id
    private void profilePanel(JSONObject data) {
        int id = data.getInt(JsonKeys.PROFILE_ID.getKey());

        resetFilters();

        profilesPanel.setText("");

        notifyAll(profilesById.getProfile(id), PROFILE_COMMAND);

        FitnessApp.getInstance().switchPanel(PROFILE_COMMAND.getFitnessAppCommand());
    }

    // MODIFIES: fitnessApp
    // EFFECTS: switches to the add profile panel
    private void addProfilePanel() {
        resetFilters();

        profilesPanel.setText("");

        FitnessApp.getInstance().switchPanel(ADD_PROFILE_COMMAND.getFitnessAppCommand());
    }

    // MODIFIES: this
    // EFFECTS: parses the profile data from t and adds a profile
    private <T> void addProfile(T t) {
        Profile profile = (Profile) t;

        addProfile(profile);

        updatePresenter();
        profilesPanel.updateDisplayCollection();
    }

    // MODIFIES: this
    // EFFECTS: adds the given profile to profiles
    private void addProfile(Profile profile) {
        profilesById.addProfile(profile);
    }

    // MODIFIES: this
    // EFFECTS: parses the profile id from t and removes the profile from the profiles
    private <T> void removeProfile(T t) {
        JSONObject jsonObject = (JSONObject) t;
        JSONObject data = jsonObject.getJSONObject(JsonKeys.DATA.getKey());

        int id = data.getInt(JsonKeys.PROFILE_ID.getKey());

        removeProfile(id);
    }

    // MODIFIES: this
    // EFFECTS: removes the profile with the given id from the model
    private void removeProfile(int id) {
        profilesById.removeProfile(id);

        updatePresenter();
        profilesPanel.updateDisplayCollection();
    }

    // MODIFIES: this
    // EFFECTS: parses the filter type and user input from t and filters profiles
    private <T> void filterProfiles(T t) {
        JSONObject jsonObject = (JSONObject) t;
        JSONObject data = jsonObject.getJSONObject(JsonKeys.DATA.getKey());

        String filterType = data.getString(JsonKeys.FILTER_TYPE.getKey());
        String input = data.getString(JsonKeys.INPUT.getKey());

        filterProfiles(filterType, input);
    }

    // MODIFIES: this
    // EFFECTS: filters the profiles with the given input
    private void filterProfiles(String filterType, String input) {
        if (filterType.equals(PROFILE_NAME)) {
            profilesById = profilesById.filterName(input);
        }
        updatePresenter();
        profilesPanel.updateDisplayCollection();
    }

    // MODIFIES: this
    // EFFECTS: removes filters and resets the profiles
    private void resetFilters() {
        profilesById = profilesByIdMaster;

        updatePresenter();
        profilesPanel.updateDisplayCollection();
    }

    // MODIFIES: this
    // EFFECTS: saves profiles to file
    //          indicates success if saving was successful, otherwise indicates failure
    private void saveProfiles() {
        try {
            saveProfilesFromJson();
            profilesPanel.setText("Successfully saved to: " + getPath() + "\n");
        } catch (FileNotFoundException exception) {
            profilesPanel.setText("Could not load from: " + getPath() + "\n");
        }
    }

    // MODIFIES: this
    // EFFECTS: loads profiles from file and updates the display of profiles
    //          indicates success if loading was successful, otherwise indicates failure
    private void loadProfiles() {
        try {
            loadProfilesFromJson();
            profilesPanel.setText("Successfully loaded from: " + getPath() + "\n");
        } catch (InvalidExerciseException exception) {
            profilesPanel.setText("Invalid exercise type encountered in: " + getPath() + "\n");
        } catch (IOException exception) {
            profilesPanel.setText("Could not load from: " + getPath() + "\n");
        }
    }

    // MODIFIES: this
    // EFFECTS: writes profiles to file with given path,
    //          throws FileNotFoundException if file path does not exist or cannot be accessed
    private void saveProfilesFromJson() throws FileNotFoundException {
        jsonWriter.open();
        jsonWriter.write(profilesById);
        jsonWriter.close();
    }

    // MODIFIES: this
    // EFFECTS: reads profiles from file and indicates success if successfully read,
    //          throws InvalidExerciseException if an exercise type is invalid,
    //          throws IOException if an error occurs in input or output
    private void loadProfilesFromJson() throws InvalidExerciseException, IOException {
        profilesById = jsonReader.read();
        profilesByIdMaster = profilesById;

        updatePresenter();
        profilesPanel.updateDisplayCollection();
    }

    // MODIFIES: this, fitnessApp
    // EFFECTS: returns to the previous panel to the profiles panel
    public void back() {
        resetFilters();
        FitnessApp.getInstance().switchPanel(HOME_COMMAND.getFitnessAppCommand());
    }

    // MODIFIES: this
    // EFFECTS: parses the profile data from t and updates the profile collection with the given profile data
    private <T> void updateProfiles(T t) {
        JSONObject jsonObject = (JSONObject) t;
        JSONObject data = jsonObject.getJSONObject(JsonKeys.DATA.getKey());

        updateProfiles(data);
    }

    // MODIFIES: this
    // EFFECTS: updates the profile in the profile collection with the same name
    private void updateProfiles(JSONObject data) {
        int id = data.getInt(JsonKeys.PROFILE_ID.getKey());
        Profile updatedProfile = (Profile) data.get(JsonKeys.PROFILE.getKey());

        Profile profile = profilesById.getProfile(id);

        profile.setName(updatedProfile.getName());
        profile.setGender(updatedProfile.getGender());
        profile.setAgeYears(updatedProfile.getAgeYears());
        profile.setWeightPounds(updatedProfile.getWeightPounds());

        updatePresenter();
        profilesPanel.updateDisplayCollection();
    }

    // MODIFIES: this
    // EFFECTS: updates the profiles collection
    @Override
    protected void updatePresenter() {
        data.clear();
        extractData();
        tableModel.setDataVector(data, infoHeader);
    }

    // EFFECTS: gets the file path for profiles
    public String getPath() {
        return PATH;
    }

    @Override
    public TableModel getTableModel() {
        return tableModel;
    }

    // EFFECTS: returns the filters for the profiles collection
    @Override
    public List<String> getFilters() {
        return filterable;
    }
}
