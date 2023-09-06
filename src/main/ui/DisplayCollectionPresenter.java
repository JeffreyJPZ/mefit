package ui;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.util.List;
import java.util.Vector;

// Represents the data and actions of a collection in the fitness app
public abstract class DisplayCollectionPresenter extends Presenter {
    protected List<String> info;
    protected Vector<String> infoHeader;
    protected List<String> filterable;

    protected Vector<Vector<Object>> data;
    protected DefaultTableModel tableModel;

    // EFFECTS: extracts the data from the collection to be displayed
    protected abstract void extractData();

    // EFFECTS: returns the table model for the collection
    public abstract TableModel getTableModel();

    // EFFECTS: returns the filters for the collection
    public abstract List<String> getFilters();
}
