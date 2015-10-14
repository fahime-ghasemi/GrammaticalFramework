package com.ikiu.tagger.controller;

/**
 * Created by Emotion on 8/22/2015.
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

/**
 * @author Emotion
 */
public class Context extends JFrame implements ProjectTree.TreeNode.TreeNodeListener{

    private MainContent currentPanel;
    private JSplitPane mainContent;
    private JSplitPane splitPane;
    private ProjectExplorer projectExplorer;

    public static void main(String args[]) {
        Context context = new Context();

        context.setLayout(new BorderLayout());
//
        GeneralView contentPane = new GeneralView();

        JPanel mainContentPanel=new JPanel(new BorderLayout());
        MainContent mainContent = new MainContent(context);
        mainContentPanel.add(mainContent.getComponent());
        contentPane.setMainContent(mainContentPanel);
        context.setCurrentPanel(mainContent);

        JPanel bottomPanel=new JPanel(new BorderLayout());
        bottomPanel.setSize(400, 300);
        bottomPanel.add(new BottomBar());
        contentPane.setBottomBar(bottomPanel);

        ProjectExplorer projectExplorer = new ProjectExplorer(context);
        contentPane.setProjectExplorer(projectExplorer);
        context.setContentPane(contentPane);

        context.setMenu(new MenuSection(context));
        context.pack();
        context.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        context.setVisible(true);
        context.setSize(600, 700);
        context.setExtendedState(MAXIMIZED_BOTH);


    }

    public void setMenu(JMenuBar menuBar)
    {
        setJMenuBar(menuBar);
    }

    public MainContent getCurrentPanel() {
        return this.currentPanel;
    }

    public void setCurrentPanel(MainContent currentPanel) {
        this.currentPanel = currentPanel;
    }

    @Override
    public void onRightClickListener() {

    }

    @Override
    public void treeNodeDoubleClickListener(String path) {
        getCurrentPanel().setTextAreaContent(path);

    }

    public void addEnglishTag(String source)
    {
        ((TaggerView) getContentPane()).getTaggerBottomBar().addEnglishTag(source);
    }

    public void addPersianTag(String source)
    {
        ((TaggerView) getContentPane()).getTaggerBottomBar().addPersianTag(source);
    }
}

