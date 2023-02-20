package model;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

// Represents a mapping of profiles by their id
public class ProfilesById implements GymCollection {
    public static final int DISPLAY_NUMBER_OF_PROFILES = 10;
    public static final String ADDITIONAL_PROFILE_MESSAGE = " additional profiles";

    private Map<Integer, Profile> profiles;

    // EFFECTS: Makes a new profile map with no profiles
    public ProfilesById() {
        profiles = new LinkedHashMap<>();
    }

    // MODIFIES: this
    // EFFECTS: adds a profile to the profile map
    public void addProfile(Profile profile) {
        profiles.put(profile.getId(), profile);
    }

    // REQUIRES: profile map is not empty
    // MODIFIES: this
    // EFFECTS: removes the profile from the profile map with the given id
    public void removeProfile(int id) {
        profiles.remove(id);
    }

    @Override
    // REQUIRES: profile map is not empty
    // MODIFIES: this
    // EFFECTS: if number of profiles in map <= DISPLAY_NUMBER_OF_PROFILES, returns the profile id and name of the first
    //          DISPLAY_NUMBER_OF_PROFILES profiles in map,
    //          otherwise also returns the number of remaining profiles in map and ADDITIONAL_PROFILE_MESSAGE
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

    // REQUIRES: profile map is not empty and string matches at least one element in profile map
    // MODIFIES: this
    // EFFECTS: returns a mapping of profiles where string matches the beginning of the profile name case insensitively
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

    // MODIFIES: this
    // EFFECTS: returns true if profile with same name ignoring case is in map, otherwise returns false
    public boolean contains(String name) {
        for (Profile profile : profiles.values()) {
            if (profile.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: returns true if map is empty, otherwise returns false
    public boolean isEmpty() {
        return profiles.isEmpty();
    }

    // MODIFIES: this
    // EFFECTS: returns the length of the profile map
    public int length() {
        return profiles.size();
    }

    // REQUIRES: profile map is not empty
    // MODIFIES: this
    // EFFECTS: returns the profile with the given id in profile map
    public Profile getProfile(int id) {
        return profiles.get(id);
    }

    public Map<Integer, Profile> getProfiles() {
        return profiles;
    }
}
