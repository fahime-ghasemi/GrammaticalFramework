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
    private MainPart container;

    public GeneralView(MainPart container) {
        this.container = container;
        JPanel mainContentPanel=new JPanel(new BorderLayout());
        MainContent mainContent = new MainContent(container);
        mainContentPanel.add(mainContent.getComponent());
        container.setCurrentPanel( mainContent);

        JPanel bottomBar=new JPanel(new BorderLayout());
        bottomBar.setSize(400,300);
        bottomBar.add(new BottomBar().getComponent());

        //------------------------------
        JPanel projectExplorer=new JPanel(new BorderLayout());
        DefaultMutableTreeNode top = new DefaultMutableTreeNode(new Tree.TreeNodeData("Project Explorer","root",(new ConfigurationTask()).getWorkspace()), true);
        Tree tree=new Tree(top,container);
        JScrollPane jScrollPane = new JScrollPane((JTree)tree);

        projectExplorer.add(jScrollPane);

        //-----------------------------------------------
        JSplitPane jSplitPane1=new JSplitPane(JSplitPane.VERTICAL_SPLIT,mainContentPanel,bottomBar);
        jSplitPane1.setResizeWeight(0.8);
        setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        setLeftComponent(projectExplorer);
        setRightComponent(jSplitPane1);
    }
}
