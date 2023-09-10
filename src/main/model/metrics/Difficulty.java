package model.metrics;

// Represents difficulty levels for exercises
public enum Difficulty {
    LIGHT(1),
    MODERATE(2),
    INTENSE(3);

    private final int difficulty;

    // MODIFIES: this
    // EFFECTS: makes a difficulty with a level
    Difficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getDifficultyAsInt() {
        return difficulty;
    }
}
