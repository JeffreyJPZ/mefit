package model.events;

import model.events.EventDescription;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Test class for EventDescription
public class EventDescriptionTest {
    @Test
    public void testValues() {
        assertEquals("Added an exercise to a profile's exercises",
                EventDescription.ADD_EXERCISE.getDescription());
        assertEquals("Removed an exercise from a profile's exercises",
                EventDescription.REMOVE_EXERCISE.getDescription());
        assertEquals("Filtered a profile's exercises",
                EventDescription.FILTER_EXERCISES.getDescription());
    }
}
