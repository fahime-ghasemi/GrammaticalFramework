package com.ikiu.tagger.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 * Created by fahime on 9/4/15.
 */
public class MainContent {

    JTabbedPane jTabbedPane;

    public JComponent getComponent() {
        jTabbedPane = new JTabbedPane();
        return jTabbedPane;
    }

    public void setTextAreaContent(String filesystemPath) {

        int l = filesystemPath.lastIndexOf("/");
        String fileName = filesystemPath.substring(l + 1);

        jTabbedPane.addTab(fileName, new MainContentTab(filesystemPath));
        jTabbedPane.setTabComponentAt(jTabbedPane.getTabCount() - 1, createTabHead(fileName));
        jTabbedPane.setSelectedIndex(jTabbedPane.getTabCount() - 1);
    }

    public JPanel createTabHead(String title) {
        final String st = title;
        JPanel pnlTab = new JPanel();
        pnlTab.setLayout(new BoxLayout(pnlTab, BoxLayout.LINE_AXIS));
        pnlTab.setOpaque(false);
        JButton btnClose = new JButton("x");
        JLabel lblTitle = new JLabel(title + "    ");
        btnClose.setBorderPainted(false);
        btnClose.setOpaque(false);

        btnClose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                int i;
                for (i = 0; i <= jTabbedPane.getTabCount() - 1; i++)//To find current index of tab
                {
                    if (st.equals(jTabbedPane.getTitleAt(i)))
                        break;
                }
                if (((MainContentTab) jTabbedPane.getComponentAt(i)).isChanged()) {
                    ((MainContentTab) jTabbedPane.getComponentAt(i)).saveChanges();
                }
                jTabbedPane.removeTabAt(i);
            }
        });

        pnlTab.add(lblTitle);
        pnlTab.add(btnClose);
        return pnlTab;
    }
}
