package ui.gui.logic;

import ui.gui.app.FitnessAppCommands;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

// Represents a UI component with a number of observers that can be notified
public abstract class UIObservable extends JPanel {
    private List<UIObserver> observers;

    // EFFECTS: makes a new UIObservable
    public UIObservable() {
        observers = new ArrayList<>();
    }

    // EFFECTS: adds a new observer if not already added, otherwise does nothing
    public void addObserver(UIObserver o) {
        if (!observers.contains(o)) {
            observers.add(o);
        }
    }

    // EFFECTS: updates each observer with t if key is a match
    //          otherwise does nothing
    public <T> void notifyAll(T t, FitnessAppCommands key) {
        for (UIObserver observer : observers) {
            observer.update(t, key);
        }
    }
}
