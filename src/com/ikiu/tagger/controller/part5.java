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
public class part5 {

    private JTextArea partD;
    private JScrollPane scrollD;

    public part5()
    {
        partD=new JTextArea(12,40);
        partD.setEditable(false);
        scrollD=new JScrollPane(partD);
    }

    public JTextArea getComponet()
    {
        return partD;
    }
}
