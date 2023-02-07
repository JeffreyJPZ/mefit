package model;

public enum Difficulty {
    LIGHT(1),
    MODERATE(2),
    INTENSE(3);

    private final int difficulty;

    Difficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getDifficulty() {
        return difficulty;
    }
}
