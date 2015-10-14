package com.ikiu.tagger.controller;

import com.ikiu.tagger.model.DatabaseManager;

import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
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
public class LanguageTags implements MouseListener{
    protected JScrollPane scrollPane;
    protected JTable table;
    protected DefaultTableModel tableModel;
    protected DatabaseManager databaseManager;
    protected Vector<DatabaseManager.TokenTableRow> tokenTableRows;
    private LanguageTagListener listener;
    public interface LanguageTagListener
    {
        public void onTokenRowChange();
    }

    public LanguageTags(DatabaseManager databaseManager) {
        scrollPane = new JScrollPane();
        this.databaseManager = databaseManager;
        table = new JTable();
        table.setFillsViewportHeight(true);
        this.tableModel = new MyTableModel();
        scrollPane.getViewport().add(table);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        table.setPreferredScrollableViewportSize(table.getPreferredSize());
        table.addMouseListener(this);

    }

    public LanguageTagListener getListener() {
        return listener;
    }

    public void setListener(LanguageTagListener listener) {
        this.listener = listener;
    }

    public JComponent getComponent()
    {
        return scrollPane;
    }

    public void addToken(String word)
    {
        DatabaseManager.TokenTableRow tokenTableRow = new DatabaseManager.TokenTableRow();
        int insertedId =0;
        tokenTableRow.setWord(word);
        tokenTableRow.setType("noun");
        if(this.getClass().getSimpleName().equals("EnglishTags"))
            insertedId=databaseManager.insertLanguageToken(DatabaseManager.ENGLISH, tokenTableRow);
        else
            insertedId=databaseManager.insertLanguageToken(DatabaseManager.PERSIAN, tokenTableRow);
        if(insertedId>0) {

            tokenTableRow.setId(insertedId);
            String []newRow={String.valueOf(insertedId), tokenTableRow.getType(), tokenTableRow.getWord(),
                    String.valueOf(tokenTableRow.getMeaning()),String.valueOf(tokenTableRow.isGenerated())};
            tableModel.addRow(newRow);
            tokenTableRows.add(tokenTableRow);
        }
    }
    public void loadTokens()
    {
        Iterator<DatabaseManager.TokenTableRow> iterator = tokenTableRows.iterator();
        while (iterator.hasNext())
        {
            DatabaseManager.TokenTableRow tokenTableRow = iterator.next();
            String []newRow={String.valueOf(tokenTableRow.getId()), tokenTableRow.getType(), tokenTableRow.getWord(),
                    String.valueOf(tokenTableRow.getMeaning()),String.valueOf(tokenTableRow.isGenerated())};
            tableModel.addRow(newRow);
        }
    }
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.isPopupTrigger())
        {
            Point p=e.getPoint();
            int rowNumber=table.rowAtPoint(p);
            table.setRowSelectionInterval(rowNumber, rowNumber);
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

    class PopupMenuTaggerTable extends JPopupMenu implements ActionListener
    {
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

                String name=LanguageTags.this.getClass().getSimpleName();
                if(LanguageTags.this.getClass().getSimpleName().equals("EnglishTags")) {
                    if (databaseManager.deleteLanguageToken(DatabaseManager.ENGLISH, tokenTableRows.get(rowNumber).getId()) > 0)
                        ((DefaultTableModel) table.getModel()).removeRow(rowNumber);
                }
                else
                {
                    if (databaseManager.deleteLanguageToken(DatabaseManager.PERSIAN, tokenTableRows.get(rowNumber).getId()) > 0)
                        ((DefaultTableModel) table.getModel()).removeRow(rowNumber);
                }

            }
        }
    }

    class MyTableModel extends DefaultTableModel
    {
        @Override
        public boolean isCellEditable(int row, int column) {
            if( column==1 || column==2 || (column==3 && LanguageTags.this instanceof EnglishTags) )
            return super.isCellEditable(row, column);
            return false;
        }

        @Override
        public void fireTableCellUpdated(final int rowEdited, int column) {
            super.fireTableCellUpdated(rowEdited, column);
            DatabaseManager.TokenTableRow tableRow = tokenTableRows.get(rowEdited);
            String value="";
            int index=0;
            switch (column)
            {
                case 1:
                {
                    value = tableRow.getType();
                    tableRow.setType(tableModel.getValueAt(rowEdited, column).toString());
                    break;
                }
                case 2:
                {
                    value = tableRow.getWord();
                    tableRow.setWord(tableModel.getValueAt(rowEdited, column).toString());
                    break;
                }
                case 3:
                {
                    value = String.valueOf(tableRow.getMeaning());
                    tableRow.setMeaning(Integer.valueOf(tableModel.getValueAt(rowEdited, column).toString()));
                }
            }
            if(!tableModel.getValueAt(rowEdited, column).toString().equals(value)) {
                tableRow.setEditedCells(tableRow.getEditedCells() + 1);

            }
            if(tableRow.getEditedCells()>0) {
                LanguageTags.this.updateTableBackground();
                if(listener!=null)
                    listener.onTokenRowChange();
            }
        }
    }

    public void updateTableBackground() {
        table.setDefaultRenderer(Object.class, new TableCellRenderer() {
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

        table.repaint();
    }
}
