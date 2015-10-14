package com.ikiu.tagger.controller;

import com.ikiu.tagger.model.DatabaseManager;

import javax.swing.table.DefaultTableModel;

/**
 * Created by fahime on 9/24/15.
 */
public class PersianTags extends LanguageTags {

    public PersianTags(DatabaseManager databaseManager) {
        super(databaseManager);
        tokenTableRows = databaseManager.getPersianTokens();
        tableModel.addColumn("id");
        tableModel.addColumn("type");
        tableModel.addColumn("word");
        tableModel.addColumn("generated");
        table.setModel(tableModel);
        loadTokens();
    }

}