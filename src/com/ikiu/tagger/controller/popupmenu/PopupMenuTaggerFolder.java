package com.ikiu.tagger.controller.popupmenu;


import com.ikiu.tagger.controller.ProjectTree;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

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
public class PopupMenuTaggerFolder extends JPopupMenu implements ActionListener {
    private JMenuItem menuItemNewFolder;
    private JMenuItem menuItemOpenFile;
    private JTree tree;
    private TreePath path;

    public PopupMenuTaggerFolder(JTree tree) {

        this.tree = tree;

        menuItemNewFolder = new JMenuItem("New Folder");
        menuItemNewFolder.addActionListener(this);
        menuItemNewFolder.setActionCommand("newFolder");

        //---
        menuItemOpenFile = new JMenuItem("Import File(s)");
        menuItemOpenFile.addActionListener(this);
        menuItemOpenFile.setActionCommand("importFile");
        //---
        add(menuItemNewFolder);
        add(menuItemOpenFile);
        setOpaque(true);
        setLightWeightPopupEnabled(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("newFolder")) {

            final JFrame fileFrame = new JFrame();
            JButton btnSave = new JButton("Save");
            final JTextField txtProjectName = new JTextField(20);

            fileFrame.setLayout(new FlowLayout());
            JLabel fileLabel = new JLabel("Name :");
            btnSave.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getSelectionPath().getLastPathComponent();

                    File newFolder = new File(((ProjectTree.FolderNode) node.getUserObject()).getFilesystemPath() + "/" + txtProjectName.getText());
                    newFolder.setWritable(true);
                    newFolder.setReadable(true);

                    if (newFolder.mkdir()) {
                        DefaultMutableTreeNode nodeFolder = new DefaultMutableTreeNode(new ProjectTree.FolderNode(txtProjectName.getText(), newFolder.getPath()), true);
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
            jFileChooser.setFileFilter(new FileNameExtensionFilter("GF Files", "gf", "txt"));
            jFileChooser.setAcceptAllFileFilterUsed(false);
            jFileChooser.setMultiSelectionEnabled(true);
            int returnVal = jFileChooser.showOpenDialog((Component) e.getSource());
            if (returnVal == JFileChooser.APPROVE_OPTION) {

                File[] files = jFileChooser.getSelectedFiles();
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getSelectionPath().getLastPathComponent();

                for (File file : files) {
                    Path source = Paths.get(file.getPath());
                    Path desc = Paths.get(((ProjectTree.FolderNode) node.getUserObject()).getFilesystemPath() + "/" + file.getName());
                    try {
                        Files.copy(source, desc, StandardCopyOption.REPLACE_EXISTING);
                        ProjectTree.FileNode fileNode = new ProjectTree.FileNode(file.getName(), file.getPath());
                        fileNode.setIsCore(false);
                        DefaultMutableTreeNode nodeFolder = new DefaultMutableTreeNode(fileNode, true);
                        ((DefaultTreeModel) tree.getModel()).insertNodeInto(nodeFolder, node, 0);
                        tree.scrollPathToVisible(new TreePath(nodeFolder.getPath()));

                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }


                }


            }

        }
    }
}
