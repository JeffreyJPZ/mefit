package model.metrics;

import model.metrics.MuscleGroup;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Test class for MuscleGroup
public class MuscleGroupTest {

    @Test
    public void testValues() {
        assertEquals("Chest", MuscleGroup.CHEST.getMuscleGroupAsString());
        assertEquals("Back", MuscleGroup.BACK.getMuscleGroupAsString());
        assertEquals("Arms", MuscleGroup.ARMS.getMuscleGroupAsString());
        assertEquals("Shoulders", MuscleGroup.SHOULDERS.getMuscleGroupAsString());
        assertEquals("Legs", MuscleGroup.LEGS.getMuscleGroupAsString());
        assertEquals("Core", MuscleGroup.CORE.getMuscleGroupAsString());
    }
}
