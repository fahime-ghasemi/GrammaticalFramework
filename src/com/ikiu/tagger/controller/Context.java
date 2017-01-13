package com.ikiu.tagger.controller;

/**
 * Created by Emotion on 8/22/2015.
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import com.ikiu.tagger.model.DatabaseManager;
import com.ikiu.tagger.model.WordsTreeManager;
import com.ikiu.tagger.util.ConfigurationTask;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JSplitPane;

/**
 * @author Emotion
 */
public class Context extends JFrame implements ProjectTree.TreeNode.TreeNodeListener {

    private MainContent currentPanel;
    private MainContent mainPanel;
    private JSplitPane mainContent;
    private JSplitPane splitPane;
    private ProjectExplorer projectExplorer;

    public static void main(String args[]) {
        WordsTreeManager wordsTreeManager = WordsTreeManager.getInstance();
        wordsTreeManager.createEnglishTree();
        wordsTreeManager.createPersianTree();

        ConfigurationTask configurationTask = ConfigurationTask.getInstance();
        if (configurationTask.getCore()==null || configurationTask.getCore().equals("") || configurationTask.getWorkspace()==null || configurationTask.getWorkspace().equals("")) {
            SettingDialog settingDialog = new SettingDialog(false);
            settingDialog.addCancelButton();
            settingDialog.addNextButton();
            settingDialog.show();
        } else {
            showGUI();
        }

//        DatabaseManager databaseManager = new DatabaseManager();
//        databaseManager.deleteTables();


    }

    public static void showGUI() {

        Context context = new Context();

        context.setLayout(new BorderLayout());
        GeneralView contentPane = new GeneralView();

        MainContent mainContent = new MainContent(context);
        contentPane.setMainContent(mainContent);
        context.mainPanel = mainContent;
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

    public MainContent getMainPanel() {
        return mainPanel;
    }

    public MainContent getCurrentPanel() {
        return this.currentPanel;
    }

    public void setCurrentPanel(MainContent currentPanel) {
        this.currentPanel = currentPanel;
    }

    @Override
    public void onRightClickListener(String action, String path) {
        if (action.equals("edit"))
            getCurrentPanel().setTextAreaContent(path);

    }

    @Override
    public void treeNodeDoubleClickListener(String path, boolean isFromTagger) {
        if (isFromTagger) {
            getCurrentPanel().showTaggerTab(path);
        } else
            getCurrentPanel().setTextAreaContent(path);

    }
}

