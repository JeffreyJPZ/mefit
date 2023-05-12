package ui;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

// Represents a UI component with a number of components that can be notified
public abstract class UIObservablePanel extends JPanel {
    private List<UIObserver> observers;

    // EFFECTS: makes a new UIObservable
    public UIObservablePanel() {
        observers = new ArrayList<>();
    }

    // EFFECTS: adds a new observer if not already added, otherwise does nothing
    public void addObserver(UIObserver o) {
        if (!observers.contains(o)) {
            observers.add(o);
        }
    }

    // EFFECTS: removes the observer if found
    public void removeObserver(UIObserver o) {
        observers.remove(o);
    }

    // EFFECTS: updates each observer with t
    public <T> void notifyAll(T t, FitnessAppCommands key) {
        for (UIObserver observer : observers) {
            observer.update(t, key);
        }
    }
}
