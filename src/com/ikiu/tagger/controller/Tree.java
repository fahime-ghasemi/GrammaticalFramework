package com.ikiu.tagger.controller;

/**
 * Created by Emotion on 8/24/2015.
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;


/**
 * @author Emotion
 */
class Tree extends JTree implements MouseListener {


    private JPopupMenu popupMenu;


    public Tree(DefaultMutableTreeNode newNode) {
        //Create popup menu
        super(newNode);
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




