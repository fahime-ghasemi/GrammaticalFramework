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

    public GeneralView() {
        setOrientation(JSplitPane.HORIZONTAL_SPLIT);
    }

    public void setProjectExplorer(ProjectExplorer projectExplorer) {
        setLeftComponent(projectExplorer);
    }

    public void setMainContent(JPanel mainContent) {
        setRightComponent(mainContent);
    }
}
