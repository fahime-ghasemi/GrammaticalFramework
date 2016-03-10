package com.ikiu.tagger.controller.popupmenu;

import com.ikiu.tagger.controller.ProjectTree;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Created by fahime on 9/4/15.
 */
public class PopupMenuFile extends JPopupMenu implements ActionListener{
    private JMenuItem menuItemRename;
    private JMenuItem menuItemEdit;
    private JTree tree;

    public PopupMenuFile(JTree tree) {

        this.tree =tree;
        menuItemRename = new JMenuItem("Rename");
        menuItemRename.addActionListener(this);
        menuItemRename.setActionCommand("rename");
        //---
        menuItemEdit = new JMenuItem("Edit");
        menuItemEdit.addActionListener(this);
        menuItemEdit.setActionCommand("edit");
        //---
        add(menuItemRename);
        add(menuItemEdit);
        setOpaque(true);
        setLightWeightPopupEnabled(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("edit")) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getSelectionPath().getLastPathComponent();
            if (((ProjectTree.TreeNode) (node.getUserObject())).getListener() != null)
                ((ProjectTree.TreeNode) (node.getUserObject())).getListener().onRightClickListener("edit",
                        ((ProjectTree.TreeNode)node.getUserObject()).getFilesystemPath());

        }
    }
}
