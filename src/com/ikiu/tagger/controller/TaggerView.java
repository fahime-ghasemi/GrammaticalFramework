package com.ikiu.tagger.controller;

import com.ikiu.tagger.model.DatabaseManager;
import com.ikiu.tagger.util.ConfigurationTask;

import java.awt.*;
import java.util.Vector;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Created by fahime on 9/25/15.
 */
public class TaggerView extends JSplitPane {
    private TaggerBottomBar taggerBottomBar;

    public TaggerView() {
        //----
        setOrientation(JSplitPane.VERTICAL_SPLIT);

        setResizeWeight(0.6);
    }

    public void setLanguagePanels(JSplitPane languagePanels)
    {
        setTopComponent(languagePanels);
    }

    public TaggerBottomBar getTaggerBottomBar() {
        return taggerBottomBar;
    }

    public void setTaggerBottomBar(TaggerBottomBar taggerBottomBar)
    {
        this.taggerBottomBar = taggerBottomBar;
        setBottomComponent(taggerBottomBar);
    }
    public void refreshEnglishTags(Vector<DatabaseManager.TokenTableRow> tokenTableRows) {
        getTaggerBottomBar().englishTags.refreshTags(tokenTableRows);
    }

    public void refreshPersianTags(Vector<DatabaseManager.TokenTableRow> tokenTableRows) {
        getTaggerBottomBar().persianTags.refreshTags(tokenTableRows);

    }
    public DatabaseManager.TokenTableRow addEnglishTag(DatabaseManager.TokenTableRow tokenTableRow) {
        return getTaggerBottomBar().addEnglishTag(tokenTableRow);
    }

    public DatabaseManager.TokenTableRow addPersianTag(DatabaseManager.TokenTableRow tokenTableRow) {
        return getTaggerBottomBar().addPersianTag(tokenTableRow);
    }
    public void deSelectEnglishTab() {
        ((EnglishPanel)((JSplitPane)getTopComponent()).getLeftComponent()).deSelect();
    }
    public void deSelectPersianTab() {
        ((PersianPanel)((JSplitPane)getTopComponent()).getRightComponent()).deSelect();
    }
}
