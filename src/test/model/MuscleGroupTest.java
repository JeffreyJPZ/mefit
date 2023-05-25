package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Test class for MuscleGroup
public class MuscleGroupTest {

    @Test
    public void testValues() {
        assertEquals("Chest", MuscleGroup.CHEST.getMuscleGroup());
        assertEquals("Back", MuscleGroup.BACK.getMuscleGroup());
        assertEquals("Arms", MuscleGroup.ARMS.getMuscleGroup());
        assertEquals("Shoulders", MuscleGroup.SHOULDERS.getMuscleGroup());
        assertEquals("Legs", MuscleGroup.LEGS.getMuscleGroup());
        assertEquals("Core", MuscleGroup.CORE.getMuscleGroup());
    }
}
