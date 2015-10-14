package com.ikiu.tagger.controller;

import com.ikiu.tagger.model.DatabaseManager;

import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Created by fahime on 9/25/15.
 */
public class TaggerBottomBar extends JPanel implements LanguageTags.LanguageTagListener{
    JPanel toolbar;
    EnglishTags englishTags;
    PersianTags persianTags;
    DatabaseManager databaseManager;
    JButton btnSaveChanges;
    JButton btnGenerator;

    public TaggerBottomBar() {
        databaseManager = new DatabaseManager();
        databaseManager.createEnglishTokenTable();
        databaseManager.createPersianTokenTable();
        //----
        this.toolbar = new JPanel(new FlowLayout());
        btnSaveChanges = new JButton("Save Changes");
        btnSaveChanges.addActionListener(btnSaveChangeActionListener);
        btnSaveChanges.setEnabled(false);
        toolbar.add(btnSaveChanges);
        toolbar.setBackground(Color.blue);
        btnGenerator = new JButton("Generate Lexicon");
        btnGenerator.addActionListener(btnGeneratorActionListener);
        toolbar.add(btnGenerator);
//        //---
        englishTags = new EnglishTags(databaseManager);
        englishTags.setListener(this);
        persianTags = new PersianTags(databaseManager);
        persianTags.setListener(this);
//        //----
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridwidth=2;
        constraints.gridx = 0;
        constraints.gridy = 0;
        add(toolbar, constraints);
        //----
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth=1;
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
            TaggerGenerator generator = new TaggerGenerator();
            generator.display();
        }
    };
    private ActionListener btnSaveChangeActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            boolean updateSuccessful=true;
            btnSaveChanges.setEnabled(false);
        Iterator <DatabaseManager.TokenTableRow> iterator = englishTags.tokenTableRows.iterator();
            while (iterator.hasNext())
            {
                DatabaseManager.TokenTableRow row = iterator.next();
                if(row.getEditedCells()>0)
                {
                    if(databaseManager.updateLanguageToken(row,DatabaseManager.ENGLISH)>0)
                        row.setEditedCells(0);
                    else
                        updateSuccessful = false;
                }
            }

        iterator = persianTags.tokenTableRows.iterator();
            while (iterator.hasNext())
            {
                DatabaseManager.TokenTableRow row = iterator.next();
                if(row.getEditedCells()>0)
                {
                    if(databaseManager.updateLanguageToken(row,DatabaseManager.PERSIAN)>0)
                        row.setEditedCells(0);
                    else
                        updateSuccessful = false;
                }
            }
            persianTags.updateTableBackground();
            englishTags.updateTableBackground();

            if(!updateSuccessful)
                btnSaveChanges.setEnabled(true);
        }
    };
    public void addEnglishTag(String word)
    {
        englishTags.addToken(word);
    }
    public void addPersianTag(String word)
    {
        persianTags.addToken(word);
    }

    @Override
    public void onTokenRowChange() {
        btnSaveChanges.setEnabled(true);
    }
}
