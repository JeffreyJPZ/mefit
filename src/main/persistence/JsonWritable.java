package persistence;

import org.json.JSONObject;

// An object that can be written to json file (attribute to JsonSerializationDemo app)
public interface JsonWritable {

    // EFFECTS: returns a json object
    JSONObject toJson();

}
