package com.ikiu.tagger.controller;

/**
 * Created by Emotion on 8/24/2015.
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import com.ikiu.tagger.controller.popupmenu.PopupMenuFile;
import com.ikiu.tagger.controller.popupmenu.PopupMenuFolder;
import com.ikiu.tagger.controller.popupmenu.PopupMenuTaggerFolder;
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
public class ProjectTree extends JTree implements MouseListener, ConfigurationTask.ConfigurationChangeListener {

    private TreeNode.TreeNodeListener listener;
    private ConfigurationTask configurationTask;
    FolderNode coreNode;
    DefaultMutableTreeNode coreNodeTree;
    DefaultMutableTreeNode taggerNodeTree;
    DefaultMutableTreeNode root;

    public ProjectTree(DefaultMutableTreeNode root, TreeNode.TreeNodeListener listener) {
        //Create popup menu
        super(root);
        this.root = root;
        configurationTask = ConfigurationTask.getInstance();
        configurationTask.setListener(this);
        this.listener = listener;
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
        addMouseListener(this);
        //--add core to explorer
        coreNode = new FolderNode("Core", "");
        coreNode.setListener(this.listener);
        coreNode.setIsCore(true);
        coreNodeTree = new DefaultMutableTreeNode(coreNode);
        ((DefaultTreeModel) getModel()).insertNodeInto(coreNodeTree, root, 0);
        loadCore();
    }

    private void loadCore() {

        File file = new File(configurationTask.getCore());
        File[] allFiles = file.listFiles();
        mergeSort(allFiles);
        for (int i = 0; allFiles != null && i < allFiles.length; ++i) {

            FolderNode fileNode = new FolderNode(allFiles[i].getName(), allFiles[i].getPath());
            fileNode.setListener(this.listener);
            fileNode.setIsCore(true);
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(fileNode, true);
            ((DefaultTreeModel) getModel()).insertNodeInto(node, coreNodeTree, 0);

            if (allFiles[i].isDirectory() && allFiles[i].listFiles().length > 0) {
                addNodes(allFiles[i].listFiles(), node, true);
            }

        }
    }

    public  void mergeSort(Comparable [ ] a)
    {
        Comparable[] tmp = new Comparable[a.length];
        mergeSort(a, tmp,  0,  a.length - 1);
    }


    private  void mergeSort(Comparable [ ] a, Comparable [ ] tmp, int left, int right)
    {
        if( left < right )
        {
            int center = (left + right) / 2;
            mergeSort(a, tmp, left, center);
            mergeSort(a, tmp, center + 1, right);
            merge(a, tmp, left, center + 1, right);
        }
    }


    private  void merge(Comparable[ ] a, Comparable[ ] tmp, int left, int right, int rightEnd )
    {
        int leftEnd = right - 1;
        int k = left;
        int num = rightEnd - left + 1;

        while(left <= leftEnd && right <= rightEnd)
            if(a[left].compareTo(a[right]) > 0)
                tmp[k++] = a[left++];
            else
                tmp[k++] = a[right++];

        while(left <= leftEnd)    // Copy rest of first half
            tmp[k++] = a[left++];

        while(right <= rightEnd)  // Copy rest of right half
            tmp[k++] = a[right++];

        // Copy tmp back
        for(int i = 0; i < num; i++, rightEnd--)
            a[rightEnd] = tmp[rightEnd];
    }

    private void loadTagger(DefaultMutableTreeNode root) {
        File file = new File(configurationTask.getWorkspace());
        File[] allFiles = file.listFiles();
        mergeSort(allFiles);
        for (int i = 0; allFiles != null && i < allFiles.length; ++i) {
            if (allFiles[i].isDirectory() && allFiles[i].getName().equals("Tagger")) {
                File[] allContent = allFiles[i].listFiles();
                mergeSort(allContent);
                for (int j = 0; allContent != null && j < allContent.length; ++j) {
                    if (allContent[j].isDirectory() && allContent[j].getName().equals("_gf")) {
                        TreeNode treeNode = new FolderNode(allFiles[i].getName(), allFiles[i].getPath());
                        treeNode.setListener(this.listener);
                        taggerNodeTree = new DefaultMutableTreeNode(treeNode);
                        ((DefaultTreeModel) getModel()).insertNodeInto(taggerNodeTree, root, 0);
                        addNodes(allContent, taggerNodeTree, false);
                    }
                }
                return;
            }
        }
        //---
        File newsubFolder = new File(configurationTask.getWorkspace() + "/Tagger/_gf");
        newsubFolder.setWritable(true);
        newsubFolder.setReadable(true);
        File newFolder = new File(configurationTask.getWorkspace() + "/Tagger");
        newFolder.setWritable(true);
        newFolder.setReadable(true);

        if (newFolder.mkdir() && newsubFolder.mkdir()) {

            TreeNode treeNode = new FolderNode("Tagger", configurationTask.getWorkspace() + "/Tagger");
            treeNode.setListener(this.listener);
            taggerNodeTree = new DefaultMutableTreeNode(treeNode);
//            taggerNodeTree.setAllowsChildrendren(true);
            ((DefaultTreeModel) getModel()).insertNodeInto(taggerNodeTree, root, 0);
        }

    }

    public void setListener(TreeNode.TreeNodeListener listener) {
        this.listener = listener;
    }

    private void addNodes(File[] content, DefaultMutableTreeNode node, boolean isCore) {
        mergeSort(content);
        for (int i = 0; content != null && i < content.length; ++i) {
            if (!content[i].getName().equals("_gf")) {
                TreeNode treeNode = null;
                if (content[i].getName().contains(".gf") || content[i].getName().contains(".txt"))
                    treeNode = new FileNode(content[i].getName(), content[i].getPath());
                else if(!content[i].isHidden()){
                    treeNode = new FolderNode(content[i].getName(), content[i].getPath());
                }
                if(treeNode!=null) {
                    if (isCore)
                        treeNode.setIsCore(true);

                    treeNode.setListener(this.listener);
                    DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(treeNode);
//                newNode.setAllowsChildren(true);
                    if (content[i].listFiles() != null)
                        addNodes(content[i].listFiles(), newNode, isCore);
                    ((DefaultTreeModel) getModel()).insertNodeInto(newNode, node, 0);
                }
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


    }

    private void doDoubleClick() {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) this.getSelectionPath().getLastPathComponent();
        if (((node.getUserObject())) instanceof FileNode && ((TreeNode) node.getUserObject()).isCore()) {
            if (((TreeNode) (node.getUserObject())).getListener() != null)
                ((TreeNode) (node.getUserObject())).getListener().treeNodeDoubleClickListener(((FileNode) node.getUserObject()).getFilesystemPath(), false);
        } else if (((node.getUserObject())) instanceof FileNode && !((TreeNode) node.getUserObject()).isCore()) {
            if (((TreeNode) (node.getUserObject())).getListener() != null)
                ((TreeNode) (node.getUserObject())).getListener().treeNodeDoubleClickListener(((FileNode) node.getUserObject()).getFilesystemPath(), true);
        }

    }

    private void showPopup(MouseEvent e) {
        JPopupMenu popupMenu = null;
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) this.getSelectionPath().getLastPathComponent();
        TreeNode treeNode = (TreeNode) node.getUserObject();
        if (treeNode instanceof FolderNode /*&& !treeNode.isCore()*/) {
            if (treeNode.getName().equals("Tagger") || treeNode.getName().equals("Core"))
                popupMenu = new PopupMenuTaggerFolder(this);
            else
                popupMenu = new PopupMenuFolder(this);
        } else if (((TreeNode) (node.getUserObject())) instanceof RootNode) {
//            popupMenu = new PopupMenuRoot(this);
        } else {
//            if (!treeNode.isCore())
                popupMenu = new PopupMenuFile(this);
        }
        //----
        if (popupMenu != null)
            popupMenu.show((JComponent) e.getSource(), e.getX(), e.getY());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton()==MouseEvent.BUTTON3) {

            setSelectionPath(getPathForLocation(e.getX(), e.getY()));
            showPopup(e);

        } else if (e.getClickCount() == 2)
            doDoubleClick();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    private void DoubleClickFile() {

    }

    @Override
    public void onCoreChangeListener() {

        ((DefaultTreeModel)getModel()).removeNodeFromParent(coreNodeTree);
        loadCore();
    }

    @Override
    public void onWorkspaceChangeListener() {
        ((DefaultTreeModel)getModel()).removeNodeFromParent(taggerNodeTree);
        loadTagger(root);
    }

    public static class TreeNode {
        protected String name;
        protected String filesystemPath;

        private boolean isCore;

        public boolean isCore() {
            return isCore;
        }

        public void setIsCore(boolean isCore) {
            this.isCore = isCore;
        }

        public interface TreeNodeListener {
            public void onRightClickListener(String action, String path);

            public void treeNodeDoubleClickListener(String path, boolean isFromTagger);
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

        public void setListener(TreeNodeListener listener) {
            this.listener = listener;
        }

        public TreeNodeListener getListener() {
            return listener;
        }

        public String getFilesystemPath() {
            return filesystemPath;
        }

        public String getName() {
            return name;
        }
    }

    public static class RootNode extends TreeNode {

        public RootNode(String name, String filesystemPath) {
            super(name, filesystemPath);
        }
    }

    public static class FolderNode extends TreeNode {

        public FolderNode(String name, String filesystemPath) {
            super(name, filesystemPath);
        }
    }

    public static class FileNode extends TreeNode {
        public FileNode(String name, String filesystemPath) {
            super(name, filesystemPath);
        }
    }
}




