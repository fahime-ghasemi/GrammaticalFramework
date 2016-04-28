package com.ikiu.translator;

import javax.swing.*;
import java.awt.*;

/**
 * Created by fahime on 3/3/16.
 */
public class PersianPanel extends JPanel {
    protected JTabbedPane jTabbedPane;
    protected MainContentTab mainContentTab;

    public PersianPanel() {
        this.jTabbedPane = new JTabbedPane();
        mainContentTab = new MainContentTab();
        this.jTabbedPane.addTab("Persian", mainContentTab);

        setLayout(new BorderLayout());
        add(jTabbedPane);
    }

    public String getText() {
        return mainContentTab.getContent();
    }

    public void setText(String text) {
        mainContentTab.setContent(text);
    }

    public void setEnabled(boolean enabled) {
        mainContentTab.setEnabled(enabled);
    }
}
