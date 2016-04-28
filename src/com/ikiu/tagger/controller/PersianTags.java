package com.ikiu.tagger.controller;

import com.ikiu.tagger.model.DatabaseManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Created by fahime on 9/24/15.
 */
public class PersianTags extends LanguageTags {

    public PersianTags(DatabaseManager databaseManager, JTable table, TaggerView taggerView) {
        super(databaseManager, table, taggerView);
        tokenTableRows = databaseManager.getPersianTokens("");
        mTableModel = new PersianTableModel();
        mTableModel.addColumn("id");
        mTableModel.addColumn("type");
        mTableModel.addColumn("word");
        mTableModel.addColumn("generated");
        mTable.setModel(mTableModel);
    }

    @Override
    public DatabaseManager.TokenTableRow addToken(DatabaseManager.TokenTableRow tokenTableRow) {
        int insertedId = 0;
        tokenTableRow.setId(insertedId);
        insertedId = databaseManager.insertLanguageToken(DatabaseManager.PERSIAN, tokenTableRow);
        if (insertedId > 0) {

            tokenTableRow.setId(insertedId);
            Object[] newRow = {String.valueOf(insertedId), tokenTableRow.getType(), tokenTableRow.getWord(),
                    String.valueOf(tokenTableRow.isGenerated())};
            mTableModel.addRow(newRow);
            tokenTableRows.add(tokenTableRow);
        }
        return tokenTableRow;
    }

    @Override
    public void loadTokens() {
        Iterator<DatabaseManager.TokenTableRow> iterator = tokenTableRows.iterator();
        while (iterator.hasNext()) {
            DatabaseManager.TokenTableRow tokenTableRow = iterator.next();
            Object[] newRow = {String.valueOf(tokenTableRow.getId()), tokenTableRow.getType(), tokenTableRow.getWord(),
                    String.valueOf(tokenTableRow.isGenerated())};
            mTableModel.addRow(newRow);
        }
    }

    public static class PersianTable extends JTable {
        public int selectedCount;

        public int getSelectedCount() {
            return selectedCount;
        }

        public void setSelectedCount(int selectedCount) {
            this.selectedCount = selectedCount;
        }

    }

    public class PersianTableModel extends DefaultTableModel {
        @Override
        public boolean isCellEditable(int row, int column) {
            if (tokenTableRows.get(row).isGenerated())
                return false;
            if (column == 1 || column == 2)
                return true;
            return false;
        }

        @Override
        public void fireTableCellUpdated(final int rowEdited, int column) {
            super.fireTableCellUpdated(rowEdited, column);
            DatabaseManager.TokenTableRow tableRow = tokenTableRows.get(rowEdited);
            String value = "";
            switch (column) {
                case 1: {
                    value = tableRow.getType();
                    tableRow.setType(mTableModel.getValueAt(rowEdited, column).toString());
                    break;
                }
                case 2: {
                    value = tableRow.getWord();
                    tableRow.setWord(mTableModel.getValueAt(rowEdited, column).toString());
                    break;
                }
            }
            if (column != 3 && !mTableModel.getValueAt(rowEdited, column).toString().equals(value)) {
                tableRow.setEditedCells(tableRow.getEditedCells() + 1);

            }
            if (tableRow.getEditedCells() > 0) {
                PersianTags.this.updateTableBackground();
                if (listener != null)
                    listener.onTokenRowChange();
            }
        }
    }

}