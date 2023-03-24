package ui;

// Represents actions that can be taken in fitness application
public enum FitnessAppCommands {
    HOME_COMMAND("Home"),
    PROFILES_COMMAND("Profiles"),
    ADD_PROFILE_COMMAND("Add Profile"),
    DELETE_PROFILE_COMMAND("Delete Selected Profile"),
    PROFILE_COMMAND("View Selected Profile"),
    EXERCISES_COMMAND("Exercises"),
    ADD_EXERCISE_COMMAND("Add Exercise"),
    DELETE_EXERCISE_COMMAND("Delete Selected Exercise(s)"),
    FILTER_EXERCISE_COMMAND("Filter Exercises"),
    RESET_EXERCISE_FILTERS_COMMAND("Reset Exercise Filters"),
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
