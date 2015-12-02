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
    public int addEnglishTag(String source) {
        return getTaggerBottomBar().addEnglishTag(source);
    }

    public int addPersianTag(String source) {
        return getTaggerBottomBar().addPersianTag(source);
    }
    public void deSelectEnglishTab() {
        ((EnglishPanel)((JSplitPane)getTopComponent()).getLeftComponent()).deSelect();
    }
    public void deSelectPersianTab() {
        ((PersianPanel)((JSplitPane)getTopComponent()).getRightComponent()).deSelect();
    }
}
