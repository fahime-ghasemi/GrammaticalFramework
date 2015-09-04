package com.ikiu.tagger.controller;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

import javax.swing.JComponent;

/**
 * Created by fahime on 9/4/15.
 */
public class MainContent {
    public JComponent getComponent()
    {
        RSyntaxTextArea rSyntaxTextArea = new RSyntaxTextArea(35,50);
        return rSyntaxTextArea;
    }
}
