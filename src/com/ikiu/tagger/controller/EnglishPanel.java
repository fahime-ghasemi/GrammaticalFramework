package com.ikiu.tagger.controller;

import com.ikiu.tagger.model.DatabaseManager;

/**
 * Created by fahime on 9/24/15.
 */
public class EnglishPanel extends MainContent{


    public EnglishPanel(Context context) {
        super(context);

    }
    @Override
    public void setTextAreaContent(String filesystemPath) {

        int l = filesystemPath.lastIndexOf("/");
        String fileName = filesystemPath.substring(l + 1);

        jTabbedPane.addTab(fileName, new TaggerContentTab(filesystemPath, DatabaseManager.ENGLISH,this));
        jTabbedPane.setTabComponentAt(jTabbedPane.getTabCount() - 1, createTabHead(fileName));
        jTabbedPane.setSelectedIndex(jTabbedPane.getTabCount() - 1);
    }
}
