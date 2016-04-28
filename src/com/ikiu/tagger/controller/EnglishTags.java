package com.ikiu.tagger.controller;

import com.ikiu.tagger.model.DatabaseManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultTreeModel;

/**
 * Created by fahime on 9/24/15.
 */
public class EnglishTags extends LanguageTags {

    public EnglishTags(DatabaseManager databaseManager, JTable table,TaggerView taggerView) {
        super(databaseManager, table,taggerView);
        mTableModel = new EnglishTableModel();
        mTableModel.addColumn("");
        mTableModel.addColumn("id");
        mTableModel.addColumn("type");
        mTableModel.addColumn("word");
        mTableModel.addColumn("meaning");
        mTableModel.addColumn("generated");
        mTable.setModel(mTableModel);
        //----
    }

    public void setMeaning(int index, int meaning) {
        DatabaseManager.TokenTableRow row = tokenTableRows.get(index);
        row.setMeaning(meaning);
        mTable.getModel().setValueAt(meaning, index, 4);
        ((DefaultTableModel) mTable.getModel()).fireTableDataChanged();
    }

    public static class EnglishTable extends JTable {
        public int selectedCount;

        public int getSelectedCount() {
            return selectedCount;
        }

        public void setSelectedCount(int selectedCount) {
            this.selectedCount = selectedCount;
        }

        @Override
        public Class<?> getColumnClass(int column) {

            switch (column) {
                case 0: {
                    return Boolean.class;
                }
            }
            return String.class;
        }
    }

    public class EnglishTableModel extends DefaultTableModel {
        @Override
        public boolean isCellEditable(int row, int column) {
            if (tokenTableRows.get(row).isGenerated())
                return false;
            if (column == 2 || column == 3 || column == 4)
                return true;
            if (column == 0 && tokenTableRows.get(row).getMeaning() != 0 && tokenTableRows.get(row).getEditedCells() == 0)
                return true;
            return false;
        }

        @Override
        public void fireTableCellUpdated(final int rowEdited, int column) {
            super.fireTableCellUpdated(rowEdited, column);
            DatabaseManager.TokenTableRow tableRow = tokenTableRows.get(rowEdited);
            String value = "";
            switch (column) {
                case 0: {
                    tableRow.setReadyForGenerate(!tableRow.isReadyForGenerate());
                    if ((Boolean) mTableModel.getValueAt(rowEdited, column))
                        ((EnglishTable) mTable).setSelectedCount(((EnglishTable) mTable).getSelectedCount() + 1);
                    else {
                        ((EnglishTable) mTable).setSelectedCount(((EnglishTable) mTable).getSelectedCount() - 1);
                    }
                    if (listener != null)
                        listener.onTableSelectedCountChange(((EnglishTable) mTable).getSelectedCount());
                    break;
                }
                case 2: {
                    value = tableRow.getType();
                    tableRow.setType(mTableModel.getValueAt(rowEdited, column).toString());
                    break;
                }
                case 3: {
                    value = tableRow.getWord();
                    tableRow.setWord(mTableModel.getValueAt(rowEdited, column).toString());
                    break;
                }
                case 4: {
                    value = String.valueOf(tableRow.getMeaning());
                    tableRow.setMeaning(Integer.valueOf(mTableModel.getValueAt(rowEdited, column).toString()));
                }
            }
            if (column != 0 && column != 5 && !mTableModel.getValueAt(rowEdited, column).toString().equals(value)) {
                tableRow.setEditedCells(tableRow.getEditedCells() + 1);

            }
            if (tableRow.getEditedCells() > 0) {
                EnglishTags.this.updateTableBackground();
                if (listener != null)
                    listener.onTokenRowChange();
            }
        }
    }


}
