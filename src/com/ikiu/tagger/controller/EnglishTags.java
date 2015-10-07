package com.ikiu.tagger.controller;

import com.ikiu.tagger.model.DatabaseManager;

/**
 * Created by fahime on 9/24/15.
 */
public class EnglishTags extends LanguageTags {

    public EnglishTags(DatabaseManager databaseManager) {
        super(databaseManager);
        tokens = databaseManager.getEnglishTokens();
        tableModel.addColumn("id");
        tableModel.addColumn("type");
        tableModel.addColumn("word");
        tableModel.addColumn("meaning");
        tableModel.addColumn("generated");
        table.setModel(tableModel);
        //----
        loadTokens();

    }




}
