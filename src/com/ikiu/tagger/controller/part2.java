package com.ikiu.tagger.controller;

/**
 * Created by Emotion on 8/22/2015.
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



        import javax.swing.JFrame;
        import javax.swing.JPanel;
        import javax.swing.JScrollPane;
        import javax.swing.JTabbedPane;
        import javax.swing.JTextArea;
        import javax.swing.JTree;

/**
 *
 * @author Emotion
 */
public class part2 {

    public JTextArea treeArea;
    // private JPanel treePanel;
    private JTabbedPane treeTab;
    private JScrollPane treeScroll;
    private JTree tree;



    public part2(){

    }

    void init(){
        treeArea=new JTextArea(12,40);
        treeArea.setEditable(false);
        treeArea.add(new Tree().getComponent());
        treeScroll=new JScrollPane(treeArea);
        treeTab=new JTabbedPane();
        treeTab.addTab("Project",treeArea);


    }


    public JTabbedPane getComponent(){
        return treeTab;

    }


}

