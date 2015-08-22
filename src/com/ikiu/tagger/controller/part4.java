package com.ikiu.tagger.controller;

/**
 * Created by Emotion on 8/22/2015.
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



        import javax.swing.JScrollPane;
        import javax.swing.JTextArea;


/**
 *
 * @author Emotion
 */
public class part4 {

    private JTextArea partC;
    private JScrollPane scrollC;

    public part4(){
        partC=new JTextArea(12,40);
        partC.setEditable(false);
        scrollC=new JScrollPane(partC);
    }

    public JTextArea getComponet()
    {
        return partC;
    }

}

