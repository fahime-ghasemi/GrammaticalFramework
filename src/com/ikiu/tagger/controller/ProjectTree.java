package com.ikiu.tagger.controller;

/**
 * Created by Emotion on 8/24/2015.
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.tree.*;


/**
 * @author Emotion
 */
class ProjectTree extends JTree implements ActionListener {


    private DefaultMutableTreeNode dmtn, newNode;
    //dmtn is abbreviation for DefaultMutableTreeNode
    private TreePath path;
    private JPopupMenu popup;
    private JMenuItem fileItem, folderItem;
    private JButton fileBtn;
    private JTextField fileTxt;


    public ProjectTree(DefaultMutableTreeNode newNode) {
        //Create popup menu
        super(newNode);
        popup = new JPopupMenu();
        fileItem = new JMenuItem("New File");
        fileItem.addActionListener(this);
        fileItem.setActionCommand("insertFile");
        popup.add(fileItem);
        folderItem = new JMenuItem("New Folder");
        folderItem.addActionListener(this);
        folderItem.setActionCommand("insertFolder");
        popup.add(folderItem);
        popup.setOpaque(true);
        popup.setLightWeightPopupEnabled(true);


        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

                if (e.isPopupTrigger()) {
                    popup.show((JComponent) e.getSource(), e.getX(), e.getY());
                }

            }

            @Override
            public void mouseReleased(MouseEvent e) {

                if (e.isPopupTrigger()) {
                    popup.show((JComponent) e.getSource(), e.getX(), e.getY());
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

    }

    public void actionPerformed(ActionEvent ea) {
        JFrame fileFrame = new JFrame();
        path = this.getSelectionPath();
        //TreePath class is used for indicates all node's path in uniqe array that the first element of that is root
        //grtSelectionPath is one of methods in treePath class that returns the selecting node's path

        if (ea.getActionCommand().equals("insertFile")) {

            fileFrame.setLayout(new FlowLayout());
            JLabel fileLabel = new JLabel("Enter Name : ");
            fileTxt = new JTextField(20);
            fileBtn = new JButton("OK");
            fileBtn.setActionCommand("pressed ok");
            fileBtn.addActionListener(this);


            fileFrame.add(fileLabel);
            fileFrame.add(fileTxt);
            fileFrame.add(fileBtn);

            fileFrame.setVisible(true);
            fileFrame.setSize(500, 200);


        }

        if (ea.getActionCommand().equals("insertFolder"))

        {
            dmtn = (DefaultMutableTreeNode) path.getLastPathComponent();
            //with this code , we create a empty node that we will add it a node in three line next
            String fieldText = "New Folder";

            DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(fieldText, true);

            dmtn.add(newNode);
            ((DefaultTreeModel) this.getModel()).nodeStructureChanged((TreeNode) dmtn);
            // fileFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        }
    }


}




