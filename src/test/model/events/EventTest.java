package model.events;

import model.events.Event;
import model.events.EventDescription;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for the Event class
 */
public class EventTest {
    private Event e1;
    private Event e2;
    private Event e3;
    private Date d;

    @BeforeEach
    public void runBefore() {
        e1 = new Event(EventDescription.ADD_EXERCISE.getDescription());
        e2 = new Event(EventDescription.REMOVE_EXERCISE.getDescription());
        e3 = new Event(EventDescription.FILTER_EXERCISES.getDescription());
        d = Calendar.getInstance().getTime();
    }

    @Test
    public void testEvent() {
        assertEquals(EventDescription.ADD_EXERCISE.getDescription(), e1.getDescription());
        assertEquals(EventDescription.REMOVE_EXERCISE.getDescription(), e2.getDescription());
        assertEquals(EventDescription.FILTER_EXERCISES.getDescription(), e3.getDescription());

        assertEquals(d, e1.getDate());
        assertEquals(d, e2.getDate());
        assertEquals(d, e3.getDate());
    }

    @Test
    public void testToString() {
        assertEquals(d.toString() + "\n" + EventDescription.ADD_EXERCISE.getDescription(), e1.toString());
        assertEquals(d.toString() + "\n" + EventDescription.REMOVE_EXERCISE.getDescription(), e2.toString());
        assertEquals(d.toString() + "\n" + EventDescription.FILTER_EXERCISES.getDescription(), e3.toString());

    }
}

