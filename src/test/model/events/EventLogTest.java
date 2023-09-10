package model.events;

import model.events.Event;
import model.events.EventDescription;
import model.events.EventLog;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the EventLog class
 */
public class EventLogTest {
    private Event e1;
    private Event e2;
    private Event e3;

    @BeforeEach
    public void loadEvents() {
        e1 = new Event(EventDescription.ADD_EXERCISE.getDescription());
        e2 = new Event(EventDescription.REMOVE_EXERCISE.getDescription());
        e3 = new Event(EventDescription.FILTER_EXERCISES.getDescription());
        EventLog el = EventLog.getInstance();
        el.logEvent(e1);
        el.logEvent(e2);
        el.logEvent(e3);
    }

    @Test
    public void testLogEvent() {
        List<Event> l = new ArrayList<Event>();

        EventLog el = EventLog.getInstance();
        for (Event next : el) {
            l.add(next);
        }

        assertTrue(l.contains(e1));
        assertTrue(l.contains(e2));
        assertTrue(l.contains(e3));
    }

    @Test
    public void testClear() {
        EventLog el = EventLog.getInstance();
        el.clear();
        Iterator<Event> itr = el.iterator();
        assertTrue(itr.hasNext());   // After log is cleared, the clear log event is added
        assertEquals("Event log cleared.", itr.next().getDescription());
        assertFalse(itr.hasNext());
    }
}

