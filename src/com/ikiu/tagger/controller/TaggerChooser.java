package com.ikiu.tagger.controller;

import com.ikiu.tagger.util.ConfigurationTask;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.io.File;

/**
 * Created by fahime on 11/23/15.
 */
public class TaggerChooser extends JTree {
    ConfigurationTask configurationTask;
    public TaggerChooser(DefaultMutableTreeNode root) {
        super(root);
        configurationTask = ConfigurationTask.getInstance();
        loadTagger(root);
        //---expand level one
        DefaultMutableTreeNode currentNode = root.getNextNode();
        while (currentNode != null) {

            if (currentNode.getLevel() == 1) {
                expandPath(new TreePath(currentNode.getPath()));
                TreePath path = new TreePath(((DefaultTreeModel) getModel()).getPathToRoot(currentNode));
                scrollPathToVisible(path);
            }
            currentNode = currentNode.getNextSibling();
        }
        //--add core to explorer
        loadCore(root);
    }

    private void loadTagger(DefaultMutableTreeNode root) {
        File file = new File(configurationTask.getWorkspace());
        File[] allFiles = file.listFiles();

        for (int i = 0; allFiles != null && i < allFiles.length; ++i) {
            if (allFiles[i].isDirectory() && allFiles[i].getName().equals("Tagger")) {
                File[] allContent = allFiles[i].listFiles();
                for (int j = 0; allContent != null && j < allContent.length; ++j) {
                    if (allContent[j].isDirectory() && allContent[j].getName().equals("_gf")) {
                        ProjectTree.TreeNode treeNode = new ProjectTree.FolderNode(allFiles[i].getName(), allFiles[i].getPath());
                        DefaultMutableTreeNode node = new DefaultMutableTreeNode(treeNode, true);
                        ((DefaultTreeModel) getModel()).insertNodeInto(node, root, 0);
                        addNodes(allContent, node, false);
                    }
                }
                return;
            }
        }
        //---
    }
    private void loadCore(DefaultMutableTreeNode root) {
        ProjectTree.FolderNode coreNode = new ProjectTree.FolderNode("Core", "");

        DefaultMutableTreeNode coreNodeTree = new DefaultMutableTreeNode(coreNode, true);
        ((DefaultTreeModel) getModel()).insertNodeInto(coreNodeTree, root, 0);

        File file = new File(configurationTask.getCore());
        File[] allFiles = file.listFiles();

        for (int i = 0; allFiles != null && i < allFiles.length; ++i) {

            ProjectTree.FolderNode fileNode = new ProjectTree.FolderNode(allFiles[i].getName(), allFiles[i].getPath());
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(fileNode, true);
            ((DefaultTreeModel) getModel()).insertNodeInto(node, coreNodeTree, 0);

            if (allFiles[i].isDirectory() && allFiles[i].listFiles().length > 0) {
                addNodes(allFiles[i].listFiles(), node, true);
            }

        }
    }
    private void addNodes(File[] content, DefaultMutableTreeNode node, boolean isCore) {
        for (int i = 0; content != null && i < content.length; ++i) {
            if (!content[i].getName().equals("_gf")) {
                ProjectTree.TreeNode treeNode = null;
                if (content[i].getName().contains(".gf") || content[i].getName().contains(".txt"))
                    treeNode = new ProjectTree.FileNode(content[i].getName(), content[i].getPath());
                else {
                    treeNode = new ProjectTree.FolderNode(content[i].getName(), content[i].getPath());
                }
                if (isCore)
                    treeNode.setIsCore(true);

                DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(treeNode, true);
                if (content[i].listFiles() != null)
                    addNodes(content[i].listFiles(), newNode, isCore);
                ((DefaultTreeModel) getModel()).insertNodeInto(newNode, node, 0);
            }
        }
    }
}
