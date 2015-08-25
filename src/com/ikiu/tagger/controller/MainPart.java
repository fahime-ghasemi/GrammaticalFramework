package com.ikiu.tagger.controller;

/**
 * Created by Emotion on 8/22/2015.
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.*;
import javax.swing.*;

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
        mainContent.add(new JButton("mainContent"));

        JPanel bottomBar=new JPanel(new BorderLayout());
        bottomBar.add(new JButton("bottomBar"));

        JPanel projectExplorer=new JPanel(new BorderLayout());
        projectExplorer.add(new JButton("projectExplorer"));

        JSplitPane jSplitPane1=new JSplitPane(JSplitPane.VERTICAL_SPLIT,mainContent,bottomBar);
        JSplitPane jSplitPane=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,projectExplorer,jSplitPane1);

        mainFrame.setContentPane(jSplitPane);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
        mainFrame.setSize(600, 600);


    }
}

