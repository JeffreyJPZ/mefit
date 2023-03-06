package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.JsonWritable;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

// Represents a mapping of profiles by their id
public class ProfilesById implements FitnessCollection, JsonWritable {
    public static final int DISPLAY_NUMBER_OF_PROFILES = 10;
    public static final String ADDITIONAL_PROFILE_MESSAGE = " additional profiles";

    private Map<Integer, Profile> profiles;

    // EFFECTS: Makes a new profile map with no profiles
    public ProfilesById() {
        profiles = new LinkedHashMap<>();
    }

    // MODIFIES: this
    // EFFECTS: adds a profile to the profiles
    public void addProfile(Profile profile) {
        profiles.put(profile.getId(), profile);
    }

    // REQUIRES: profiles is not empty, id > 0 and matches a profile
    // MODIFIES: this
    // EFFECTS: removes the profile from the profiles with the given id
    public void removeProfile(int id) {
        profiles.remove(id);
    }

    @Override
    // EFFECTS: if number of profiles <= DISPLAY_NUMBER_OF_PROFILES, returns the profile id and name of the first
    //          for each profile up to the first DISPLAY_NUMBER_OF_PROFILES profiles,
    //          otherwise also returns the number of remaining profiles and ADDITIONAL_PROFILE_MESSAGE
    public String toString() {
        String retString = "ID\tName" + "\n";
        int count = 0;

        for (Profile profile: profiles.values()) {
            if (count == DISPLAY_NUMBER_OF_PROFILES) {
                break;
            }
            retString = retString + "[" + profile.getId() + "]" + "\t" + profile.getName() + "\n";
            count++;
        }

        if (profiles.size() <= DISPLAY_NUMBER_OF_PROFILES) {
            return retString;
        } else {
            return retString + "... with "
                    + (profiles.size() - DISPLAY_NUMBER_OF_PROFILES)
                    + ADDITIONAL_PROFILE_MESSAGE;
        }
    }

    // EFFECTS: returns the profiles where string matches the beginning of the profile name case insensitively
    public ProfilesById filter(String name) {
        ProfilesById profilesById = new ProfilesById();

        Pattern pattern = Pattern.compile("^" + name + ".*", Pattern.CASE_INSENSITIVE);

        for (Profile profile : this.profiles.values()) {
            if (pattern.matcher(profile.getName()).matches()) {
                profilesById.getProfiles().put(profile.getId(), profile);
            }
        }

        return profilesById;
    }

    // EFFECTS: returns true if profile with same name ignoring case is in map, otherwise returns false
    public boolean contains(String name) {
        for (Profile profile : profiles.values()) {
            if (profile.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    // EFFECTS: returns true if the profiles contain a profile with the given id, otherwise returns false
    public boolean contains(int id) {
        return profiles.containsKey(id);
    }


    // EFFECTS: returns true if profiles has no profiles, otherwise returns false
    public boolean isEmpty() {
        return profiles.isEmpty();
    }

    // EFFECTS: returns the number of profiles
    public int length() {
        return profiles.size();
    }

    // EFFECTS: returns the profile with the given id in the profiles
    public Profile getProfile(int id) {
        return profiles.get(id);
    }

    public Map<Integer, Profile> getProfiles() {
        return profiles;
    }

    // EFFECTS: Returns a json object with profiles
    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("profiles", profilesToJson());

        return jsonObject;
    }

    // EFFECTS: Returns a json array with profiles
    private JSONArray profilesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Profile profile : profiles.values()) {
            jsonArray.put(profile.toJson());
        }

        return jsonArray;
    }
}
