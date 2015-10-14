package com.ikiu.tagger.controller;

import com.ikiu.tagger.util.ConfigurationTask;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Created by fahime on 9/25/15.
 */
public class TaggerView extends JSplitPane {
    private JSplitPane mainContent;
    private TaggerBottomBar taggerBottomBar;

    public TaggerView() {

        mainContent = new JSplitPane();
        mainContent.setOrientation(JSplitPane.VERTICAL_SPLIT);
        mainContent.setResizeWeight(0.6);
        //----
        setOrientation(JSplitPane.HORIZONTAL_SPLIT);

        setRightComponent(mainContent);
        setResizeWeight(0.1);
    }

    public void setLanguagePanels(JSplitPane languagePanels)
    {
        mainContent.setTopComponent(languagePanels);
    }

    public TaggerBottomBar getTaggerBottomBar() {
        return taggerBottomBar;
    }

    public void setTaggerBottomBar(TaggerBottomBar taggerBottomBar)
    {
        this.taggerBottomBar = taggerBottomBar;
        mainContent.setBottomComponent(taggerBottomBar);
    }
    public void setProjectExplorer(ProjectExplorer projectExplorer)
    {
        setLeftComponent(projectExplorer);
    }
}
