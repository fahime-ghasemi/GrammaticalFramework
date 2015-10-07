package com.ikiu.tagger.controller;

import com.ikiu.tagger.model.DatabaseManager;

import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

/**
 * Created by fahime on 9/25/15.
 */
public class TaggerBottomBar extends JPanel{
    JPanel toolbar;
    EnglishTags englishTags;
    PersianTags persianTags;
    DatabaseManager databaseManager;


    public TaggerBottomBar() {
        databaseManager = new DatabaseManager();
        databaseManager.createEnglishTokenTable();
        databaseManager.createPersianTokenTable();
        //----
        this.toolbar = new JPanel(new FlowLayout());
        toolbar.add(new Button("Save Changes"));
        toolbar.setBackground(Color.blue);
        toolbar.add(new Button("Generate Lexicon"));
//        //---
        englishTags = new EnglishTags(databaseManager);
        persianTags = new PersianTags(databaseManager);
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

    public void addEnglishTag(String word)
    {
        englishTags.addToken(word);
    }
    public void addPersianTag(String word)
    {
        persianTags.addToken(word);
    }
}
