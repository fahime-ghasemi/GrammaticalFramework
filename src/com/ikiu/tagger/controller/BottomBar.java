package com.ikiu.tagger.controller;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

import javax.swing.JComponent;

/**
 * Created by fahime on 9/4/15.
 */
public class BottomBar {

    public JComponent getComponent()
    {
        RSyntaxTextArea rSyntaxTextArea = new RSyntaxTextArea(3,50);
        return rSyntaxTextArea;
    }
}
