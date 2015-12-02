package com.ikiu.tagger.controller;

import com.ikiu.tagger.util.ConfigurationTask;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;

/**
 * Created by fahime on 9/4/15.
 */
public class PopupMenuRoot extends JPopupMenu implements ActionListener {
    private JMenuItem menuItemNewProject;
    private JTree tree;

    public PopupMenuRoot(JTree tree) {

        this.tree = tree;
        menuItemNewProject = new JMenuItem("New Project");
        menuItemNewProject.addActionListener(this);
        menuItemNewProject.setActionCommand("newProject");
        //---
        add(menuItemNewProject);
        setOpaque(true);
        setLightWeightPopupEnabled(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("newProject")) {

            final JFrame fileFrame = new JFrame();
            JButton btnSave = new JButton("Save");
            final JTextField txtProjectName = new JTextField(20);

            fileFrame.setLayout(new FlowLayout());
            JLabel fileLabel = new JLabel("Name :");
            btnSave.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

//                    File newFolder=new File((new ConfigurationTask()).getWorkspace()+txtProjectName.getText());
//                    File newsubFolder=new File((new ConfigurationTask()).getWorkspace()+txtProjectName.getText()+"/_gf");
//                    newFolder.setWritable(true);
//                    newFolder.setReadable(true);
//                    newsubFolder.setWritable(true);
//                    newsubFolder.setReadable(true);
//                    if(newFolder.mkdir() && newsubFolder.mkdir())
//                    {
//                        DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getSelectionPath().getLastPathComponent();
//
//                        DefaultMutableTreeNode nodeFolder = new DefaultMutableTreeNode(new ProjectTree.FolderNode(txtProjectName.getText(), newFolder.getPath()), true);
//                        //node.add(nodeFolder);
//                        DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
//                        model.insertNodeInto(nodeFolder, (MutableTreeNode) model.getRoot(),((DefaultMutableTreeNode)model.getRoot()).getChildCount());
//                        TreePath path = new TreePath(((DefaultTreeModel)tree.getModel()).getPathToRoot(nodeFolder));
//                        //((DefaultTreeModel)tree.getModel()).nodeStructureChanged(nodeFolder);
//                        tree.scrollPathToVisible(path);
//                        tree.setSelectionPath(path);
//
//                    }
//                    fileFrame.dispose();


                }
            });

            fileFrame.add(fileLabel);
            fileFrame.add(txtProjectName);
            fileFrame.add(btnSave);
            fileFrame.setVisible(true);
            fileFrame.setSize(500, 200);

        }
    }
}
