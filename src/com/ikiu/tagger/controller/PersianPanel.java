package com.ikiu.tagger.controller;

import com.ikiu.tagger.model.DatabaseManager;

/**
 * Created by fahime on 9/24/15.
 */
public class PersianPanel extends MainContent {

    public PersianPanel(MainPart mainPart) {
        super(mainPart);
    }
    @Override
    public void setTextAreaContent(String filesystemPath) {

        int l = filesystemPath.lastIndexOf("/");
        String fileName = filesystemPath.substring(l + 1);

        jTabbedPane.addTab(fileName, new TaggerContentTab(filesystemPath, DatabaseManager.PERSIAN,this));
        jTabbedPane.setTabComponentAt(jTabbedPane.getTabCount() - 1, createTabHead(fileName));
        jTabbedPane.setSelectedIndex(jTabbedPane.getTabCount() - 1);
    }
}
