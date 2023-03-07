package persistence;

import org.json.JSONObject;

// An object that can be written to json file
// attributed to JsonSerializationDemo app from CPSC 210
public interface JsonWritable {

    // EFFECTS: returns a json object
    JSONObject toJson();

}
