package com.ikiu.translator;

import javax.swing.*;
import java.awt.*;

/**
 * Created by fahime on 3/3/16.
 */
public class MainContentTab extends JScrollPane {
    protected JTextPane textPane;

    public MainContentTab() {
        super();

        this.textPane = new JTextPane();
        Font font = textPane.getFont();
        textPane.setFont(font.deriveFont(20f));
        getViewport().add(textPane);

    }

    public String getContent() {
        return textPane.getText();
    }

    public void setContent(String content) {
        textPane.setText(content);
    }

    public JTextPane getTextPane() {
        return textPane;
    }

    public void setEnabled(boolean enabled) {
        if (!enabled)
            textPane.setBackground(Color.LIGHT_GRAY);
        else
            textPane.setBackground(Color.WHITE);

        textPane.setEnabled(enabled);
    }
}
