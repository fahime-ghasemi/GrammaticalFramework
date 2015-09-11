package com.ikiu.tagger.controller;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

/**
 * Created by fahime on 9/4/15.
 */
public class MainContent {
    JTextArea textArea;
    JTabbedPane jTabbedPane;
    public JComponent getComponent()
    {
        jTabbedPane = new JTabbedPane();
        return jTabbedPane;
    }

    public void setTextAreaContent(String filesystemPath) {

        try {
            JTextArea  textArea = new JTextArea();
            Font font=textArea.getFont();
            textArea.setFont(font.deriveFont(20f));
            JScrollPane scrollPane = new JScrollPane(textArea);

            int l=filesystemPath.lastIndexOf("/");
            String fileName=filesystemPath.substring(l+1);
            jTabbedPane.addTab(fileName, scrollPane);
            jTabbedPane.setTabComponentAt(jTabbedPane.getTabCount()-1,createTabHead(fileName));
            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(new File(filesystemPath)),"UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            textArea.read(bufferedReader, null);
            bufferedReader.close();
            inputStreamReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
    public JPanel createTabHead(String title)
    {
        final String st=title;
        JPanel pnlTab = new JPanel();
        pnlTab.setLayout(new BoxLayout(pnlTab,BoxLayout.LINE_AXIS));
        pnlTab.setOpaque(false);
        JButton btnClose = new JButton("x");
        JLabel lblTitle = new JLabel(title+"    ");
        btnClose.setBorderPainted(false);
        btnClose.setOpaque(false);

        btnClose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int i;
                for(i=0;i<=jTabbedPane.getTabCount()-1;i++)//To find current index of tab
                {
                    if(st.equals(jTabbedPane.getTitleAt(i)))
                        break;
                }
                jTabbedPane.removeTabAt(i);
            }
        });

        pnlTab.add(lblTitle);
        pnlTab.add(btnClose);
        return pnlTab;
    }
}
