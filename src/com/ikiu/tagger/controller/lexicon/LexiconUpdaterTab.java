package com.ikiu.tagger.controller.lexicon;

import com.ikiu.tagger.model.DatabaseManager;
import com.ikiu.tagger.util.ConfigurationTask;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Created by fahime on 10/9/15.
 */
public class LexiconUpdaterTab extends JPanel {
    protected JScrollPane scrollPane;
    protected JTable mTable;
    protected DefaultTableModel mTableModel;
    protected Vector<DatabaseManager.TokenTableRow> tokenTableRows;
    protected java.util.List<String> linList;
    protected String corePath;


    public LexiconUpdaterTab(Vector<DatabaseManager.TokenTableRow> tokenTableRows) {
        this.tokenTableRows = tokenTableRows;
        ConfigurationTask configurationTask = ConfigurationTask.getInstance();
        corePath = configurationTask.getCore();
        linList = new ArrayList<>();
        mTable = new LexiconTable();
        mTableModel = new LexiconTableModel();
        mTableModel.addColumn("word");
        mTableModel.addColumn("lin");
        mTable.setModel(mTableModel);

        scrollPane = new JScrollPane();
        scrollPane.getViewport().add(mTable);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 0;
        constraints.gridy = 0;
        add(scrollPane, constraints);
        //----
        setSize(600, 800);

    }

    public void setGeneratedForAllRows(DefaultTableModel mTableModel) {
        for (int i = 0; i < tokenTableRows.size(); ++i) {
            if (tokenTableRows.get(i).isReadyForGenerate()) {
                tokenTableRows.get(i).setIsGenerated(true);
                try {
                    mTableModel.setValueAt(true, i, 5);
                    //dirty code :-) for remove checked after closing generate lexicon dialog
                    mTableModel.setValueAt(false, i, 0);
                    tokenTableRows.get(i).setReadyForGenerate(true);
                } catch (Exception ignore) {

                }
            }
        }
    }

    public String getLinListAsString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < linList.size(); ++i) {
            stringBuilder.append(linList.get(i));
            if (i != linList.size() - 1)
                stringBuilder.append("\r\n");

        }
        return stringBuilder.toString();
    }

    public static class LexiconTable extends JTable {
        @Override
        public Class<?> getColumnClass(int column) {
            return String.class;
        }
    }

    public class LexiconTableModel extends DefaultTableModel {
        @Override
        public boolean isCellEditable(int row, int column) {
            if (column == 1)
                return true;
            return false;
        }

        @Override
        public void fireTableCellUpdated(final int rowEdited, int column) {
            super.fireTableCellUpdated(rowEdited, column);
            if (column == 1) {
                String updatedValue = (String) mTableModel.getValueAt(rowEdited, column);
                linList.remove(rowEdited);
                linList.add(rowEdited - 1, updatedValue);
            }
        }
    }

}
