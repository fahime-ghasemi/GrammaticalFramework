package com.ikiu.tagger.controller;

import com.ikiu.tagger.model.DatabaseManager;

import java.awt.*;
import java.awt.event.*;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

/**
 * Created by fahime on 9/25/15.
 */
public class LanguageTags implements MouseListener {
    protected JPanel jPanel;
    protected JPanel toolbar;
    protected JScrollPane scrollPane;
    protected JTable mTable;
    public DefaultTableModel mTableModel;
    protected DatabaseManager databaseManager;
    protected Vector<DatabaseManager.TokenTableRow> tokenTableRows;
    protected LanguageTagListener listener;
    JTextField searchTextField;
    protected TaggerView taggerView;
    String search = "Id";
    ActionListener actionListenerPersian = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JComboBox<String> combo = (JComboBox<String>) e.getSource();
            String selectedType = (String) combo.getSelectedItem();

            if (selectedType.equals("Noun")) {
                taggerView.refreshPersianTags(taggerView.getPersianPanel().getCurrentTab().getTokenList("noun"));
            } else if (selectedType.equals("Adjective")) {
                taggerView.refreshPersianTags(taggerView.getPersianPanel().getCurrentTab().getTokenList("adjective"));
            } else if (selectedType.equals("All"))
                taggerView.refreshPersianTags(taggerView.getPersianPanel().getCurrentTab().getTokenList(""));
        }
    };
    ActionListener actionListenerEnglish = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JComboBox<String> combo = (JComboBox<String>) e.getSource();
            String selectedType = (String) combo.getSelectedItem();

            try {
                if (selectedType.equals("Noun")) {
                    taggerView.refreshEnglishTags(taggerView.getEnglishPanel().getCurrentTab().getTokenList("noun"));
                } else if (selectedType.equals("Adjective")) {
                    taggerView.refreshEnglishTags(taggerView.getEnglishPanel().getCurrentTab().getTokenList("adjective"));
                } else if (selectedType.equals("All"))
                    taggerView.refreshEnglishTags(taggerView.getEnglishPanel().getCurrentTab().getTokenList(""));
            } catch (Exception ignore) {
            }

        }
    };

    public interface LanguageTagListener {
        public void onTokenRowChange();

        public void onTableSelectedCountChange(int selectedCount);
    }

    public Vector<DatabaseManager.TokenTableRow> getTokenTableRows() {
        return tokenTableRows;
    }

    public void refreshTags(Vector<DatabaseManager.TokenTableRow> rows) {
        mTableModel.getDataVector().removeAllElements();
        mTableModel.fireTableDataChanged();
        tokenTableRows = rows;
        loadTokens();
    }

    public LanguageTags(DatabaseManager databaseManager, JTable table, TaggerView taggerView) {
        this.taggerView = taggerView;
        jPanel = new JPanel(new GridBagLayout());
        this.toolbar = new JPanel(new FlowLayout());
        addSearchCombo();
        searchTextField = new JTextField(8);
        searchTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == 8 && searchTextField.getText().equals("")) {
                    if (LanguageTags.this instanceof EnglishTags)
                        taggerView.refreshEnglishTags(taggerView.getEnglishPanel().getCurrentTab().getTokenList(""));
                    else if (LanguageTags.this instanceof PersianTags)
                        taggerView.refreshPersianTags(taggerView.getPersianPanel().getCurrentTab().getTokenList(""));

                }
            }
        });
        searchTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = searchTextField.getText();
                if (input.equals(""))
                    return;
                try {
                    if (search.equals("Id")) {
                        try {
                            if (LanguageTags.this instanceof EnglishTags)
                                taggerView.refreshEnglishTags(taggerView.getEnglishPanel().getCurrentTab().getToken(Integer.valueOf(input)));
                            else if (LanguageTags.this instanceof PersianTags)
                                taggerView.refreshPersianTags(taggerView.getPersianPanel().getCurrentTab().getToken(Integer.valueOf(input)));
                        } catch (Exception ignore) {

                        }

                    } else if (search.equals("Word")) {
                        if (LanguageTags.this instanceof EnglishTags)
                            taggerView.refreshEnglishTags(taggerView.getEnglishPanel().getCurrentTab().getTokens(input));
                        else if (LanguageTags.this instanceof PersianTags)
                            taggerView.refreshPersianTags(taggerView.getPersianPanel().getCurrentTab().getTokens(input));

                    }
                } catch (Exception ignore) {

                }
            }
        });
        toolbar.add(searchTextField);
        addCombo();

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridwidth = 2;
        constraints.gridx = 0;
        constraints.gridy = 0;
        jPanel.add(toolbar, constraints);

        scrollPane = new JScrollPane();
        this.databaseManager = databaseManager;
        mTable = table;
        mTable.setFillsViewportHeight(true);
        scrollPane.getViewport().add(mTable);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        mTable.setPreferredScrollableViewportSize(mTable.getPreferredSize());
        mTable.addMouseListener(this);

        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.weightx = 0.5;
        constraints.weighty = 1.0;

        jPanel.add(scrollPane, constraints);

    }

    public void addCombo() {
        toolbar.add(new JLabel("Type:"));
        JComboBox<String> combo = new JComboBox<>();
        combo.addItem("All");
        combo.addItem("Noun");
        combo.addItem("Adjective");
        if (this instanceof PersianTags)
            combo.addActionListener(actionListenerPersian);
        else if (this instanceof EnglishTags)
            combo.addActionListener(actionListenerEnglish);

        toolbar.add(combo);
    }

    public void addSearchCombo() {
        toolbar.add(new JLabel("Search:"));
        JComboBox<String> combo = new JComboBox<>();
        combo.addItem("Id");
        combo.addItem("Word");
        combo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox<String> combo = (JComboBox<String>) e.getSource();
                search = (String) combo.getSelectedItem();
            }
        });

        toolbar.add(combo);
    }

    public LanguageTagListener getListener() {
        return listener;
    }

    public boolean isTagSelected() {
        return mTable.getSelectedRowCount() > 0;
    }

    public int getIdOfSelectedToken() {
        if (!isTagSelected())
            return 0;
        DatabaseManager.TokenTableRow row = tokenTableRows.get(mTable.getSelectedRow());
        return row.getId();

    }

    public void deselectAll() {

//        mTableModel.setValueAt();
    }

    public int getSelectedToken() {
        return mTable.getSelectedRow();
    }

    public void setListener(LanguageTagListener listener) {
        this.listener = listener;
    }

    public JComponent getComponent() {
        return jPanel;
    }

    public DatabaseManager.TokenTableRow addToken(DatabaseManager.TokenTableRow tokenTableRow) {
        int insertedId = 0;
        tokenTableRow.setId(insertedId);
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
        return tokenTableRow;
    }

    public void loadTokens() {
        if (tokenTableRows == null)
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
        if (e.getButton() == MouseEvent.BUTTON3) {
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
