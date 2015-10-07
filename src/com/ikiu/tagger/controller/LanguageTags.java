package com.ikiu.tagger.controller;

import com.ikiu.tagger.model.DatabaseManager;

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
import javax.swing.table.DefaultTableModel;

/**
 * Created by fahime on 9/25/15.
 */
public class LanguageTags implements MouseListener{
    protected JScrollPane scrollPane;
    protected JTable table;
    protected DefaultTableModel tableModel;
    protected DatabaseManager databaseManager;
    protected Vector<DatabaseManager.Token> tokens;

    public LanguageTags(DatabaseManager databaseManager,DefaultTableModel tableModel) {
        scrollPane = new JScrollPane();
        this.databaseManager = databaseManager;
        table = new JTable();
        table.setFillsViewportHeight(true);
        this.tableModel = tableModel;
        scrollPane.getViewport().add(table);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        table.setPreferredScrollableViewportSize(table.getPreferredSize());
        table.addMouseListener(this);
    }
    public JComponent getComponent()
    {
        return scrollPane;
    }

    public void addToken(String word)
    {
        DatabaseManager.Token token = new DatabaseManager.Token();
        int insertedId =0;
        token.setWord(word);
        token.setType("noun");
        if(this.getClass().getSimpleName().equals("EnglishTags"))
            insertedId=databaseManager.insertLanguageToken(DatabaseManager.ENGLISH,token );
        else
            insertedId=databaseManager.insertLanguageToken(DatabaseManager.PERSIAN,token );
        if(insertedId>0) {

            token.setId(insertedId);
            String []newRow={String.valueOf(insertedId),token.getType(),token.getWord(),
                    String.valueOf(token.getMeaning()),String.valueOf(token.isGenerated())};
            tableModel.addRow(newRow);
            tokens.add(token);
        }
    }
    public void loadTokens()
    {
        Iterator<DatabaseManager.Token> iterator = tokens.iterator();
        while (iterator.hasNext())
        {
            DatabaseManager.Token token = iterator.next();
            String []newRow={String.valueOf(token.getId()),token.getType(),token.getWord(),
                    String.valueOf(token.getMeaning()),String.valueOf(token.isGenerated())};
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
                    if (databaseManager.deleteLanguageToken(DatabaseManager.ENGLISH, tokens.get(rowNumber).getId()) > 0)
                        ((DefaultTableModel) table.getModel()).removeRow(rowNumber);
                }
                else
                {
                    if (databaseManager.deleteLanguageToken(DatabaseManager.PERSIAN, tokens.get(rowNumber).getId()) > 0)
                        ((DefaultTableModel) table.getModel()).removeRow(rowNumber);
                }

            }
        }
    }

    class MyTabelModel extends DefaultTableModel
    {
        @Override
        public boolean isCellEditable(int row, int column) {
            return super.isCellEditable(row, column);
        }
    }
}
