package com.ikiu.tagger.controller;

import com.ikiu.tagger.model.DatabaseManager;

import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

/**
 * Created by fahime on 9/25/15.
 */
public class LanguageTags implements MouseListener {
    protected JScrollPane scrollPane;
    protected JTable mTable;
    protected DefaultTableModel mTableModel;
    protected DatabaseManager databaseManager;
    protected Vector<DatabaseManager.TokenTableRow> tokenTableRows;
    protected LanguageTagListener listener;

    public interface LanguageTagListener {
        public void onTokenRowChange();

        public void onTableSelectedCountChange(int selectedCount);
    }

    public void refreshTags(Vector<DatabaseManager.TokenTableRow> rows) {
        mTableModel.getDataVector().removeAllElements();
        mTableModel.fireTableDataChanged();
        tokenTableRows = rows;
        loadTokens();
    }

    public LanguageTags(DatabaseManager databaseManager,JTable table) {
        scrollPane = new JScrollPane();
        this.databaseManager = databaseManager;
        mTable = table;
        mTable.setFillsViewportHeight(true);
        scrollPane.getViewport().add(mTable);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        mTable.setPreferredScrollableViewportSize(mTable.getPreferredSize());
        mTable.addMouseListener(this);

    }

    public LanguageTagListener getListener() {
        return listener;
    }

    public void setListener(LanguageTagListener listener) {
        this.listener = listener;
    }

    public JComponent getComponent() {
        return scrollPane;
    }

    public int addToken(String word) {
        DatabaseManager.TokenTableRow tokenTableRow = new DatabaseManager.TokenTableRow();
        int insertedId = 0;
        tokenTableRow.setWord(word);
        tokenTableRow.setType("noun");
        if (this.getClass().getSimpleName().equals("EnglishTags"))
            insertedId = databaseManager.insertLanguageToken(DatabaseManager.ENGLISH, tokenTableRow);
        else
            insertedId = databaseManager.insertLanguageToken(DatabaseManager.PERSIAN, tokenTableRow);
        if (insertedId > 0) {

            tokenTableRow.setId(insertedId);
            Object[] newRow = {false, String.valueOf(insertedId), tokenTableRow.getType(), tokenTableRow.getWord(),
                    String.valueOf(tokenTableRow.getMeaning()), String.valueOf(tokenTableRow.isGenerated())};
            mTableModel.addRow(newRow);
            tokenTableRows.add(tokenTableRow);
        }
        return insertedId;
    }

    public void loadTokens() {
        if(tokenTableRows==null)
            return;
        Iterator<DatabaseManager.TokenTableRow> iterator = tokenTableRows.iterator();
        while (iterator.hasNext()) {
            DatabaseManager.TokenTableRow tokenTableRow = iterator.next();
            Object[] newRow = {false, String.valueOf(tokenTableRow.getId()), tokenTableRow.getType(), tokenTableRow.getWord(),
                    String.valueOf(tokenTableRow.getMeaning()), String.valueOf(tokenTableRow.isGenerated())};
            mTableModel.addRow(newRow);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.isPopupTrigger()) {
            Point p = e.getPoint();
            int rowNumber = mTable.rowAtPoint(p);
            mTable.setRowSelectionInterval(rowNumber, rowNumber);
            JPopupMenu popupMenu = new PopupMenuTaggerTable(rowNumber);
            popupMenu.show((JComponent) e.getSource(), e.getX(), e.getY());
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {


    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    class PopupMenuTaggerTable extends JPopupMenu implements ActionListener {
        private JMenuItem menuItemDelete;
        private int rowNumber;

        public PopupMenuTaggerTable(int rowNumber) {
            this.rowNumber = rowNumber;
            menuItemDelete = new JMenuItem("Delete");
            menuItemDelete.addActionListener(this);
            menuItemDelete.setActionCommand("delete");

            add(menuItemDelete);
            setOpaque(true);
            setLightWeightPopupEnabled(true);

        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("delete")) {

                String name = LanguageTags.this.getClass().getSimpleName();
                if (LanguageTags.this.getClass().getSimpleName().equals("EnglishTags")) {
                    if (databaseManager.deleteLanguageToken(DatabaseManager.ENGLISH, tokenTableRows.get(rowNumber).getId()) > 0) {
                        ((DefaultTableModel) mTable.getModel()).removeRow(rowNumber);
                        tokenTableRows.remove(rowNumber);
                    }
                } else {
                    if (databaseManager.deleteLanguageToken(DatabaseManager.PERSIAN, tokenTableRows.get(rowNumber).getId()) > 0) {
                        ((DefaultTableModel) mTable.getModel()).removeRow(rowNumber);
                        tokenTableRows.remove(rowNumber);
                    }
                }

            }
        }
    }
    public void updateTableBackground() {
        mTable.setDefaultRenderer(Object.class, new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

                DefaultTableCellRenderer defaultTableCellRenderer = new DefaultTableCellRenderer();
                Component component = defaultTableCellRenderer.getTableCellRendererComponent(table,
                        value, isSelected, hasFocus, row, column);
                if (tokenTableRows.get(row).getEditedCells() > 0) {
                    component.setBackground(Color.cyan);
                }
                return component;
            }
        });

        mTable.repaint();
    }
}
