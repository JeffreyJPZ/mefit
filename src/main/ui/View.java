package ui;

// Represents a UI element of the fitness app
public abstract class View extends UIObservable implements UIObserver {

    // EFFECTS: returns the presenter associated with this view
    public abstract Presenter getPresenter();
}
