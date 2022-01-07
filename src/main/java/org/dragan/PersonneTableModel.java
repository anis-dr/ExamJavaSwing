package org.dragan;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class PersonneTableModel extends AbstractTableModel {

    private final List<Personne> items;

    public PersonneTableModel() {
        items = new ArrayList<>();
    }

    public PersonneTableModel(List<Personne> items) {
        this.items = items;
    }

    public List<Personne> getItems() {
        return items;
    }

    @Override
    public int getRowCount() {
        return items.size();
    }

    public int getMaleCount() {
        return (int) items.stream().filter(p -> p.getGenre().equals("Homme")).count();
    }

    public int getFemaleCount() {
        return (int) items.stream().filter(p -> p.getGenre().equals("Femme")).count();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return switch (columnIndex) {
            case 0 -> Integer.class;
            case 1, 2 -> String.class;
            default -> Object.class;
        };
    }

    @Override
    public String getColumnName(int column) {
        return switch (column) {
            case 0 -> "Id";
            case 1 -> "Nom";
            case 2 -> "Genre";
            default -> null;
        };
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Personne item = items.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> item.getId();
            case 1 -> item.getNom();
            case 2 -> item.getGenre();
            default -> null;
        };
    }

    public void add(Personne item) {
        items.add(item);
        int row = items.indexOf(item);
        fireTableRowsInserted(row, row);
    }

    public void remove(Personne item) {
        if (items.contains(item)) {
            int row = items.indexOf(item);
            items.remove(row);
            fireTableRowsDeleted(row, row);
        }
    }

}