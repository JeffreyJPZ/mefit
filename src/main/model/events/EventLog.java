package model.events;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

// Represents a log of fitness application events
public class EventLog implements Iterable<Event> {
    private static EventLog theLog;
    private Collection<Event> events;

    // EFFECTS: creates the event log with no events added
    private EventLog() {
        events = new ArrayList<Event>();
    }

    // MODIFIES: this
    // EFFECTS: creates an event log if not yet created, returns the event log
    public static EventLog getInstance() {
        if (theLog == null) {
            theLog = new EventLog();
        }
        return theLog;
    }

    // MODIFIES: this
    // EFFECTS: adds an event to the event log
    public void logEvent(Event e) {
        events.add(e);
    }

    // MODIFIES: this
    // EFFECTS: clears the event log and logs the event
    public void clear() {
        events.clear();
        logEvent(new Event("Event log cleared."));
    }

    // EFFECTS: returns the iterator for the event log
    @Override
    public Iterator<Event> iterator() {
        return events.iterator();
    }
}

