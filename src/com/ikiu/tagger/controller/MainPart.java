package com.ikiu.tagger.controller;

/**
 * Created by Emotion on 8/22/2015.
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author Emotion
 */
public class MainPart extends JFrame {


    public static void main(String args[]){
        MainPart mainFrame=new MainPart();

        mainFrame.setLayout(new BorderLayout());

        MenuSection menuHandler = new MenuSection(mainFrame);

        JPanel mainContent=new JPanel(new BorderLayout());
        mainContent.add(new MainContent().getComponent());

        JPanel bottomBar=new JPanel(new BorderLayout());
        bottomBar.add(new BottomBar().getComponent());

        //------------------------------
        JPanel projectExplorer=new JPanel(new BorderLayout());
        DefaultMutableTreeNode top = new DefaultMutableTreeNode(new Tree.TreeNodeData("Project Explorer","root"), true);
        Tree tree=new Tree(top);
        JScrollPane jScrollPane = new JScrollPane((JTree)tree);
        jScrollPane.setSize(200,600);
        projectExplorer.add(jScrollPane);

        //-----------------------------------------------
        JSplitPane jSplitPane1=new JSplitPane(JSplitPane.VERTICAL_SPLIT,mainContent,bottomBar);
        JSplitPane jSplitPane=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,projectExplorer,jSplitPane1);

        mainFrame.setContentPane(jSplitPane);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
        mainFrame.setSize(600,700);
        mainFrame.setExtendedState(MAXIMIZED_BOTH);


    }
}

