package com.ikiu.tagger.controller;

import com.ikiu.tagger.controller.Tree.TreeNodeData;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

/**
 * Created by fahime on 9/4/15.
 */
public class PopupMenuFolder extends JPopupMenu implements ActionListener {
    private JMenuItem menuItemNewFolder;
    private JMenuItem menuItemDelete;
    private JMenuItem menuItemOpenFile;
    private JTree tree;
    private TreePath path;

    public PopupMenuFolder(JTree tree) {

        this.tree = tree;

        menuItemNewFolder = new JMenuItem("New Folder");
        menuItemNewFolder.addActionListener(this);
        menuItemNewFolder.setActionCommand("newFolder");

        menuItemDelete = new JMenuItem("Delete");
        menuItemDelete.addActionListener(this);
        menuItemDelete.setActionCommand("delete");
        //---
        menuItemOpenFile = new JMenuItem("Import File(s)");
        menuItemOpenFile.addActionListener(this);
        menuItemOpenFile.setActionCommand("importFile");
        //---
        add(menuItemNewFolder);
        add(menuItemDelete);
        add(menuItemOpenFile);
        setOpaque(true);
        setLightWeightPopupEnabled(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("import")) {


        } else if (e.getActionCommand().equals("newFolder")) {

            DefaultMutableTreeNode node = (DefaultMutableTreeNode) this.tree.getSelectionPath().getLastPathComponent();
            DefaultMutableTreeNode nodeFolder = new DefaultMutableTreeNode(new TreeNodeData("New Folder","folder"), true);
            node.add(nodeFolder);

            TreePath path = new TreePath(((DefaultTreeModel)tree.getModel()).getPathToRoot(nodeFolder));
            tree.scrollPathToVisible(path);
            tree.startEditingAtPath(path);
            tree.setSelectionPath(path);

        } else if (e.getActionCommand().equals("importFile")) {

            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            jFileChooser.setFileFilter(new FileNameExtensionFilter("GF Files", "gf"));
            jFileChooser.setAcceptAllFileFilterUsed(false);
            jFileChooser.setMultiSelectionEnabled(true);
            int returnVal = jFileChooser.showOpenDialog((Component) e.getSource());
            if (returnVal == JFileChooser.APPROVE_OPTION) {

                File[] files = jFileChooser.getSelectedFiles();
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getSelectionPath().getLastPathComponent();

                for (File file:files) {

                    DefaultMutableTreeNode nodeFolder = new DefaultMutableTreeNode(new TreeNodeData(file.getName(),"file"), true);

                    node.add(nodeFolder);
                }

                ((DefaultTreeModel) this.tree.getModel()).nodeStructureChanged(node);

            }


        }
    }
}
