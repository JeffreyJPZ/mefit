package model;


import exceptions.AlreadyContainedException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.JsonWritable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Folder extends ExerciseComponent {
    private Map<String, ExerciseComponent> components;

    // EFFECTS: makes a new folder
    public Folder(String name) {
        super(name, ExerciseComponentTypes.FOLDER);
        components = new HashMap<>();
    }

    // MODIFIES: this
    // EFFECTS: adds the given exercise component to the folder
    //          if folder contains an exercise component with the same name, throws AlreadyContainedException
    public void addComponent(ExerciseComponent c) throws AlreadyContainedException {
        if (components.containsKey(c.getName())) {
            throw new AlreadyContainedException();
        }
        components.put(c.getName(), c);
    }

    // MODIFIES: this
    // EFFECTS: removes the given exercise component from the folder
    public void removeComponent(ExerciseComponent c) {
        components.remove(c.getName());
    }

    // EFFECTS: returns the exercise component with the given name in the folder
    public ExerciseComponent getComponent(String name) {
        return components.get(name);
    }

    // EFFECTS: returns the components in the folder
    public Map<String, ExerciseComponent> getComponents() {
        return Collections.unmodifiableMap(components);
    }

    // EFFECTS: returns the time of all exercise components in this folder
    @Override
    public int getTime() {
        int time = 0;

        for (ExerciseComponent c : components.values()) {
            time += c.getTime();
        }
        return time;
    }

    // EFFECTS: returns the number of items in the folder, excluding the folder itself
    @Override
    public int getSize() {
        int size = 0;

        for (ExerciseComponent c : components.values()) {
            size += c.getSize();
        }
        return size;
    }

    // EFFECTS: returns a json representation of the folder
    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("exerciseComponentType", getType().getType());
        jsonObject.put("name", getName());
        jsonObject.put("data", folderToJson());

        return jsonObject;
    }

    // EFFECTS: returns a json array of all components in the folder
    private JSONArray folderToJson() {
        JSONArray jsonArray = new JSONArray();

        for (ExerciseComponent c : components.values()) {
            jsonArray.put(c.toJson());
        }

        return jsonArray;
    }
}
