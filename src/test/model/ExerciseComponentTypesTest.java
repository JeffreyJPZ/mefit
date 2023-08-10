package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Test class for ExerciseComponentTypes
public class ExerciseComponentTypesTest {
    @Test
    public void testValues() {
        assertEquals("Exercise",
                ExerciseComponentTypes.EXERCISE.getType());
        assertEquals("Workout",
                ExerciseComponentTypes.WORKOUT.getType());
        assertEquals("Folder",
                ExerciseComponentTypes.FOLDER.getType());
    }
}
