package com.ikiu.tagger.controller;

/**
 * Created by Emotion on 8/24/2015.
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import com.ikiu.tagger.util.ConfigurationTask;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.JComponent;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;


/**
 * @author Emotion
 */
class Tree extends JTree implements MouseListener {


    private JPopupMenu popupMenu;


    public Tree(DefaultMutableTreeNode root) {
        //Create popup menu
        super(root);
        File file = new File((new ConfigurationTask()).getWorkspace());
        File[] allFiles = file.listFiles();
        for (int i = 0; i < allFiles.length; ++i) {
            if (allFiles[i].isDirectory()) {
                File[] allContent = allFiles[i].listFiles();
                for (int j = 0; j < allContent.length; ++j) {
                    if (allContent[j].isDirectory() && allContent[j].getName().equals(".gf")) {
                        DefaultMutableTreeNode node = new DefaultMutableTreeNode(new Tree.TreeNodeData(allFiles[i].getName(), "folder"), true);
                        root.add(node);
                    }
                }
            }
        }
        //---
        DefaultMutableTreeNode currentNode = root.getNextNode();
        while (currentNode != null) {

            if (currentNode.getLevel() == 1) {
                expandPath(new TreePath(currentNode.getPath()));
                TreePath path = new TreePath(((DefaultTreeModel) getModel()).getPathToRoot(currentNode));
                scrollPathToVisible(path);
            }
            currentNode = currentNode.getNextSibling();
        }

        addMouseListener(this);
        setEditable(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

        if (e.isPopupTrigger()) {

            showPopup(e);

        }
    }

    private void showPopup(MouseEvent e) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) this.getSelectionPath().getLastPathComponent();
        if (((TreeNodeData) (node.getUserObject())).getType().equals("folder")) {
            popupMenu = new PopupMenuFolder(this);
        } else if (((TreeNodeData) (node.getUserObject())).getType().equals("root")) {
            popupMenu = new PopupMenuRoot(this);
        } else {
            popupMenu = new PopupMenuFile(this);
        }
        //----
        popupMenu.show((JComponent) e.getSource(), e.getX(), e.getY());
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    private void DoubleClickFile() {

    }

    public static class TreeNodeData {
        private String name;
        private String type;

        public TreeNodeData(String name, String type) {
            this.name = name;
            this.type = type;
        }

        @Override
        public String toString() {
            return name;
        }

        public String getType() {
            return type;
        }
    }
}




