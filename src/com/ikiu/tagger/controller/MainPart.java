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

/**
 * @author Emotion
 */
public class MainPart extends JFrame {

    private MainContent currentPanel;

    public static void main(String args[]) {
        MainPart mainFrame = new MainPart();

        mainFrame.setLayout(new BorderLayout());
//
        MenuSection menuHandler = new MenuSection(mainFrame);
        mainFrame.setContentPane(new GeneralView(mainFrame));

        mainFrame.pack();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
        mainFrame.setSize(600, 700);
        mainFrame.setExtendedState(MAXIMIZED_BOTH);


    }

    public MainContent getCurrentPanel() {
        return this.currentPanel;
    }

    public void setCurrentPanel(MainContent currentPanel) {
        this.currentPanel = currentPanel;
    }
}

