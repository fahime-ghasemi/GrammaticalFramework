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
public class GeneralView extends JSplitPane {

    private JSplitPane jSplitPane;
    public GeneralView() {

        jSplitPane = new JSplitPane();
        jSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        jSplitPane.setResizeWeight(0.8);
        setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        setRightComponent(jSplitPane);
    }

    public  void setProjectExplorer(ProjectExplorer projectExplorer) {
        setLeftComponent(projectExplorer);
    }
    public void setMainContent(JPanel mainContent)
    {
        jSplitPane.setTopComponent(mainContent);
    }
    public void setBottomBar(JPanel bottomBar)
    {
        jSplitPane.setBottomComponent(bottomBar);
    }
}
