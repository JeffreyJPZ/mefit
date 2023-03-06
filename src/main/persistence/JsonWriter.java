package persistence;

import model.ProfilesById;
import org.json.JSONObject;

import java.io.*;

// Represents a writer for a json representation of profiles to a given file path
// (attributed to JsonSerializationDemo)
public class JsonWriter {
    private static final int INDENT = 4; // number of spaces for indentation in json file

    private String path;
    private PrintWriter writer;

    // EFFECTS: makes a new json writer with the given path
    public JsonWriter(String path) {
        this.path = path;
    }

    // MODIFIES: this
    // EFFECTS: opens the writer and makes a new print writer with the given path,
    //          if file cannot be opened throws a FileNotFoundException
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(path);
    }

    // MODIFIES: this
    // EFFECTS: writes profiles to json file
    public void write(ProfilesById profilesById) {
        JSONObject jsonObject = profilesById.toJson();
        writeToFile(jsonObject.toString(INDENT));
    }

    // MODIFIES: this
    // EFFECTS: closes the writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string representation of profiles to file
    private void writeToFile(String data) {
        writer.print(data);
    }
}
