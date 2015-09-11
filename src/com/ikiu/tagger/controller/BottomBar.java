package com.ikiu.tagger.controller;

import javax.swing.JComponent;
import javax.swing.JTabbedPane;

/**
 * Created by fahime on 9/4/15.
 */
public class BottomBar {

    JTabbedPane jTabbedPane;
    public JComponent getComponent()
    {
        jTabbedPane = new JTabbedPane();
        return jTabbedPane;
    }
}
