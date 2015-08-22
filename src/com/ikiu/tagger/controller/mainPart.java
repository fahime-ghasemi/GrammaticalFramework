package com.ikiu.tagger.controller;

/**
 * Created by Emotion on 8/22/2015.
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


        import java.awt.Component;
        import java.awt.GridBagConstraints;
        import java.awt.GridBagLayout;
        import javax.swing.JComponent;
        import javax.swing.JFrame;
        import javax.swing.JPanel;

/**
 *
 * @author Emotion
 */
public class mainPart extends JFrame{
    private  GridBagConstraints constriant=new GridBagConstraints();
    //JFrame mainFrame = new JFrame();constriant

    void addGBL(Component component , int x ,int y){
        constriant.gridx=x;
        constriant.gridy=y;
        add(component);


    }


    public static void main(String args[]){
        mainPart obj=new mainPart();
        JFrame mainFrame = new JFrame();
        mainFrame.setLayout(new GridBagLayout());
        //constriant.weightx=1.0;
        //constriant.weighty=2.0;
        int x,y;
        part1 p1 = new part1();
        Component pt1= p1.getComponent();
        obj.addGBL(pt1,x=0, y=0);
        JPanel mainPanel=new JPanel();
        obj.addGBL(mainPanel, x=1, y=0);



        mainFrame.setVisible(true);
        mainFrame.setSize(600,600);


    }
}

