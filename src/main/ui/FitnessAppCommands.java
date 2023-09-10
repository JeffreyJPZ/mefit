package ui;

// Represents actions that can be taken in fitness application
public enum FitnessAppCommands {
    HOME_COMMAND("Home"),
    VIEW_COMMAND("View"),
    PROFILES_COMMAND("Profiles"),
    PROFILE_COMMAND("View Selected Profile"),
    SAVE_PROFILE_TO_PROFILES("Save Profile"),
    SAVE_COMMAND("Save Profiles"),
    LOAD_COMMAND("Load Profiles"),
    EDIT_COMMAND("Save Edits"),
    GET_EXERCISES_COMMAND("Get Exercises"),
    SEND_EXERCISES_COMMAND("Send Exercises"),
    SAVE_EXERCISES_TO_PROFILE("Save Exercises"),
    SAVE_EXERCISE_TO_EXERCISES("Save Exercise"),
    EXERCISES_COMMAND("Exercises"),
    EXERCISE_COMMAND("View Selected Exercise"),
    ADD_COMMAND("Add New"),
    ADD_PROFILE_COMMAND("Add Profile"),
    ADD_EXERCISE_COMMAND("Add Exercise"),
    ADD_WORKOUT_COMMAND("Add Workout"),
    SAVE_WORKOUT_TO_WORKOUTS("Save Workouts"),
    WORKOUTS_COMMAND("Workouts"),
    WORKOUT_COMMAND("View Selected Workout"),
    REMOVE_COMMAND("Remove Selected"),
    FILTER_COMMAND("Filter"),
    RESET_FILTERS_COMMAND("Reset Filters"),
    BACK_COMMAND("Back");

    private final String command;

    // REQUIRES: command is not empty
    // EFFECTS: makes a new command
    FitnessAppCommands(String command) {
        this.command = command;
    }

    public String getFitnessAppCommand() {
        return command;
    }
}
