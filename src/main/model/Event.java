package model;

import java.util.Calendar;
import java.util.Date;


// Represents an event in the fitness application
public class Event {
    private static final int HASH_CONSTANT = 13;
    private Date dateLogged;
    private String description;

    // EFFECTS: creates an event with the given description and the current date/time stamp.
    public Event(String description) {
        dateLogged = Calendar.getInstance().getTime();
        this.description = description;
    }

    public Date getDate() {
        return dateLogged;
    }

    public String getDescription() {
        return description;
    }

    // EFFECTS: returns true if this event and given event are equal, otherwise returns false
    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other.getClass() != this.getClass()) {
            return false;
        }

        Event otherEvent = (Event) other;

        return (this.dateLogged.equals(otherEvent.dateLogged)
                &&
                this.description.equals(otherEvent.description));
    }

    // EFFECTS: returns the hashcode for this event
    @Override
    public int hashCode() {
        return (HASH_CONSTANT * dateLogged.hashCode() + description.hashCode());
    }

    // EFFECTS: returns the string representation of this event
    @Override
    public String toString() {
        return dateLogged.toString() + "\n" + description;
    }
}

