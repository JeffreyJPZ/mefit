package ui;

// Represents actions that can be taken in fitness application
public enum FitnessAppCommands {
    HOME_COMMAND("Home"),
    VIEW_COMMAND("View"),
    PROFILES_COMMAND("Profiles"),
    PROFILE_COMMAND("View Selected Profile"),
    EXERCISES_COMMAND("Exercises"),
    SELECT_TYPE_COMMAND("Select Type"),
    ADD_COMMAND("Add New"),
    ADD_PROFILE_COMMAND("Add Profile"),
    ADD_EXERCISE_COMMAND("Add Exercise"),
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
