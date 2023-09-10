package ui.gui.logic;

// Represents the data and actions of a view
public abstract class Presenter extends UIObservable implements UIObserver {

    // EFFECTS: updates the presenter appropriately
    protected abstract void updatePresenter();
}
