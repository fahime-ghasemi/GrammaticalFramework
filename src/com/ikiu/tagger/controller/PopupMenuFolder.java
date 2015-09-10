package com.ikiu.tagger.controller;

import com.ikiu.tagger.controller.Tree.TreeNodeData;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
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
        if (e.getActionCommand().equals("newFolder")) {

            JFrame fileFrame = new JFrame();
            JButton btnSave = new JButton("Save");
            JTextField txtProjectName = new JTextField(20);

            fileFrame.setLayout(new FlowLayout());
            JLabel fileLabel = new JLabel("Name :");
            btnSave.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getSelectionPath().getLastPathComponent();

                    File newFolder = new File(((TreeNodeData) node.getUserObject()).getFilesystemPath() + "/" + txtProjectName.getText());
                    newFolder.setWritable(true);
                    newFolder.setReadable(true);

                    if (newFolder.mkdir()) {
                        DefaultMutableTreeNode nodeFolder = new DefaultMutableTreeNode(new Tree.TreeNodeData(txtProjectName.getText(), "folder", newFolder.getPath()), true);
                        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
                        model.insertNodeInto(nodeFolder, node, node.getChildCount());
                        TreePath path = new TreePath(((DefaultTreeModel) tree.getModel()).getPathToRoot(nodeFolder));
                        //((DefaultTreeModel)tree.getModel()).nodeStructureChanged(nodeFolder);
                        tree.scrollPathToVisible(path);
                        tree.setSelectionPath(path);

                    }
                    fileFrame.dispose();


                }
            });

            fileFrame.add(fileLabel);
            fileFrame.add(txtProjectName);
            fileFrame.add(btnSave);
            fileFrame.setVisible(true);
            fileFrame.setSize(500, 200);


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

                for (File file : files) {

                    DefaultMutableTreeNode nodeFolder = new DefaultMutableTreeNode(new TreeNodeData(file.getName(), "file", ""), true);

                    node.add(nodeFolder);
                }

                ((DefaultTreeModel) this.tree.getModel()).nodeStructureChanged(node);

            }


        } else if (e.getActionCommand().equals("delete")) {


            int select = JOptionPane.showConfirmDialog(null, "Are you sure?", "WARNING", JOptionPane.YES_NO_OPTION);
            if (select == JOptionPane.YES_OPTION) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getSelectionPath().getLastPathComponent();
                File file = new File(((TreeNodeData) node.getUserObject()).getFilesystemPath());
                delete(file);
                ((DefaultTreeModel) tree.getModel()).removeNodeFromParent(node);

            }
        }
    }

    private void delete(File file) {
        if (file.length() == 0)
            file.delete();
        else {
            for (int i = 0; i < file.listFiles().length; ++i) {
                delete(file.listFiles()[i]);
            }
            file.delete();
        }
    }
}
