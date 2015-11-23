package com.ikiu.tagger.controller;

/**
 * Created by Emotion on 8/22/2015.
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import com.ikiu.tagger.model.DatabaseManager;

import java.awt.BorderLayout;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JSplitPane;

/**
 * @author Emotion
 */
public class Context extends JFrame implements ProjectTree.TreeNode.TreeNodeListener {

    private MainContent currentPanel;
    private JSplitPane mainContent;
    private JSplitPane splitPane;
    private ProjectExplorer projectExplorer;

    public static void main(String args[]) {
        Context context = new Context();

        context.setLayout(new BorderLayout());
        GeneralView contentPane = new GeneralView();

        MainContent mainContent = new MainContent(context);
        contentPane.setMainContent(mainContent);
        context.setCurrentPanel(mainContent);

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

    public void setMenu(JMenuBar menuBar) {
        setJMenuBar(menuBar);
    }

    public MainContent getCurrentPanel() {
        return this.currentPanel;
    }

    public void setCurrentPanel(MainContent currentPanel) {
        this.currentPanel = currentPanel;
    }

    public void refreshTags(Vector<DatabaseManager.TokenTableRow> tokenTableRows) {
//        ((TaggerView)getContentPane()).getTaggerBottomBar().
    }


    @Override
    public void onRightClickListener() {

    }

    @Override
    public void treeNodeDoubleClickListener(String path, boolean isFromTagger) {
        if (isFromTagger) {
            getCurrentPanel().showTaggerTab(path);
//            if (getCurrentPanel() instanceof EnglishPanel)
//                ((EnglishPanel) getCurrentPanel()).setTextPaneContent(path, DatabaseManager.ENGLISH);
//            else if (getCurrentPanel() instanceof PersianPanel)
//                ((PersianPanel) getCurrentPanel()).setTextPaneContent(path, DatabaseManager.PERSIAN);
        } else
            getCurrentPanel().setTextAreaContent(path);

    }

    public int addEnglishTag(String source) {
        return ((TaggerView) getContentPane()).getTaggerBottomBar().addEnglishTag(source);
    }

    public int addPersianTag(String source) {
        return ((TaggerView) getContentPane()).getTaggerBottomBar().addPersianTag(source);
    }
}

