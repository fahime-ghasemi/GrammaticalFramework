package com.ikiu.tagger.controller;

import com.ikiu.tagger.model.DatabaseManager;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Created by fahime on 9/25/15.
 */
public class TaggerBottomBar extends JPanel implements LanguageTags.LanguageTagListener {
    JPanel toolbar;
    EnglishTags englishTags;
    PersianTags persianTags;
    DatabaseManager databaseManager;
    JButton btnSaveChanges;
    JButton btnGenerator;
    JButton btnSetMeaning;
    TaggerView taggerView;

    public TaggerBottomBar(TaggerView taggerView) {
        databaseManager = new DatabaseManager();
        //----
        this.taggerView = taggerView;
        this.toolbar = new JPanel(new FlowLayout());

        btnSetMeaning = new JButton("Set Meaning");
        btnSetMeaning.addActionListener(btnSetMeaningActionListener);
        toolbar.add(btnSetMeaning);
        btnSaveChanges = new JButton("Save Changes");
        btnSaveChanges.addActionListener(btnSaveChangeActionListener);
        btnSaveChanges.setEnabled(false);
        toolbar.add(btnSaveChanges);
        toolbar.setBackground(Color.blue);
        btnGenerator = new JButton("Generate Lexicon");
        btnGenerator.setEnabled(false);
        btnGenerator.addActionListener(btnGeneratorActionListener);
        toolbar.add(btnGenerator);
//        //---
        englishTags = new EnglishTags(databaseManager, new EnglishTags.EnglishTable(),taggerView);
        englishTags.setListener(this);
        persianTags = new PersianTags(databaseManager, new PersianTags.PersianTable(),taggerView);
        persianTags.setListener(this);
//        //----
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridwidth = 2;
        constraints.gridx = 0;
        constraints.gridy = 0;
        add(toolbar, constraints);
        //----
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.weightx = 0.5;
        constraints.weighty = 1.0;
        add(englishTags.getComponent(), constraints);
        //---
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.weighty = 1.0;
        add(persianTags.getComponent(), constraints);

    }

    private ActionListener btnGeneratorActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
//            Iterator<DatabaseManager.TokenTableRow> rowIterator = englishTags.tokenTableRows.iterator();
//            while (rowIterator.hasNext())
//            {
//                DatabaseManager.TokenTableRow row = rowIterator.next();
//                row.setIsGenerated(false);
//                databaseManager.updateLanguageToken(row, DatabaseManager.ENGLISH);
//            }
//            rowIterator = persianTags.tokenTableRows.iterator();
//            while (rowIterator.hasNext())
//            {
//                DatabaseManager.TokenTableRow row = rowIterator.next();
//                row.setIsGenerated(false);
//                databaseManager.updateLanguageToken(row, DatabaseManager.PERSIAN);
//            }

            com.ikiu.tagger.controller.lexicon.LexiconUpdater lexiconUpdater = new com.ikiu.tagger.controller.lexicon.LexiconUpdater(englishTags, persianTags);
            lexiconUpdater.setListener(new com.ikiu.tagger.controller.lexicon.LexiconUpdater.LexiconUpdaterListener() {
                @Override
                public void onGenerateComplete() {

                    Iterator<DatabaseManager.TokenTableRow> iterator = englishTags.tokenTableRows.iterator();
                    while (iterator.hasNext()) {
                        DatabaseManager.TokenTableRow row = iterator.next();
                        if (row.isReadyForGenerate())
                            databaseManager.updateLanguageToken(row, DatabaseManager.ENGLISH);
                    }
                    ((DefaultTableModel) englishTags.mTable.getModel()).fireTableDataChanged();

                    iterator = persianTags.tokenTableRows.iterator();
                    while (iterator.hasNext()) {
                        DatabaseManager.TokenTableRow row = iterator.next();
                        if (row.isReadyForGenerate())
                            databaseManager.updateLanguageToken(row, DatabaseManager.PERSIAN);
                    }
                    ((DefaultTableModel) persianTags.mTable.getModel()).fireTableDataChanged();

                }
            });
            lexiconUpdater.display();
        }
    };
    private ActionListener btnSaveChangeActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            boolean updateSuccessful = true;
            btnSaveChanges.setEnabled(false);
            Iterator<DatabaseManager.TokenTableRow> iterator = englishTags.tokenTableRows.iterator();
            while (iterator.hasNext()) {
                DatabaseManager.TokenTableRow row = iterator.next();
                if (row.getEditedCells() > 0) {
                    if (databaseManager.updateLanguageToken(row, DatabaseManager.ENGLISH) > 0)
                        row.setEditedCells(0);
                    else
                        updateSuccessful = false;
                }
            }

            iterator = persianTags.tokenTableRows.iterator();
            while (iterator.hasNext()) {
                DatabaseManager.TokenTableRow row = iterator.next();
                if (row.getEditedCells() > 0) {
                    if (databaseManager.updateLanguageToken(row, DatabaseManager.PERSIAN) > 0)
                        row.setEditedCells(0);
                    else
                        updateSuccessful = false;
                }
            }
            persianTags.updateTableBackground();
            englishTags.updateTableBackground();

            if (!updateSuccessful)
                btnSaveChanges.setEnabled(true);
        }
    };
    private ActionListener btnSetMeaningActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!englishTags.isTagSelected() || !persianTags.isTagSelected()) {
                JOptionPane.showMessageDialog(toolbar, "Select rows first");
                return;
            }
            int persianId = persianTags.getIdOfSelectedToken();
            englishTags.setMeaning(englishTags.getSelectedToken(), persianId);

        }
    };

    public DatabaseManager.TokenTableRow addEnglishTag(DatabaseManager.TokenTableRow tokenTableRow) {
        return englishTags.addToken(tokenTableRow);
    }

    public DatabaseManager.TokenTableRow addPersianTag(DatabaseManager.TokenTableRow tokenTableRow) {
        return persianTags.addToken(tokenTableRow);
    }

    @Override
    public void onTokenRowChange() {
        btnSaveChanges.setEnabled(true);
    }

    @Override
    public void onTableSelectedCountChange(int selectedCount) {
        if (selectedCount > 0)
            btnGenerator.setEnabled(true);
        else btnGenerator.setEnabled(false);
    }

}
