package com.ikiu.tagger.controller;

import com.ikiu.tagger.util.ConfigurationTask;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Created by fahime on 10/8/15.
 */
public class ProjectExplorer extends JPanel{
    private ProjectTree projectTree;

    public ProjectExplorer(Context context) {
        setLayout(new BorderLayout());
        ConfigurationTask configurationTask = ConfigurationTask.getInstance();
        DefaultMutableTreeNode top = new DefaultMutableTreeNode(new ProjectTree.RootNode("Project Explorer",configurationTask.getWorkspace()), true);
         projectTree =new ProjectTree(top,context);
        JScrollPane jScrollPane = new JScrollPane((JTree) projectTree);
        add(jScrollPane);
    }
}
