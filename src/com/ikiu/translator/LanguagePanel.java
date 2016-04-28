package com.ikiu.translator;

import javax.swing.*;
import java.awt.*;

/**
 * Created by fahime on 3/3/16.
 */
public class LanguagePanel extends JPanel {
    protected JTabbedPane jTabbedPane;
    protected MainContentTab mainContentTab;
    private String header;

    public LanguagePanel(String header) {
        this.jTabbedPane = new JTabbedPane();
        mainContentTab = new MainContentTab();
        this.header = header;
        this.jTabbedPane.addTab(header, mainContentTab);

        setLayout(new BorderLayout());
        add(jTabbedPane);
    }

    public void setHeader(String header) {
        this.header = header;
        this.jTabbedPane.setTitleAt(0, header);
    }

    public JTextPane getTextPane() {
        return mainContentTab.getTextPane();
    }

    public String getHeader() {
        return header;
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
