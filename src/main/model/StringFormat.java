package model;

// Represents the formatting used by text output to the user
public enum StringFormat {
    SEPARATOR("\t"),
    LINE_BREAK("\n"),
    CUTOFF("... with "),
    LEFT_BRACKET("["),
    RIGHT_BRACKET("]");

    private final String format;

    // MODIFIES: this
    // EFFECTS: makes a format with given characters
    StringFormat(String format) {
        this.format = format;
    }

    public String getFormat() {
        return format;
    }
}
