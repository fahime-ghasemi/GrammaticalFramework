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
class ProjectTree extends JTree implements MouseListener {

    private JPopupMenu popupMenu;
    private TreeNode.TreeNodeListener listener;

    public ProjectTree(DefaultMutableTreeNode root,TreeNode.TreeNodeListener listener) {
        //Create popup menu
        super(root);
        this.listener = listener;
        File file = new File((new ConfigurationTask()).getWorkspace());
        File[] allFiles = file.listFiles();

        for (int i = 0; allFiles != null && i < allFiles.length; ++i) {
            if (allFiles[i].isDirectory()) {
                File[] allContent = allFiles[i].listFiles();
                for (int j = 0; allContent != null && j < allContent.length; ++j) {
                    if (allContent[j].isDirectory() && allContent[j].getName().equals("_gf")) {
                        TreeNode treeNode = new ProjectTree.FolderNode(allFiles[i].getName(), allFiles[i].getPath());
                        treeNode.setListener(this.listener);
                        DefaultMutableTreeNode node = new DefaultMutableTreeNode(treeNode, true);
                        ((DefaultTreeModel) getModel()).insertNodeInto(node, root, 0);
                        addNodes(allContent, node);
                    }
                }
            }
        }
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
        addMouseListener(this);
    }

    public void setListener(TreeNode.TreeNodeListener listener)
    {
        this.listener = listener;
    }
    private void addNodes(File[] content, DefaultMutableTreeNode node) {
        for (int i = 0; content != null && i < content.length; ++i) {
            if(!content[i].getName().equals("_gf")) {
                TreeNode treeNode=null;
                if(content[i].getName().contains(".gf") || content[i].getName().contains(".txt") )
                    treeNode = new FileNode(content[i].getName(),content[i].getPath());
                else
                    treeNode = new FolderNode(content[i].getName(),content[i].getPath());

                treeNode.setListener(this.listener);
                DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(treeNode, true);
                if (content[i].listFiles() != null)
                    addNodes(content[i].listFiles(), newNode);
                ((DefaultTreeModel) getModel()).insertNodeInto(newNode, node, 0);
            }
        }
    }

    @Override
    public boolean isPathEditable(TreePath path) {
        if (((DefaultMutableTreeNode) path.getLastPathComponent()).isRoot())
            return false;
        return super.isPathEditable(path);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

        if (e.isPopupTrigger()) {

            setSelectionPath(getPathForLocation(e.getX(), e.getY()));
            showPopup(e);

        }
        else if(e.getClickCount()==2)
            doDoubleClick();
    }

    private void doDoubleClick() {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) this.getSelectionPath().getLastPathComponent();
        if (((TreeNode) (node.getUserObject())) instanceof FileNode) {
            if(((TreeNode) (node.getUserObject())).getListener()!=null)
                ((TreeNode) (node.getUserObject())).getListener().treeNodeDoubleClickListener(((FileNode) node.getUserObject()).getFilesystemPath());
        }
    }

    private void showPopup(MouseEvent e) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) this.getSelectionPath().getLastPathComponent();
        if (((TreeNode) (node.getUserObject())) instanceof FolderNode) {
            popupMenu = new PopupMenuFolder(this);
        } else if (((TreeNode) (node.getUserObject())) instanceof RootNode) {
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

    public static class TreeNode
    {
        protected String name;
        protected String filesystemPath;
        public interface TreeNodeListener
        {
            public void onRightClickListener();
            public void treeNodeDoubleClickListener(String path);
        }

        public TreeNode(String name, String filesystemPath) {
            this.filesystemPath = filesystemPath;
            this.name = name;
        }

        protected TreeNodeListener listener;
        @Override
        public String toString() {
            return name;
        }

        public void setListener(TreeNodeListener listener)
        {
            this.listener = listener;
        }

        public TreeNodeListener getListener() {
            return listener;
        }
        public String getFilesystemPath() {
            return filesystemPath;
        }
    }
    public static class RootNode extends TreeNode{

        public RootNode(String name, String filesystemPath) {
            super(name, filesystemPath);
        }
    }

    public static class FolderNode extends TreeNode
    {
        public FolderNode(String name, String filesystemPath) {
            super(name,filesystemPath);
        }
    }

    public static class FileNode extends TreeNode
    {
        public FileNode(String name, String filesystemPath) {
            super(name, filesystemPath);
        }
    }
}




