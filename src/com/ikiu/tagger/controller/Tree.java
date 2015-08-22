package com.ikiu.tagger.controller;

/**
 * Created by Emotion on 8/22/2015.
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//package controller;

        import java.awt.*;

        import java.awt.event.*;
        import javax.swing.*;
        import javax.swing.event.TreeSelectionEvent;
        import javax.swing.event.TreeSelectionListener;
        import javax.swing.tree.*;



public class Tree extends JFrame implements TreeSelectionListener , ActionListener{

    private JTree tree;
    private DefaultMutableTreeNode top;
    private JPopupMenu pop;
    private JMenuItem popItem;
    private JButton popbtn;
    private JTextField popTxt;

    public Tree(){

        createTree();
    }

    public void createTree(){

        top=new DefaultMutableTreeNode("Project");
        tree=new JTree(top);


        pop=new JPopupMenu();
        popItem=new JMenuItem("New");
        popItem.addActionListener(this);
        pop.add(popItem);
        popbtn=new JButton("OK");
        popbtn.addActionListener(this);

        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.addTreeSelectionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource()==popItem)
        {
            JPanel popPanel=new JPanel(new FlowLayout());

            JLabel popLabel=new JLabel("Enter Name : ");
            popPanel.add(popLabel);
            popPanel.add(popTxt);
            popPanel.add(popbtn);
        }
        else if(e.getSource()==popbtn)
        {
            String fieldText=popTxt. getText();
            DefaultMutableTreeNode NewNode=new DefaultMutableTreeNode(fieldText);
            top.add(NewNode);
        }

    }
    @Override
    public void valueChanged(TreeSelectionEvent e) {
        //   if(top.){}
    }

    public JTree getComponent(){
        return tree;
    }



}
